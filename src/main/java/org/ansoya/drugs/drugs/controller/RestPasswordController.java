package org.ansoya.drugs.drugs.controller;

import org.ansoya.drugs.drugs.entity.Result;
import org.ansoya.drugs.drugs.entity.Results;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/11/7.
 */
@Controller
public class RestPasswordController {
    @RequestMapping("restPassword")
    public void showRestPassword(HttpServletRequest request, HttpServletResponse response){
        //TODO 响应重置密码界面
    }

    @RequestMapping("restPassword")
    @ResponseBody
    public Result<Boolean> restPassword(String userName, String password){
        //TODO 重置密码
        return Results.newSuccessResult("密码重置成功！");
    }
}
