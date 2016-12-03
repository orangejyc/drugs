package org.ansoya.wechats.api.response;

import com.alibaba.fastjson.annotation.JSONField;
import org.ansoya.wechats.api.entity.CustomAccount;

import java.util.List;

/**
 * @author peiyu
 */
public class GetCustomAccountsResponse extends BaseResponse {

    @JSONField(name = "kf_list")
    private List<CustomAccount> customAccountList;

    public List<CustomAccount> getCustomAccountList() {
        return customAccountList;
    }

    public void setCustomAccountList(List<CustomAccount> customAccountList) {
        this.customAccountList = customAccountList;
    }
}
