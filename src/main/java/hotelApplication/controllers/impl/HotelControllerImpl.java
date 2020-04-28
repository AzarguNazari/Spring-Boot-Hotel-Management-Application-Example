package hotelApplication.controllers.impl;

import hotelApplication.controllers.HotelController;
import hotelApplication.exception.AbstractRestHandler;
import hotelApplication.exception.DataFormatException;
import hotelApplication.exception.ResourceNotFoundException;
import hotelApplication.models.Hotel;
import hotelApplication.service.HotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/example/v1/hotels")
@Api(tags = {"hotels"})
public class HotelControllerImpl extends AbstractRestHandler implements HotelController {

    @Autowired
    private HotelService hotelService;

    @Override
    public void createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = this.hotelService.createHotel(hotel);
    }

    
    @Override
    public @ResponseBody Page<Hotel> getAllHotel(@ApiParam(value = "The page number (zero-based)", required = true)
                                      @RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @ApiParam(value = "Tha page size", required = true)
                                      @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        return this.hotelService.getAllHotels(page, size);
    }

    @Override
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

    @Override
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

    @Override
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
