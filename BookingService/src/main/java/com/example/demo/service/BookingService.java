package com.example.demo.service;


import java.util.List;

import com.example.demo.dto.Events;
import com.example.demo.dto.RequestBooking;
import com.example.demo.entity.Booking;

public interface BookingService {
	
	Events bookTicketService(RequestBooking requestBooking);

	Booking findBookTicket(int id);

	List<Booking> getAllDetails();

	String deleteBookingTicket(int bookingId);	

}
