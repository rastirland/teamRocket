package application;

import java.io.Serializable;
import java.util.Random;

public class admin implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private String username; // primary key
    private String password;
    private String firstName;
    private String surName;
    private String email;
    private String CreatorId;;
    
    
    private AccessLevel accessLevel; // Fix: Use AccessLevel enum as the type for accessLevel

    public enum AccessLevel {
        ADMIN,
        HANDLER,
        CREATOR,
        ANALYST,
        INACTIVE
        
        
    }

    // Constructor
    public admin(AccessLevel accessLevel) { // Fix: Rename to "admin"
        this.accessLevel = accessLevel;
    }

    // Getter and Setter methods
    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreatorId() {
		return CreatorId;
	}

	public void setCreatorId(String creatorId) {
		 String randomId = generateRandomId();
		    CreatorId = randomId;
	}
	
	
	public String generateRandomId() {
	    Random random = new Random();
	    int randomId = random.nextInt(900) + 100; // Generates a random number between 100 and 999 (inclusive)
	    return String.valueOf(randomId); // Converts the random number to a string and returns it
	}
	
	
}
