package com.code.hacker_rank;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;


public class main {

    public static void main(String[] args) {

        double payment = 12324.134;

        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
        String currency = formatter.format(payment);

        System.out.println(currency);


        Object o = new Inner().new Private();
        ((main.Inner.Private) o).powerof2(8);
    }


    static class Inner {
        private class Private {
            private String powerof2(int num) {
                return ((num & num - 1) == 0) ? "power of 2" : "not a power of 2";
            }
        }
    }

}
