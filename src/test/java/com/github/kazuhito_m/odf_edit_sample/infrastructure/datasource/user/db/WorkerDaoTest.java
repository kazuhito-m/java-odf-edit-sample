package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.db;

import com.github.kazuhito_m.odf_edit_sample.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.seasar.doma.internal.util.AssertionUtil.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class WorkerDaoTest {

    @Autowired
    private WorkerDao sut;

    @Test
    public void ユーザを全件取得できる() {
        // 実行
        List<WorkerTable> actual = sut.selectAll();
        // 検証
        assertFalse(actual.isEmpty());
    }

    @Test
    public void プライマリキーにてユーザを取得できる() {
        // 実行
        WorkerTable actual = sut.selectById(1);
        // 検証
        assertEquals(1, actual.id);
        assertTrue(actual.name.contains("Miura"));
    }

}
