package com.yyyxl.lottery.domain.strategy.model.aggregates;

import com.yyyxl.lottery.infrastructure.po.Strategy;
import com.yyyxl.lottery.infrastructure.po.StrategyDetail;

import java.util.List;

/**
 * 聚合信息
 */
public class StrategyRich {

    // 策略ID
    private Long StrategyId;

    // 策略配置
    private Strategy strategy;

    // 策略明细
    private List<StrategyDetail> strategyDetailList;

    public StrategyRich() {
    }

    public StrategyRich(Long strategyId, Strategy strategy, List<StrategyDetail> strategyDetailList) {
        StrategyId = strategyId;
        this.strategy = strategy;
        this.strategyDetailList = strategyDetailList;
    }

    public Long getStrategyId() {
        return StrategyId;
    }

    public void setStrategyId(Long strategyId) {
        StrategyId = strategyId;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public List<StrategyDetail> getStrategyDetailList() {
        return strategyDetailList;
    }

    public void setStrategyDetailList(List<StrategyDetail> strategyDetailList) {
        this.strategyDetailList = strategyDetailList;
    }
}
