package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation = reservationRepository2.findById(reservationId).get();
        int bill = 0;
        int hours = reservation.getNumberOfHour();
        Spot spot = reservation.getSpot();
        int price = spot.getPricePerHour();
        bill = hours*price;
        if(amountSent<bill){
            throw new Exception("Insufficient Amount");
        }
        PaymentMode paymentMode;
        if(mode.equals("CASH")){
            paymentMode = PaymentMode.CASH;
        }else if(mode.equals("CARD")){
            paymentMode = PaymentMode.CARD;
        }else if (mode.equals("UPI")){
            paymentMode = PaymentMode.UPI;
        }else{
            throw new Exception("Payment mode not detected");
        }
        Payment payment = new Payment();
        payment.setPaymentCompleted(true);
        payment.setPaymentMode(paymentMode);
        payment.setReservation(reservation);
        paymentRepository2.save(payment);
        return payment;
    }
}
