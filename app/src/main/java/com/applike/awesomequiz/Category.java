package com.applike.awesomequiz;

public class Category {
    public static final int MajorArcana = 1;
    public static final int SuitOfWands = 2;
    public static final int SuitOfCups = 3;
    public static final int SuitOfSwords = 4;
    public static final int SuitOfPentacles = 5;

    private int id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
