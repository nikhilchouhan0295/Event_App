package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.EventDetails;
import com.example.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Event")
public class EventController {

	@Autowired
	private EventService service;
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody @Valid EventDetails e) {
		return service.addEvent(e);
	}
	
	@GetMapping("/allEvents")
	public ResponseEntity<?> allEvents(){
		return service.getEvents();
	}
	
	@GetMapping("EventbyId/{id}")
	public ResponseEntity<?> eventbyId(@PathVariable int id){
		return service.getEventById(id);
	}
	
	@GetMapping("EventbyType/{type}")
	public ResponseEntity<?> eventbyType(@PathVariable String type){
		return service.getEventByType(type);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody @Valid EventDetails e){
		return service.updateEvent(e);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable @Valid int id){
		return service.deleteEvent(id);
	}
}
