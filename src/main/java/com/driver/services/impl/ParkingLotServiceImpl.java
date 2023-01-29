package com.driver.services.impl;

import com.driver.ParkPlus;
import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.PaymentRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public ParkingLot addParkingLot(String name, String address) {

        ParkingLot parkingLot = new ParkingLot(name,address);
        parkingLotRepository1.save(parkingLot);

        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {

        SpotType spotType;
        int wheels = numberOfWheels;
        if(wheels>2){
            spotType = SpotType.OTHERS;
        }else if(wheels<=4 && wheels >2) {
            spotType = SpotType.FOUR_WHEELER;
        }else{
            spotType = SpotType.TWO_WHEELER;
        }
        Spot spot = new Spot();
        spot.setSpotType(spotType);
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        spot.setParkingLot(parkingLot);


        spot.setPricePerHour(pricePerHour);

        spot.setOccupied(false);
        parkingLot.getSpotList().add(spot);
        spotRepository1.save(spot);

        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {

        Spot spot = spotRepository1.findById(spotId).get();
        spot.getParkingLot().getSpotList().remove(spot);
        spotRepository1.deleteById(spotId);

//        parkingLotRepository1.save(parkingLot);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        Spot spot = null;


        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();
        //parkingLot.setId(parkingLotId);
        for(Spot spot1 : spotList){
            if(spot1.getId() == spotId){
                spot = spot1;
            }
        }


        spot.setPricePerHour(pricePerHour);
        spot.setParkingLot(parkingLot);
        spotRepository1.save(spot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {

        parkingLotRepository1.deleteById(parkingLotId);
    }
}
