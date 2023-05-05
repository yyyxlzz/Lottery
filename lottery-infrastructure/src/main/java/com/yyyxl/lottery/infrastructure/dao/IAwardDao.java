package com.yyyxl.lottery.infrastructure.dao;

import com.yyyxl.lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 奖品信息表DAO
 */
@Mapper
public interface IAwardDao {

    /**
     * 查询奖品信息
     *
     * @param awardId 奖品ID
     * @return        奖品信息
     */
    Award queryAwardInfo(String awardId);

}
