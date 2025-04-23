package com.greenacademy;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProdukMakanan extends Product implements BisaDibeli{

    LocalDate expired;


    ProdukMakanan (String nama, double harga, int stok,LocalDate expired){
        super(nama, harga, stok);
        this.expired = expired;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("[Makanan] " + this.nama + " - " +"Rp"+ getHarga() + " - " + getStok() + " ( " + this.expired + " )");
    }

    @Override
    public boolean bisaDibeli (int jumlahBeli) throws Exception {

        if (jumlahBeli > getStok()){
            throw new StockException("Stok tidak mencukupi. Produk "+super.nama+" hanya tersdia "+super.getStok());
        } else {
            LocalDate currentDate = LocalDate.now();
            //? Menghitung selisih antara tanggal sekarang dan tanggal expired
            long days =  ChronoUnit.DAYS.between(currentDate, expired);
            
            if(days < 1){
                throw new Exception("Produk " + this.nama +" Sudah Expired. " +"( "+ expired +" )");
            }
        }
        return true;
    }
}
