package com.example.demo.dao;

import com.example.demo.model.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Gender gender;
    private Integer age;
    private LocalDateTime creationDate;

    // JPA
    public Account() {
    }

    public Account(String username, Gender gender, Integer age, LocalDateTime creationTime) {
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.creationDate = creationTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
