package com.miked;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
//global array lists for use whereever I may need them
    static Scanner numberScanner = new Scanner(System.in);
    static ArrayList<String> item = new ArrayList<>();
    static ArrayList<String> expense = new ArrayList<>();
    static ArrayList<String > revenue = new ArrayList<>();

    public static void main(String[] args) {
        //file to read from
        String filename = "coffee.txt";
        //try with resources
        try (BufferedReader bReader = new BufferedReader(new FileReader(filename))) {
            //read each line in the file and split it up for calculations and writing
            String line = bReader.readLine();
            while (line != null) {
                String[] separated = line.split(";");
                item.add(separated[0]);//add item names to item ArrayList
                expense.add(separated[1]);//add expense numbers to expense ArrayList
                revenue.add(separated[2]);//add revenue numbers to revenue ArrayList
                line = bReader.readLine();//move on to next line for reading next item etc..
            }
            bReader.close();//close the reader
            //Print for testing
//            System.out.println("Item: " + item);
//            System.out.println("Expense: " + expense);
//            System.out.println("Revenue: " + revenue);
        }//catch and display IOExceptions
        catch (IOException ioe) {
            System.out.println("Could not open or read " + filename + "," + ioe);

        }//try with resources
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter("coffeeShopReport.txt"))) {
            //new ArrayLists for converting strings to doubles(numbers actually have value)
            ArrayList<Double> expenseArr = new ArrayList<>();
            ArrayList<Double> revenueArr = new ArrayList<>();
            //loop over string ArrayLists and convert to Double
            for (int a = 0; a < item.size(); a++) {
                expenseArr.add(Double.parseDouble(expense.get(a)));
                revenueArr.add(Double.parseDouble(revenue.get(a)));
            }//Call methods for all calculations
            ArrayList<Double> quantitySold = getNumSold();
            ArrayList<Double> calculatedExpenses = getExpenses(expenseArr, quantitySold);
            ArrayList<Double> calculatedRevenue = getRevenue(revenueArr, quantitySold);
            ArrayList<Double> calculatedProfit = getProfit(calculatedExpenses, calculatedRevenue);
            //variables for calling methods to calculate totals
            double totalExpenses = getTotalExpenses(calculatedExpenses);
            double totalRevenues = getTotalRevenues(calculatedRevenue);
            double totalProfit = getTotalProfit(calculatedProfit);
            //loop over ArrayLists to print/write to report for each item

            for (int a = 0; a < item.size(); a++) {
                bWriter.write(item.get(a).toUpperCase() + ": Sold: " + String.format("%.0f", quantitySold.get(a)) + ", Expenses: $"
                        + String.format("%.2f", calculatedExpenses.get(a)) + ", Revenue: $" + String.format("%.2f", calculatedRevenue.get(a))
                        + ", Profit: $" + String.format("%.2f", calculatedProfit.get(a)) + "\n");
                //print for testing and confirming writing
                System.out.println(item.get(a).toUpperCase() + ": Sold: " + String.format("%.0f", quantitySold.get(a)) + ", Expenses: $"
                        + String.format("%.2f", calculatedExpenses.get(a)) + ", Revenue: $" + String.format("%.2f", calculatedRevenue.get(a))
                        + ", Profit: $" + String.format("%.2f", calculatedProfit.get(a)));
                //System.out.println(calculatedProfit);(TESTING)
            }//write totals to report file
            bWriter.write("\n");
            bWriter.write("TOTAL EXPENSES: $" + String.format("%.2f", totalExpenses) + " TOTAL REVENUE: $"
                    + String.format("%.2f", totalRevenues) + " TOTAL PROFIT: $" + String.format("%.2f", totalProfit));
            System.out.println("TOTAL EXPENSES: $" + String.format("%.2f", totalExpenses) + " TOTAL REVENUE: $"
                    + String.format("%.2f", totalRevenues) + " TOTAL PROFIT: $" + String.format("%.2f", totalProfit));

        }//catch IOException
        catch (IOException ioe) {
            System.out.println("Could not open or write to file");
        }//catch input exception if user spells out number
        catch (InputMismatchException mismatch) {
            System.out.println("Please enter numbers only. " + mismatch);

        }
    }
    //method for calculating total profit
    private static double getTotalProfit(ArrayList<Double> calculatedProfit) {
        double totalProfit = 0;
        for(int i = 0; i < item.size(); i++){
            totalProfit += calculatedProfit.get(i);
        }
        return totalProfit;
    }
    //method for calculating total revenue
    private static double getTotalRevenues(ArrayList<Double> calculatedRevenue) {
        double totalRevenues = 0;
        for(int i = 0; i < item.size(); i++){
            totalRevenues += calculatedRevenue.get(i);
        }
        return totalRevenues;
    }
    //method for calculating total expenses
    private static double getTotalExpenses(ArrayList<Double> calculatedExpenses) {
        double totalExpenses = 0.0;
        for (int i = 0; i < item.size(); i++){
            totalExpenses += calculatedExpenses.get(i);
        }
        return totalExpenses;
    }
    //method for calculating profit for each item
    private static ArrayList<Double> getProfit(ArrayList<Double> calculatedExpenses, ArrayList<Double> calculatedRevenue) {
        ArrayList<Double> calculatedProfit = new ArrayList<>();
        for (int i = 0; i < item.size(); i++) {
            calculatedProfit.add(calculatedRevenue.get(i) - calculatedExpenses.get(i));
        }
        return calculatedProfit;
    }
    //method to ask user for input (number of each item sold)
    private static ArrayList<Double> getNumSold() {
        ArrayList<Double> quantitySold = new ArrayList<>();
        for (int i = 0; i < item.size(); i++) {
            System.out.println("How many " + item.get(i) + " were sold?");
            double quantity = numberScanner.nextDouble();
            quantitySold.add(quantity);
            //System.out.println(quantitySold);
        }
        return quantitySold;
    }
    //method to calculate revenue for each item sold
    private static ArrayList<Double> getRevenue(ArrayList<Double> revenueArr, ArrayList<Double> quantitySold) {
        ArrayList<Double> calculatedRevenue = new ArrayList<>();
        for(int i=0; i<item.size(); i++){
            calculatedRevenue.add(quantitySold.get(i) * revenueArr.get(i));
        }

        return calculatedRevenue;
    }
    //method to calculate expenses for each item sold
    private static ArrayList<Double> getExpenses(ArrayList<Double> expenses, ArrayList<Double> quantitySold) {
        ArrayList<Double> calculatedExpenses = new ArrayList<>();
        for(int i=0; i<item.size(); i++){
            calculatedExpenses.add(quantitySold.get(i) * expenses.get(i));
            //System.out.println(calculatedExpenses);
    }

    return calculatedExpenses;

    }
}
