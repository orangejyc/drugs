package org.ansoya.wechats.api.response;

import org.ansoya.wechats.api.entity.UpstreamMsgDist;

import java.util.List;

/**
 * @author peiyu
 */
public class GetUpstreamMsgDistResponse extends BaseResponse {

    private List<UpstreamMsgDist> list;

    public List<UpstreamMsgDist> getList() {
        return list;
    }

    public void setList(List<UpstreamMsgDist> list) {
        this.list = list;
    }
}
