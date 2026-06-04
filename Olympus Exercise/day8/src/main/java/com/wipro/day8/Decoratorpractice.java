package com.wipro.day8;

public class Decoratorpractice {


	    public static void main(String[] args) {

	        FileData file = new NormalFile();

	        file = new Compression(file);
	        file = new Encryption(file);
	        file = new Logging(file);

	        file.read();
	    }
	}

	interface FileData {
	    void read();
	}

	class NormalFile implements FileData {
	    public void read() {
	        System.out.println("Reading File");
	    }
	}

	abstract class FileDecorator
	implements FileData {

	    FileData file;

	    FileDecorator(FileData file) {
	        this.file = file;
	    }
	}

	class Compression extends FileDecorator {

	    Compression(FileData file) {
	        super(file);
	    }

	    public void read() {
	        file.read();
	        System.out.println("Compression Added");
	    }
	}

	class Encryption extends FileDecorator {

	    Encryption(FileData file) {
	        super(file);
	    }

	    public void read() {
	        file.read();
	        System.out.println("Encryption Added");
	    }
	}

	class Logging extends FileDecorator {

	    Logging(FileData file) {
	        super(file);
	    }

	    public void read() {
	        file.read();
	        System.out.println("Logging Added");
	    }
	}