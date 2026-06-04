package com.wipro.day3;

class Mobile {
    String brand;
    int batteryLevel;

    // Constructor
    Mobile(String brand, int batteryLevel) {
        this.brand = brand;
        this.batteryLevel = batteryLevel;
    }

    // Make Call
    void makeCall() {
        if (batteryLevel > 0) {
            System.out.println(brand + " is making a call...");
            batteryLevel -= 10;
        } else {
            System.out.println("Battery is empty. Please charge the mobile.");
        }
    }

    // Charge Battery
    void chargeBattery() {
        batteryLevel = 100;
        System.out.println(brand + " is fully charged.");
    }

    // Check Battery
    void checkBattery() {
        System.out.println(brand + " Battery Level: " + batteryLevel + "%");
    }

    public static void main(String[] args) {

        // Passing values directly
        Mobile m1 = new Mobile("Samsung", 50);

        m1.checkBattery();
        m1.makeCall();
        m1.checkBattery();
        m1.chargeBattery();
        m1.checkBattery();
    }
}