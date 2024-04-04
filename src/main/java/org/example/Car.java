package org.example;

import java.util.Date;

public class Car {
    private String brand;
    private String model;
    private String imc;
    private Date year;
    private int price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public Date getDate() {
        return year;
    }

    public void setDate(Date year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Car(String brand, String model, String imc, Date year, int price) {
        this.brand = brand;
        this.model = model;
        this.imc = imc;
        this.year = year;
        this.price = price;
    }

    public void display() {
        System.out.println(getBrand() +" "+ getModel());
    }
    public int getAnnee() {
        // Récupérer l'année de la voiture
        return year.getYear();
    }
}
