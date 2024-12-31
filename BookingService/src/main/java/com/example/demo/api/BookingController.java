package com.example.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Events;
import com.example.demo.dto.RequestBooking;
import com.example.demo.entity.Booking;
import com.example.demo.service.BookingService;

import jakarta.validation.Valid;


@RestController
public class BookingController {
	@Autowired
	private BookingService bookingservice;
	
	@PostMapping("/bookticket")
	public Events bookticket(@RequestBody @Valid RequestBooking booking,@RequestHeader(value="loggedInUser") int userId)
	{
		System.out.println(userId);
		booking.setUserId(userId);
		Events event = bookingservice.bookTicketService(booking);
		return event;
	}
	
	@GetMapping("/findBookticketById/{id}")
	public Booking findbooking(@PathVariable int id)
	{
		Booking booking= bookingservice.findBookTicket(id);
		return booking;
	}
	
	@GetMapping("/getAllTickets")
	public List<Booking> getAllTickets()
	{
		List<Booking> booking=bookingservice.getAllDetails();
		return booking;
	}
	
	@DeleteMapping("/deleteTicket/{bookingId}")
	public String deleteTicket(@PathVariable int bookingId)
	{
		String status=bookingservice.deleteBookingTicket(bookingId);
		return status;
	}
	
	
}
