package com.wipro.day8;

public class FactoryDemoTask {
	

	    public static void main(String[] args) {

	        Notification n1 =
	            NotificationFactory.getNotification("email");
	        n1.send();

	        Notification n2 =
	            NotificationFactory.getNotification("sms");
	        n2.send();

	        Notification n3 =
	            NotificationFactory.getNotification("push");
	        n3.send();
	    }
	}

	interface Notification {
	    void send();
	}

	class Email implements Notification {
	    public void send() {
	        System.out.println("Email Sent");
	    }
	}

	class SMS implements Notification {
	    public void send() {
	        System.out.println("SMS Sent");
	    }
	}

	class Push implements Notification {
	    public void send() {
	        System.out.println("Push Notification Sent");
	    }
	}

	class NotificationFactory {

	    public static Notification getNotification(String type) {

	        if(type.equalsIgnoreCase("email"))
	            return new Email();

	        else if(type.equalsIgnoreCase("sms"))
	            return new SMS();

	        return new Push();
	    }
	}
