package com.example.parking.spot.service;


import com.example.parking.spot.dto.SpotFullDto;
import com.example.parking.spot.dto.SpotUpdateDto;
import com.example.parking.spot.mapper.SpotMapper;
import com.example.parking.spot.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {
    //private final SpotRepository spotRepository;
    private final SpotMapper spotMapper;

    public SpotFullDto create(SpotFullDto spotFullDto) {
        return null;
    }

    public SpotFullDto update(Long spotId, SpotUpdateDto spotUpdateDto) {
        return null;
    }

    public SpotFullDto getData(Long spotId) {
        return null;
    }
}
