package com.example.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.model.EventDetails;

@Repository
public interface EventRepo extends JpaRepository<EventDetails, Integer>{
	List<EventDetails> findByEventType(String event);
}
