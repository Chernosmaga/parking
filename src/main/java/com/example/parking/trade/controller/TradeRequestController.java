package com.example.parking.trade.controller;

import com.example.parking.enums.RequestState;
import com.example.parking.trade.dto.CreateTradeRequestDto;
import com.example.parking.trade.dto.TradeRequestFullDto;
import com.example.parking.trade.service.TradeRequestService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@RestController
@RequestMapping("/personal/requests")
@RequiredArgsConstructor
public class TradeRequestController {
    private final TradeRequestService tradeRequestService;

    @PostMapping("/{userId}/{spotId}")
    @ResponseStatus(CREATED)
    public TradeRequestFullDto create(@PathVariable @NotNull Long userId,
                                      @PathVariable @NotNull Long spotId,
                                      @RequestBody @Valid CreateTradeRequestDto requestDto) {
        return tradeRequestService.create(userId, spotId, requestDto);
    }

    @PutMapping("/cancel/{userId}/{tradeRequestId}")
    @ResponseStatus(NO_CONTENT)
    public void cancel(@PathVariable @NotNull Long userId, @PathVariable @NotNull Long tradeRequestId) {
        tradeRequestService.cancel(userId, tradeRequestId);
    }

    @PutMapping("/{userId}/{tradeRequestId}")
    public TradeRequestFullDto update(@PathVariable @NotNull Long userId,
                                      @PathVariable @NotNull Long tradeRequestId,
                                      @RequestParam(defaultValue = "ACCEPTED") @NotNull RequestState state) {
        return tradeRequestService.update(userId, tradeRequestId, state);
    }

    @GetMapping("/{userId}/{tradeRequestId}")
    public TradeRequestFullDto get(@PathVariable @NotNull Long userId, @PathVariable @NotNull Long tradeRequestId) {
        return tradeRequestService.get(userId, tradeRequestId);
    }

    @GetMapping("/{userId}")
    public List<TradeRequestFullDto> getAll(@PathVariable @NotNull Long userId,
                                            @RequestParam(defaultValue = "PENDING") RequestState state,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size) {
        return tradeRequestService.getAll(userId, state, from, size);
    }

    @GetMapping("/owner/{userId}")
    public List<TradeRequestFullDto> getAllByOwner(@PathVariable @NotNull Long userId,
                                                   @RequestParam(defaultValue = "PENDING") RequestState state,
                                                   @RequestParam(defaultValue = "0") int from,
                                                   @RequestParam(defaultValue = "10") int size) {
        return tradeRequestService.getAllByOwner(userId, state, from, size);
    }
}
