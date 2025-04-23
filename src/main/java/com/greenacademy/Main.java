package com.greenacademy;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;



public class Main {
    public static void main(String[] args) throws Exception {
        ArrayList<Product> daftarProduct = new ArrayList<Product>();

        // AppConfig appConfig = new AppConfig();
        // LoggerService loggerService = appConfig.loggerService();
        ApplicationContext context = new AnnotationConfigApplicationContext( AppConfig.class);
        LoggerService loggerService = context.getBean(LoggerService.class);

        Scanner input = new Scanner(System.in);

        int pilihan = 0;
        while (pilihan != 4) {
            System.out.println("===================================");
            System.out.println("         APLIKASI TOKO MINI        ");
            System.out.println("===================================");
            System.out.println("1. Tambah Produk");
            System.out.println("2. Tampilkan Produk");
            System.out.println("3. Beli Produk");
            System.out.println("4. Keluar");

            boolean inputValid = false;
            while (!inputValid) {
                System.out.print("\nMasukkan Pilihan (1-4): ");
                try {
                    pilihan = input.nextInt();
                    input.nextLine();

                    if (pilihan >= 1 && pilihan <= 4) {
                        inputValid = true;
                    } else {
                        System.out.println("Pilihan tidak tersedia. Silakan masukkan angka 1-4.\n");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Input tidak valid! Masukkan angka antara 1-4.\n");
                    input.nextLine();
                }

            }

            for (int i = 0; i < 3; i++) {
                System.out.println();
            }

            if (pilihan == 1) {

                boolean isAddProduct = false;
                while (!isAddProduct) {
                    int jenisProduk = 0;
                    try {
                        System.out.println("===================================");
                        System.out.println("         TAMBAH PRODUK BARU        ");
                        System.out.println("===================================");
                        System.out.println("Pilih Jenis Produk:");
                        System.out.println("1. Electronic");
                        System.out.println("2. Makanan");
                        System.out.print("\nPilihan (1/2): ");
                        jenisProduk = input.nextInt();
                        input.nextLine();

                        if (jenisProduk != 1 && jenisProduk != 2) {
                            // System.out.println("\nPilihan tidak valid. Coba lagi.\n");
                            loggerService.loggerError(" Pilihan tidak valid. Coba lagi.");

                            continue;
                        }

                        System.out.print("Masukkan Nama Produk              : ");
                        String nama = input.nextLine();

                        System.out.print("Masukkan Harga Produk (Rp)        : ");
                        double harga = input.nextDouble();
                        input.nextLine();

                        System.out.print("Masukkan Jumlah Produk (pcs)      : ");
                        int stok = input.nextInt();
                        input.nextLine();

                        if (jenisProduk == 1) {
                            System.out.print("Masukkan Tanggal Garansi (YYYY-MM-DD)     : ");
                            String inputTanggal = input.nextLine();
                            LocalDate garansiTahun = LocalDate.parse(inputTanggal);
                            daftarProduct.add(new Electronic(nama, harga, stok, garansiTahun));
                        }

                        if (jenisProduk == 2) {
                            System.out.print("Masukkan Tanggal Expired (YYYY-MM-DD): ");
                            String inputTanggal = input.nextLine();
                            LocalDate expired = LocalDate.parse(inputTanggal);
                            daftarProduct.add(new ProdukMakanan(nama, harga, stok, expired));
                        }

                        loggerService.loggerSuccess(" Yeayy! Produk " + nama + " berhasil ditambahkan!");
                        // System.out.println("\nYeayy! Produk berhasil ditambahkan!");
                        for (int i = 0; i < 3; i++)
                            System.out.println();

                        isAddProduct = false;
                        break;

                    } catch (InputMismatchException e) {
                        // System.out.println("\nInput angka tidak valid! Coba lagi.\n");
                        loggerService.loggerError(" Input angka tidak valid! Coba lagi.\n");
                        input.nextLine();
                    } catch (DateTimeParseException e) {
                        // System.out.println("\nFormat tanggal salah! Gunakan format YYYY-MM-DD.\n");
                        loggerService.loggerError(" Format tanggal salah! Gunakan format YYYY-MM-DD.\n");

                    } catch (Exception e) {
                        // System.out.println("\nTerjadi kesalahan Tidak Terduga: " + e.getMessage() +
                        // "\n");
                        loggerService.loggerError(" Terjadi kesalahan Tidak Terduga: " + e.getMessage() + "\n");
                    }
                }

            } else if (pilihan == 2) {
                System.out.println("===================================");
                System.out.println("          DAFTAR PRODUK            ");
                System.out.println("===================================");
                if (daftarProduct.isEmpty()) {
                    System.out.println("Sorry!!  Tidak ada produk yang tersedia.");
                } else {
                    int nomor = 1;
                    for (Product product : daftarProduct) {
                        System.out.print(nomor + ". ");
                        product.tampilkanInfo();
                        nomor++;
                    }
                }
                for (int i = 0; i < 3; i++)
                    System.out.println();

            } else if (pilihan == 3) {
                System.out.println("===================================");
                System.out.println("            BELI PRODUK            ");
                System.out.println("===================================");

                if (daftarProduct.isEmpty()) {
                    System.out.println("Sorry!!  Produk tidak ditemukan.");
                } else {
                    int nomor = 1;
                    for (Product product : daftarProduct) {
                        System.out.print(nomor + ". ");
                        product.tampilkanInfo();
                        nomor++;
                    }

                    boolean produkValid = false;

                    while (!produkValid) {
                        System.out.print("\nMasukkan Nama Produk yang Dibeli : ");
                        String namaProduk = input.nextLine();

                        for (Product product : daftarProduct) {
                            if (product.nama.equalsIgnoreCase(namaProduk)) {
                                produkValid = true;

                                int jumlahBeli = 0;

                                boolean stockValid = false;

                                while (!stockValid) {
                                    try {
                                        System.out.print("Masukkan Jumlah Beli             : ");
                                        jumlahBeli = input.nextInt();
                                        input.nextLine();

                                        // ! proses order
                                        OrderService orderService = context.getBean(OrderService.class);
                                        orderService.proccesOrder(product,jumlahBeli);
                                        // orderService = appConfig.orderService(product);
                                        // orderService.proccesOrder(jumlahBeli);

                                        if (product.getStok() == 0) {
                                            daftarProduct.remove(product);
                                            loggerService.loggerWarning(" Waduhh!! Stok habis. Produk '" + product.nama
                                                    + " telah dihapus dari daftar.");
                                    
                                            // System.out.println("\nWaduhh!! Stok habis. Produk '" + product.nama
                                            //         + "' telah dihapus dari daftar.");
                                        }
                                        stockValid = true;

                                    } catch (StockException e) {
                                        System.out.println(e.getMessage() + "\n");
                                    } catch (InputMismatchException e) {
                                        loggerService.loggerError("Input jumlah beli harus angka!\n");
                                        // System.out.println("Input jumlah beli harus angka!\n");
                                        input.nextLine();
                                    } catch (Exception e) {
                                        System.out.println(e.getMessage() + "\n");
                                        break; // jika expired keluar dari loop
                                    }
                                }

                                break; // keluar biar gak tetap di form pembelian
                            }
                        }

                        if (!produkValid) {
                            loggerService.loggerError("Yahh! Produk tidak ditemukan. Pastikan penulisan nama sesuai.\n");
                            // System.out.println("Yahh! Produk tidak ditemukan. Pastikan penulisan nama sesuai.\n");
                        }
                    }
                }

                for (int i = 0; i < 3; i++) {
                    System.out.println();
                }
            }
        }
        loggerService.loggerSuccess("Terima kasih telah menggunakan aplikasi!\n");  
        // System.out.println("\nTerima kasih telah menggunakan aplikasi!\n");
    }
}
