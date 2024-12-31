package com.example.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPamentCredential {
	private int accountNumber;
	private double withdrawAmmount;
	private String upiId;
}
