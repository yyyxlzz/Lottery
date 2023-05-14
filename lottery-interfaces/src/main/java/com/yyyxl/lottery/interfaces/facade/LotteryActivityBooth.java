package com.yyyxl.lottery.interfaces.facade;

import com.alibaba.fastjson.JSON;
import com.itedus.lottery.rpc.ILotteryActivityBooth;
import com.itedus.lottery.rpc.dto.AwardDTO;
import com.itedus.lottery.rpc.req.DrawReq;
import com.itedus.lottery.rpc.req.QuantificationDrawReq;
import com.itedus.lottery.rpc.res.DrawRes;
import com.yyyxl.lottery.application.process.IActivityProcess;
import com.yyyxl.lottery.application.process.req.DrawProcessReq;
import com.yyyxl.lottery.application.process.res.DrawProcessResult;
import com.yyyxl.lottery.application.process.res.RuleQuantificationCrowdResult;
import com.yyyxl.lottery.common.Constants;
import com.yyyxl.lottery.domain.rule.model.req.DecisionMatterReq;
import com.yyyxl.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.yyyxl.lottery.interfaces.assembler.IMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class LotteryActivityBooth implements ILotteryActivityBooth {

    private Logger logger = LoggerFactory.getLogger(LotteryActivityBooth.class);

    @Resource
    private IActivityProcess activityProcess;

    @Resource
    private IMapping<DrawAwardVO, AwardDTO> awardMapping;


    @Override
    public DrawRes doDraw(DrawReq drawReq) {

        try{
            logger.info("抽奖，开始uId: {} activityId: {}",drawReq.getuId(),drawReq.getActivityId());

            // 执行抽奖
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(drawReq.getuId(), drawReq.getActivityId()));
            if(!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())){
                logger.error("抽奖，失败(抽奖过程异常) uId：{} activityId：{}", drawReq.getuId(), drawReq.getActivityId());
                return new DrawRes(drawProcessResult.getCode(),drawProcessResult.getInfo());
            }

            // 数据转换
            DrawAwardVO drawAwardVO = drawProcessResult.getDrawAwardVO();
            AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardVO);
            awardDTO.setActivityId(drawReq.getActivityId());

            // 封装数据
            DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(),Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            logger.info("抽奖，完成 uId：{} activityId：{} drawRes：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawRes));

            return drawRes;

        }catch (Exception e){
            logger.error("抽奖，失败 uId：{} activityId：{} reqJson：{}", drawReq.getuId(), drawReq.getActivityId(), JSON.toJSONString(drawReq), e);
            return new DrawRes(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());

        }
    }

    @Override
    public DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq) {
        try{
            logger.info("量化人群抽奖，开始 uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());

            // 执行规则引擎，获取用户可以参与的活动号
            RuleQuantificationCrowdResult ruleQuantificationCrowdResult = activityProcess.doRuleQuantificationCrowd(new DecisionMatterReq(quantificationDrawReq.getTreeId(), quantificationDrawReq.getuId(), quantificationDrawReq.getValMap()));
            if(!Constants.ResponseCode.SUCCESS.getCode().equals(ruleQuantificationCrowdResult.getCode())){
                logger.error("量化人群抽奖，失败(规则引擎执行异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawRes(ruleQuantificationCrowdResult.getCode(), ruleQuantificationCrowdResult.getInfo());
            }

            // 执行抽奖
            Long activityId = ruleQuantificationCrowdResult.getActivityId();
            DrawProcessResult drawProcessResult = activityProcess.doDrawProcess(new DrawProcessReq(quantificationDrawReq.getuId(), activityId));
            if(!Constants.ResponseCode.SUCCESS.getCode().equals(drawProcessResult.getCode())){
                logger.error("量化人群抽奖，失败(抽奖过程异常) uId：{} treeId：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId());
                return new DrawRes(drawProcessResult.getCode(), drawProcessResult.getInfo());
            }

            // 数据转换
            DrawAwardVO drawAwardVO = drawProcessResult.getDrawAwardVO();
            AwardDTO awardDTO = awardMapping.sourceToTarget(drawAwardVO);
            awardDTO.setActivityId(activityId);

            // 数据封装
            DrawRes drawRes = new DrawRes(Constants.ResponseCode.SUCCESS.getCode(),Constants.ResponseCode.SUCCESS.getInfo());
            drawRes.setAwardDTO(awardDTO);

            logger.info("量化人群抽奖，完成 uId：{} treeId：{} drawRes：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(drawRes));

            return drawRes;


        }catch (Exception e){
            logger.error("量化人群抽奖，失败 uId：{} treeId：{} reqJson：{}", quantificationDrawReq.getuId(), quantificationDrawReq.getTreeId(), JSON.toJSONString(quantificationDrawReq), e);
            return new DrawRes(Constants.ResponseCode.UN_ERROR.getCode(), Constants.ResponseCode.UN_ERROR.getInfo());
        }
    }
}
