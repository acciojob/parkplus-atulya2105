package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.*;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        try {
            User user = userRepository3.findById(userId).orElse(null);
            if (Objects.isNull(user)) {
                throw new Exception("Cannot make reservation");
            }
            ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).orElse(null);
            if (Objects.isNull(parkingLot)) {
                throw new Exception("Cannot make reservation");
            }
            int minPrice = Integer.MAX_VALUE;
            Spot requiredSpot = null;
            for (Spot spot : parkingLot.getSpotList()) {
                int NoOfWheelsPossible = Integer.MAX_VALUE;
                if (spot.getSpotType() == SpotType.TWO_WHEELER)
                    NoOfWheelsPossible = 2;
                else if (spot.getSpotType() == SpotType.FOUR_WHEELER)
                    NoOfWheelsPossible = 4;

                if (numberOfWheels <= NoOfWheelsPossible && !spot.getOccupied()) {
                    if ((spot.getPricePerHour() * timeInHours) < minPrice) {
                        minPrice = (spot.getPricePerHour() * timeInHours);
                        requiredSpot = spot;
                    }
                }
            }
            if (Objects.isNull(requiredSpot)) {
                throw new Exception("Cannot make reservation");
            }
            requiredSpot.setOccupied(true);
            spotRepository3.save(requiredSpot);
            Reservation reservation = new Reservation(timeInHours,requiredSpot);
            List<Reservation> userReservations = user.getReservationList();
            if (Objects.isNull(userReservations)) {
                userReservations = new ArrayList<>();
            }
            userReservations.add(reservation);
            userRepository3.save(user);
            return reservation;
        }catch (Exception e) {
            throw new Exception("Cannot make reservation");
        }

//
//        if(!userRepository3.findById(userId).isPresent() || !parkingLotRepository3.findById(parkingLotId).isPresent()){
//            throw new Exception("Cannot make reservation");
//        }
//        User user = userRepository3.findById(userId).get();
//        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
//
//        List<Spot> spotList = parkingLot.getSpotList();
//
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
//
//        Reservation reservation = new Reservation();
//        reservation.setNumberOfHours(timeInHours);
//        reservation.setSpot(spot);
//        reservation.setUser(user);
//
//        spot.getReservationList().add(reservation);
//        user.getReservationList().add(reservation);
//
//        userRepository3.save(user);
//        spotRepository3.save(spot);
//
//        return reservation;


    }
}
