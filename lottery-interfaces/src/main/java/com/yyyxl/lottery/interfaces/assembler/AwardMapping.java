package com.yyyxl.lottery.interfaces.assembler;

import com.itedus.lottery.rpc.dto.AwardDTO;
import com.yyyxl.lottery.domain.strategy.model.vo.DrawAwardVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
/**
 * @description: 对象转换配置
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AwardMapping extends IMapping<DrawAwardVO, AwardDTO> {


    @Override
    AwardDTO sourceToTarget(DrawAwardVO var1);

    @Override
    DrawAwardVO targetToSource(AwardDTO var1);

}

