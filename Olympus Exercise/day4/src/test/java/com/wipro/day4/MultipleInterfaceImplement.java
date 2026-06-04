package com.wipro.day4;

public class MultipleInterfaceImplement {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Smartphone m1=new Smartphone();
		m1.playmusic();
		m1.takephoto();

	}

}

interface Camera {void takephoto();}

interface Musicplayer{void playmusic();}

class Smartphone implements Camera,Musicplayer{
	
	public void takephoto() {
		System.out.println("music palying");
	}
	
	public void playmusic(){
		System.out.println("camera is woring");
	}
	
}

