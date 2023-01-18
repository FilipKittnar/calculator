package cz.assist.axon_training.projection.domain.history.mapper;

import cz.assist.axon_training.OperationType;
import cz.assist.axon_training.projection.domain.history.entity.CalculatorEntity;
import cz.assist.axon_training.projection.domain.history.entity.HistoryRow;
import cz.assist.axon_training.query.response.CalculatorDTO;
import cz.assist.axon_training.query.response.CalculatorHistoryRowDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Mapper(imports = OperationType.class)
public interface CalculatorQueryMapper {

    CalculatorQueryMapper INSTANCE = Mappers.getMapper(CalculatorQueryMapper.class);

    @Mapping(target = "results", source = "entity.history", qualifiedByName = "mapHistory")
    CalculatorDTO map(CalculatorEntity entity);

    @Named("mapHistory")
    static List<CalculatorHistoryRowDTO> mapHistory(Map<String, HistoryRow> history) {
        if (CollectionUtils.isEmpty(history)) {
            return Collections.emptyList();
        }
        var values = history.values()
            .stream()
            .sorted(Comparator.comparing(HistoryRow::getAddedToProjectionAt).reversed())
            .toList();
        return INSTANCE.map(values);
    }

    List<CalculatorHistoryRowDTO> map(List<HistoryRow> rows);
}
