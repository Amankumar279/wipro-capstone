package com.wipro.day3;



// Class Product
class Product {
    String name;
    double price;
    int quantity;

    // Constructor
    Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Method to calculate total price
    double calculateTotalPrice() {
        return price * quantity;
    }

    // Display product details
    void displayProduct() {
        System.out.println(name + " | Price: ₹" + price + " | Quantity: " + quantity +
                           " | Total: ₹" + calculateTotalPrice());
    }

    public static void main(String[] args) {
       
    		Product obj=new Product("Aman",78,4);
    		obj.displayProduct();
       
    }
}