package com.springboot.example.service;

import com.springboot.example.repository.HotelRepository;
import com.springboot.example.models.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class HotelService {

    private static final Logger log = LoggerFactory.getLogger(HotelService.class);

    @Autowired
    private HotelRepository hotelRepository;

    public HotelService() {
    }

    public Hotel createHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Optional<Hotel> getHotel(long id) {
        return hotelRepository.findById(id);
    }

    public void updateHotel(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    public Page<Hotel> getAllHotels(Integer page, Integer size) {
        Page<Hotel> pageOfHotels = hotelRepository.findAll(PageRequest.of(page, size));
        return pageOfHotels;
    }
}
