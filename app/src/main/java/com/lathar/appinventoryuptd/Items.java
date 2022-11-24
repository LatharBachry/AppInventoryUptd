package com.lathar.appinventoryuptd;
//Items class untuk setter and getter pengambilan database
public class Items {

    private String itemQrcode;
    private String itemName;
    private String itemCategory;
    private String itemPrice;

    public Items() {

    }
    //setter
    public Items(String itemName, String itemCategory, String itemQrcode, String itemPrice){
        this.itemQrcode = itemQrcode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
    }


    //getter

    public String getItemName() {
        return itemName;
    }
    public String getItemCategory() {
        return itemCategory;
    }
    public String getItemPrice() {
        return itemPrice;
    }
    public String getItemQrcode() {
        return itemQrcode;
    }

}
