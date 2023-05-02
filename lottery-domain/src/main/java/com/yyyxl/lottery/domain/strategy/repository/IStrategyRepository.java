package com.yyyxl.lottery.domain.strategy.repository;

import com.yyyxl.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.yyyxl.lottery.infrastructure.po.Award;

public interface IStrategyRepository {

    StrategyRich queryStrategyRich(Long strategyId);

    Award queryAwardInfo(String awardId);

}
