package com.example.jguzikowski.aidlproductservice;

// Declare any non-default types here with import statements
import com.example.jguzikowski.model.Product;

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void addProduct(in Product p);
  void deleteProduct(in long id);
  List<Product> getAllProduct();
}