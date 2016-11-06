package org.ansoya.drugs.drugs.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.ansoya.drugs.drugs.common.DrugsConstants;
import org.ansoya.drugs.drugs.dal.UserDao;
import org.ansoya.drugs.drugs.engine.FormClient;
import org.ansoya.drugs.drugs.engine.FormClientHolder;
import org.ansoya.drugs.drugs.entity.Result;
import org.ansoya.drugs.drugs.entity.Results;
import org.ansoya.drugs.drugs.entity.User;
import org.ansoya.drugs.drugs.utils.Utils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/7.
 */
@Controller
@RequestMapping("winform")
@Slf4j
public class WinFormController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("login")
    public Result<Boolean> fLogin(HttpServletRequest request, @RequestParam("account") String accout, @RequestParam("password") String password, @RequestParam("port") Integer port) {
        Result<Boolean> result = Results.newFailedResult(DrugsConstants.OPER_FAILD);
        try {
            String clientHost = Utils.getIpAddr(request);
            Preconditions.checkArgument(!Strings.isNullOrEmpty(accout));
            Preconditions.checkArgument(!Strings.isNullOrEmpty(password));
            Preconditions.checkArgument(!Strings.isNullOrEmpty(clientHost));
            Preconditions.checkArgument(null != port && port > 0);

            //查询数据库
            User user = userDao.findByAccount(accout);
            if (null == user) {
                return Results.newFailedResult("登录失败,不存在该用户");
            }
            if (!password.equals(user.getPwd())) {
                return Results.newFailedResult("登录失败,用户名密码错误");
            }

            if (new Date().after(user.getExpirationTime())) {
                return Results.newFailedResult("登录失败,该用户VIP已经过期,请手机端登录续费");
            }
            FormClient formClient = new FormClient(user.getUid(), clientHost, port);
            FormClientHolder.addFormClient(formClient);
            return Results.newSuccessResult("登录成功");
        } catch (Throwable t) {
            result = Results.newFailedResult("登录异常" + t.getMessage());
            log.error(result.getStatusText(), t);
            return result;
        }
    }
}
