package com.example.parking.spot.service;

import com.example.parking.exception.NotFoundException;
import com.example.parking.spot.dto.SpotReviewsFullDto;
import com.example.parking.spot.dto.SpotReviewsGetSpotDto;
import com.example.parking.spot.dto.SpotReviewsGetUserDto;
import com.example.parking.spot.dto.UserReviewDto;
import com.example.parking.spot.mapper.SpotReviewsMapper;
import com.example.parking.spot.model.SpotReviews;
import com.example.parking.spot.repository.SpotReviewsRepository;
import com.example.parking.spot.repository.SpotRepository;
import com.example.parking.user.mapper.UserMapper;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpotReviewsServiceImpl implements SpotReviewsService {
    private final SpotReviewsRepository spotReviewsRepository;
    private final SpotReviewsMapper spotReviewsMapper;
    private final SpotRepository spotRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public SpotReviewsFullDto createReview(SpotReviewsFullDto spotRating, String userPhone, Long spotId) {

        User user = userRepository.findByPhone(userPhone).orElseThrow(() -> new NotFoundException("The user wasn't found"));
        spotRepository.findById(spotId).orElseThrow(() -> new NotFoundException("Spot with id " + spotId + " wasn't found"));

        Optional<SpotReviews> existingSpotRating = spotReviewsRepository.findByUserIdAndSpotId(user.getId(), spotId);
        if (existingSpotRating.isPresent()) {
            SpotReviews oldSpotReviews = existingSpotRating.get();
            log.info("The old review looked like this : {}", oldSpotReviews);
            SpotReviews newSpotReviews = spotReviewsMapper.toSpotReviews(spotRating);
            newSpotReviews.setId(oldSpotReviews.getId());
            newSpotReviews.setSpotId(spotId);
            newSpotReviews.setUser(user);
            newSpotReviews.setComment(spotRating.getComment() != null ? spotRating.getComment() : "");
            newSpotReviews.setDateTime(LocalDateTime.now());
            SpotReviews savedSpotReviews = spotReviewsRepository.save(newSpotReviews);
            log.info("The user has updated an old review about the Spot : {}", savedSpotReviews);

            SpotReviewsFullDto spotReviewsFullDto = spotReviewsMapper.toSpotReviewsFullDto(savedSpotReviews);
            spotReviewsFullDto.setOperationStatus("The review has been successfully updated");
            return spotReviewsFullDto;
        }

        SpotReviews newSpotReviews = spotReviewsMapper.toSpotReviews(spotRating);
        newSpotReviews.setSpotId(spotId);
        newSpotReviews.setUser(user);
        newSpotReviews.setComment(spotRating.getComment() != null ? spotRating.getComment() : "");
        newSpotReviews.setDateTime(LocalDateTime.now());
        SpotReviews savedSpotReviews = spotReviewsRepository.save(newSpotReviews);
        log.info("The user has left a new review about the Spot : {}", savedSpotReviews);

        SpotReviewsFullDto spotReviewsFullDto = spotReviewsMapper.toSpotReviewsFullDto(savedSpotReviews);
        spotReviewsFullDto.setOperationStatus("The review has been successfully created");
        return spotReviewsFullDto;
    }

    @Override
    public SpotReviewsGetSpotDto getSpotReviews(Long spotId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Page<SpotReviews> reviewsPage = spotReviewsRepository.findBySpotId(spotId, pageable);
        log.info("The list of reviews for spot with id {} has been received : {}", spotId, reviewsPage.getContent());

        List<UserReviewDto> userReviews = reviewsPage.getContent().stream()
                .map(review -> {
                    User user = review.getUser();
                    if (user == null) {
                        throw new NotFoundException("User not found");
                    }
                    return spotReviewsMapper.toUserReviewDto(userMapper.toUserShortDto(user), review);
                })
                .collect(Collectors.toList());

        SpotReviewsGetSpotDto response = new SpotReviewsGetSpotDto();
        response.setReviews(userReviews);
        response.setCurrentPage(reviewsPage.getNumber() + 1);
        response.setTotalPages(reviewsPage.getTotalPages());
        response.setNumberOfItems(reviewsPage.getSize());
        response.setTotalItems(reviewsPage.getTotalElements());

        return response;
    }

    @Override
    public SpotReviewsFullDto deleteSpotReview(String userPhone, Long spotId) {
        User user = userRepository.findByPhone(userPhone).orElseThrow(() -> new NotFoundException("The user wasn't found"));
        SpotReviews spotReviews = spotReviewsRepository.findByUserIdAndSpotId(user.getId(), spotId).orElseThrow(() -> new NotFoundException("Spot rating for user with id " + user.getId() + " and spot id " + spotId + " wasn't found"));

        spotReviewsRepository.deleteById(spotReviews.getId());
        log.info("The user with id " + user.getId() + " deleted the review about the Spot : {}", spotReviews);

        SpotReviewsFullDto spotReviewsFullDto = spotReviewsMapper.toSpotReviewsFullDto(spotReviews);
        spotReviewsFullDto.setOperationStatus("The review has been successfully deleted");
        return spotReviewsFullDto;
    }


    @Override
    public SpotReviewsGetUserDto getMyReviews(String userPhone, int from, int size) {
        User user = userRepository.findByPhone(userPhone).orElseThrow(() -> new NotFoundException("The user wasn't found"));
        log.info("A user with Id " + user.getId() + " requested a list of their reviews of the spots");

        Pageable pageable = PageRequest.of(from, size);
        Page<SpotReviews> userReviewsPage = spotReviewsRepository.findByUserId(user.getId(), pageable);
        log.info("The list of reviews has been received : {}", userReviewsPage.getContent());

        SpotReviewsGetUserDto spotReviewsGetUserDto = new SpotReviewsGetUserDto();
        spotReviewsGetUserDto.setUser(userMapper.toUserShortDto(user));
        spotReviewsGetUserDto.setReviews(userReviewsPage.getContent().stream()
                .map(spotReviewsMapper::toSpotReviewsShortDto)
                .collect(Collectors.toList()));
        spotReviewsGetUserDto.setCurrentPage(userReviewsPage.getNumber() + 1); // Spring Data JPA использует нумерацию с 0, поэтому добавляем 1
        spotReviewsGetUserDto.setTotalPages(userReviewsPage.getTotalPages());
        spotReviewsGetUserDto.setNumberOfItems(userReviewsPage.getSize());
        spotReviewsGetUserDto.setTotalItems(userReviewsPage.getTotalElements());

        return spotReviewsGetUserDto;
    }
}
