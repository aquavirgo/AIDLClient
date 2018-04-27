package com.example.jguzikowski.model;

/**
 * Created by j.guzikowski on 9/20/17.
 */

import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by j.guzikowski on 9/19/17.
 */

public class Product implements Parcelable {
    private String name;
    private  double price;
    private String country;
    private String delivery;
   private long id;

    // No-arg Ctor
    public Product(){}

    // all getters and setters go here //...

    /** Used to give additional hints on how to process the received parcel.*/
    @Override
    public int describeContents() {
// ignore for now
        return 0;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCountry() {
        return country;
    }

    public String getDelivery() {
        return delivery;
    }
    public long getId() {
       return id;}


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

  public void setId(long id) {
        this.id = id;
   }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeString(name);
        pc.writeDouble(price);
        pc.writeString(country);
        pc.writeString( delivery);
       pc.writeLong( id);
    }

    /** Static field used to regenerate object, individually or as arrays */
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel pc) {
            return new Product(pc);
        }
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    /**Ctor from Parcel, reads back fields IN THE ORDER they were written */
    public Product(Parcel pc){
        name         = pc.readString();
        price        =  pc.readDouble();
        country      = pc.readString();
        delivery = ( pc.readString() );
        id = ( pc.readLong() );
    }
}
