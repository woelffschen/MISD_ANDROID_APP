package com.misd.cookapp;

import java.io.Serializable;

/**
 * This Meal contains all Meal attributes.
 * @author Michael Landreh, Ines Mueller
 */
public class Meal implements Serializable{

    private String name;
    private int mealId;
    private boolean lactoseFree;
    private boolean glutenFree;
    private boolean fructoseFree;
    private boolean sorbitFree;
    private boolean vegan;
    private boolean vegetarisch;

    public Meal(String name, boolean lactoseFree, boolean glutenFree, boolean fructoseFree, boolean sorbitFree, boolean vegan, boolean vegetarisch) {
        this.name = name;
        this.lactoseFree = lactoseFree;
        this.glutenFree = glutenFree;
        this.fructoseFree = fructoseFree;
        this.sorbitFree = sorbitFree;
        this.vegan = vegan;
        this.vegetarisch = vegetarisch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public boolean isLactoseFree() {
        return lactoseFree;
    }

    public void setLactoseFree(boolean lactoseFree) {
        this.lactoseFree = lactoseFree;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }

    public boolean isFructoseFree() {
        return fructoseFree;
    }

    public void setFructoseFree(boolean fructoseFree) {
        this.fructoseFree = fructoseFree;
    }

    public boolean isSorbitFree() {
        return sorbitFree;
    }

    public void setSorbitFree(boolean sorbitFree) {
        this.sorbitFree = sorbitFree;
    }

    public boolean isVegan() { return vegan; }

    public void setVegan(boolean vegan) { this.vegan = vegan; }

    public boolean isVegetarisch() { return vegetarisch; }

    public void setVegetarisch(boolean vegetarisch) { this.vegetarisch = vegetarisch; }
}
