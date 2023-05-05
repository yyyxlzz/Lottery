package com.yyyxl.lottery.domain.award.service.goods.impl;

import com.yyyxl.lottery.common.Constants;
import com.yyyxl.lottery.domain.award.model.req.GoodsReq;
import com.yyyxl.lottery.domain.award.model.res.DistributionRes;
import com.yyyxl.lottery.domain.award.service.goods.DistributionBase;
import com.yyyxl.lottery.domain.award.service.goods.IDistributionGoods;
import org.springframework.stereotype.Component;

/**
 * @description 兑换码类商品
 * @date 2023/5/5
 */
@Component
public class RedeemCodeGoods extends DistributionBase implements IDistributionGoods {
    @Override
    public DistributionRes doDistribution(GoodsReq req) {

        // 模拟调用实物发放接口
        logger.info("模拟调用兑换码 uId：{} awardContent：{}", req.getuId(), req.getAwardContent());
        // 更新用户领奖结果
        super.updateUserAwardState(req.getuId(), req.getOrderId(), req.getAwardId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());

        return new DistributionRes(req.getuId(), Constants.AwardState.SUCCESS.getCode(), Constants.AwardState.SUCCESS.getInfo());
    }

    @Override
    public Integer getDistributionGoodsName() {
        return Constants.AwardType.RedeemCodeGoods.getCode();
    }
}
