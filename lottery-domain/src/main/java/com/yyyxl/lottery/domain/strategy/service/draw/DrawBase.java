package com.yyyxl.lottery.domain.strategy.service.draw;

import com.yyyxl.lottery.domain.strategy.model.vo.AwardRateInfo;
import com.yyyxl.lottery.domain.strategy.service.algorithm.IDrawAlgorithm;
import com.yyyxl.lottery.infrastructure.po.StrategyDetail;

import java.util.ArrayList;
import java.util.List;

public class DrawBase extends DrawConfig {

    public void checkAndInitRateData(Long strategyId, Integer strategyMode, List<StrategyDetail> strategyDetailList) {
        if(1 != strategyMode) return;
        IDrawAlgorithm drawAlgorithm = drawAlgorithmMap.get(strategyMode);

        boolean existRateTuple = drawAlgorithm.isExistRateTuple(strategyId);
        if(existRateTuple) return;

        List<AwardRateInfo> awardRateInfoList = new ArrayList<>(strategyDetailList.size());
        for (StrategyDetail strategyDetail : strategyDetailList) {
            awardRateInfoList.add(new AwardRateInfo(strategyDetail.getAwardId(), strategyDetail.getAwardRate()));
        }

        drawAlgorithm.initRateTuple(strategyId, awardRateInfoList);


    }

}
