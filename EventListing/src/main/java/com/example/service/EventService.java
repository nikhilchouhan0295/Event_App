package com.example.service;

import org.springframework.http.ResponseEntity;
import com.example.model.EventDetails;

import jakarta.validation.Valid;

public interface EventService {

	ResponseEntity<?> addEvent(EventDetails e);

	ResponseEntity<?> getEvents();

	ResponseEntity<?> updateEvent(EventDetails e);

	ResponseEntity<?> deleteEvent(int id);

	ResponseEntity<?> getEventById(int id);

	ResponseEntity<?> getEventByType(String type);

}
