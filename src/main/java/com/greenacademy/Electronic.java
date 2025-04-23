package com.greenacademy;
import java.time.LocalDate;

public class Electronic extends Product implements BisaDibeli{

    LocalDate garansiTahun;

    public Electronic(String nama, double harga, int stok, LocalDate garansiTahun) {
        super(nama, harga, stok);
        this.garansiTahun = garansiTahun;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("[Electronic] " + this.nama + " - " +"Rp"+ getHarga() + " - " + getStok() + " ( " + this.garansiTahun + " ) ");
    }

    @Override
    public boolean bisaDibeli(int jumlahBeli) throws StockException {
        if (jumlahBeli > getStok()){
            throw new StockException("Stok tidak mencukupi. Produk "+super.nama+" hanya tersdia "+super.getStok());
        } else {
            return true;
        }
    }
}
