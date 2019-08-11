package com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OdsUtilsTest {
    @Test
    public void JavaのDateの内部保持値をODSをの内部保持値に変換を試みる() {
        assertEquals("0.3958333333", to10p(OdsUtils.convJavaDateToExcelDateValue(1800000L)));
        assertEquals("0.5624999999", to10p(OdsUtils.convJavaDateToExcelDateValue(16200000L)));
    }

    private String to10p(double value) {
        return BigDecimal.valueOf(value).setScale(10, RoundingMode.DOWN).toString();
    }
}
