package com.example.jb.demoreg;

import java.io.Serializable;

public class User implements Serializable {
   private String name;
   private String nrc;
   private String age;
   private String dob;
   private String gender;
   private String Fname;
   private String email;
   private String phone;
   private byte[] img;
  

    public User(String name) {
        this.name = name;
    }

    public User(byte[] img) {
        this.img = img;
    }

    public User(String name, byte[] img) {
        this.name = name;
        this.img = img;
    }

    public User() {
    }

    public User(String name, String nrc, String age, String dob, String gender, String fname, String email, String phone) {
        this.name = name;
        this.nrc = nrc;
        this.age = age;
        this.dob = dob;
        this.gender = gender;
        Fname = fname;
        this.email = email;
        this.phone = phone;
    }

    public User(String name, String nrc, String age, String dob, String gender, String fname, String email, String phone, byte[] img) {
        this.name = name;
        this.nrc = nrc;
        this.age = age;
        this.dob = dob;
        this.gender = gender;
        Fname = fname;
        this.email = email;
        this.phone = phone;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
