package com.yyyxl.lottery.domain.strategy.repository.impl;

import com.yyyxl.lottery.domain.strategy.model.aggregates.StrategyRich;
import com.yyyxl.lottery.domain.strategy.repository.IStrategyRepository;
import com.yyyxl.lottery.infrastructure.dao.IAwardDao;
import com.yyyxl.lottery.infrastructure.dao.IStrategyDao;
import com.yyyxl.lottery.infrastructure.dao.IStrategyDetailDao;
import com.yyyxl.lottery.infrastructure.po.Award;
import com.yyyxl.lottery.infrastructure.po.Strategy;
import com.yyyxl.lottery.infrastructure.po.StrategyDetail;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class StrategyRepository implements IStrategyRepository {


    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyDetailDao strategyDetailDao;

    @Resource
    private IAwardDao awardDao;

    @Override
    public StrategyRich queryStrategyRich(Long strategyId) {
        Strategy strategy = strategyDao.queryStrategy(strategyId);
        List<StrategyDetail> strategyDetailList = strategyDetailDao.queryStrategyDetailList(strategyId);
        return new StrategyRich(strategyId, strategy, strategyDetailList);
    }

    @Override
    public Award queryAwardInfo(String awardId) {
        return awardDao.queryAwardInfo(awardId);
    }

    @Override
    public List<String> queryNoStockStrategyAwardList(Long strategyId) {
        return strategyDetailDao.queryNoStockStrategyAwardList(strategyId);
    }

    @Override
    public boolean deductStock(Long strategyId, String awardId) {
        StrategyDetail req = new StrategyDetail();
        req.setStrategyId(strategyId);
        req.setAwardId(awardId);
        int count = strategyDetailDao.deductStock(req);
        return count == 1;
    }


}
