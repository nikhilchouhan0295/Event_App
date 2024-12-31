package com.example.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class EventDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "mySeq")
	@SequenceGenerator(name="mySeq",initialValue = 100)
	private int eventId;
	@NotNull(message = "Invalid Date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate eventDate;
	@NotNull(message = "Invalid Event type")
	@Pattern(regexp="[a-zA-Z]+",message = "Invalid Event type")
	private String eventType;
	@NotEmpty(message = "Invalid Event name")
	@Pattern(regexp="[a-zA-Z ]+",message = "Invalid Event name")
	private String eventName;
	@NotNull(message = "Invalid Event venue")
	@Pattern(regexp="[a-zA-Z ]+",message = "Invalid Event Venue")
	private String eventVenue;
	@Pattern(regexp="^\\d+(\\.\\d{2})?$",message="Enter the price in the correct format.")
	private String ticketPrice;
	@NotNull(message = "Invalid Event rating")
	@Pattern(regexp="^[0-5]{1}$", message="Enter the rating in the correct format.")
	private String eventRating;
}
