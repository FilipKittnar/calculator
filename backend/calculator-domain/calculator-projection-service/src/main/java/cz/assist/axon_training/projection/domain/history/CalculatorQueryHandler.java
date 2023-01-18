package cz.assist.axon_training.projection.domain.history;

import cz.assist.axon_training.projection.domain.history.mapper.CalculatorQueryMapper;
import cz.assist.axon_training.projection.domain.history.service.CalculatorMongoService;
import cz.assist.axon_training.query.GetUserCalculator;
import cz.assist.axon_training.query.response.GetUserCalculatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatorQueryHandler {

    private final CalculatorMongoService mongoService;

    @QueryHandler
    public GetUserCalculatorResponse getCalculatorHistory(GetUserCalculator query) {
        log.info("Going to get calculator history for user {}", query.id());
        var entityOptional = mongoService.findById(query.id());
        var dto = entityOptional.map(CalculatorQueryMapper.INSTANCE::map).orElse(null);
        log.info("Returning calculator {}", dto);
        return new GetUserCalculatorResponse(dto);
    }

}
