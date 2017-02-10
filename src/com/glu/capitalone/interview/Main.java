package com.glu.capitalone.interview;


import com.glu.capitalone.interview.handler.*;
import com.glu.capitalone.interview.utils.OutputUtils;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        usage();

        while(true) {
            String includingTestData = readLine("-include all test only data?(y/n): ");
            while (!validInput(includingTestData)) {
                OutputUtils.println("Your input is invalid, please try again!!!!");
                includingTestData = readLine("-include all test only data?(y/n):");
            }

            String ignoreDonuts = readLine("-ignore donuts Transactions?(y/n):");
            while (!validInput(ignoreDonuts)) {
                OutputUtils.println("Your input is invalid, please try again!!!!");
                ignoreDonuts = readLine("-ignore donuts Transactions?(y/n):");
            }

            String runProjectedTransaction = readLine("-include projected transactions(crystal-ball)?(y/n):");
            while (!validInput(runProjectedTransaction)) {
                OutputUtils.println("Your input is invalid, please try again!!!!");
                runProjectedTransaction = readLine("-include projected transactions(crystal-ball)?(y/n):");
            }

            String ignoreCcPayment = readLine("-ignore Credit Card Payment Transactions?(y/n):");
            while (!validInput(ignoreCcPayment)) {
                OutputUtils.println("Your input is invalid, please try again!!!!");
                ignoreCcPayment = readLine("-ignore Credit Card Payment Transactions?(y/n):");
            }

            OutputUtils.println("\n\n---------------------- Start Result---------------------");
            new TransactionDataHandler().handle(
                "Y".equals(includingTestData.trim().toUpperCase()), "Y".equals(ignoreDonuts.trim().toUpperCase()),
                "Y".equals(runProjectedTransaction.trim().toUpperCase()), "Y".equals(ignoreCcPayment.trim().toUpperCase()));

            OutputUtils.println(
                "---------------------- End Result---------------------\n\n");


            String tryAgain = readLine("try again?(y/n):");
            while (!validInput(tryAgain)) {
                OutputUtils.println("Your input is invalid, please try again!!!!");
                tryAgain = readLine("ignore Credit Card Payment Transactions?(y/n):");
            }
            if ("N".equalsIgnoreCase(tryAgain.trim())) {
                OutputUtils.println("goodbye!");
                break;
            }
        }
    }

    private static void usage() {
        OutputUtils.println("===================================== Welcome ==================================");
        OutputUtils.println("This Program loads a user's transactions from the GetAllTransactions endpoint");
        OutputUtils.println("and determine how much money the user spends and makes in each of the months.");
        OutputUtils.println("Output format like:");
        OutputUtils.println("{");
        OutputUtils.println(" \"2014-10\": {\"spent\": \"$200.00\", \"income\": \"$500.00\"},");
        OutputUtils.println(" \"2014-11\": {\"spent\": \"$1510.05\", \"income\": \"$1000.00\"},");
        OutputUtils.println(" ... ");
        OutputUtils.println(" \"2015-04\": {\"spent\": \"$300.00\", \"income\": \"$500.00\"},");
        OutputUtils.println(" \"average\": {\"spent\": \"$750.00\", \"income\": \"$950.00\"}");
        OutputUtils.println("}");
        OutputUtils.println("You can click 'ctrl-c' to exit program any time");
        OutputUtils.println("================================================================================\n");
    }

    /**
     *
     * @param input: user input the selection number
     * @return 0 if selection is invalid, otherwise selection number
     */
    public static boolean validInput(String input) {
        return (input != null  &&
            ("Y".equalsIgnoreCase(input.trim()) || "N".equalsIgnoreCase(input.trim())));
    }

    public static String readLine(String str) {
        try {
            System.out.print(str);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
            return buffer.readLine();
        } catch (IOException ex) {
            return "";
        }
    }
}
