package com.wipro.day8;

public class ObserverPractice { public static void main(String[] args) {

    Display d1 = new Mobiles();
    Display d2 = new Website();
    Display d3 = new LED();

    Display arr[] = {d1,d2,d3};

    WeatherStation ws =
        new WeatherStation(arr);

    ws.notifyAllDisplays("Temp = 35C");
}
}

interface Display {
void update(String msg);
}

class Mobiles implements Display {
public void update(String msg) {
    System.out.println("Mobile: " + msg);
}
}

class Website implements Display {
public void update(String msg) {
    System.out.println("Website: " + msg);
}
}

class LED implements Display {
public void update(String msg) {
    System.out.println("LED: " + msg);
}
}

class WeatherStation {

Display arr[];

WeatherStation(Display arr[]) {
    this.arr = arr;
}

void notifyAllDisplays(String msg) {

    for(Display d : arr) {
        d.update(msg);
    }
}
}