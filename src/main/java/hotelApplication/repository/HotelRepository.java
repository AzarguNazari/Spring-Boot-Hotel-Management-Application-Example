package hotelApplication.repository;

import hotelApplication.models.Hotel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository can be used to delegate CRUD operations against the data source: http://goo.gl/P1J8QH
 */
@Repository
public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
    Hotel findHotelByCity(String city);
}
