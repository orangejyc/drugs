package org.ansoya.drugs.drugs.support.tzheng517.com;


import org.ansoya.drugs.drugs.support.alipay.config.AlipayConfig;
import org.ansoya.drugs.drugs.support.alipay.util.AlipayCore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

public class PayService extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String total_fee = request.getParameter("total");

        String orderInfo = AlipayCore.getOrderInfo("支付宝支付",
                "用于测试支付宝快捷支付测试！", total_fee);
        String sign = AlipayCore.sign(orderInfo, AlipayConfig.private_key);
        // 仅需对sign 做URL编码
        sign = URLEncoder.encode(sign, "UTF-8");

        String linkString = orderInfo + "&sign=\"" + sign + "\"&"
                + AlipayCore.getSignType();

        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(linkString);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.doGet(request, response);
    }

}
