package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OdsHelperTest {

    private static final Logger logger = LoggerFactory.getLogger(OdsHelperTest.class);

    private OdsHelper sut;

    @Before
    public void setup() {
        sut = new OdsHelper();
    }

    @Test
    public void JavaのDateの内部保持値をODSをの内部保持値に変換を試みる() {
        assertThat(to10p(sut.convJavaDateToExcelDateValue(1800000L)), is("0.3958333333"));
        assertThat(to10p(sut.convJavaDateToExcelDateValue(16200000L)), is("0.5624999999"));
    }

    private String to10p(double value) {
        return BigDecimal.valueOf(value).setScale(10, RoundingMode.DOWN).toString();
    }

}
