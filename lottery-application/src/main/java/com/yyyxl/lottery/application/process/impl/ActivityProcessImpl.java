package com.yyyxl.lottery.application.process.impl;

import com.yyyxl.lottery.application.process.IActivityProcess;
import com.yyyxl.lottery.application.process.req.DrawProcessReq;
import com.yyyxl.lottery.application.process.res.DrawProcessResult;
import com.yyyxl.lottery.application.process.res.RuleQuantificationCrowdResult;
import com.yyyxl.lottery.common.Constants;
import com.yyyxl.lottery.domain.activity.model.req.PartakeReq;
import com.yyyxl.lottery.domain.activity.model.res.PartakeResult;
import com.yyyxl.lottery.domain.activity.model.vo.DrawOrderVO;
import com.yyyxl.lottery.domain.activity.service.partake.IActivityPartake;
import com.yyyxl.lottery.domain.activity.service.partake.impl.ActivityPartakeImpl;
import com.yyyxl.lottery.domain.rule.model.req.DecisionMatterReq;
import com.yyyxl.lottery.domain.rule.model.res.EngineResult;
import com.yyyxl.lottery.domain.rule.service.engine.EngineFilter;
import com.yyyxl.lottery.domain.strategy.model.req.DrawReq;
import com.yyyxl.lottery.domain.strategy.model.res.DrawResult;
import com.yyyxl.lottery.domain.strategy.model.vo.DrawAwardVO;
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

    @Resource(name = "ruleEngineHandle")
    private EngineFilter engineFilter;

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
        DrawAwardVO drawAwardVO = drawResult.getDrawAwardVO();

        // 3. 结果落库
        activityPartake.recordDrawOrder(buildDrawOrderVO(req, strategyId, takeId, drawAwardVO));

        // 4. 发送MQ，触发发奖流程

        // 5. 返回结果
        return new DrawProcessResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo(), drawAwardVO);

    }

    @Override
    public RuleQuantificationCrowdResult doRuleQuantificationCrowd(DecisionMatterReq req) {

        // 量化决策
        EngineResult engineResult  = engineFilter.process(req);

        if(!engineResult.isSuccess()){
            return new RuleQuantificationCrowdResult(Constants.ResponseCode.RULE_ERR.getCode(),Constants.ResponseCode.RULE_ERR.getInfo());
        }

        // 封装结果
        RuleQuantificationCrowdResult ruleQuantificationCrowdResult = new RuleQuantificationCrowdResult(Constants.ResponseCode.SUCCESS.getCode(), Constants.ResponseCode.SUCCESS.getInfo());
        ruleQuantificationCrowdResult.setActivityId(Long.valueOf(engineResult.getNodeValue()));

        return ruleQuantificationCrowdResult;
    }

    private DrawOrderVO buildDrawOrderVO(DrawProcessReq req, Long strategyId, Long takeId, DrawAwardVO drawAwardVO) {
        long orderId = idContext.iIdGenerator(Constants.Ids.SnowFlake).nextId();
        DrawOrderVO drawOrderVO = new DrawOrderVO();
        drawOrderVO.setuId(req.getuId());
        drawOrderVO.setTakeId(takeId);
        drawOrderVO.setActivityId(req.getActivityId());
        drawOrderVO.setOrderId(orderId);
        drawOrderVO.setStrategyId(strategyId);
        drawOrderVO.setStrategyMode(drawAwardVO.getStrategyMode());
        drawOrderVO.setGrantType(drawAwardVO.getGrantType());
        drawOrderVO.setGrantDate(drawAwardVO.getGrantDate());
        drawOrderVO.setGrantState(Constants.GrantState.INIT.getCode());
        drawOrderVO.setAwardId(drawAwardVO.getAwardId());
        drawOrderVO.setAwardType(drawAwardVO.getAwardType());
        drawOrderVO.setAwardName(drawAwardVO.getAwardName());
        drawOrderVO.setAwardContent(drawAwardVO.getAwardContent());
        return drawOrderVO;
    }
}
