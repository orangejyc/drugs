package org.ansoya.wechats.api.response;

import org.ansoya.wechats.api.entity.UserCumulate;

import java.util.List;

/**
 * @author peiyu
 */
public class GetUserCumulateResponse extends BaseResponse {

    private List<UserCumulate> list;

    public List<UserCumulate> getList() {
        return list;
    }

    public void setList(List<UserCumulate> list) {
        this.list = list;
    }
}
