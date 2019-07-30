package com.example.mattiaschang.fruitmix;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by boutell on 11/14/2015.
 */
public class Item {
    private String mName;
    private int mQuantity;
    private GregorianCalendar mDeliveryDate;

    public Item() {
        mName = "Nothing";
        mQuantity = 0;
        mDeliveryDate = new GregorianCalendar();
    }

    public Item(String name, int quantity, GregorianCalendar deliveryDate) {
        mName = name;
        mQuantity = quantity;
        mDeliveryDate = deliveryDate;
    }

    public static Item getDefaultItem() {
        return new Item("Earplugs", 5, new GregorianCalendar());
    }

    public static Item getEmptyItem() {
        return new Item ("---", 0, new GregorianCalendar());
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public String getDeliveryDateString() {
        return DateFormat.getDateInstance(DateFormat.MEDIUM).format(mDeliveryDate.getTime());
        // DateFormat.getDateInstance().format(mDeliveryDate);
    }

    public long getDeliveryDateTime() {
        return mDeliveryDate.getTime().getTime();
    }

    public void setDeliveryDate(GregorianCalendar deliveryDate) {
        mDeliveryDate = deliveryDate;
    }
}
