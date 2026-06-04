package com.wipro.day8;





public class Observerdemo {

	public static void main(String[] args) {

		Subscribers user1 = new User1("Sakshi");

		Subscribers user2 = new User1("Prashant");

		Subscribers users[] = { user1, user2 };
		YoutubeApp app = new YoutubeApp(users);
		app.notifyUserService("new video is uploaded!");
		
	}

}

interface Subscribers {
	void updates(String message);
}

class User1 implements Subscribers {

	String name;

	public User1(String name) {
		this.name = name;
	}

	public void updates(String message) {
		System.out.println(name + " - " + message);
	}
}

class User2 implements Subscribers {

	String name;

	public User2(String name) {
		this.name = name;
	}

	public void updates(String message) {
		System.out.println(name + " - " + message);
	}
}

class YoutubeApp {
	  Subscribers users[];

	public YoutubeApp(Subscribers users[]) {
		this.users = users;
	}

	public void notifyUserService(String msg) {
		for (Subscribers user : users) {
			user.updates(msg);
		}
	}

}
