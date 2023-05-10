package com.yyyxl.lottery.domain.activity.service.partake;

import com.yyyxl.lottery.domain.activity.model.req.PartakeReq;
import com.yyyxl.lottery.domain.activity.model.res.PartakeResult;

/**
 * @description: 抽奖活动参与接口
 */
public interface IActivityPartake {

    /**
     * 参与活动
     * @param req 入参
     * @return 领取结果
     */
    PartakeResult doPartake(PartakeReq req);

}
