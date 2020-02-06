package edu.wctc;
import java.util.ArrayList;
import java.util.Collections;
public class Cookbook {

    // Hold all the meals that are read in from the file
    private ArrayList<Meal> meals = new ArrayList<>(100);
    // Hold the next (empty) index in the array

    public void addElementWithStrings(String mealTypeStr, String mealNameStr, String caloriesStr) {
        MealType mealType;

        // Do we have room in the array for one more?


            // Find the correct enum using a switch? Or use .fromValue() instead?
            switch (mealTypeStr) {
                case "Breakfast":
                    mealType = MealType.BREAKFAST;
                    break;
                case "Lunch":
                    mealType = MealType.LUNCH;
                    break;
                case "Dinner":
                    mealType = MealType.DINNER;
                    break;
                case "Dessert":
                    mealType = MealType.DESSERT;
                    break;
                default:
                    mealType = MealType.DINNER;
                    System.out.println("Meal Creation Error: Invalid Meal Type " + mealTypeStr + ", defaulted to Dinner.");
            }

            int calories;

            if (caloriesStr.matches("-?\\d+(\\.\\d+)?")) {
                calories = Integer.parseInt(caloriesStr);
            } else {
                calories = 100;
                System.out.println("Meal Creation Error: Invalid Calories " + caloriesStr + ", defaulted to 100.");
            }
            meals.add(new Meal(mealType, mealNameStr, calories));
        }


    public ArrayList<Meal> getMeals() {
        return meals;
    }

    public void printAllMeals() {
        for (Meal item : meals) {
            if (item != null) {
                System.out.println(item);
            }
        }
    }

    public void printMealsByType(MealType mealType) {
        for (Meal item : meals) {
            if (item != null && item.getMealType() == mealType) {
                System.out.println(item);
            }
        }
    }

    public void printByNameSearch(String s) {
        // Maybe add a message if no match found?
        for (Meal item : meals) {
            // Maybe make this case-insensitive?
            if (item != null && item.getItem().indexOf(s) >= 0) {
                System.out.println(item);
            }
        }
    }

    public void doControlBreak() {

        ArrayList<Integer> medians = new ArrayList<>();
        MealType currentType = null;
        int calorieTotal = 0;
        double counter = 0.0;
        double median = 0.0;
        int minimum = 10000;
        int maximum = 0;
        for (Meal item : meals) {

            if (currentType == null)
            {
                System.out.println("Meal Type\t\tTotal\t\tMean\t\tMin\t\tMax\t\tMedian");
                currentType = item.getMealType();
            }

            if ( item.getMealType() != currentType) {
                System.out.printf(currentType + "\t\t" + calorieTotal +
                        "\t\t" + "%.2f\t\t" + minimum  + "\t\t" + maximum +
                        "\t\t" + medians.get((int)counter/2) + "\n", median);
                currentType = item.getMealType();
                Collections.sort(medians);

                calorieTotal = 0;
                counter = 0;
                minimum = 10000;
                maximum = 0;
                medians = new ArrayList<>();
            }
            if(item.getCalories() <= minimum )
            {
                minimum  = item.getCalories();
            }
            if(item.getCalories() >= maximum)
            {
                maximum = item.getCalories();
            }
            calorieTotal += item.getCalories();
            counter ++;
            median = calorieTotal / counter;
            medians.add(item.getCalories());
        }
        Collections.sort(medians);
        System.out.printf(currentType + "\t\t" + calorieTotal + "\t\t"
                + "%.2f\t\t" + minimum  + "\t\t" + maximum + "\t\t" +
                medians.get((int)counter/2) + "\n",median);

    }
}
