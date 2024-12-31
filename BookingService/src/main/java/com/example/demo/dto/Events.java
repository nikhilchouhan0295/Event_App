package com.example.demo.dto;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Events{
	
	private int bookingId;
	private LocalDate eventDate;
	private String eventType;
	private String eventName;
	private String eventVenue;
	private double ticketPrice;
	private int eventRating;
	private double totalPrice;
	private boolean bookingStatus;
	public boolean getBookingStatus() { return this.bookingStatus;}
}
