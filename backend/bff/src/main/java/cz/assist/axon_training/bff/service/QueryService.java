package cz.assist.axon_training.bff.service;

import cz.assist.axon_training.common.message.Query;
import cz.assist.axon_training.common.message.QueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.GenericQueryMessage;
import org.axonframework.queryhandling.GenericSubscriptionQueryMessage;
import org.axonframework.queryhandling.QueryBus;
import org.axonframework.queryhandling.QueryResponseMessage;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.axonframework.queryhandling.SubscriptionQueryUpdateMessage;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class QueryService {

    private final QueryBus queryBus;

    public <T extends QueryResponse> T queryMessage(Query query, Class<T> responseClass) {
        log.info("Going to run query {}", query.getClass().getName());
        log.debug("Going to run query {}", query);

        var queryMessage = new GenericQueryMessage<>(query, ResponseTypes.instanceOf(responseClass));
        return queryBus.query(queryMessage)
            .thenApply(QueryResponseMessage::getPayload)
            .join();
    }

    public <I, U> SubscriptionQueryResult<QueryResponseMessage<I>, SubscriptionQueryUpdateMessage<U>> subscriptionQuery(
        Query query, Class<I> initResponseClass, Class<U> updateClass) {

        log.debug("Going to run subscription query {}", query);
        var initResponseType = ResponseTypes.instanceOf(initResponseClass);
        var updateResponseType = ResponseTypes.instanceOf(updateClass);
        var genericSubscriptionQueryMessage = new GenericSubscriptionQueryMessage<>(query, initResponseType, updateResponseType);
        return queryBus.subscriptionQuery(genericSubscriptionQueryMessage);
    }

}
