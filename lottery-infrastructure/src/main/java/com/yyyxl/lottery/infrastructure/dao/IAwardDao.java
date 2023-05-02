package com.yyyxl.lottery.infrastructure.dao;

import com.yyyxl.lottery.infrastructure.po.Award;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IAwardDao {

    Award queryAwardInfo(String awardId);

}
