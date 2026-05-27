package com.smartops.authservice.entity;

// To make ORM as Jakarta Persistence also called Java Persistence API(JPA)
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Marks this class as a JPA entity mapped to a database table
@Entity
@Table(name = "users")
// Generates default constructor. Required by JPA
@NoArgsConstructor
// Generates constructor with all fields
@AllArgsConstructor
public class User {

    //Primary key of the table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User details stored in DB
    private String username;
    private String email;
    private String password;
    private String role;
    private String branchId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
