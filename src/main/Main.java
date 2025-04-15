package main;

import java.time.LocalDateTime;
import java.util.Scanner;

import dao.*;
import entity.*;
import exception.InsufficientFundsException;

public class Main {
	public static void main(String args[]) throws InsufficientFundsException {
		PetDAO petDAO = new PetDAOImpl();
		DonationDAO donationDAO = new DonationDAOImpl();
		PetShelter petShelter = new PetShelter();
		Scanner sc = new Scanner(System.in);
		int x = 0;
		
		while(true) {
			if(x > 0) {
			System.out.println();
			}
			x += 1;
			System.out.println("-----------------------------------------------------------------");
			System.out.println("----------------Welcome to Pet Adoption Platform----------------");
			System.out.println("1. Add Pet");
			System.out.println("2. List Available Pets");
			System.out.println("3. Add Donation");
			System.out.println("4. List Donations");
			System.out.println("5. Exit");
			System.out.println("-----------------------------------------------------------------");
			System.out.print("Enter your Choice : ");
			int choice = sc.nextInt();
			sc.nextLine();
			
			switch(choice) {
			     case 1:
			    	 System.out.print("Enter your Pet Type (Dog / Cat) : ");
			    	 String petType = sc.next();
			    	 sc.nextLine();
			    	 System.out.print("Enter you Pet Name : ");
			    	 String name = sc.next();
			    	 sc.nextLine();
			    	 System.out.print("Enter you Pet Age : ");
			    	 int age = sc.nextInt();
			    	 sc.nextLine();
			    	 System.out.print("Enter you Pet Breed : ");
			    	 String breed = sc.next();
			    	 sc.nextLine();
			    	 
			    	 Pet pet;
			    	 
			    	 try {
			    		 if("Dog".equalsIgnoreCase(petType)) {
			    			 pet = new Dog(name, age, breed);
			    		 }
			    		 else if("Cat".equalsIgnoreCase(petType)) {
			    			 System.out.print("Enter Cat Color : ");
			    			 String color = sc.next();
			    			 sc.nextLine();
			    			 pet = new Cat(name, age, breed, color);
			    		 }
			    		 else {
			    			 System.out.println("Invalid pet type. Please enter either Dog or Cat.");
			    			 break;
			    		 }
			    		 
			    		 petDAO.addPet(pet);
			    		 petShelter.addPet(pet);
			    		 System.out.println("Pet added Successfully.");
			    	 }
			    	 catch(Exception e) {
			    		 System.out.println("Error : "+e.getMessage());
			    	 }
			    	 break;
			     case 2:
			    	 System.out.println("Available Pets : ");
			    	 for(Pet p : petShelter.listAvailablePets()) {
			    		 System.out.println(p);
			    	 }
			    	 break;
			     case 3:
			    	 System.out.print("Enter Donor Name : ");
			    	 String donorName = sc.next();
			    	 sc.nextLine();
			    	 System.out.print("Enter Donation Amount : ");
			    	 double amount = sc.nextDouble();
			    	 sc.nextLine();
			    	 
			    	 System.out.print("Is it a Cash or Item Donation ? (Cash / Item) : ");
			    	 String type = sc.next();
			    	 sc.nextLine();
			    	 LocalDateTime donationDate = LocalDateTime.now();
			    	 if("Cash".equalsIgnoreCase(type)) {
			    		 CashDonation cashDonation = new CashDonation(donorName, amount, donationDate);
			    		 donationDAO.addDonation(cashDonation);
			    	 }
			    	 else {
			    		 System.out.print("Enter Item Type : ");
			    		 String itemType = sc.next();
			    		 sc.nextLine();
			    		 ItemDonation itemDonation = new ItemDonation(donorName, amount, itemType, donationDate);
			    		 donationDAO.addDonation(itemDonation);
			    		 
			    	 }
			    	 System.out.println("Donation added successfully.");
			    	 break;
			     case 4:
			    	 System.out.println("Donations : ");
			    	 for(Donation donation : donationDAO.getAllDonations()) {
			    		 System.out.println(donation.getDonationDate()+"     "+donation.getDonorName()+"     "+donation.getAmount());
			    	 }
			    	 break;
			     case 5:
			    	 System.out.println("---------ThankYou----------");
			    	 sc.close();
			    	 return;
			    default:
			    	System.out.println("Invalid choice. Try again.");
			}
		}
	}

}
