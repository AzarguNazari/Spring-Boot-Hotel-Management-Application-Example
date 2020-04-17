package com.springboot.example.controllers;

import com.springboot.example.models.Hotel;
import com.springboot.example.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import com.springboot.example.exception.DataFormatException;
import com.springboot.example.service.HotelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/example/v1/hotels")
@Api(tags = {"hotels"})
public class HotelController extends AbstractRestHandler {

    @Autowired
    private HotelService hotelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a hotel resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = this.hotelService.createHotel(hotel);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all hotels.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public @ResponseBody Page<Hotel> getAllHotel(@ApiParam(value = "The page number (zero-based)", required = true)
                                      @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @ApiParam(value = "Tha page size", required = true)
                                      @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        return this.hotelService.getAllHotels(page, size);
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single hotel.", notes = "You have to provide a valid hotel ID.")
    public @ResponseBody Hotel getHotel(@ApiParam(value = "The ID of the hotel.", required = true)
                             @PathVariable("id") Long id) {
        Optional<Hotel> hotel = this.hotelService.getHotel(id);

        if(hotel.isPresent()){
            return hotel.get();
        }
        else{
            throw new ResourceNotFoundException("resource not found");
        }
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a hotel resource.", notes = "You have to provide a valid hotel ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateHotel(@ApiParam(value = "The ID of the existing hotel resource.", required = true)
                                 @PathVariable("id") Long id, @RequestBody Hotel hotel) {
        Optional<Hotel> findHotel = this.hotelService.getHotel(id);
        if(findHotel.isPresent()){
            this.hotelService.deleteHotel(id);
        }
        else{
            throw new ResourceNotFoundException("resource not found");
        }
        if (id != hotel.getId()) throw new DataFormatException("ID doesn't match!");
        this.hotelService.updateHotel(hotel);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a hotel resource.", notes = "You have to provide a valid hotel ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteHotel(@ApiParam(value = "The ID of the existing hotel resource.", required = true)
                                 @PathVariable("id") Long id) {
        Optional<Hotel> hotel = this.hotelService.getHotel(id);
        if(hotel.isPresent()){
            this.hotelService.deleteHotel(id);
        }
        else{
            throw new ResourceNotFoundException("resource not found");
        }
    }
}
