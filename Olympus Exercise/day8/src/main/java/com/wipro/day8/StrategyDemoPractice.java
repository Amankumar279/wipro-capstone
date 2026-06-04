package com.wipro.day8;

public class StrategyDemoPractice {

	

	    public static void main(String[] args) {

	        Navigation nav =
	            new Navigation(new FastestRoute());
	        nav.findRoute();

	        nav.setRoute(new ShortestRoute());
	        nav.findRoute();

	        nav.setRoute(new AvoidToll());
	        nav.findRoute();
	    }
	}

	interface RouteStrategy {
	    void route();
	}

	class FastestRoute implements RouteStrategy {
	    public void route() {
	        System.out.println("Fastest Route Selected");
	    }
	}

	class ShortestRoute implements RouteStrategy {
	    public void route() {
	        System.out.println("Shortest Route Selected");
	    }
	}

	class AvoidToll implements RouteStrategy {
	    public void route() {
	        System.out.println("Avoid Toll Route Selected");
	    }
	}

	class Navigation {

	    RouteStrategy strategy;

	    Navigation(RouteStrategy strategy) {
	        this.strategy = strategy;
	    }

	    void setRoute(RouteStrategy strategy) {
	        this.strategy = strategy;
	    }

	    void findRoute() {
	        strategy.route();
	    }
	}