package com.example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.model.EventDetails;
import com.example.repo.EventRepo;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventRepo repo;
	
	@Override
	public ResponseEntity<?> addEvent(EventDetails e) {
		return new ResponseEntity<>("Event "+repo.save(e).getEventName()+" added successfully.",HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<?> getEvents() {
		List ls = repo.findAll();
		if(ls.isEmpty()) return new ResponseEntity<>("No event to be displayed",HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(ls,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getEventById(int id) {
		EventDetails e =  repo.findById(id).orElseThrow(()->new RuntimeException("Event by id: "+id+" not found"));
		return new ResponseEntity<>(e,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> getEventByType(String type) {
		List ls = repo.findByEventType(type);
		if(ls.isEmpty()) return new ResponseEntity<>("No event to be displayed",HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(ls,HttpStatus.OK);
	}


	@Override
	public ResponseEntity<?> updateEvent(EventDetails e) {
		repo.findById(e.getEventId()).orElseThrow(()->new RuntimeException("Event by id: "+e.getEventId()+" not found"));
		EventDetails E =  repo.save(e);
		return new ResponseEntity<>(E,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteEvent(int id) {
		repo.findById(id).orElseThrow(()->new RuntimeException("Event by id: "+id+" not found"));
		repo.deleteById(id);
		return new ResponseEntity<>("Event with event id: "+id+" deleted successfully.",HttpStatus.OK);
	}
}
