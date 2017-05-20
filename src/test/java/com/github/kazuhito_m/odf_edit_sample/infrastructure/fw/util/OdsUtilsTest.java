package com.github.kazuhito_m.odf_edit_sample.infrastructure.fw.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OdsUtilsTest {

    @Test
    public void JavaのDateの内部保持値をODSをの内部保持値に変換を試みる() {
        assertThat(to10p(OdsUtils.convJavaDateToExcelDateValue(1800000L)), is("0.3958333333"));
        assertThat(to10p(OdsUtils.convJavaDateToExcelDateValue(16200000L)), is("0.5624999999"));
    }

    private String to10p(double value) {
        return BigDecimal.valueOf(value).setScale(10, RoundingMode.DOWN).toString();
    }

}
