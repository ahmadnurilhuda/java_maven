package com.greenacademy;

import org.springframework.stereotype.Service;

@Service
public class PromoService {

    public double usePromo(String code) throws Exception {
        // Pencarian kode promo di database
        String kodePromo = "IDULFITRI10K";

        if(code.equals(kodePromo)){
            return 50000;
        }else {
            throw new Exception("Kode Promo tidak valid");
        }
    }
}
