package com.example.parking.trade.service;

import com.example.parking.booking.model.Booking;
import com.example.parking.booking.repository.BookingRepository;
import com.example.parking.enums.RequestState;
import com.example.parking.exception.DataAccessException;
import com.example.parking.exception.NotFoundException;
import com.example.parking.spot.model.Spot;
import com.example.parking.spot.repository.SpotRepository;
import com.example.parking.trade.dto.CreateTradeRequestDto;
import com.example.parking.trade.dto.TradeRequestFullDto;
import com.example.parking.trade.mapper.TradeRequestMapper;
import com.example.parking.trade.model.TradeRequest;
import com.example.parking.trade.repository.TradeRequestRepository;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.parking.enums.RequestState.PENDING;
import static com.example.parking.enums.RequestState.REJECTED;
import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeRequestServiceImpl implements TradeRequestService {
    private final TradeRequestRepository tradeRequestRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final SpotRepository spotRepository;
    private final TradeRequestMapper tradeRequestMapper;

    @Override
    public TradeRequestFullDto create(Long userId, Long spotId, CreateTradeRequestDto tradeRequest) {
        Spot spot = findSpot(spotId);
        TradeRequest request = tradeRequestMapper.toTradeRequest(tradeRequest);
        request.setRequester(findUser(userId));
        request.setOwner(findOwnerOfTheLatestRequest(spot));
        request.setSpot(spot);
        request.setRequestDate(LocalDateTime.now());
        request.setRequestState(PENDING);
        TradeRequest saved = tradeRequestRepository.save(request);
        log.info("Request for spot trade has been created: {}", saved);
        return tradeRequestMapper.toTradeRequestFullDto(saved);
    }

    @Override
    public TradeRequestFullDto get(Long userId, Long tradeRequestId) {
        User user = findUser(userId);
        TradeRequest request = tradeRequestRepository.findById(tradeRequestId)
                .orElseThrow(() -> new NotFoundException("Request hasn't been found"));
        if (!user.equals(request.getOwner()) || !user.equals(request.getRequester())) {
            throw new DataAccessException("No access to perform this operation");
        }
        log.info("The request for trade was returned: {}", request);
        return tradeRequestMapper.toTradeRequestFullDto(request);
    }

    @Override
    public List<TradeRequestFullDto> getAll(Long userId, RequestState state, int from, int size) {
        PageRequest pages = PageRequest.of(from, size, DESC, "requestDate");
        User requester = findUser(userId);
        List<TradeRequest> requests =
                tradeRequestRepository.findAllByRequesterAndRequestState(requester, state, pages).getContent();
        log.info("User has returned the list of outgoing requests: {}", requests);
        return requests.stream().map(tradeRequestMapper::toTradeRequestFullDto).collect(Collectors.toList());
    }

    @Override
    public List<TradeRequestFullDto> getAllByOwner(Long userId, RequestState state, int from, int size) {
        PageRequest pages = PageRequest.of(from, size, DESC, "requestDate");
        User owner = findUser(userId);
        List<TradeRequest> requests =
                tradeRequestRepository.findAllByOwnerAndRequestState(owner, state, pages).getContent();
        log.info("User has returned the list of incoming requests: {}", requests);
        return requests.stream().map(tradeRequestMapper::toTradeRequestFullDto).collect(Collectors.toList());
    }

    @Override
    public void cancel(Long userId, Long tradeRequestId) {
        User user = findUser(userId);
        TradeRequest request = findTradeRequest(tradeRequestId);
        if (!request.getRequester().equals(user) || REJECTED.equals(request.getRequestState())) {
            throw new DataAccessException("Violation of data, you are not allowed to perform this operation");
        }
        request.setRequestState(REJECTED);
        TradeRequest updated = tradeRequestRepository.save(request);
        log.info("User canceled request for trading: {}", updated);
    }

    @Override
    public TradeRequestFullDto update(Long userId, Long tradeRequestId, RequestState state) {
        User user = findUser(userId);
        TradeRequest request = findTradeRequest(tradeRequestId);
        if (!request.getOwner().equals(user) || REJECTED.equals(request.getRequestState())) {
            throw new DataAccessException("Violation of data, this type of operation is not allowed");
        }
        request.setRequestState(state);
        TradeRequest saved = tradeRequestRepository.save(request);
        log.info("Updated data for trade request: {}", saved);
        return tradeRequestMapper.toTradeRequestFullDto(saved);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User hasn't been found"));
    }

    private Spot findSpot(Long spotId) {
        return spotRepository.findById(spotId)
                .orElseThrow(() -> new NotFoundException("Spot's data haven't been found"));
    }

    private TradeRequest findTradeRequest(Long tradeRequestId) {
        return tradeRequestRepository.findById(tradeRequestId)
                .orElseThrow(() -> new NotFoundException("Trade request data wasn't found"));
    }

    private User findOwnerOfTheLatestRequest(Spot spot) {
        PageRequest pageRequest = PageRequest.of(0, 1, DESC, "end");
        Booking booking = bookingRepository.findBookingBySpot(spot, pageRequest)
                .stream().findFirst().orElseThrow(() -> new NotFoundException("The spot is available for booking, " +
                        "you can't trade for it"));
        log.info("The information for booking was found: {}", booking);
        return booking.getBooker();
    }
}
