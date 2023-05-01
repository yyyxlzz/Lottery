package com.yyyxl.lottery.interfaces;

import com.itedus.lottery.rpc.IActivityBooth;
import com.itedus.lottery.rpc.dto.ActivityDto;
import com.itedus.lottery.rpc.req.ActivityReq;
import com.itedus.lottery.rpc.res.ActivityRes;
import com.yyyxl.lottery.common.Constants;
import com.yyyxl.lottery.common.Result;
import com.yyyxl.lottery.infrastructure.dao.IActivityDao;
import com.yyyxl.lottery.infrastructure.po.Activity;
import org.apache.dubbo.config.annotation.Service;


import javax.annotation.Resource;

/**
 * @author yyyxl
 * @description 启动入口
 */
@Service
public class ActivityBooth implements IActivityBooth {

    @Resource
    private IActivityDao activityDao;

    @Override
    public ActivityRes queryActivityById(ActivityReq req) {

        Activity activity = activityDao.queryActivityById(req.getActivityId());

        ActivityDto activityDto = new ActivityDto();
        activityDto.setActivityId(activity.getActivityId());
        activityDto.setActivityName(activity.getActivityName());
        activityDto.setActivityDesc(activity.getActivityDesc());
        activityDto.setBeginDateTime(activity.getBeginDateTime());
        activityDto.setEndDateTime(activity.getEndDateTime());
        activityDto.setStockCount(activity.getStockCount());
        activityDto.setTakeCount(activity.getTakeCount());

        return new ActivityRes(new Result(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo()), activityDto);
    }

}

