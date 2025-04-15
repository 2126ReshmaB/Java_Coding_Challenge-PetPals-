package entity;
import java.time.LocalDateTime;

public class ItemDonation extends Donation {
	private String itemType;
	
	
	public ItemDonation(String donorName, double amount,String itemType, LocalDateTime donationDate) {
		super(donorName, amount, donationDate);
		this.itemType = itemType;
	}
	
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	@Override
	public void recordDonation() {
		System.out.println("Recording Item donation from "+donorName+" : "+itemType + " valued at $ " + amount);
	}
}
