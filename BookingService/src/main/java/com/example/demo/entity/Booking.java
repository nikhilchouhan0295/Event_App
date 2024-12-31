package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Booking {
	
	@Id
	@GeneratedValue
	private int bookingId;
	@NotNull
	private int seatCount;
	@NotNull
	private int userId;
	@NotNull
	private int eventId;
	private boolean bookingStatus;
	private int accountNo;
	private String upiId;


}
