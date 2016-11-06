package org.ansoya.drugs.drugs.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2016/11/6.
 */
@ConfigurationProperties(prefix = "pay.wx")
@Getter
@Setter
public class WxPayConfiguration {

    /**
     * 微信支付商户号（从微信发给你的邮件中获得的）
     */
    private String mchId;


    /**
     * 应用的APPID（微信发给你的邮件中也有这项内容，一般以wx开头，微信开放平台-管理中心-应用详情也可以看到这项内容）
     */
    private String appId;


    /**
     * 这里是API密钥，不是Appsecret，这里最容易出错了！请务必注意！
     * 设置方法：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
     */
    private String apiKey;


    /**
     * 支付成功后，微信会请求这个路径
     */
    private String notifyUrl;
}
