package com.github.kazuhito_m.odf_edit_sample.workresult.domain.report;


import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
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
        assertThat(sut.convJavaDateToExcelDateValue(1800000L), is(bd("0.39583333333333319400")));
        assertThat(sut.convJavaDateToExcelDateValue(16200000L), is(bd("0.56249999999999980200")));
    }

    private BigDecimal bd(String value) {
        return new BigDecimal(value);
    }

}
