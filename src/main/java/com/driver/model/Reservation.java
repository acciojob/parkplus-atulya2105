package com.driver.model;

import javax.persistence.*;

@Entity
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int Id;

    private  int numberOfHour ;
    @ManyToOne
    @JoinColumn
    private Spot spot;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToOne(mappedBy = "reservation",cascade = CascadeType.ALL)
    private  Payment payment;

    public Reservation(int numberOfHour, Spot spot) {
        this.numberOfHour = numberOfHour;
        this.spot = spot;
    }

    public Reservation() {
    }

    public Reservation(int numberOfHour) {
        this.numberOfHour = numberOfHour;
    }

    public int getId() {
        return Id;
    }

    public void setId(int reservationId) {
        this.Id = reservationId;
    }

    public int getNumberOfHour() {
        return numberOfHour;
    }

    public void setNumberOfHour(int numberOfHour) {
        this.numberOfHour = numberOfHour;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
