package com.misd.cookapp;

import java.io.Serializable;

public class Meal implements Serializable{

    private String name;
    private int mealId;
    private boolean lactoseFree;
    private boolean glutenFree;
    private boolean fructoseFree;
    private boolean sorbitFree;

    public Meal(String name, boolean lactoseFree, boolean glutenFree, boolean fructoseFree, boolean sorbitFree) {
        this.name = name;
        this.lactoseFree = lactoseFree;
        this.glutenFree = glutenFree;
        this.fructoseFree = fructoseFree;
        this.sorbitFree = sorbitFree;
        //TODO Vegan und vegetarisch ergänzen
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
}
