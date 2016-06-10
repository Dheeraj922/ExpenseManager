package com.dheeraj.actitproject.models;

/**
 * Created by DHEERAJ on 2/21/2016.
 */
public class RemoveItemsModel {

    private String name;
    private String cost;
    boolean selected;

    public RemoveItemsModel(String name, String cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
