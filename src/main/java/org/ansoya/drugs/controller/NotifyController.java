package org.ansoya.drugs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/11/7.
 */
@Controller
@RequestMapping("wxpay")
public class NotifyController {

    @RequestMapping("notify")
    @ResponseBody
    public String notify(@RequestParam(value = "return_code", required = true) String return_code, @RequestParam(value = "return_msg", required = false) String return_msg) {
        return "<xml>" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>" +
                "  <return_msg><![CDATA[OK]]></return_msg>" +
                "</xml> ";
    }
}
