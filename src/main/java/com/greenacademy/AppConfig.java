//! ini contoh bean
//! bean isinya adalah kumpulan method service yang akan dipanggil ketika aplikasi berjalan
//! bean tidak boleh ada class yang menyimpan data contohnya adalah product
//! butuh bean ketika ada dependency injection
package com.greenacademy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.greenacademy")

public class AppConfig {

    // public PaymentService paymentService(){
    //     return new PaymentService(loggerService());
    // }

    // public LoggerService loggerService(){
    //     return new LoggerService();
    // }
    // public PromoService promoService(){
    //     return new PromoService();
    // }

    // //? Methode OrderService Dependecy (bergantung) dengan class PaymentService
    // // karna OrderService juga dependecy dengan Product, sehingga Product dimasukkan sebagai parameter
    // public OrderService orderService(Product product){
    //     return new OrderService(product,paymentService(),loggerService(),promoService());
    // }
    
}
