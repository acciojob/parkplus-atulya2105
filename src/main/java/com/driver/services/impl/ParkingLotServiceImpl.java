package com.driver.services.impl;

import com.driver.ParkPlus;
import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
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
        if(wheels==2){
            spotType = SpotType.TWO_WHEELER;
        }else if(wheels==4){
            spotType = SpotType.FOUR_WHEELER;
        }else{
            spotType = SpotType.OTHERS;
        }
        Spot spot = new Spot();
        spot.setSpotType(spotType);
        ParkingLot parkingLot = spot.getParkingLot();
        parkingLot.setId(parkingLotId);
        spot.setPricePerHour(pricePerHour);
        spot.setNumberOfWheels(numberOfWheels);

//        List<Spot> list = parkingLot.getSpotList();
//        list.add(spot);
//        parkingLot.setSpotList(list);
//        parkingLotRepository1.save(parkingLot);
        spotRepository1.save(spot);

        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {

        Spot spot = spotRepository1.findById(spotId).get();
        ParkingLot parkingLot = spot.getParkingLot();
        parkingLot.getSpotList().remove(spotId);
        spotRepository1.deleteById(spotId);

//        parkingLotRepository1.save(parkingLot);

    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        Spot spot = spotRepository1.findById(spotId).get();


        ParkingLot parkingLot = spot.getParkingLot();
        parkingLot.setId(parkingLotId);
        spot.setPricePerHour(pricePerHour);
        spotRepository1.save(spot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {

        parkingLotRepository1.deleteById(parkingLotId);
    }
}
