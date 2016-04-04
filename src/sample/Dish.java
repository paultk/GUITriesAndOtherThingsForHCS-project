package sample;

import java.util.ArrayList;

/**
 * Created by paul thomas on 16.03.2016.
 */
public class Dish {
    private int dishId;
    private double price;
    private String dishName;
    private ArrayList<String> intsForIngredientId;

    public Dish(int dishId, double price, String dishName) {
        this.dishId = dishId;
        this.price = price;
        this.dishName = dishName;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String toString() {
        return dishName;
    }

    public ArrayList<String> getIntsForIngredientId() {
        return intsForIngredientId;
    }

    public void setIntsForIngredientId(ArrayList<String> intsForIngredientId) {
        this.intsForIngredientId = intsForIngredientId;
    }
}
