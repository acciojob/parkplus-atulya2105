package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.*;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

//        Reservation reservation = new Reservation();
//        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
//        if(parkingLot==null){
//            throw new Exception("Cannot make reservation");
//        }
//        List<Spot> spotList = parkingLot.getSpotList();
//        Spot spot = null;
//        int mnPrice = Integer.MAX_VALUE;
//        for(Spot spot1 : spotList){
//            int wheels = spot1.getNumberOfWheels();
//            int price = spot1.getPricePerHour();
//
//            if(price<mnPrice && wheels>=numberOfWheels){
//                spot =spot1;
//            }
//
//        }
//        if(spot==null || spot.getOccupied()==true){
//            throw new Exception("Cannot make reservation");
//        }
//
//        spot.setOccupied(true);
//        reservation.setSpot(spot);
//        spot.setParkingLot(parkingLot);
//        reservation.setNumberOfHours(timeInHours);
//        User user = userRepository3.findById(userId).get();
//        reservation.setUser(user);
//        spotRepository3.save(spot);
//
//        reservationRepository3.save(reservation);
//        return reservation;
        if(!userRepository3.findById(userId).isPresent() || !parkingLotRepository3.findById(parkingLotId).isPresent()){
            throw new Exception("Cannot make reservation");
        }
        User user = userRepository3.findById(userId).get();
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

        List<Spot> spotList = parkingLot.getSpotList();

        Spot spot = null;
        int mnPrice = Integer.MAX_VALUE;
        for(Spot spot1 : spotList){
            int wheels = spot1.getNumberOfWheels();
            int price = spot1.getPricePerHour();

            if(price<mnPrice && wheels>=numberOfWheels){
                spot =spot1;
            }

        }
        if(spot==null || spot.getOccupied()==true){
            throw new Exception("Cannot make reservation");
        }

        spot.setOccupied(true);

        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setSpot(spot);
        reservation.setUser(user);

        spot.getReservationList().add(reservation);
        user.getReservationList().add(reservation);

        userRepository3.save(user);
        spotRepository3.save(spot);

        return reservation;


    }
}
