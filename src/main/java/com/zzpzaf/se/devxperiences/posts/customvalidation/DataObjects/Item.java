package com.zzpzaf.se.devxperiences.posts.customvalidation.DataObjects;

public class Item {
    
    private int itemId;
    private String itemName;
    private int itemVendorId;
    private int itemModelYear;
    private float itemListPrice;

    public Item() {};

    public Item(int itemId, String itemName, int itemVendorId, int itemModelYear, float itemListPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemVendorId = itemVendorId;
        this.itemModelYear = itemModelYear;
        this.itemListPrice = itemListPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemVendorId() {
        return itemVendorId;
    }

    public void setItemVendorId(int itemVendorId) {
        this.itemVendorId = itemVendorId;
    }

    public int getItemModelYear() {
        return itemModelYear;
    }

    public void setItemModelYear(int itemModelYear) {
        this.itemModelYear = itemModelYear;
    }

    public float getItemListPrice() {
        return itemListPrice;
    }

    public void setItemListPrice(float itemListPrice) {
        this.itemListPrice = itemListPrice;
    }

    @Override
    public String toString() {
        return "Items [itemId=" + itemId + ", itemListPrice=" + itemListPrice + ", itemModelYear=" + itemModelYear
                + ", itemName=" + itemName + ", itemVendorId=" + itemVendorId + "]";
    }

    

}
