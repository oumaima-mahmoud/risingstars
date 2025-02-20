package org.example;

import entities.*;
import Services.ReservationService;
import Services.TicketService;
import Services.ParkingService;
import java.sql.Date;

public class TestMain {
    public static void main(String[] args) {
        // Test TicketService
        TicketService ticketService = new TicketService();
        Ticket ticket = new Ticket(0, Date.valueOf("2023-12-25"), "Concert", 50.0, true);
        ticketService.ajouter(ticket);
        System.out.println("Ticket ajouté : " + ticketService.getOne(ticket));

        // Test ParkingService
        ParkingService parkingService = new ParkingService();
        Parking parking = new Parking(0, "A1", Date.valueOf("2023-12-25"), Date.valueOf("2023-12-26"), true);
        parkingService.ajouter(parking);
        System.out.println("Parking ajouté : " + parkingService.getOne(parking));

        // Test ReservationService
        ReservationService reservationService = new ReservationService();
        Reservation reservation = new Reservation(0, ticket, parking, StatutRes.EN_ATTENTE, new Date(System.currentTimeMillis()));
        reservationService.ajouter(reservation);
        System.out.println("Reservation ajoutée : " + reservation);
    }
}