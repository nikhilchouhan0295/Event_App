package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.exception.PaymentException;
import com.example.model.BankAccounts;
import com.example.model.RequestPamentCredential;
import com.example.repository.BankRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentSerrvice {
 
	@Autowired
	private BankRepo bankRepo;

	public void createUserAccout(BankAccounts course) {
		bankRepo.save(course);
		
	}
	
	public  boolean withdraAmmount(BankAccounts bk ,double ammount) {
		if(bk.getBalance()<ammount) throw new PaymentException("Insufficient Ammount in Acc");
		bk.setBalance(bk.getBalance() - ammount);
		if( bankRepo.save(bk) != null)
			return true;
		else return false;
	}
	
	public boolean paymentViaUpi(RequestPamentCredential rqdetails) {
		BankAccounts bankUser = bankRepo.findByUpiId(rqdetails.getUpiId()).orElseThrow(() -> new PaymentException("Check The Account No") );
		if(withdraAmmount(bankUser,rqdetails.getWithdrawAmmount())) {
			return true;
		}
		return false;
		
		
	}
	public boolean paymentViaAccNu(RequestPamentCredential rqdetails) {
		
		BankAccounts bankUser = bankRepo.findById(rqdetails.getAccountNumber()).orElseThrow(() -> new PaymentException("Check The Account No") );
		if(withdraAmmount(bankUser,rqdetails.getWithdrawAmmount())) {
			return true;
		}
		return false;
	}




}
