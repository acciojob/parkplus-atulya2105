package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table

public class Spot {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  int SpotId;

    private int numberOfWheels;
    private int pricePerHour;
    private SpotType spotType;
    private  boolean occupied;

    @ManyToOne
    @JoinColumn
    private ParkingLot parkingLot;

    @OneToMany(mappedBy = "spot",cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    public Spot(int numberOfWheels, int pricePerHour, SpotType spotType) {
        this.numberOfWheels = numberOfWheels;
        this.pricePerHour = pricePerHour;
        this.spotType = spotType;
    }


    public Spot() {
    }

    public Spot(int numberOfWheels, int pricePerHour, SpotType spotType, boolean occupied) {
        this.numberOfWheels = numberOfWheels;
        this.pricePerHour = pricePerHour;
        this.spotType = spotType;
        this.occupied = occupied;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(int pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public int getSpotId() {
        return SpotId;
    }

    public void setSpotId(int spotId) {
        SpotId = spotId;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
