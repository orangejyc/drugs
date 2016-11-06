package org.ansoya.drugs.drugs.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private String uid;
    private String account;
    private String pwd;
    private String email;
    private String phone;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date regTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expirationTime;
}
