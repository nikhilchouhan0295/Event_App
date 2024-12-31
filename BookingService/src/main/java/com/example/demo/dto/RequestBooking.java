package com.example.demo.dto;

import lombok.Data;

@Data
public class RequestBooking {
	
	private int seatCount;
	private int eventId;
	private int accountNo;
	private String upiId;
	private int userId;


}
