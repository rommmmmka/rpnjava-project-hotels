package com.kravets.hotels.rpnjava.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kravets.hotels.rpnjava.data.form.AddHotelForm;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
public class HotelEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Length(min = 6, max = 45)
    private String name;

    @Length(max = 300)
    private String description;

    @NonNull
    @Length(max = 40)
    private String coverPhoto;

    @ManyToOne
    @NonNull
    private CityEntity city;

    @JsonIgnore
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
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

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(@NonNull String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    @NonNull
    public CityEntity getCity() {
        return city;
    }

    public void setCity(@NonNull CityEntity city) {
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
