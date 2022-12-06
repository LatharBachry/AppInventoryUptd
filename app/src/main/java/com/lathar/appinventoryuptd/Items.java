package com.lathar.appinventoryuptd;
//Items class untuk setter and getter pengambilan database
public class Items {

    private String itemQrcode;



    private String itemName;
    private String itemCategory;
    private String itemPrice;

    public Items() {

    }
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

    //setter
    public void setItemQrcode(String itemQrcode) {
        this.itemQrcode = itemQrcode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

}
