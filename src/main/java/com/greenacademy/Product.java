package com.greenacademy;



public abstract class Product{
    String nama;
    private double harga;
    private int stok;

    Product(String nama, double harga, int stok){
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    abstract void tampilkanInfo();

    public double getHarga(){
        return harga;
    }
    public double setHarga(double harga){
        return this.harga = harga;
    }

    public int getStok(){
        return stok;
    }
    public int setStok(int stok){
        return this.stok = stok;
    }

}