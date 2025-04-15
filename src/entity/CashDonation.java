package entity;

import java.time.LocalDateTime;

public class CashDonation extends Donation {
	public CashDonation(String donorName, double amount, LocalDateTime donationDate) {
		super(donorName, amount);
		this.donationDate = donationDate;
	}
	
	@Override
	public String toString() {
		return "Case Donation [DonationDate = " + donationDate + ", Donor Name = " + donorName + ", amount = " + amount;
	}
	
	@Override
	public void recordDonation() {
		System.out.println("Recording cash donation from " + donorName + " of amount $" + amount + " on " + donationDate);
	}
}
