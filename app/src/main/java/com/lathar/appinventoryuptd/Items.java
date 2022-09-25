package com.lathar.appinventoryuptd;

public class Items {


    private String itemName;
    private String itemCategory;
    private String itemQrcode;

    public Items(){

    }

    public Items(String itemName, String itemCategory, String itemQrcode){
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemQrcode = itemQrcode;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getItemQrcode() {
        return itemQrcode;
    }
}
