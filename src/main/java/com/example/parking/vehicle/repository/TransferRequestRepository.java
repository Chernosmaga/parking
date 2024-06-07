package com.example.parking.vehicle.repository;

import com.example.parking.enums.RequestState;
import com.example.parking.user.model.User;
import com.example.parking.vehicle.model.TransferRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRequestRepository extends JpaRepository<TransferRequest, Long> {
    Page<TransferRequest> findAllByRequesterAndState(User requester, RequestState state, Pageable page);

    Page<TransferRequest> findAllByOwnerAndState(User owner, RequestState state, Pageable pageable);

    boolean existsByOwnerAndRequester(User owner, User requester);
}
