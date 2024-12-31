package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.Events;
import com.example.demo.dto.MailDetails;
import com.example.demo.dto.Payment;
import com.example.demo.dto.RequestBooking;
import com.example.demo.dto.UserCredential;
import com.example.demo.entity.Booking;
import com.example.demo.repo.BookingRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingRepo bookingrepo;
	@Autowired
	RestTemplate restTemplate;
	
	public boolean mailnotification(MailDetails mail)
	{
		String mailurl="http://notification-service/sendMail";
		Boolean senddetails= restTemplate.postForObject(mailurl, mail,Boolean.class);
		return senddetails;
	}
	
	public UserCredential getUserDetails(int userId) {
		System.out.println(userId);
		//retrieve user details by user-service
		String url = "http://user-service/auth/getUserDetailsbyUserId/"+userId;
		UserCredential userCredential = restTemplate.getForObject(url, UserCredential.class);
		return userCredential;
	}
	
	public Booking copyRequestBookingToBooking(RequestBooking requestBooking) {
		Booking booking = new Booking();
		booking.setAccountNo(requestBooking.getAccountNo());
		booking.setEventId(requestBooking.getEventId());
		booking.setSeatCount(requestBooking.getSeatCount());
		booking.setUpiId(requestBooking.getUpiId());
		booking.setUserId(requestBooking.getUserId());
		return booking;
		
	}
	
	@Override
	public Events bookTicketService(RequestBooking requestBooking) {
		
		Booking booking =copyRequestBookingToBooking(requestBooking);
		//calling eventListing service 
		
		String url = "http://eventlisting/Event/EventbyId/" + booking.getEventId();
		Events event = restTemplate.getForObject(url, Events.class);
		double totalPrice = booking.getSeatCount() * event.getTicketPrice();
		event.setTotalPrice(totalPrice);
		
		Payment payment = new Payment();
		payment.setAccountNumber(booking.getAccountNo());
		payment.setWithdrawAmmount(event.getTotalPrice());
		payment.setUpiId(booking.getUpiId());
		
		String paymenturl;
		
		if(booking.getUpiId()==null) 
			paymenturl = "http://payment-gatway/paymentApi/paymentViaAccNu";
		else
			paymenturl = "http://payment-gatway/paymentApi/paymentViaUpi";
		//calling payment service to deduct amount
		boolean paymentStatus = restTemplate.postForObject(paymenturl, payment, Boolean.class);
			
			if (paymentStatus) {
				System.out.println(paymentStatus);
				
				event.setBookingStatus(paymentStatus);
				booking.setBookingStatus(paymentStatus);
			}
		
		
		
		UserCredential user=getUserDetails(booking.getUserId());
		System.out.println(user);
		
		MailDetails mail=new MailDetails();
		mail.setRecipient(user.getEmail());
		
		String msgbodydetails= "User Name: "+user.getUsername()+"\n"+
								"Event Name: "+event.getEventName()+"\n"+
								"Event Venue: "+event.getEventVenue()+"\n"+
								"Event Date: "+event.getEventDate()+"\n\n\n"+
								"YOUR BOOKING IS CONFIRMED "+"\n\n"
								;
		mail.setMsgBody(msgbodydetails);
		mail.setSubject("Event Details");
		
		System.out.println(mail);
		
		if(mailnotification(mail))
		{
			System.out.println("Mail Successfully Send.");
			
		}
		else System.out.println("Mail is not send.");
		
		System.out.println(event.getBookingStatus());
		
		booking=bookingrepo.save(booking);
		event.setBookingId(booking.getBookingId());
		return event;

	}

	@Override
	public Booking findBookTicket(int id) {
		Booking booking=bookingrepo.findById(id).get();
		return booking;
	}

	@Override
	public List<Booking> getAllDetails() {
		List<Booking> booking= bookingrepo.findAll();
		return booking;
	}

	@Override
	public String deleteBookingTicket(int bookingId) {
		bookingrepo.deleteById(bookingId);
		return "Booking Ticket Is Delete Successfully";
	}
	
	
}
