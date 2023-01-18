package cz.assist.axon_training.bff.calculator.mapper;

import cz.assist.axon_training.bff.calculator.dto.FetchCalculatorResponse;
import cz.assist.axon_training.query.response.CalculatorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT, imports = Collections.class)
public interface CalculatorMapper {

    CalculatorMapper INSTANCE = Mappers.getMapper(CalculatorMapper.class);

    static FetchCalculatorResponse map(CalculatorDTO dto) {
        return dto == null ? new FetchCalculatorResponse() : INSTANCE.toResponse(dto);
    }

    @Mapping(target = "results", defaultExpression = "java(Collections.emptyList())")
    FetchCalculatorResponse toResponse(CalculatorDTO dto);

}
