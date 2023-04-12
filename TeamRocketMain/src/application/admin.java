package application;

import java.io.Serializable;

public class admin implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private String username; // primary key
    private String password;
    private String firstName;
    private String surName;
    private String email;
    
    
    private AccessLevel accessLevel; // Fix: Use AccessLevel enum as the type for accessLevel

    public enum AccessLevel {
        ADMIN,
        HANDLER,
        CREATOR,
        ANALYST 
        
        
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
	
}
