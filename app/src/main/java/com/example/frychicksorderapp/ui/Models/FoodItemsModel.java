package com.example.frychicksorderapp.ui.Models;

import android.content.Context;

public class FoodItemsModel {
    String Name, MenuId, FoodId, Price, Discount, Description, AvailabilityFlag, Image;
    Context context;

    public FoodItemsModel() {
    }

    public FoodItemsModel(String name, String menuId, String foodId, String price, String discount, String description, String availabilityFlag, String image, Context context) {
        Name = name;
        MenuId = menuId;
        FoodId = foodId;
        Price = price;
        Discount = discount;
        Description = description;
        AvailabilityFlag = availabilityFlag;
        Image = image;
        this.context = context;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAvailabilityFlag() {
        return AvailabilityFlag;
    }

    public void setAvailabilityFlag(String availabilityFlag) {
        AvailabilityFlag = availabilityFlag;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}