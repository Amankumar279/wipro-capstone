package com.wipro.day8;

public class Builderdemo {

	public static void main(String[] args) {
		
			Mobile android = new Mobile.Builder()

					.setBrand("Samsung")
					.setRam(8)
					.build();
			
			android.show();
			
			
			Mobile apple = new Mobile.Builder()

					.setBrand("Iphone17")
					.setRam(16)
					.setBattery(100)
					.build();
			
			apple.show();
		}

	}

	class Mobile {
		String brand;
		int ram;
		int battery;

		public Mobile(Builder obj) {
			this.brand = obj.brand;
			this.ram = obj.ram;
			this.battery = obj.battery;
		}

		void show() {
			System.out.println("Brand: " + brand + "\nRam:" + ram + "\nBattery:" + battery);
		}

		static class Builder {
			String brand;
			int ram;
			int battery;

			public Builder setBrand(String brand) {
				this.brand = brand;
				return this;
			}

			public Builder setRam(int ram) {
				this.ram = ram;
				return this;
			}

			public Builder setBattery(int battery) {
				this.battery = battery;
				return this;
			}

			public Mobile build() {
				return new Mobile(this);
			}

		}

	}