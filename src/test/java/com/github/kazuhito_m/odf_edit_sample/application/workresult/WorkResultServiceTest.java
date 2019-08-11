package com.github.kazuhito_m.odf_edit_sample.application.workresult;

import com.github.kazuhito_m.odf_edit_sample.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class WorkResultServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkResultServiceTest.class);

    @Autowired
    private WorkResultService sut;

    @Test
    public void ユーザ指定で勤怠の履歴から月のリストを作成取得できる() {
        // 実行
        List<String> actual = sut.getMonths();
        // 検証
        assertEquals(false, actual.isEmpty());
        // これ以上過去データは増やさないと思うので…末尾データは最古のもの
        String last = actual.get(actual.size() - 1);
        assertEquals("2014/11", last);
    }

    @Test
    public void ODSの印刷ファイルが作成取得出来る() throws IOException {
        // 実行
        ResponseEntity<byte[]> actual = sut.makeDlEntityWorkResultReport("2016/01");
        // 検証
        assertNotEquals(0, actual.getBody().length); // "今は内容がある程度
    }

}