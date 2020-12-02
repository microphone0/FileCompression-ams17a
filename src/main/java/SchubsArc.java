/**
 * Program name: SchubsArc
 * Program description: Begin to copy n or all .txt files in the directory to one, long file
 * Author: Adam Saxton
 * Date: 11/30/2020
 * Course: CS375 Software Engineering II
 * Compile instructions: javac SchubsArc.java
 * Execute instructions: java SchubsArc archive-name file1 [file2 ...]
 					  OR java SchubsArc archive-name *.txt
 */

import java.io.IOException;
import java.io.File;

public class SchubsArc {

	public static void makeTars(File in, String fileName, BinaryOut out) throws IOException {

		BinaryIn bin = null;
		char separator =  (char) 255;  // all ones 11111111

		try {
			
		    // Read in first file
		    long filesize = in.length();
		    int filenamesize = fileName.length(); 

		    out.write(filenamesize);
		    out.write(separator);

		    out.write(fileName);
		    out.write(separator);

		    out.write(filesize);
		    out.write(separator);

		    bin = new BinaryIn(fileName);
			while (!bin.isEmpty()) {
				out.write(bin.readChar());
			}
		} finally {
		    System.out.println("It worked!");
		}
	}

    public static void main(String[] args) throws IOException {

    	if (args.length < 2) {
    		System.out.println("Too little arguments.");
    		return;
    	}
		
		BinaryOut out = null;
		File archive = null;
		File in = null;

		try {
			// Check if archive already exist and if it does delete it. Initialize if first, so we don't have duplicate files
			archive = new File(args[0]);
		    if (archive.exists()) {
		    	archive.delete();
		    }

		    if (args.length == 2) {
			    out = new BinaryOut(args[0]);
			    // archive file is at args[0]
			    // layout: file-name-length, separator, filename, file-size, file
			    for (int i=1; i < args.length; i++) {
			    	in = new File(args[i]);
			    	if (!in.exists() || !in.isFile()) return;
			    }
			    int argEnd = (args[1].length()-1)-6;
		    	String dir = args[1].substring(0,argEnd);
		    	File currentDir = new File(dir);
		        String[] currentDirFiles = currentDir.list();
		        for(int i=1; i < currentDirFiles.length; i++){
		        	// notice the input files start at arg[1], not arg[0]
					in = new File(currentDir.getPath(), currentDirFiles[i]);
		            makeTars(in, currentDirFiles[i], out);
		        }
		    } else {
			    for (int i=1; i < args.length; i++) {
			    	in = new File(args[i]);
			    	if (!in.exists() || !in.isFile()) return;
			    }
			    // archive file is at args[0]
			    // layout: file-name-length, separator, filename, file-size, file
			    out = new BinaryOut(args[0]);
			    // Loop through args to get all the file to put into archive-file
				for (int i=1; i < args.length; i++) {
					// notice the input files start at arg[1], not arg[0]
					in = new File(args[i]);
					makeTars(in, args[i], out);
				}
			}

		} finally {
			if (out != null)
				out.close();
		}
    }

}