package org.ansoya.wechats.api.response;

import org.ansoya.wechats.api.entity.UpstreamMsg;

import java.util.List;

/**
 * @author peiyu
 */
public class GetUpstreamMsgResponse extends BaseResponse {

    private List<UpstreamMsg> list;

    public List<UpstreamMsg> getList() {
        return list;
    }

    public void setList(List<UpstreamMsg> list) {
        this.list = list;
    }
}
