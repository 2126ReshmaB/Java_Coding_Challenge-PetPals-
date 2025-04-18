create schema PetPals1;
use PetPals1;

CREATE TABLE Pets (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age > 0),
    breed VARCHAR(100),
    pet_type ENUM('Dog', 'Cat') NOT NULL
);

CREATE TABLE Cats (
    cat_id INT PRIMARY KEY,
    color VARCHAR(50),
    FOREIGN KEY (cat_id) REFERENCES Pets(id) ON DELETE CASCADE
);

CREATE TABLE donations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    donor_name VARCHAR(100) NOT NULL,
    amount DECIMAL(10,2) NOT NULL CHECK (amount >= 10),
    donation_type ENUM('Cash', 'Item') NOT NULL,
    donation_date DATETIME NOT NULL,
    item_type VARCHAR(100)  
);
