package com.yyyxl.lottery.application.process.impl;

import com.yyyxl.lottery.application.process.IActivityProcess;
import com.yyyxl.lottery.application.process.req.DrawProcessReq;
import com.yyyxl.lottery.application.process.res.DrawProcessResult;
import com.yyyxl.lottery.common.Constants;
import com.yyyxl.lottery.domain.activity.model.req.PartakeReq;
import com.yyyxl.lottery.domain.activity.model.res.PartakeResult;
import com.yyyxl.lottery.domain.activity.model.vo.DrawOrderVO;
import com.yyyxl.lottery.domain.activity.service.partake.IActivityPartake;
import com.yyyxl.lottery.domain.activity.service.partake.impl.ActivityPartakeImpl;
import com.yyyxl.lottery.domain.strategy.model.req.DrawReq;
import com.yyyxl.lottery.domain.strategy.model.res.DrawResult;
import com.yyyxl.lottery.domain.strategy.model.vo.DrawAwardInfo;
import com.yyyxl.lottery.domain.strategy.service.draw.IDrawExec;
import com.yyyxl.lottery.domain.support.ids.IdContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 活动抽奖流程编排
 */
@Service
public class ActivityProcessImpl implements IActivityProcess {

    private Logger logger = LoggerFactory.getLogger(ActivityPartakeImpl.class);

    @Resource
    private IActivityPartake activityPartake;

    @Resource
    private IDrawExec iDrawExec;

    @Resource
    private IdContext idContext;

    @Override
    public DrawProcessResult doDrawProcess(DrawProcessReq req) {

        // 领取活动
        PartakeReq partakeReq = new PartakeReq(req.getuId(),req.getActivityId());
        PartakeResult partakeResult = activityPartake.doPartake(partakeReq);
        if(!Constants.ResponseCode.SUCCESS.getCode().equals(partakeResult.getCode())){
            return new DrawProcessResult(partakeResult.getCode(),partakeResult.getInfo());
        }
        Long strategyId = partakeResult.getStrategyId();
        Long takeId = partakeResult.getTakeId();

        // 执行抽奖
        DrawResult drawResult = iDrawExec.doDrawExec(new DrawReq(req.getuId(),strategyId,String.valueOf(takeId)));
        logger.info("FATL:{}",Constants.DrawState.FAIL.getCode());
        if(Constants.DrawState.FAIL.getCode().equals(drawResult.getDrawState())){
            return new DrawProcessResult(Constants.ResponseCode.LOSING_DRAW.getCode(), Constants.ResponseCode.LOSING_DRAW.getInfo());
        }
        DrawAwardInfo drawAwardInfo = drawResult.getDrawAwardInfo();

        // 3. 结果落库
        activityPartake.recordDrawOrder(buildDrawOrderVO(req, strategyId, takeId, drawAwardInfo));

        // 4. 发送MQ，触发发奖流程

        // 5. 返回结果
        return new DrawProcessResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), drawAwardInfo);

    }

    private DrawOrderVO buildDrawOrderVO(DrawProcessReq req, Long strategyId, Long takeId, DrawAwardInfo drawAwardInfo) {
        long orderId = idContext.iIdGenerator(Constants.Ids.SnowFlake).nextId();
        DrawOrderVO drawOrderVO = new DrawOrderVO();
        drawOrderVO.setuId(req.getuId());
        drawOrderVO.setTakeId(takeId);
        drawOrderVO.setActivityId(req.getActivityId());
        drawOrderVO.setOrderId(orderId);
        drawOrderVO.setStrategyId(strategyId);
        drawOrderVO.setStrategyMode(drawAwardInfo.getStrategyMode());
        drawOrderVO.setGrantType(drawAwardInfo.getGrantType());
        drawOrderVO.setGrantDate(drawAwardInfo.getGrantDate());
        drawOrderVO.setGrantState(Constants.GrantState.INIT.getCode());
        drawOrderVO.setAwardId(drawAwardInfo.getAwardId());
        drawOrderVO.setAwardType(drawAwardInfo.getAwardType());
        drawOrderVO.setAwardName(drawAwardInfo.getAwardName());
        drawOrderVO.setAwardContent(drawAwardInfo.getAwardContent());
        return drawOrderVO;
    }
}
