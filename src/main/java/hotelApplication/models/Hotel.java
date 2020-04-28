package hotelApplication.models;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.*;

/*
 * a simple domain entity doubling as a DTO
 */
@Entity
@Table(name = "hotel")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Hotel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    String city;

    @Column
    private int rating;

    public Hotel() {
    }

    public Hotel(String name, String description, int rating) {
        this.name = name;
        this.description = description;
        this.rating = rating;
    }
}
