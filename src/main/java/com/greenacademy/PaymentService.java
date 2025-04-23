package com.greenacademy;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    @Autowired
    LoggerService loggerService;

    // public PaymentService(LoggerService loggerService) {
    //     this.loggerService = loggerService;
    // }

    public double processPayment(double totalBayar, double uangPembelian) {

        Scanner input = new Scanner(System.in);
        boolean isUangCukup = false;

        while (!isUangCukup) {

            if (uangPembelian == totalBayar || uangPembelian > totalBayar) {
                isUangCukup = true;
            } else {
                loggerService.loggerWarning("Uang Tidak Cukup, Tolong Berikan Uang Pas");
                System.out.print("Masukkan Uang Pembayaran : ");
                uangPembelian = input.nextDouble();
            }
        }

        double UangKembalian = uangPembelian - totalBayar;
        return UangKembalian;
    }
}
