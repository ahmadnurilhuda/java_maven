package com.greenacademy;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("OrderService")

public class OrderService {
    
    private Product product;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private LoggerService loggerService;
    @Autowired
    private PromoService promoService;

    // public OrderService(Product product, PaymentService paymentService, LoggerService loggerService,
    //         PromoService promoService) {
    //     this.product = product;
    //     this.paymentService = paymentService;
    //     this.loggerService = loggerService;
    //     this.promoService = promoService;
    // }

    public void proccesOrder(Product product, int jumlahBeli) throws Exception {
        // Check Produk Bisa dibeli apa tidak (expired)
        if (product instanceof ProdukMakanan) {
            ProdukMakanan makanan = (ProdukMakanan) product;
            makanan.bisaDibeli(jumlahBeli);
        } else if (product instanceof Electronic) {
            Electronic electronic = (Electronic) product;
            electronic.bisaDibeli(jumlahBeli);
        }

        if (jumlahBeli <= 0) {
            throw new StockException("Jumlah beli tidak boleh kurang dari atau sama dengan 0");
        }
        if (jumlahBeli > product.getStok()) {
            throw new StockException(
                    "Stok tidak mencukupi. Produk " + product.nama + " hanya tersedia " + product.getStok());
        } else {
            Scanner input = new Scanner(System.in);
            double totalBayar = product.getHarga() * jumlahBeli;

            System.out.println("===================================");
            System.out.println("         RINCIAN PEMBELIAN         ");
            System.out.println("===================================");
            System.out.println("Nama Produk       : " + product.nama);
            System.out.println("Harga Produk (Rp) : " + product.getHarga());
            System.out.println("Jumlah Beli       : " + jumlahBeli);
            System.out.println("Total Bayar (Rp)  : " + totalBayar + "\n");

            boolean isPunyaKodePromo = false;
            
            while (!isPunyaKodePromo) {
                System.out.print("Punya Kode Promo (Y/N) :  ");
                String inputPromo = input.next();
                if (inputPromo.equalsIgnoreCase("Y")) {
                    System.out.print("Masukkan Kode Promo : ");
                    String inputKodePromo = input.next();
                    try{
                        double discount = promoService.usePromo(inputKodePromo);
                        double totalBayarPromo = totalBayar-discount;

                    if (totalBayar <= discount) {
                        totalBayar = 0;
                        product.setStok(product.getStok() - jumlahBeli);

                        loggerService.loggerSuccess(" Yeayy! Produk berhasil dibeli dengan Voucher Promo sebesar Rp" + discount);
                        System.out.println("===================================");
                        System.out.println("         RINCIAN PEMBELIAN         ");
                        System.out.println("===================================");
                        System.out.println("Nama Produk       : " + product.nama);
                        System.out.println("Harga Produk (Rp) : " + product.getHarga());
                        System.out.println("Jumlah Beli       : " + jumlahBeli);
                        System.out.println("Total Bayar (Rp)  : " + (product.getHarga() * jumlahBeli));
                        System.out.println("Voucher Promo     : " + discount);
                        System.out.println("                                  -");
                        System.out.println("===================================\n");
                        System.out.println("Total Bayar Promo (Rp)  : " + totalBayar);
                        System.out.println("Uang Pembayaran   : " + totalBayar);
                        System.out.println("Uang Kembalian    : " + totalBayar);
                        loggerService
                                .loggerInfo("Stok " + product.nama + " tersisa : " + product.getStok() + "\n");
                    } else {
                        loggerService.loggerSuccess(" Yeayy Anda Dapat Voucher Potongan Rp" + discount);
                        loggerService.loggerWarning(" Silahkan lakukan pembayaran sebesar Rp" + totalBayarPromo);
                        System.out.println("===================================");
                        System.out.println("            Proses Payment         ");
                        System.out.println("===================================");
                        System.out.print("\nMasukkan Uang Pembayaran : ");
                        double uangPembelian = input.nextDouble();
                        double uangKembalian = paymentService.processPayment(totalBayarPromo, uangPembelian);

                        product.setStok(product.getStok() - jumlahBeli);

                        loggerService.loggerSuccess(" Yeayy! Produk berhasil dibeli!\n");
                        System.out.println("===================================");
                        System.out.println("         RINCIAN PEMBELIAN         ");
                        System.out.println("===================================");
                        System.out.println("Nama Produk             : " + product.nama);
                        System.out.println("Harga Produk (Rp)       : " + product.getHarga());
                        System.out.println("Jumlah Beli             : " + jumlahBeli);
                        System.out.println("Total Bayar (Rp)        : " + totalBayar);
                        System.out.println("Voucher Promo           : " + discount);
                        System.out.println("                                  -");
                        System.out.println("===================================\n");
                        System.out.println("Total Bayar Promo (Rp)  : " + totalBayarPromo);
                        System.out.println("Uang Pembayaran         : " + uangPembelian);
                        System.out.println("Uang Kembalian          : " + uangKembalian);
                        loggerService
                                .loggerInfo("Stok " + product.nama + " tersisa : " + product.getStok() + "\n");
                        }
                        isPunyaKodePromo = true;
                    }catch(Exception e){
                        loggerService.loggerError(e.getMessage());
                        continue;
                    }
                    
                } else if (inputPromo.equalsIgnoreCase("N")) {
                    // Tanpa promo
                    
                    System.out.println("===================================");
                    System.out.println("            Proses Payment         ");
                    System.out.println("===================================");
                    System.out.print("\nMasukkan Uang Pembayaran : ");
                    double uangPembelian = input.nextDouble();

                    double uangKembalian = paymentService.processPayment(totalBayar, uangPembelian);

                    product.setStok(product.getStok() - jumlahBeli);

                    loggerService.loggerSuccess("\nYeayy! Produk berhasil dibeli!\n");
                    System.out.println("===================================");
                    System.out.println("         RINCIAN PEMBELIAN         ");
                    System.out.println("===================================");
                    System.out.println("Nama Produk         : " + product.nama);
                    System.out.println("Harga Produk (Rp)   : " + product.getHarga());
                    System.out.println("Jumlah Beli         : " + jumlahBeli);
                    System.out.println("Total Bayar (Rp)    : " + totalBayar);
                    System.out.println("Voucher Promo     : 0");
                    System.out.println("Uang Pembayaran     : " + uangPembelian);
                    System.out.println("Uang Kembalian      : " + uangKembalian);
                    loggerService.loggerInfo("Stok " + product.nama + " tersisa : " + product.getStok() + "\n");

                    isPunyaKodePromo = true;
                } else {
                    loggerService.loggerError("Pilihan Tidak Valid. Pilihan Bukan Y/N");
                }
            }

            // ! jika totalBayar < 10000 (voucherDiscount nya) maka langsung berhasil
        }
    }
}
