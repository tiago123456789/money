package com.tiago.money.money.util;

import java.time.LocalDate;

public class DataUtil {

    public static LocalDate getDataComPrimeiroDiaMes(LocalDate data) {
        return data.withDayOfMonth(1);
    }

    public static LocalDate getDataComUltimoDiaMes(LocalDate data) {
        return data.withDayOfMonth(data.lengthOfMonth());
    }

}
