package junitcucumber;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junitcucumber.beans.Customer;
import junitcucumber.beans.Wallet;
import junitcucumber.repo.WalletRepoInterface;
import junitcucumber.repo.WalletRepositoryImp;
import junitcucumber.service.WalletService;
import junitcucumber.service.WalletServiceImp;

public class WalletSteps {

	private WalletRepoInterface walletRepo = new WalletRepositoryImp();
	private WalletService walletService = new WalletServiceImp(walletRepo);

	@Given("^the name is (.*) and mobile number is (.*)and balance is (\\d+) Rs$")
	public void getInformation(String name, String mobileNo, BigDecimal balance) throws Throwable {
		Customer customer = walletService.createAccount(name, mobileNo, new Wallet(balance));
		assertEquals(customer, walletService.showBalance(mobileNo));
	}

	@When("^(.*) of mobile number (.*) has balance (\\d+) give$")
	public void infoIsRight(String name, String mobileNo, BigDecimal balance) throws Throwable {
		walletService.createAccount(name, mobileNo, new Wallet(balance));
		assertEquals("Vikash", walletService.showBalance(mobileNo).getName());
	}

	@Then("^(.*) has mobile number of (.*) has balance will be (\\d+) Rs$")
	public void certifiedYes(String name, String mobileNo, BigDecimal balance) throws Throwable {
		assertEquals(balance, walletService.showBalance(mobileNo).getWallet().getBalance());
	}
	
	@Then("^Vikash has mobile number of (.*) and deposit (\\d+) Rs$")
	public void vikash_has_mobile_number_of_and_deposit_Rs(String arg1, BigDecimal arg2) throws Throwable {
	    walletService.depositAmount(arg1, arg2);
	    assertEquals(1600, walletService.showBalance(arg1).getWallet().getBalance().intValue());
	}
	
	@Then("^Vikash has mobile number of (.*) and withdraw (\\d+) Rs$")
	public void vikash_has_mobile_number_of_and_withdraw_Rs(String arg1, BigDecimal arg2) throws Throwable {
		walletService.withDrawAmount(arg1, arg2);
		assertEquals(800, walletService.showBalance(arg1).getWallet().getBalance().intValue());
		
	}
	
	@Then("^Vikash has mobile number of (.*) and transfer to (.*) (\\d+) Rs$")
	public void vikash_has_mobile_number_of_and_fundtransfer_Rs(String arg1, String arg2, BigDecimal arg3) throws Throwable {
		walletService.createAccount("balveer", "7599962922", new Wallet(new BigDecimal(1000)));
		walletService.fundTransfer(arg1, arg2, arg3);
		assertEquals(700, walletService.showBalance(arg1).getWallet().getBalance().intValue());
		assertEquals(1500, walletService.showBalance(arg2).getWallet().getBalance().intValue());
		
	}
}