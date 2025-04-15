package dao;

import java.util.List;

import entity.Donation;
import exception.InsufficientFundsException;

public interface DonationDAO {
	void addDonation(Donation donation) throws InsufficientFundsException ;
	List<Donation> getAllDonations();
}
