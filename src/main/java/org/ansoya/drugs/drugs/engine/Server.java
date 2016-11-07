package org.ansoya.drugs.drugs.engine;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.ansoya.drugs.drugs.dal.UserDao;
import org.ansoya.drugs.drugs.entity.RequestCmd;
import org.ansoya.drugs.drugs.entity.Result;
import org.ansoya.drugs.drugs.entity.Results;
import org.ansoya.drugs.drugs.entity.User;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

@Slf4j
public class Server extends Thread {

	private UserDao userDao;
	private Integer port;

	public Server(UserDao userDao, Integer port) {
		this.userDao = userDao;
		this.port = port;
	}


	private Result<Boolean> doSlogin(String account, String pwd, Socket socket) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(account));
		Preconditions.checkArgument(!Strings.isNullOrEmpty(pwd));
		Preconditions.checkArgument(null != socket);

		User user = userDao.findByAccount(account);
		if (null == user) {
			return Results.newFailedResult("登录失败,不存在该用户");
		}
		if (!pwd.equals(user.getPwd())) {
			return Results.newFailedResult("登录失败,用户名密码错误");
		}
		if (new Date().after(user.getExpirationTime())) {
			return Results.newFailedResult("登录失败,该用户VIP已经过期,请手机端登录续费");
		}
		FormClient formClient = new FormClient(user.getUid(), socket);
		FormClientHolder.addFormClient(formClient);
		return Results.newSuccessResult("登录成功");
	}

	@Override
	public void run() {
		Result<Boolean> result = Results.newFailedResult("操作失败");
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			log.error("监听线程启动异常" + e.getMessage(), e);
			throw new RuntimeException(e);
		}
		Socket socket = null;
		log.error("监听线程启动成功");
		while (true) {
			try {
				socket = serverSocket.accept();
				if (socket != null) {
					String clientHost = socket.getInetAddress().toString().replace("/", "");
					int clientPort = socket.getPort();
					log.info("客户端:" + clientHost + ":" + clientPort + "请求登录");
					// socket.setKeepAlive(true);
					InputStream is = socket.getInputStream();
					OutputStream os = socket.getOutputStream();
					byte[] recBuffer = new byte[1024];
					int readNum = is.read(recBuffer);
					String loginString = new String(recBuffer, 0, readNum, Constans.CHARACTER_ENCODING);
					log.info("接收到客户端:" + clientHost + ":" + clientPort + "请求数据:" + loginString);
					RequestCmd rc = JSON.parseObject(loginString, RequestCmd.class);

					switch (rc.getAction()) {
						case Constans.S_LOGIN:
							result = doSlogin(rc.getUname(), rc.getPwd(), socket);
							os.write(result.toJson().getBytes(Constans.CHARACTER_ENCODING));
							os.flush();
							break;

						default:
							break;
					}

				}

			} catch (Throwable e) {
				log.error("监听线程异常" + e.getMessage(), e);
			}
		}

	}
}
