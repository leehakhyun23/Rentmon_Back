package com.himedia.rentmon_back.util;

import java.util.Random;

public class CreatedCoupon {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateCoupon() {
        Random random = new Random();
        StringBuilder coupon = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            if (i > 0) {
                coupon.append("-");
            }
            for (int j = 0; j < 5; j++) {
                coupon.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
        }

        return coupon.toString();
    }
}
