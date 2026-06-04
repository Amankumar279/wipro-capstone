package FrameworkPracticequestion;

import java.util.ArrayList;

public class Moviewatchlist {

    public static void main(String[] args) {

        // Movies stored in order added
        ArrayList<String> movies = new ArrayList<>();

        // Add movies
        movies.add("Pushpa");
        movies.add("KGF");
        movies.add("RRR");
        movies.add("Jawan");
        movies.add("Animal");

        // Display movies in insertion order
        System.out.println("Movie List:");
        for(String movie : movies) {
            System.out.println(movie);
        }

        // Search by name
        String searchMovie = "RRR";

        if(movies.contains(searchMovie)) {
            System.out.println("\nMovie Found: " + searchMovie);
        } else {
            System.out.println("\nMovie Not Found");
        }
    }
}