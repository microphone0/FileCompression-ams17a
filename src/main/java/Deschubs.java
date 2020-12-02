/**
 * Program name: SchubsArc
 * Program description: Decompresses a archive or compressed file
 * Author: Adam Saxton
 * Date: 11/30/2020
 * Course: CS375 Software Engineering II
 * Compile instructions: javac SchubsArc.java
 * Execute instructions: java SchubsArc archive-name
 					  OR java SchubsArc archive-name
 */

import java.io.IOException;
import java.io.File;

public class Deschubs {

    private static final int R = 256;		 // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width
    public static boolean logging = true;

    // SchubsH trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch    = ch;
            this.freq  = freq;
            this.left  = left;
            this.right = right;
        }

        // is the node a leaf node?
        private boolean isLeaf() {
            assert (left == null && right == null) || (left != null && right != null);
            return (left == null && right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }


    public static void err_print(String msg) {
		if(logging)
	    	System.err.print(msg);
    }

    public static void err_println(String msg) {
		if(logging) {
			System.err.println(msg);
	    }
    }

	public static void expandLZW(BinaryIn in, BinaryOut out) {
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = in.readInt(W);
        String val = st[codeword];

        while (true) {
            out.write(val);
            codeword = in.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
        }
        out.close();
    }

    // expand SchubsH-encoded input from standard input and write to standard output
    public static void expandHuffman(BinaryIn in, BinaryOut out) {

        // read in SchubsH trie from input stream
        Node root = readTrie(in); 

        // number of bytes to write
        int length = in.readInt();

        // decode using the SchubsH trie
        for (int i = 0; i < length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = in.readBoolean();
                if (bit) x = x.right;
                else     x = x.left;
            }
            out.write(x.ch);
        }
        out.flush();
    }


    private static Node readTrie(BinaryIn in) {
        boolean isLeaf = in.readBoolean();
        if (isLeaf) {
	    char x = in.readChar();
	    err_println("t: " + x );
            return new Node(x, -1, null, null);
        }
        else {
	    err_print("f");
            return new Node('\0', -1, readTrie(in), readTrie(in));
        }
    }

    public static void expandArchive(String name) {
    	BinaryIn in = new BinaryIn(name);
        BinaryOut out = null;
        char sep =  (char) 255;  // all ones 11111111

        // continure reading from archive until empty
        while (!in.isEmpty()) {
            try {
                // get individual archived file filename
                int filenamesize = in.readInt();
	            sep = in.readChar();
	            String filename="";
	            for (int i=0; i<filenamesize; i++) {
	                // concatenate characters to string
                    filename = filename + in.readChar();
                }

                // get individual archived file content
	            sep = in.readChar();
	            long filesize = in.readLong();
	            sep = in.readChar();
	            out = new BinaryOut(filename);
	            for (int i=0; i<filesize; i++){
                    // copy input to output
                    out.write(in.readChar());
                }
                out.close();
	        } finally {
	            if (out != null)
	    	    out.close();
            }    
        }
    }

    public static void main(String[] args) throws IOException {
    	if (args.length <= 0) {
    		System.out.println("Too many or too little arguments.");
    		return;
    	}

    	for (int i=0; i < args.length; i++) {
    		String name = "";
        	String type = "";

        	// Get file name
        	for (int j = 0; j < args[i].length()-3; j++) {
                name = name + args[i].charAt(j);
            }

            // Get file type
            for (int j = args[i].length()-3; j < args[i].length(); j++) {
                type = type + args[i].charAt(j);
            }

            // System.out.println("Name: " + name);
            // System.out.println("Type: " + type);

            BinaryIn in = null;
            BinaryOut out = null;
            try {
            	in = new BinaryIn(args[i]);
                out = new BinaryOut(name+type);

                if (type.equals(".zl")) {
                    expandLZW(in, out);
                    expandArchive(name);
                }
                else if (type.equals(".ll")) {
                    expandLZW(in, out);
                }
                else if (type.equals(".hh")) {
                    expandHuffman(in, out);
                }
                else {
                    System.out.println("This file type not supported");
                }

            } finally {
            	if (out != null);
                    out.close();
            }

    	}
		
    }
}