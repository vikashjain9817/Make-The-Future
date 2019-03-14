package junitcucumber.service;


import java.math.BigDecimal;

import junitcucumber.beans.Customer;
import junitcucumber.beans.Wallet;
import junitcucumber.exception.DupicateMobileNumberException;
import junitcucumber.exception.InsufficientBalanceException;
import junitcucumber.exception.MobilenumberIsNotFoundException;


public interface WalletService {


	Customer showBalance(String mobileNo) throws MobilenumberIsNotFoundException;

	Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InsufficientBalanceException, MobilenumberIsNotFoundException;

	Customer depositAmount(String mobileNo, BigDecimal amount) throws MobilenumberIsNotFoundException;

	Customer withDrawAmount(String mobileNo, BigDecimal amount) throws InsufficientBalanceException, MobilenumberIsNotFoundException;

	Customer createAccount(String name, String mobileNo, Wallet wallet) throws DupicateMobileNumberException;

}