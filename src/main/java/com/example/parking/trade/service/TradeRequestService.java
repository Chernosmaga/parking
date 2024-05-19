package com.example.parking.trade.service;

import com.example.parking.enums.RequestState;
import com.example.parking.trade.dto.CreateTradeRequestDto;
import com.example.parking.trade.dto.TradeRequestFullDto;

import java.util.List;

public interface TradeRequestService {

    /**
     * Creation of tradeRequest entity in Database
     * @param userId the creator's identifier of request
     * @param spotId the spot's identifier which will they trade for
     * @param tradeRequest tradeRequest dto with minimum data
     * @return the full tradeRequest entity with additional fields
     */
    TradeRequestFullDto create(Long userId, Long spotId, CreateTradeRequestDto tradeRequest);

    /**
     * The information of the particular tradeRequest
     * @param userId the user's identifier which wants to get information
     * @param tradeRequestId the tradeRequest's identifier
     * @return the info of requested TradeRequest
     */
    TradeRequestFullDto get(Long userId, Long tradeRequestId);

    /**
     * Returns list of tradeRequests of bookers requests
     * @param userId the identifier of booker which wants to get data
     * @param state the state of tradeRequest
     * @param from the beginning of the page
     * @param size the end of the page
     * @return the list of bookers data for tradeRequest
     */
    List<TradeRequestFullDto> getAll(Long userId, RequestState state, int from, int size);

    /**
     * Returns list of tradeRequests for owner of the spot
     * @param userId the owner of the spot
     * @param state the state of the tradeRequest
     * @param from the start of the page
     * @param size the end of the page
     * @return the list of owner of the spot for tradeRequest
     */
    List<TradeRequestFullDto> getAllByOwner(Long userId, RequestState state, int from, int size);

    /**
     * Cancels the tradeRequest by requester
     * @param userId the requester
     * @param tradeRequestId the tradeRequest identifier which need to be canceled
     */
    void cancel(Long userId, Long tradeRequestId);

    /**
     * Updates the status of tradeRequest for owner of the spot
     * @param userId the owner of the spot
     * @param tradeRequestId the particular tradeRequest
     * @param state the state which needs to be set in tradeRequest entity
     * @return the updated entity for tradeRequest
     */
    TradeRequestFullDto update(Long userId, Long tradeRequestId, RequestState state);
}
