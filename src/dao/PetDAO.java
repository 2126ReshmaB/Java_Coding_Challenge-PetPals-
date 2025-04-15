package dao;

import java.util.List;
import entity.Pet;
import exception.InvalidPetAgeException;

public interface PetDAO {
	void addPet(Pet pet) throws InvalidPetAgeException;
	List<Pet> getAllPets();
}
