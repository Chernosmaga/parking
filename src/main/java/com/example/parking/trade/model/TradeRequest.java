package com.example.parking.trade.model;

import com.example.parking.enums.RequestState;
import com.example.parking.spot.model.Spot;
import com.example.parking.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "trade_requests")
@AllArgsConstructor
@NoArgsConstructor
public class TradeRequest {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "spot_id")
    private Spot spot;
    @Column(name = "request_date")
    private LocalDateTime requestDate;
    @Enumerated(STRING)
    private RequestState requestState;
    private String comment;
}
