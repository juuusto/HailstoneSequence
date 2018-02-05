package com.company;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

/**
 *This class does all the logic.
 *
 * @author Oskari Järvinen
 */
public class Main {
    static ArrayList<Integer> storage = new ArrayList();
    static int steps = 0;
    static int secondHighestNumber,firstNumber;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Input number: ");
        int number = scan.nextInt();
        firstNumber = number;
        calculateNextNumber(number);
        findSecondHighestNumber();
        try {
            createHTMLFile();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    /**
     * Used to find the second highest number in the sequence.
     */
    static void findSecondHighestNumber() {
        Collections.sort(storage);
        if(steps > 1) {
            secondHighestNumber = (storage.get(storage.size()-2));
        } else {
            secondHighestNumber = 1;
        }
    }
    /**
     * Calculates all the steps and stores all the numbers into a list.
     * @param number
     */
    static void calculateNextNumber(int number) {
        if (number == 1) {
            storage.add(number);
            findSecondHighestNumber();
        } else {
            if(number % 2 == 0) { //even
                storage.add(number);
                steps ++;
                number = number/2;
                calculateNextNumber(number);
            } else { //odd
                storage.add(number);
                steps ++;
                number = 3*number +1;
                calculateNextNumber(number);
            }
        }
    }
    /**
     * Used to create the HTML file and input the number.Creates the HTML document into src folder.
     * Using apache commons FileUtils, since it's way more clean and easier to read than java IO.
     */
    static void createHTMLFile() throws IOException {
        File htmlTemplateFile = new File("template.html");//get data from a template html file.
        String htmlString = FileUtils.readFileToString(htmlTemplateFile,"UTF-8" );
        htmlString = htmlString.replace("$initialNumber", Integer.toString(firstNumber));
        htmlString = htmlString.replace("$steps", Integer.toString(steps));
        htmlString = htmlString.replace("$secondHighestNumber", Integer.toString(secondHighestNumber));
        File newHtmlFile = new File("HMTLFile.html");
        FileUtils.writeStringToFile(newHtmlFile, htmlString, "UTF-8");
    }
}
