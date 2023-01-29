package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
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
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

        Reservation reservation = new Reservation();
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        if(parkingLot==null){
            throw new Exception("Cannot make reservation");
        }
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
        reservation.setSpot(spot);
        spot.setParkingLot(parkingLot);
        reservation.setNumberOfHour(timeInHours);
        User user = userRepository3.findById(userId).get();
        reservation.setUser(user);
        spotRepository3.save(spot);

        return reservation;
    }
}
