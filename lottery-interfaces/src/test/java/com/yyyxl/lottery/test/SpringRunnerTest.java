/*
package com.yyyxl.lottery.test;


import com.yyyxl.lottery.common.Constants;
import com.yyyxl.lottery.domain.award.model.req.GoodsReq;
import com.yyyxl.lottery.domain.award.model.res.DistributionRes;
import com.yyyxl.lottery.domain.award.service.factory.DistributionGoodsFactory;
import com.yyyxl.lottery.domain.award.service.goods.IDistributionGoods;
import com.yyyxl.lottery.domain.strategy.model.req.DrawReq;
import com.yyyxl.lottery.domain.strategy.model.res.DrawResult;
import com.yyyxl.lottery.domain.strategy.model.vo.DrawAwardVO;
import com.yyyxl.lottery.domain.strategy.service.draw.IDrawExec;
import com.yyyxl.lottery.infrastructure.dao.IActivityDao;
import com.yyyxl.lottery.infrastructure.po.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import com.alibaba.fastjson.JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringRunnerTest {
    private Logger logger = LoggerFactory.getLogger(SpringRunnerTest.class);

    @Resource
    private IActivityDao activityDao;

    @Resource
    private IActivityBooth activityBooth;

    @Resource
    private IDrawExec drawExec;

    @Resource
    private DistributionGoodsFactory distributionGoodsFactory;


    @Test
    public void test_drawExec(){
        drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));
        drawExec.doDrawExec(new DrawReq("小佳佳", 10001L));
        drawExec.doDrawExec(new DrawReq("小蜗牛", 10001L));
        drawExec.doDrawExec(new DrawReq("八杯水", 10001L));

    }

    @Test
    public void test_award(){
        // 执行抽奖
        DrawResult drawResult  = drawExec.doDrawExec(new DrawReq("小傅哥", 10001L));

        // 判断抽奖结果
        Integer drawState = drawResult.getDrawState();
        if(Constants.DrawState.FAIL.getCode().equals(drawState)){
            logger.info("未中奖");
            return;
        }

        // 封装发奖参数，orderId：2109313442431 为模拟ID，需要在用户参与领奖活动时生成
        DrawAwardVO drawAwardVO = drawResult.getDrawAwardVO();
        GoodsReq goodsReq = new GoodsReq(drawResult.getuId(),"2109313442431", drawAwardVO.getAwardId(), drawAwardVO.getAwardName(), drawAwardVO.getAwardContent());

        // 根据 awardType 从抽奖工厂中获取对应的发奖服务
        IDistributionGoods distributionGoodsService = distributionGoodsFactory.getDistributionGoodsService(drawAwardVO.getAwardType());
        DistributionRes distributionRes = distributionGoodsService.doDistribution(goodsReq);

        logger.info("测试结果：{}", JSON.toJSONString(distributionRes));

    }


    @Test
    public void test_insert(){
        Activity activity = new Activity();
        activity.setActivityId(100002L);
        activity.setActivityName("测试活动");
        activity.setActivityDesc("仅用于插入数据测试");
        activity.setBeginDateTime(new Date());
        activity.setEndDateTime(new Date());
        activity.setStockCount(100);
        activity.setTakeCount(10);
        activity.setState(0);
        activity.setCreator("yyyxl");
        activityDao.insert(activity);
    }

    @Test
    public void test_query(){
        ActivityReq activityReq = new ActivityReq();
        activityReq.setActivityId(100002L);
        ActivityRes activityRes = activityBooth.queryActivityById(activityReq);
        logger.info("查询数据：{}",JSON.toJSONString(activityRes));
    }

}
*/
