package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.CashDonation;
import entity.Donation;
import entity.ItemDonation;
import exception.InsufficientFundsException;
import util.DBConnUtil;

public class DonationDAOImpl implements DonationDAO {
	
	@Override
	public void addDonation(Donation donation) throws InsufficientFundsException {
		
		if (donation.getAmount() < 100) {
            throw new InsufficientFundsException("Minimum donation amount is 100.");
        }
		
	    String INSERT_DONATION_QUERY = "INSERT INTO Donations (donor_name, amount, donation_type, donation_date, item_type) VALUES (?, ?, ?, ?, ?)";

	    try (Connection connection = DBConnUtil.getConnection("db.properties");
	         PreparedStatement stmt = connection.prepareStatement(INSERT_DONATION_QUERY)) {

	        stmt.setString(1, donation.getDonorName());
	        stmt.setDouble(2, donation.getAmount());

	        if (donation instanceof CashDonation) {
	            stmt.setString(3, "Cash");
	            stmt.setTimestamp(4, Timestamp.valueOf(((CashDonation) donation).getDonationDate()));
	            stmt.setString(5, null);
	        } else if (donation instanceof ItemDonation) {
	            stmt.setString(3, "Item");
	            stmt.setTimestamp(4, Timestamp.valueOf(((ItemDonation) donation).getDonationDate()));
	            stmt.setString(5, ((ItemDonation) donation).getItemType());
	        }

	        stmt.executeUpdate();

	    } catch (SQLException e) {
	        System.err.println("Error adding donation: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	@Override
	public List<Donation> getAllDonations() {
	    String SELECT_ALL_DONATIONS_QUERY = "SELECT * FROM donations";
	    List<Donation> donations = new ArrayList<>();

	    try (Connection connection = DBConnUtil.getConnection("db.properties");
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(SELECT_ALL_DONATIONS_QUERY)) {

	        while (rs.next()) {
	            String donorName = rs.getString("donor_name");
	            double amount = rs.getDouble("amount");
	            String donationType = rs.getString("donation_type");
	            LocalDateTime donationDate = rs.getTimestamp("donation_date").toLocalDateTime();

	            if ("Cash".equalsIgnoreCase(donationType)) {
	                donations.add(new CashDonation(donorName, amount, donationDate));
	            } else if ("Item".equalsIgnoreCase(donationType)) {
	                String itemType = rs.getString("item_type");
	                donations.add(new ItemDonation(donorName, amount, itemType, donationDate));
	            }
	        }

	    } catch (SQLException e) {
	        System.err.println("Error retrieving donations: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return donations;
	}


}
