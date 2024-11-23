package org.example.airport.domain;

import jakarta.persistence.*;


@Table(name = "adminuser")
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    @Column(name="adminname")
    private String adminName;
    @Column(name = "adminpassword")
    private String adminPassword;


    public int getUid() {
        return uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }
    public String getAdminName() {
        return adminName;
    }
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}