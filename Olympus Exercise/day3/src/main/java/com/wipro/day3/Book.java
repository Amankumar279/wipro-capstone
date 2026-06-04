package com.wipro.day3;

class Book {
    String title;
    String author;
    boolean isIssued;

    // Constructor
    Book(String title, String author, boolean isIssued) {
        this.title = title;
        this.author = author;
        this.isIssued = isIssued;
    }

    // Issue book
    void issueBook() {
        if (!isIssued) {
            isIssued = true;
            System.out.println(title + " has been issued.");
        } else {
            System.out.println(title + " is already issued.");
        }
    }

    // Return book
    void returnBook() {
        if (isIssued) {
            isIssued = false;
            System.out.println(title + " has been returned.");
        } else {
            System.out.println(title + " was not issued.");
        }
    }

    // Display status
    void displayStatus() {
        System.out.println("Title   : " + title);
        System.out.println("Author  : " + author);
        System.out.println("Status  : " + (isIssued ? "Issued" : "Available"));
        System.out.println("----------------------");
    }

    public static void main(String[] args) {

        // Passing arguments directly
        Book b1 = new Book("Java Basics", "James Gosling", false);
        Book b2 = new Book("Data Structures", "Mark Allen", true);

        // Display initial status
        b1.displayStatus();
        b2.displayStatus();

        // Perform operations
        b1.issueBook();
        b2.returnBook();

        // Display updated status
        b1.displayStatus();
        b2.displayStatus();
    }
}
