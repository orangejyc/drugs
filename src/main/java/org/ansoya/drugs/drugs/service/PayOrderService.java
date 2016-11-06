package org.ansoya.drugs.drugs.service;

import com.alibaba.fastjson.JSON;
import com.beust.jcommander.internal.Lists;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import lombok.extern.slf4j.Slf4j;
import org.ansoya.drugs.drugs.common.DrugsConstants;
import org.ansoya.drugs.drugs.configuration.WxPayConfiguration;
import org.ansoya.drugs.drugs.support.HttpClientWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import sun.misc.BASE64Encoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/6.
 */
@Service
@Slf4j
public class PayOrderService {

    @Autowired
    private WxPayConfiguration wxPayConfiguration;

    /**
     * 开发发起支付
     *
     * @param totalFee 总金额，单位：分，不能为空
     * @param tradeNo  订单号，你自己定就好了，不要重复，不能为空
     * @param des      订单描述，不能为空
     * @param clientIp 客户端的IP地址，不能为空
     * @param feeType  货币类型，默认是CNY，人民币
     * @return
     */
    public String pay(int totalFee, String tradeNo, String des, String clientIp, String feeType) {
        if (Strings.isNullOrEmpty(feeType)) {
            feeType = "CNY";
        }
        //为发送请求给微信服务器准备数据
        String nstr = makeNonceStr();
        HashMap<String, String> packageParameter = Maps.newHashMap();
        packageParameter.put("appid", wxPayConfiguration.getAppId());
        packageParameter.put("body", des);
        packageParameter.put("mch_id", wxPayConfiguration.getMchId());
        packageParameter.put("notify_url", wxPayConfiguration.getNotifyUrl());
        packageParameter.put("nonce_str", nstr);
        packageParameter.put("out_trade_no", tradeNo);
        packageParameter.put("total_fee", String.valueOf(totalFee));
        packageParameter.put("spbill_create_ip", clientIp);
        packageParameter.put("trade_type", "APP");
        packageParameter.put("fee_type", feeType);
        String sign = createMd5Sign(packageParameter);
        packageParameter.put("sign", sign);
        Document xe = postDataToWeiXin(packageParameter);
        //为响应客户端的请求准备数据
        Long timeStamp = makeTimestamp();
        String prepayId = xe.getElementsByTagName("prepay_id").item(0).getNodeValue();
        //String prepayId = xe.Element("prepay_id").Value;
        //nstr = xe.Element("nonce_str").Value;
        nstr = xe.getElementsByTagName("nonce_str").item(0).getNodeValue();
        HashMap<String, String> paySignReqHandler = Maps.newHashMap();
        paySignReqHandler.put("appid", wxPayConfiguration.getAppId());
        paySignReqHandler.put("partnerid", wxPayConfiguration.getMchId());
        paySignReqHandler.put("prepayid", prepayId);
        paySignReqHandler.put("noncestr", nstr);
        paySignReqHandler.put("package", DrugsConstants.packageStr);
        paySignReqHandler.put("timestamp", String.valueOf(timeStamp));
        String paySign = createMd5Sign(paySignReqHandler);

        HashMap<String, String> rMap = Maps.newHashMap();

        rMap.put("appid", wxPayConfiguration.getAppId());
        rMap.put("partnerid", wxPayConfiguration.getMchId());
        rMap.put("prepayid", prepayId);
        rMap.put("package", DrugsConstants.packageStr);
        rMap.put("noncestr", nstr);
        rMap.put("timestamp", String.valueOf(timeStamp));
        rMap.put("sign", paySign);
        return JSON.toJSONString(rMap);
    }

    private Long makeTimestamp() {
        return System.currentTimeMillis();
    }

    private Document postDataToWeiXin(HashMap<String, String> parameters) {
        try {
            String xmlStr = getXmlStr(parameters);
            byte[] data = xmlStr.getBytes("UTF-8");
            byte[] responseBytes = HttpClientWrap.builder().url(DrugsConstants.WeiXinPayUrl).build().post(data, "application/x-www-form-urlencoded");
            String responseStr = new String(responseBytes, "UTF-8");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            Document xe = db.parse(new ByteArrayInputStream(responseBytes));
            return xe;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }


    private String getXmlStr(HashMap<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (String k : parameters.keySet()) {
            String v = parameters.get(k);
            if (Pattern.compile("^[0-9.]$").matcher(v).matches()) {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            } else {
                sb.append("<" + k + "><![CDATA[" + v + "]]></" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    private String createMd5Sign(HashMap<String, String> parameters) {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> akeys = Lists.newArrayList(parameters.keySet());
            Ordering<String> naturalOrdering = Ordering.natural();
            naturalOrdering.sortedCopy(akeys);//排序，这是微信要求的
            //akeys.Sort();//排序，这是微信要求的
            for (String k : akeys) {
                String v = parameters.get(k);
                sb.append(k + "=" + v + "&");
            }
            sb.append("key=" + wxPayConfiguration.getApiKey());
            String sign = encoderByMd5(sb.toString());
            return sign;
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private String makeNonceStr() {
        try {
            String timestap = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            return encoderByMd5(timestap);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public String encoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }



}
