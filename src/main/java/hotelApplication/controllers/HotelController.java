package hotelApplication.controllers;

import hotelApplication.models.Hotel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"hotels"})
public interface HotelController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a hotel resource.", notes = "Returns the URL of the new resource in the Location header.")
    void createHotel(@RequestBody Hotel hotel);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all hotels.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    @ResponseBody Page<Hotel> getAllHotel(@ApiParam(value = "The page number (zero-based)", required = true) @RequestParam Integer page, @ApiParam @RequestParam Integer size);

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single hotel.", notes = "You have to provide a valid hotel ID.")
    @ResponseBody Hotel getHotel(@ApiParam(value = "The ID of the hotel.", required = true)
                                        @PathVariable("id") Long id);

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a hotel resource.", notes = "You have to provide a valid hotel ID in the URL and in the payload. The ID attribute can not be updated.")
    void updateHotel(@ApiParam(value = "The ID of the existing hotel resource.", required = true)
                            @PathVariable("id") Long id, @RequestBody Hotel hotel);

    //todo: @ApiImplicitParams, @ApiResponses
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a hotel resource.", notes = "You have to provide a valid hotel ID in the URL. Once deleted the resource can not be recovered.")
    void deleteHotel(@ApiParam(value = "The ID of the existing hotel resource.", required = true)
                            @PathVariable("id") Long id);
}
