package junitcucumber.repo;

import junitcucumber.beans.Customer;

public interface WalletRepoInterface {

	boolean save(Customer Customer);

	Customer findOne(String mobileNo);

}