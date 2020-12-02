/**
 * Program name: SchubsL
 * Program description: Compresses one or multiple file using the LZW method
 * Author: Adam Saxton
 * Date: 11/30/2020
 * Course: CS375 Software Engineering II
 * Compile instructions: javac SchubsL.java
 * Execute instructions: java SchubsL file1 [file2 ...]
 					  OR java SchubsL *.txt or blee*.txt or Blee.* etc.
 */

import java.io.IOException;
import java.io.File;

public class SchubsL {
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width

    public static void kindOfExtension(String file) throws IOException {
    	BinaryIn in = null;
    	BinaryOut out = null;
    	String name = "";
    	String type = "";
    	// Get file name
        for (int i = 0; i < file.length()-3; i++) {
            name = name + file.charAt(i);
        }

        // Get file type
        for (int i = file.length()-3; i < file.length(); i++) {
            type = type + file.charAt(i);
        }
    	if (type.equals(".zl")){
            in = new BinaryIn(file);
            out = new BinaryOut(file);
            compress(in, out);
        } else {
            in = new BinaryIn(file);
            out = new BinaryOut(file + ".ll");
            compress(in, out);
        }
    }

    public static void compress(BinaryIn in, BinaryOut out) throws IOException {
    	try {
	        String input = in.readString();
	        TST<Integer> st = new TST<Integer>();
	        for (int i = 0; i < R; i++)
	            st.put("" + (char) i, i);
	        int code = R+1;  // R is codeword for EOF

	        while (input.length() > 0) {
	            String s = st.longestPrefixOf(input);  // Find max prefix match s.
	            out.write(st.get(s), W);      // Print s's encoding.
	            int t = s.length();
	            if (t < input.length() && code < L)    // Add s to symbol table.
	                st.put(input.substring(0, t + 1), code++);
	            input = input.substring(t);            // Scan past s in input.
	        }
	        out.write(R, W);
	        out.close();
	    } finally {
	    	if (out != null)
                out.close();
	    }
    } 


    // public static void expand() {
    //     String[] st = new String[L];
    //     int i; // next available codeword value

    //     // initialize symbol table with all 1-character strings
    //     for (i = 0; i < R; i++)
    //         st[i] = "" + (char) i;
    //     st[i++] = "";                        // (unused) lookahead for EOF

    //     int codeword = BinaryStdIn.readInt(W);
    //     String val = st[codeword];

    //     while (true) {
    //         BinaryStdOut.write(val);
    //         codeword = BinaryStdIn.readInt(W);
    //         if (codeword == R) break;
    //         String s = st[codeword];
    //         if (i == codeword) s = val + val.charAt(0);   // special case hack
    //         if (i < L) st[i++] = val + s.charAt(0);
    //         val = s;
    //     }
    //     BinaryStdOut.close();
    // }



    public static void main(String[] args) throws IOException {
    	if (args.length <= 0) {
    		System.out.println("Too little arguments!");
    		return;
    	}

        for (int i=0; i < args.length; i++) {
        	File file = new File(args[i]);
        	if (!file.exists() || !file.isFile()) {
        		System.out.println(args[i]+" is not a file");
        	} else if (file.length() == 0) {
        		System.out.println(args[i]+" is empty and cannot compress.");
        	} else {
        		kindOfExtension(args[i]);
        	}	
        }
        //else if (args[0].equals("+")) expand();
        //else throw new RuntimeException("Illegal command line argument");
    }

}
