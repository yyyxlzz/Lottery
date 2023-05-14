package com.yyyxl.lottery.application.process.res;

import com.yyyxl.lottery.common.Result;

public class RuleQuantificationCrowdResult extends Result {

    private Long activityId;

    public RuleQuantificationCrowdResult(String code, String info) {
        super(code, info);
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

}
