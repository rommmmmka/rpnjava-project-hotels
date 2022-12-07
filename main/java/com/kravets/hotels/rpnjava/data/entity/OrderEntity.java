package com.kravets.hotels.rpnjava.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kravets.hotels.rpnjava.misc.DateUtils;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate checkInDate;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate checkOutDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expireDateTime;

    private long cost;

    @ManyToOne
    @NonNull
    private UserEntity user;

    @ManyToOne
    @NonNull
    private RoomEntity room;

    @ManyToOne
    @NonNull
    private StatusEntity status;


    public OrderEntity() {
    }

    public OrderEntity(@NonNull LocalDate checkInDate, @NonNull LocalDate checkOutDate, @NonNull UserEntity user, @NonNull RoomEntity room, @NonNull StatusEntity status) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.user = user;
        this.room = room;
        this.status = status;
    }

    @JsonIgnore
    public String getExpireDateTimeFormatted() {
        return DateUtils.convertDateTimeToString(expireDateTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(@NonNull LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    @NonNull
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(@NonNull LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDateTime getExpireDateTime() {
        return expireDateTime;
    }

    public void setExpireDateTime(LocalDateTime expireDateTime) {
        this.expireDateTime = expireDateTime;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    @NonNull
    public UserEntity getUser() {
        return user;
    }

    public void setUser(@NonNull UserEntity user) {
        this.user = user;
    }

    @NonNull
    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(@NonNull RoomEntity room) {
        this.room = room;
    }

    @NonNull
    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(@NonNull StatusEntity status) {
        this.status = status;
    }
}
