package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.Cat;
import entity.Dog;
import entity.Pet;
import exception.InvalidPetAgeException;
import util.DBConnUtil;
import java.util.List;
import java.util.ArrayList;

public class PetDAOImpl implements PetDAO {
	
	@Override
	public void addPet(Pet pet) throws InvalidPetAgeException {
	    if (pet.getAge() <= 0) {
	        throw new InvalidPetAgeException("Pet age must be greater than 0.");
	    }

	    Connection connection = DBConnUtil.getConnection("db.properties");
	    String INSERT_PET_QUERY = "INSERT INTO Pets (name, age, breed, pet_type) VALUES (?, ?, ?, ?)";
	    String INSERT_CAT_QUERY = "INSERT INTO Cats (cat_id, color) VALUES (?, ?)";

	    try (
	        PreparedStatement petStmt = connection.prepareStatement(INSERT_PET_QUERY, Statement.RETURN_GENERATED_KEYS)
	    ) {
	        petStmt.setString(1, pet.getName());
	        petStmt.setInt(2, pet.getAge());
	        petStmt.setString(3, pet.getBreed());
	        petStmt.setString(4, pet instanceof Dog ? "Dog" : "Cat");

	        petStmt.executeUpdate();

	        ResultSet generatedKeys = petStmt.getGeneratedKeys();
	        int petId = -1;
	        if (generatedKeys.next()) {
	            petId = generatedKeys.getInt(1);
	        }

	        if (pet instanceof Cat) {
	            String color = ((Cat) pet).getCatColor();
	            try (PreparedStatement catStmt = connection.prepareStatement(INSERT_CAT_QUERY)) {
	                catStmt.setInt(1, petId);
	                catStmt.setString(2, color);
	                catStmt.executeUpdate();
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	
	@Override
	public List<Pet> getAllPets() {
	    List<Pet> pets = new ArrayList<>();
	    String SELECT_ALL_PETS_QUERY = "SELECT * FROM Pets";
	    String SELECT_CAT_COLOR_QUERY = "SELECT color FROM Cats WHERE cat_id = ?";

	    try (Connection connection = DBConnUtil.getConnection("db.properties");
	         Statement stmt = connection.createStatement();
	         ResultSet rs = stmt.executeQuery(SELECT_ALL_PETS_QUERY)) {

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            int age = rs.getInt("age");
	            String breed = rs.getString("breed");
	            String petType = rs.getString("pet_type");

	            if ("Dog".equalsIgnoreCase(petType)) {
	                pets.add(new Dog(name, age, breed));
	            } else if ("Cat".equalsIgnoreCase(petType)) {
	                String color = "Unknown";
	                try (PreparedStatement catStmt = connection.prepareStatement(SELECT_CAT_COLOR_QUERY)) {
	                    catStmt.setInt(1, id);
	                    ResultSet catRs = catStmt.executeQuery();
	                    if (catRs.next()) {
	                        color = catRs.getString("color");
	                    }
	                }

	                pets.add(new Cat(name, age, breed, color));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return pets;
	}


}
