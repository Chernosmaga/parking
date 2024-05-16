package com.example.parking.trade.repository;

import com.example.parking.enums.RequestState;
import com.example.parking.trade.model.TradeRequest;
import com.example.parking.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for tradeRequest entity
 */
@Repository
public interface TradeRequestRepository extends JpaRepository<TradeRequest, Long> {
    Page<TradeRequest> findAllByRequesterAndRequestState(User requester, RequestState state, Pageable page);

    Page<TradeRequest> findAllByOwnerAndRequestState(User owner, RequestState state, Pageable page);
}
