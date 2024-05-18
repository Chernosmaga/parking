package com.example.parking.spot.service;

import com.example.parking.exception.NotFoundException;
import com.example.parking.spot.dto.SpotRatingResponseDto;
import com.example.parking.spot.mapper.SpotMapper;
import com.example.parking.spot.model.SpotRating;
import com.example.parking.spot.repository.SpotRatingRepository;
import com.example.parking.spot.repository.SpotRepository;
import com.example.parking.user.model.User;
import com.example.parking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpotRatingServiceImpl implements SpotRatingService {
    private final SpotRatingRepository spotRatingRepository;
    private final SpotMapper spotMapper;
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;

    @Override
    public SpotRatingResponseDto createRating(SpotRatingResponseDto spotRating, String userPhone, Long spotId) {
        System.out.println("userPhone: " + userPhone);
        User user = userRepository.findByPhone(userPhone).orElseThrow(() -> new NotFoundException("The user wasn't found"));
        spotRepository.findById(spotId).orElseThrow(() -> new NotFoundException("Spot with id " + spotId + " hasn't been found"));

        Optional<SpotRating> existingSpotRating = spotRatingRepository.findByUserIdAndSpotId(user.getId(), spotId);
        if (existingSpotRating.isPresent()) {
            SpotRating oldSpotRating = existingSpotRating.get();
            log.info("The old review looked like this : {}", oldSpotRating);

            SpotRating newSpotRating = spotMapper.toSpotRating(spotRating);
            newSpotRating.setId(oldSpotRating.getId());
            newSpotRating.setSpotId(spotId);
            newSpotRating.setUserId(user.getId());
            newSpotRating.setComment(spotRating.getComment() != null ? spotRating.getComment() : "");
            newSpotRating.setDateTime(LocalDateTime.now());
            SpotRating savedSpotRating = spotRatingRepository.save(newSpotRating);

            log.info("The user has updated an old review about the Spot : {}", savedSpotRating);
            return spotMapper.toSpotRatingResponseDto(savedSpotRating);
        }

        SpotRating newSpotRating = spotMapper.toSpotRating(spotRating);
        newSpotRating.setSpotId(spotId);
        newSpotRating.setUserId(user.getId());
        newSpotRating.setComment(spotRating.getComment() != null ? spotRating.getComment() : "");
        newSpotRating.setDateTime(LocalDateTime.now());
        SpotRating savedSpotRating = spotRatingRepository.save(newSpotRating);

        log.info("The user has left a new review about the Spot : {}", savedSpotRating);
        return spotMapper.toSpotRatingResponseDto(savedSpotRating);
    }
}
