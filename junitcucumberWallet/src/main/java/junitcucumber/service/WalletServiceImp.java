package junitcucumber.service;


import java.math.BigDecimal;

import junitcucumber.beans.Customer;
import junitcucumber.beans.Wallet;
import junitcucumber.exception.DupicateMobileNumberException;
import junitcucumber.exception.InsufficientBalanceException;
import junitcucumber.exception.MobilenumberIsNotFoundException;
import junitcucumber.repo.WalletRepoInterface;

public class WalletServiceImp implements WalletService {
	
	
	WalletRepoInterface walletRepo;
	
	public WalletServiceImp(WalletRepoInterface walletRepo)
	{
		this.walletRepo = walletRepo;
	}
	
	
	@Override
	public Customer createAccount(String name, String mobileNo, Wallet wallet) throws DupicateMobileNumberException  // This Method creates the new wallet account.
	{
		
		Customer customer = new Customer(name, mobileNo, wallet); // Create The object of Customer and pass the value in constructor.
		
		if(walletRepo.findOne(mobileNo) == null) {
			walletRepo.save(customer);  // check whether return true or false. 
			return customer;
		}
		throw new DupicateMobileNumberException();

	}
	
	
	
	@Override
	public Customer showBalance(String mobileNo) throws MobilenumberIsNotFoundException  // This method takes mobile no as argument and return the balance of that customer.
	{	
		if(walletRepo.findOne(mobileNo) != null)
			return walletRepo.findOne(mobileNo);
		
		throw new MobilenumberIsNotFoundException();
	}
	
	
	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InsufficientBalanceException // This method takes the mobileNo of source and destination and withdraw the amount to destination account. 
, MobilenumberIsNotFoundException
	{
		if(walletRepo.findOne(sourceMobileNo) != null && walletRepo.findOne(targetMobileNo) != null) {
			Customer sourceCustomer = walletRepo.findOne(sourceMobileNo);
			Customer targetCustomer = walletRepo.findOne(targetMobileNo);
			BigDecimal sourceAmount = sourceCustomer.getWallet().getBalance();
			int i = sourceAmount.compareTo(amount);
			if(i == -1) {
			throw new InsufficientBalanceException();
			}
			targetCustomer.getWallet().setBalance(targetCustomer.getWallet().getBalance().add(amount));
			sourceCustomer.getWallet().setBalance(sourceCustomer.getWallet().getBalance().subtract(amount));
			return targetCustomer;
		}
		throw new MobilenumberIsNotFoundException();
		
	}
	
	
	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws MobilenumberIsNotFoundException // This method takes mobileNo and amount and deposit the amount to the given mobileNo account.
	{
		if(walletRepo.findOne(mobileNo) != null) {
			Customer customer = walletRepo.findOne(mobileNo);
			customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount));
			return customer;
		}
		throw new MobilenumberIsNotFoundException();
	}
	
	
	@Override
	public Customer withDrawAmount(String mobileNo, BigDecimal amount) throws InsufficientBalanceException // This method is used to withdraw the amount from the given mobileNo Account.
, MobilenumberIsNotFoundException
	{
		if(walletRepo.findOne(mobileNo) != null) {
			Customer customer = walletRepo.findOne(mobileNo);
			BigDecimal amountInCustomer = customer.getWallet().getBalance();
			int i = amountInCustomer.compareTo(amount);
			if(i == -1) {
				throw new InsufficientBalanceException();
			}
			customer.getWallet().setBalance(customer.getWallet().getBalance().subtract(amount));
			return customer;
		}
		throw new MobilenumberIsNotFoundException();
	}
	
}
