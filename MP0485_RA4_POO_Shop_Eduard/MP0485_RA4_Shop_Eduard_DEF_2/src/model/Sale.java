/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author EduardOnnichannn
 */
import java.util.ArrayList;

public class Sale {
    Cliente client;
    ArrayList<Product> products;
    Amount amount;

    public Sale(Cliente client, ArrayList<Product> products, Amount amount) {
        this.client = client;
        this.products = products;
        this.amount = amount;
    }

    public Cliente getClient() {
        return client;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Amount getAmount() {
        return amount;
    }
}