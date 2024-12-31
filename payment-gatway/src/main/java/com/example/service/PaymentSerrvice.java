package com.example.service;

import java.util.List;

import com.example.model.BankAccounts;
import com.example.model.RequestPamentCredential;

public interface PaymentSerrvice {

	void createUserAccout(BankAccounts course);

	boolean withdraAmmount(BankAccounts bk, double ammount);

	boolean paymentViaUpi(RequestPamentCredential rqdetails);

	boolean paymentViaAccNu(RequestPamentCredential rqdetails);


}