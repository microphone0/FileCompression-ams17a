/**
 * Program name: FileCompressionTest
 * Program description: Test to see if FileCompressionTest is working
 * Author: Adam Saxton
 * Date: 11/30/2020
 * Course: CS375 Software Engineering II
 * Compile instructions: javac FileCompressionTest.java
 * Execute instructions: java FileCompressionTest
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import java.io.IOException;
import java.io.FileWriter;
import java.lang.Math;
import java.io.File;
import java.util.*;

public class FileCompressionTest {

	String dir1 = "src"+File.separator+"files"+File.separator;
    String dir2 = "src"+File.separator+"files"+File.separator;
    String dir3 = "src"+File.separator+"files"+File.separator;
	String dirToArcSolutions1 = dir1+"solution"+File.separator;
    String dirToArcSolutions2 = dir2+"solution"+File.separator;
    String dirToArcSolutions3 = dir3+"solution"+File.separator;
	SchubsL schubL;
    SchubsH schubH;
    SchubsArc schubArc;
    Deschubs deschub;

    // Code for this function inspired by https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
    public String randomFiles(String dir) throws IOException {
        
        // Initialize variables
        String fileContent = "";
        Random random = new Random();

        // Set limit to only be lower case letters
        int lowerLimit = 97; 
        int upperLimit = 122;
         
  
        // Create a StringBuffer to store the result with a random size
        int n = random.nextInt(100);
        int c = random.nextInt(100);
        StringBuffer name = new StringBuffer(n);
        StringBuffer content = new StringBuffer(c);
  
        // Create file's name
        for (int i = 0; i < n; i++) { 
            int nextRandomChar = lowerLimit + (int)(random.nextFloat() * (upperLimit - lowerLimit + 1));
            name.append((char)nextRandomChar); 
        }
        // Set and create file's name
        dir += (name.toString())+".txt";
        File f = new File(dir);
        f.createNewFile();

        // Reset limits to include any character
        lowerLimit = 32;
        upperLimit = 126;

        // Create file's content
        for (int i = 0; i < c; i++) { 
            int nextRandomChar = lowerLimit + (int)(random.nextFloat() * (upperLimit - lowerLimit + 1));
            content.append((char)nextRandomChar); 
        }
        // Set file's content
        fileContent += content.toString();
        FileWriter myWriter = new FileWriter(dir);
        myWriter.write(fileContent);
        myWriter.close();

        return dir;
    }

	public Boolean compare(String[] args) {
		try {
			File src = new File(args[0]);
			File dest = new File(args[1]);
			Boolean isEqual = FileUtils.contentEquals(src,dest);

			return isEqual;
		} catch (Exception e) {
			return false;
		}
	}

    public Boolean noCompArcFiles(String [] args) {
        for (int i=0; i<args.length; i++) {
            File file = new File(args[i]);
            if(file.exists()) {
                return false;
            }
        }
        return true;
    }

    public void deleteCompArcFiles(String[] args) {
        for (int i=0; i<args.length; i++) {
            File file = new File(args[i]);
            if(file.exists()) {
                file.delete();
            }
        }
        return;
    }

    public void makeTempCompArcFiles(String[] args) throws IOException {
        for (int i=0; i<args.length; i++) {
            File file = new File(args[i]);
            if(!file.exists()) {
                file.createNewFile();
            }
        }
        return;
    }

    @Test
    public void normalTestCaseLZW() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test1"+File.separator+"test1a.txt";
        dir2 += "Test1"+File.separator+"test1b.txt";
        dir3 += "Test1"+File.separator+"test1c.txt";
        dirToArcSolutions1 += "test1a.txt";
        dirToArcSolutions2 += "test1b.txt";
        dirToArcSolutions3 += "test1c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".ll";
        String compfile2 = dir2 + ".ll";
        String compfile3 = dir3 + ".ll";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubL.main(args);
        deschub.main(args2);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        String[] argsTest2 = {dir2, dirToArcSolutions2};
        String[] argsTest3 = {dir3, dirToArcSolutions3};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
        contents = compare(argsTest2);
        assertTrue(contents);
        contents = compare(argsTest3);
        assertTrue(contents);
    }

    @Test
    public void fileIsEmptyLZW() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test2"+File.separator+"test2a.txt";
        dir2 += "Test2"+File.separator+"test2b.txt";
        dir3 += "Test2"+File.separator+"test2c.txt";
        dirToArcSolutions1 += "test2a.txt";
        dirToArcSolutions2 += "test2b.txt";
        dirToArcSolutions3 += "test2c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".ll";
        String compfile2 = dir2 + ".ll";
        String compfile3 = dir3 + ".ll";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubL.main(args);

        // Send to compare to see if arcFile is correct
        Boolean contents = noCompArcFiles(args2);
        assertTrue(contents);
    }

    @Test
    public void fileDoesNotExistLZW() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test3"+File.separator+"test3a.txt";
        dir2 += "Test3"+File.separator+"test3b.txt";
        dir3 += "Test3"+File.separator+"test3c.txt";
        dirToArcSolutions1 += "test3a.txt";
        dirToArcSolutions2 += "test3b.txt";
        dirToArcSolutions3 += "test3c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".ll";
        String compfile2 = dir2 + ".ll";
        String compfile3 = dir3 + ".ll";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubL.main(args);

        // Send to compare to see if arcFile is correct
        Boolean contents = noCompArcFiles(args2);
        assertTrue(contents);
    }

    @Test
    public void compFileDoesExistLZW() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test4"+File.separator+"test4a.txt";
        dir2 += "Test4"+File.separator+"test4b.txt";
        dir3 += "Test4"+File.separator+"test4c.txt";
        dirToArcSolutions1 += "test4a.txt";
        dirToArcSolutions2 += "test4b.txt";
        dirToArcSolutions3 += "test4c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".ll";
        String compfile2 = dir2 + ".ll";
        String compfile3 = dir3 + ".ll";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        makeTempCompArcFiles(args2);
        schubL.main(args);
        deschub.main(args2);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        String[] argsTest2 = {dir2, dirToArcSolutions2};
        String[] argsTest3 = {dir3, dirToArcSolutions3};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
        contents = compare(argsTest2);
        assertTrue(contents);
        contents = compare(argsTest3);
        assertTrue(contents);
    }

    @Test
    public void fileIsDirectoryLZW() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test5"+File.separator+"test5a.txt";
        dir2 += "Test5"+File.separator+"test5b.txt";
        dir3 += "Test5"+File.separator+"test5c.txt";
        dirToArcSolutions1 += "test5a.txt";
        dirToArcSolutions2 += "test5b.txt";
        dirToArcSolutions3 += "test5c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".ll";
        String compfile2 = dir2 + ".ll";
        String compfile3 = dir3 + ".ll";
        String[] args2 = {compfile2, compfile3};
        String[] args3 = {compfile1};
        deleteCompArcFiles(args2);
        deleteCompArcFiles(args3);
        noCompArcFiles(args2);
        schubL.main(args);
        deschub.main(args3);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
    }

    @Test
    public void fileContainsManyCharsLZW() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test6"+File.separator+"test6a.txt";
        dir2 += "Test6"+File.separator+"test6b.txt";
        dir3 += "Test6"+File.separator+"test6c.txt";
        dirToArcSolutions1 += "test6a.txt";
        dirToArcSolutions2 += "test6b.txt";
        dirToArcSolutions3 += "test6c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".ll";
        String compfile2 = dir2 + ".ll";
        String compfile3 = dir3 + ".ll";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubL.main(args);
        deschub.main(args2);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        String[] argsTest2 = {dir2, dirToArcSolutions2};
        String[] argsTest3 = {dir3, dirToArcSolutions3};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
        contents = compare(argsTest2);
        assertTrue(contents);
        contents = compare(argsTest3);
        assertTrue(contents);
    }

    @Test
    public void fileContainsManyCharsAndMoreLZW() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test7"+File.separator+"test7a.txt";
        dir2 += "Test7"+File.separator+"test7b.txt";
        dir3 += "Test7"+File.separator+"test7c.txt";
        dirToArcSolutions1 += "test7a.txt";
        dirToArcSolutions2 += "test7b.txt";
        dirToArcSolutions3 += "test7c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".ll";
        String compfile2 = dir2 + ".ll";
        String compfile3 = dir3 + ".ll";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubL.main(args);
        deschub.main(args2);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        String[] argsTest2 = {dir2, dirToArcSolutions2};
        String[] argsTest3 = {dir3, dirToArcSolutions3};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
        contents = compare(argsTest2);
        assertTrue(contents);
        contents = compare(argsTest3);
        assertTrue(contents);
    }

    @Test
    public void userPassesInWrongNumArgumentsLZW() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test8"+File.separator+"test8a.txt";
        dir2 += "Test8"+File.separator+"test8b.txt";
        dir3 += "Test8"+File.separator+"test8c.txt";
        dirToArcSolutions1 += "test8a.txt";
        dirToArcSolutions2 += "test8b.txt";
        dirToArcSolutions3 += "test8c.txt";


        // Create arguements then send to program 
        String[] args = {};
        String compfile1 = dir1 + ".ll";
        String compfile2 = dir2 + ".ll";
        String compfile3 = dir3 + ".ll";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubL.main(args);

        Boolean contents = noCompArcFiles(args2);
        assertTrue(contents);
    }

    @Test
    public void dynamicTestLZW() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test9"+File.separator;
        dir2 += "Test9"+File.separator;
        dir3 += "Test9"+File.separator;
        dirToArcSolutions1 += "test9a.txt";
        dirToArcSolutions2 += "test9b.txt";
        dirToArcSolutions3 += "test9c.txt";


        // Create arguements then send to program
        // Delete all files in current directory
        File currentDir = new File(dir1);
        String[] currentDirFiles = currentDir.list();
        for(int i=0; i < currentDirFiles.length; i++){
            File currentFile = new File(currentDir.getPath(), currentDirFiles[i]);
            currentFile.delete();
        }
        // Generate random files
        dir1 = randomFiles(dir1);
        dir2 = randomFiles(dir2);
        dir3 = randomFiles(dir3); 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".ll";
        String compfile2 = dir2 + ".ll";
        String compfile3 = dir3 + ".ll";
        String[] args2 = {compfile1, compfile2, compfile3};
        schubL.main(args);
        deschub.main(args2);
    }

    @Test
    public void normalTestCaseHuff() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test10"+File.separator+"test10a.txt";
        dir2 += "Test10"+File.separator+"test10b.txt";
        dir3 += "Test10"+File.separator+"test10c.txt";
        dirToArcSolutions1 += "test10a.txt";
        dirToArcSolutions2 += "test10b.txt";
        dirToArcSolutions3 += "test10c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        schubH.main(args);
        String compfile1 = dir1 + ".hh";
        String compfile2 = dir2 + ".hh";
        String compfile3 = dir3 + ".hh";
        String[] args2 = {compfile1, compfile2, compfile3};
        deschub.main(args2);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        String[] argsTest2 = {dir2, dirToArcSolutions2};
        String[] argsTest3 = {dir3, dirToArcSolutions3};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
        contents = compare(argsTest2);
        assertTrue(contents);
        contents = compare(argsTest3);
        assertTrue(contents);
    }

    @Test
    public void fileIsEmptyHuff() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test11"+File.separator+"test11a.txt";
        dir2 += "Test11"+File.separator+"test11b.txt";
        dir3 += "Test11"+File.separator+"test11c.txt";
        dirToArcSolutions1 += "test11a.txt";
        dirToArcSolutions2 += "test11b.txt";
        dirToArcSolutions3 += "test11c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".hh";
        String compfile2 = dir2 + ".hh";
        String compfile3 = dir3 + ".hh";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubH.main(args);

        // Send to compare to see if arcFile is correct
        Boolean contents = noCompArcFiles(args2);
        assertTrue(contents);
    }

    @Test
    public void fileDoesNotExistHuff() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test12"+File.separator+"test12a.txt";
        dir2 += "Test12"+File.separator+"test12b.txt";
        dir3 += "Test12"+File.separator+"test12c.txt";
        dirToArcSolutions1 += "test12a.txt";
        dirToArcSolutions2 += "test12b.txt";
        dirToArcSolutions3 += "test12c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".hh";
        String compfile2 = dir2 + ".hh";
        String compfile3 = dir3 + ".hh";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubH.main(args);

        // Send to compare to see if arcFile is correct
        Boolean contents = noCompArcFiles(args2);
        assertTrue(contents);
    }

    @Test
    public void compFileDoesExistHuff() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test13"+File.separator+"test13a.txt";
        dir2 += "Test13"+File.separator+"test13b.txt";
        dir3 += "Test13"+File.separator+"test13c.txt";
        dirToArcSolutions1 += "test13a.txt";
        dirToArcSolutions2 += "test13b.txt";
        dirToArcSolutions3 += "test13c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".hh";
        String compfile2 = dir2 + ".hh";
        String compfile3 = dir3 + ".hh";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        makeTempCompArcFiles(args2);
        schubH.main(args);
        deschub.main(args2);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        String[] argsTest2 = {dir2, dirToArcSolutions2};
        String[] argsTest3 = {dir3, dirToArcSolutions3};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
        contents = compare(argsTest2);
        assertTrue(contents);
        contents = compare(argsTest3);
        assertTrue(contents);
    }

    @Test
    public void fileIsDirectoryHuff() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test14"+File.separator+"test14a.txt";
        dir2 += "Test14"+File.separator+"test14b.txt";
        dir3 += "Test14"+File.separator+"test14c.txt";
        dirToArcSolutions1 += "test14a.txt";
        dirToArcSolutions2 += "test14b.txt";
        dirToArcSolutions3 += "test14c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".hh";
        String compfile2 = dir2 + ".hh";
        String compfile3 = dir3 + ".hh";
        String[] args2 = {compfile2, compfile3};
        String[] args3 = {compfile1};
        deleteCompArcFiles(args2);
        deleteCompArcFiles(args3);
        noCompArcFiles(args2);
        schubH.main(args);
        deschub.main(args3);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
    }

    @Test
    public void fileContainsManyCharsHuff() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test15"+File.separator+"test15a.txt";
        dir2 += "Test15"+File.separator+"test15b.txt";
        dir3 += "Test15"+File.separator+"test15c.txt";
        dirToArcSolutions1 += "test15a.txt";
        dirToArcSolutions2 += "test15b.txt";
        dirToArcSolutions3 += "test15c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".hh";
        String compfile2 = dir2 + ".hh";
        String compfile3 = dir3 + ".hh";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubH.main(args);
        deschub.main(args2);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        String[] argsTest2 = {dir2, dirToArcSolutions2};
        String[] argsTest3 = {dir3, dirToArcSolutions3};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
        contents = compare(argsTest2);
        assertTrue(contents);
        contents = compare(argsTest3);
        assertTrue(contents);
    }

    @Test
    public void fileContainsManyCharsAndMoreHuff() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test16"+File.separator+"test16a.txt";
        dir2 += "Test16"+File.separator+"test16b.txt";
        dir3 += "Test16"+File.separator+"test16c.txt";
        dirToArcSolutions1 += "test16a.txt";
        dirToArcSolutions2 += "test16b.txt";
        dirToArcSolutions3 += "test16c.txt";


        // Create arguements then send to program 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".hh";
        String compfile2 = dir2 + ".hh";
        String compfile3 = dir3 + ".hh";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubH.main(args);
        deschub.main(args2);

        // Send to compare to see if arcFile is correct
        String[] argsTest1 = {dir1, dirToArcSolutions1};
        String[] argsTest2 = {dir2, dirToArcSolutions2};
        String[] argsTest3 = {dir3, dirToArcSolutions3};
        Boolean contents = compare(argsTest1);
        assertTrue(contents);
        contents = compare(argsTest2);
        assertTrue(contents);
        contents = compare(argsTest3);
        assertTrue(contents);
    }

    @Test
    public void userPassesInWrongNumArgumentsHuff() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test17"+File.separator+"test17a.txt";
        dir2 += "Test17"+File.separator+"test17b.txt";
        dir3 += "Test17"+File.separator+"test17c.txt";
        dirToArcSolutions1 += "test17a.txt";
        dirToArcSolutions2 += "test17b.txt";
        dirToArcSolutions3 += "test17c.txt";


        // Create arguements then send to program 
        String[] args = {};
        String compfile1 = dir1 + ".hh";
        String compfile2 = dir2 + ".hh";
        String compfile3 = dir3 + ".hh";
        String[] args2 = {compfile1, compfile2, compfile3};
        deleteCompArcFiles(args2);
        schubH.main(args);

        Boolean contents = noCompArcFiles(args2);
        assertTrue(contents);
    }

    @Test
    public void dynamicTestHuff() throws IOException {
        // Set strings to point to correct directory
        dir1 += "Test18"+File.separator;
        dir2 += "Test18"+File.separator;
        dir3 += "Test18"+File.separator;
        dirToArcSolutions1 += "test18a.txt";
        dirToArcSolutions2 += "test18b.txt";
        dirToArcSolutions3 += "test18c.txt";


        // Create arguements then send to program
        // Delete all files in current directory
        File currentDir = new File(dir1);
        String[] currentDirFiles = currentDir.list();
        for(int i=0; i < currentDirFiles.length; i++){
            File currentFile = new File(currentDir.getPath(), currentDirFiles[i]);
            currentFile.delete();
        }
        // Generate random files
        dir1 = randomFiles(dir1);
        dir2 = randomFiles(dir2);
        dir3 = randomFiles(dir3); 
        String[] args = {dir1, dir2, dir3};
        String compfile1 = dir1 + ".hh";
        String compfile2 = dir2 + ".hh";
        String compfile3 = dir3 + ".hh";
        String[] args2 = {compfile1, compfile2, compfile3};
        schubH.main(args);
        deschub.main(args2);
    }
}