package org.ansoya.drugs.drugs.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.ansoya.drugs.drugs.common.DrugsConstants;
import org.ansoya.drugs.drugs.dal.UserDao;
import org.ansoya.drugs.drugs.engine.FormClient;
import org.ansoya.drugs.drugs.engine.FormClientHolder;
import org.ansoya.drugs.drugs.entity.Result;
import org.ansoya.drugs.drugs.entity.Results;
import org.ansoya.drugs.drugs.entity.User;
import org.ansoya.drugs.drugs.service.PayOrderService;
import org.ansoya.drugs.drugs.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/11/6.
 */
@Controller
@Slf4j
@Api("app-api")
@RequestMapping("api")
public class AppController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PayOrderService payOrderService;
    @Value("${pay.amount.y}")
    private Double yAmount;
    @Value("${pay.amount.m}")
    private Double mAmount;


    @RequestMapping(path = "reg", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @ApiOperation("注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "account", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "email", dataType = "string"),
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "string"),
    })
    public Result<?> reg(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("phone") String phone) {
        Result<?> result = Results.newFailedResult(DrugsConstants.OPER_FAILD);
        try {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(account));
            Preconditions.checkArgument(!Strings.isNullOrEmpty(password));
            Preconditions.checkArgument(!Strings.isNullOrEmpty(email));
            Preconditions.checkArgument(!Strings.isNullOrEmpty(phone));
            if (!DrugsConstants.pwdPattern.matcher(password).matches()) {
                return Results.newFailedResult("注册失败,密码必须以字母开头，长度在6~12之间，只能包含字符、数字和下划线");
            }
            if (!DrugsConstants.phonePattern.matcher(phone).matches()) {
                return Results.newFailedResult("注册失败,手机号码填写错误");
            }
            if (!DrugsConstants.unamePattern.matcher(account).matches()) {
                return Results.newFailedResult("注册失败,用户名填写错误必须字符、数字和下划线且长度在6~12之间");
            }
            if (!DrugsConstants.mailPattern.matcher(email).matches()) {
                return Results.newFailedResult("注册失败,请正确填写邮箱");
            }
            User user = new User();
            user.setAccount(account);
            user.setPwd(password);
            user.setEmail(email);
            user.setPhone(phone);
            userDao.insert(user);
            return Results.newSuccessResult("注册成功");
        } catch (Throwable t) {
            result = Results.newFailedResult("注册异常" + t.getMessage());
            log.error(result.getStatusText(), t);
            return result;
        }
    }


    @RequestMapping(path = "login", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> login(@RequestParam("account") String account, @RequestParam("password") String password) {
        Result<?> result = Results.newFailedResult(DrugsConstants.OPER_FAILD);
        try {

            Preconditions.checkArgument(!Strings.isNullOrEmpty(account));
            Preconditions.checkArgument(!Strings.isNullOrEmpty(password));

            //查询数据库
            User user = userDao.findByAccount(account);
            if (null == user) {
                return Results.newFailedResult("登录失败,不存在该用户");
            }
            if (!password.equals(user.getPwd())) {
                return result = Results.newFailedResult("登录失败,用户名密码错误");
            }
            return Results.newSuccessResult(user, "登陆成功");
        } catch (Throwable t) {
            result = Results.newFailedResult("登录异常" + t.getMessage());
            log.error(result.getStatusText(), t);
            return result;
        }
    }


    @RequestMapping(path = "changePassword", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> changePwd(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("newPassword") String newPassword) {
        Result<Boolean> result = Results.newFailedResult(DrugsConstants.OPER_FAILD);
        try {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(account));
            Preconditions.checkArgument(!Strings.isNullOrEmpty(password));
            Preconditions.checkArgument(!Strings.isNullOrEmpty(newPassword));

            User user = userDao.findByAccount(account);
            if (null == user) {
                return Results.newFailedResult("修改密码失败,不存在该用户");

            }
            if (!password.equals(user.getPwd())) {
                return Results.newFailedResult("修改密码失败,用户名密码错误");

            }
            if (!DrugsConstants.pwdPattern.matcher(newPassword).matches()) {
                return result = Results.newFailedResult("注册失败,密码必须以字母开头，长度在6~12之间，只能包含字符、数字和下划线");
            }
            User updateUser = new User();
            updateUser.setPwd(newPassword);
            updateUser.setUid(user.getUid());

            userDao.update(updateUser.getUid(), updateUser);
            return Results.newSuccessResult("修改密码成功");
        } catch (Throwable t) {
            result = Results.newFailedResult("修改密码异常" + t.getMessage());
            log.error(result.getStatusText(), t);
            return result;
        }
    }

    @RequestMapping(path = "forgetPassword", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> forgetPwd(@RequestParam("account") String account, @RequestParam("email") String email) {
        Result<Boolean> result = Results.newFailedResult(DrugsConstants.OPER_FAILD);
        try {
            return Results.newSuccessResult("重置邮件已发送到您的邮箱，请查收邮件");
        } catch (Throwable t) {
            result = Results.newFailedResult("忘记密码处理异常" + t.getMessage());
            log.error(result.getStatusText(), t);
            return result;
        }
    }

    @RequestMapping(path = "qrUpload", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> qrUpload(@RequestParam("uid") String uid, @RequestParam("qr") String qr, @RequestParam("qrcharset") String qrcharset) {
        Result<?> result = Results.newFailedResult(DrugsConstants.OPER_FAILD);
        String message = "";
        try {
            if (Strings.isNullOrEmpty(qrcharset)) {
                qrcharset = "UTF-8";
            }
            message = new String(qr.getBytes("ISO8859-1"), qrcharset);
            //msg=new String(qr.getBytes(),"GBK");
            log.info("接收到qr:" + qr);
            FormClient formClient = FormClientHolder.getFormClientBySik(uid);
            if (null == formClient) {
                return Results.newFailedResult(message, "上传二维码扫描结果失败,该用户未登录电脑客户端");
            }
            formClient.handle(message);
            log.info(result.toJson());
            return Results.newSuccessResult(message, "上传二维码扫描结果成功");

        } catch (Throwable t) {
            result = Results.newFailedResult(message, "上传二维码扫描结果异常" + t.getMessage());
            log.error(result.getStatusText(), t);
            return result;
        }
    }

    @RequestMapping(path = "wxOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> getWxOrderInfo(HttpServletRequest request, @RequestParam("account") String account, @RequestParam("num") Integer num, @RequestParam("type") String type) {
        Result<String> result = Results.newFailedResult(DrugsConstants.OPER_FAILD);
        try {
            Preconditions.checkArgument(!Strings.isNullOrEmpty(account), "account must be not null");
            User user = userDao.findByAccount(account);
            Preconditions.checkArgument(null != user, "用户不存在");
            Preconditions.checkArgument("Y".equals(type) || "M".equals(type), "支付类型不存在");
            Preconditions.checkArgument(null != num && num.intValue() > 0, "购买数量需大于0");
            Preconditions.checkArgument(null != yAmount && yAmount.intValue() > 0, "包年费用配置异常");
            Preconditions.checkArgument(null != mAmount && mAmount.intValue() > 0, "包月费用配置异常");
            int totalFee = 0;
            if ("Y".equals(type)) {
                Double fee = yAmount * num;
                totalFee = fee.intValue();
            } else {
                Double fee = mAmount * num;
                totalFee = fee.intValue();
            }
            String orderInfo = payOrderService.pay(totalFee, "wxPayOrder-" + type + System.currentTimeMillis(), "扫码服务购买", Utils.getIpAddr(request), "CNY");
            return result;
        } catch (Throwable throwable) {
            result = Results.newFailedResult("生成微信支付订单失败!" + throwable.getMessage());
            log.error(result.getStatusText(), throwable);
            return result;
        }
    }


}
