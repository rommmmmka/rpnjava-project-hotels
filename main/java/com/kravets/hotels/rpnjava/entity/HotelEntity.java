package com.kravets.hotels.rpnjava.entity;

import com.kravets.hotels.rpnjava.form.AddHotelForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String coverPhoto;

    @ManyToOne
    private CityEntity city;

    @OneToMany(mappedBy = "hotel")
    private List<RoomEntity> rooms = new ArrayList<>();


    public HotelEntity() {
    }

    public HotelEntity(AddHotelForm addHotelForm) {
        this.name = addHotelForm.getName();
        this.description = addHotelForm.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
        this.city = city;
    }

    public void setCityId(CityEntity city) {
        this.city = city;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }
}
