package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.model.BankAccounts;
import com.example.model.RequestPamentCredential;
import com.example.repository.BankRepo;

@SpringBootTest
class PaymentServiceImplTest {

	@Autowired private PaymentSerrvice paymentSerrvice;
	
	@MockBean private BankRepo bankRepo;
	
	@BeforeEach
	public void setupOfMockito() {
		Optional<BankAccounts> bankAcc = Optional.of(new BankAccounts(123, "Abhi", 12000.0, "av@upi"));
		Mockito.when(bankRepo.findByUpiId("av@upi")).thenReturn(bankAcc);
		Mockito.when(bankRepo.findById(123)).thenReturn(bankAcc);
		Mockito.when(bankRepo.save(bankAcc.get())).thenReturn(bankAcc.get());
	}

	@Test
	void testPaymentViaUpi() {
		RequestPamentCredential credential = new RequestPamentCredential();
		credential.setUpiId("av@upi");
		credential.setWithdrawAmmount(1000);
		boolean res = paymentSerrvice.paymentViaUpi(credential);
		assertEquals(true, res);
	}

	@Test
	void testPaymentViaAccNu() {
		RequestPamentCredential credential = new RequestPamentCredential();
		credential.setAccountNumber(123);
		credential.setWithdrawAmmount(1000);
		boolean res = paymentSerrvice.paymentViaAccNu(credential);
		assertEquals(true, res);
	}

	
}
