package com.yyyxl.lottery.domain.activity.service.deploy;

import com.yyyxl.lottery.domain.activity.model.req.ActivityConfigReq;

public interface IActivityDeploy {
    /**
     * 创建活动
     * @param req 活动配置信息
     */
    void createActivity(ActivityConfigReq req);

    /**
     * 修改活动信息
     * @param req 活动配置信息
     */
    void updateActivity(ActivityConfigReq req);
}
