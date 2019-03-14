package junitcucumber.repo;



import java.util.HashMap;
import java.util.Map;

import junitcucumber.beans.Customer;


public class WalletRepositoryImp implements WalletRepoInterface {
	
	Map<String, Customer> customers  = new HashMap<String, Customer>();
	
	@Override
	public boolean save(Customer customer) {
		
		customers.put(customer.getMobileNo(), customer);
		return true;
	}
	
	
	@Override
	public Customer findOne(String mobileNo) {
		
		for (Map.Entry<String, Customer> entry : customers.entrySet())  {
				
			if(entry.getValue().getMobileNo().equals(mobileNo))
				return entry.getValue();
			  
		}
		return null;
	}
}
