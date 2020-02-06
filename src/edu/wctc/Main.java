package edu.wctc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private Scanner keyboard;
    private Cookbook cookbook;

    public Main() {
        keyboard = new Scanner(System.in);
        cookbook = new Cookbook();

        FileInput indata = new FileInput("meals_data.csv");

        String line;

        System.out.println("Reading in meals information from file...");
        while ((line = indata.fileReadLine()) != null) {
            String[] fields = line.split(",");
            cookbook.addElementWithStrings(fields[0], fields[1], fields[2]);
        }

        runMenu();
    }

    public static void main(String[] args) {
        new Main();
    }

    private void printMenu() {
        System.out.println("");
        System.out.println("Select Action");
        System.out.println("1. List All Items");
        System.out.println("2. List All Items by Meal");
        System.out.println("3. Search by Meal Name");
        System.out.println("4. Do Control Break");
        System.out.println("5. Exit");
        System.out.print("Please Enter your Choice: ");
    }

    private void runMenu() {
        boolean userContinue = true;

        while (userContinue) {
            printMenu();

            String ans = keyboard.nextLine();
            switch (ans) {
                case "1":
                    cookbook.printAllMeals();
                    break;
                case "2":
                    listByMealType();
                    break;
                case "3":
                    searchByName();
                    break;
                case "4":
                    // doControlBreak();
                    break;
                case "5":
                    userContinue = false;
                    break;
            }
        }

        System.out.println("Goodbye");
        System.exit(0);
    }

    private void listByMealType() {
        // Default value pre-selected in case
        // something goes wrong w/user choice
        MealType mealType = MealType.DINNER;

        System.out.println("Which Meal Type");

        // Generate the menu using the ordinal value of the enum
        for (MealType m : MealType.values()) {
            System.out.println((m.ordinal() + 1) + ". " + m.getMeal());
        }

        System.out.print("Please Enter your Choice: ");
        String ans = keyboard.nextLine();

        try {
            int ansNum = Integer.parseInt(ans);
            if (ansNum < MealType.values().length) {
                mealType = MealType.values()[ansNum - 1];
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Invalid Meal Type " + ans + ", defaulted to " + mealType.getMeal() + ".");
        }

        cookbook.printMealsByType(mealType);
    }

    private void searchByName() {
        keyboard.nextLine();
        System.out.print("Please Enter Value: ");
        String ans = keyboard.nextLine();
        cookbook.printByNameSearch(ans);
    }

    private void doControlBreak() throws FileNotFoundException{
        File file = new File("meals_data.csv");
        Scanner sc = new Scanner(file);

        if (file.exists()){
            System.out.println("Meal Type\\tTotal\\tMean\\tMin\\tMax\\tMedian");
            ArrayList<Integer> calories = new ArrayList<>();
            String currentTypeMeal = "";
            String nextTypeMeal = null;
            int totalCalories = 0;
            int median = (calories.size()/2);
            while(sc.hasNext()){
                String line = sc.nextLine();
                String []meal = line.split(",");
                nextTypeMeal = meal[0];
                String mealCalories = meal[2];

                if (nextTypeMeal.equalsIgnoreCase(currentTypeMeal)|| currentTypeMeal.equalsIgnoreCase("")){
                    calories.add(Integer.parseInt(mealCalories));
                    if (currentTypeMeal.equalsIgnoreCase("")){
                        currentTypeMeal = nextTypeMeal;
                    }
                }else {
                    for(int i = 0; i < (calories.size()-1); i++){
                        for (int j = 0; j < calories.size()-i-1; j++){
                            if (calories.get(j).compareTo(calories.get(j+1))>0){
                                int temp = calories.get(j);
                                calories.set(j, calories.get(j+1));
                                calories.set(j+1, temp);
                            }
                        }
                    }
                    for (int i = 0; i < calories.size(); i++){
                        totalCalories += calories.get(i);
                    }
                    System.out.println(currentTypeMeal + "\n" + totalCalories + "\n" + (totalCalories/(calories.size())) + "\n" + calories.get(0) + "\n" + calories.get(calories.size()));
                    currentTypeMeal = nextTypeMeal;
                    calories.clear();
                    calories.add(Integer.parseInt(mealCalories));
                }
            }
        }
    }
}
