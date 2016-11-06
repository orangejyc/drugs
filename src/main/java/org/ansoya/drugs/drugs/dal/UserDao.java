package org.ansoya.drugs.drugs.dal;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.ansoya.drugs.drugs.entity.User;
import org.ansoya.drugs.drugs.support.alipay.util.UtilDate;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Repository
public class UserDao {

    //@Resource(name = "dataSource")
    @Autowired
    private DataSource dataSource;

    private Sql2o getSql2o() {
        return new Sql2o(dataSource);
    }

    public String insert(User user) {
        String uid = UUID.randomUUID().toString();

        StringBuilder sl = new StringBuilder("insert into drugs_user(uid, account,pwd, email,phone,regTime,expirationTime)values(:uid, :pwd, :account, :email,:phone,:regTime,:expirationTime)");
        //sl.append(UUID.randomUUID().toString());
        try (Connection con = getSql2o().open()) {
            con.createQuery(sl.toString())
                    .addParameter("uid", uid)
                    .addParameter("account", user.getAccount())
                    .addParameter("pwd", user.getPwd())
                    .addParameter("email", user.getEmail())
                    .addParameter("phone", user.getPhone())
                    .addParameter("regTime", new Date())
                    .addParameter("expirationTime", UtilDate.getDefaultExpTime())
                    .executeUpdate();
            return uid;
        }
    }

    public void del(String uid) {

    }

    public void update(String uid, User user) {

        Preconditions.checkArgument(!Strings.isNullOrEmpty(uid));
        Preconditions.checkArgument(null != user);

        StringBuilder sl = new StringBuilder();
        sl.append("update drugs_user set ")
                .append("uid=:uid");
        if (!Strings.isNullOrEmpty(user.getPwd())) {
            sl.append(",pwd=:pwd");
        }
        if (null != user.getExpirationTime() && !Strings.isNullOrEmpty(String.valueOf(user.getExpirationTime()))) {
            sl.append(",expirationTime=:expirationTime");
        }
        if (!Strings.isNullOrEmpty(user.getEmail())) {
            sl.append(",email=:email");
        }
        if (!Strings.isNullOrEmpty(user.getAccount())) {
            sl.append(",account=:account");
        }
        if (!Strings.isNullOrEmpty(user.getPhone())) {
            sl.append(",phone=:phone");
        }
        sl.append(" WHERE uid = :uid");

        try (Connection con = getSql2o().open()) {
            Query query = con.createQuery(sl.toString())
                    .addParameter("uid", uid);
            if (!Strings.isNullOrEmpty(user.getPwd())) {
                query.addParameter("pwd", user.getPwd());
            }
            if (null != user.getExpirationTime() && !Strings.isNullOrEmpty(String.valueOf(user.getExpirationTime()))) {
                query.addParameter("expirationTime", user.getExpirationTime());
            }
            if (!Strings.isNullOrEmpty(user.getEmail())) {
                query.addParameter("email", user.getEmail());
            }
            if (!Strings.isNullOrEmpty(user.getAccount())) {
                query.addParameter("account", user.getAccount());
            }
            if (!Strings.isNullOrEmpty(user.getPhone())) {
                query.addParameter("phone", user.getPhone());
            }
            query.executeUpdate();
        }
    }

    public User findByAccount(String account) {
        String sql =
                "SELECT uid, pwd, account, email,phone,regTime,expirationTime " +
                        "FROM drugs_user " +
                        "WHERE account = :account ";

        try (Connection con = getSql2o().open()) {
            List<User> users = con.createQuery(sql)
                    .addParameter("account", account)
                    .executeAndFetch(User.class);
            if (!CollectionUtils.isEmpty(users)) {
                return users.get(0);
            }
            return null;
        }
    }

    public User findByUID(String uid) {
        String sql =
                "SELECT uid, pwd, account, email,phone,regTime,expirationTime " +
                        "FROM drugs_user " +
                        "WHERE uid = :uid ";

        try (Connection con = getSql2o().open()) {
            List<User> users = con.createQuery(sql)
                    .addParameter("uid", uid)
                    .executeAndFetch(User.class);
            if (!CollectionUtils.isEmpty(users)) {
                return users.get(0);
            }
            return null;
        }
    }
}
