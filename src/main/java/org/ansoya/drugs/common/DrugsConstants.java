package org.ansoya.drugs.common;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/11/6.
 */
public abstract class DrugsConstants {

    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final String CONTENT_TYPE = "text/html;charset=utf-8";
    public static final String ACTION = "action";

    public static final String ACTION_F_LOGIN = "flogin";

    public static final String ACTION_QR_UPLOAD = "mqrupload";
    public static final String ACTION_M_REG = "mreg";
    public static final String ACTION_M_LOGIN = "mlogin";
    public static final String ACTION_M_CHANGE_PWD = "mchangepwd";
    public static final String ACTION_M_FP = "mfp";


    public static final String ACTION_M_PAY_ALI = "mpayali";
    public static final String ACTION_M_PAY_WX = "mpaywx";

    public static final String P_UID = "uid";
    public static final String P_PWD = "pwd";
    public static final String P_N_PWD = "npwd";
    public static final String P_CLIENT_PORT = "port";
    public static final String P_QR = "qr";

    public static final String P_UNAME = "uname";
    public static final String P_NAME = "name";
    public static final String P_PHONE = "phone";

    public static final String OPER_FAILD = "操作失败";

    public static final Pattern pwdPattern = Pattern.compile("^[a-zA-Z]\\w{5,11}$"); //以字母开头，长度在6~12之间，只能包含字符、数字和下划线
    public static final Pattern phonePattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  //手机号码
    //private Pattern unamePattern = Pattern.compile("[^\u4e00-\u9fa5]");//非中文
    public static final Pattern unamePattern = Pattern.compile("[a-zA-Z_0-9]{5,11}");//字母数字6~12
    public static final Pattern mailPattern = Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");//邮箱


    public static final String WeiXinPayUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String packageStr = "Sign=WXPay";
}
