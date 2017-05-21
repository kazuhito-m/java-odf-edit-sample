package com.github.kazuhito_m.odf_edit_sample.application.workresult;

import com.github.kazuhito_m.odf_edit_sample.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.class})
public class WorkResultServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(WorkResultServiceTest.class);

    @Autowired
    private WorkResultService sut;

    @Test
    public void ユーザ指定で勤怠の履歴から月のリストを作成取得できる() {
        // 実行
        List<String> actual = sut.getMonths();
        // 検証
        assertThat(actual.isEmpty(), is(false));
        // これ以上過去データは増やさないと思うので…末尾データは最古のもの
        String last = actual.get(actual.size() - 1);
        assertThat(last, is("2014/11"));
    }

    @Test
    public void ODSの印刷ファイルが作成取得出来る() throws IOException {
        // 実行
        ResponseEntity<byte[]> actual = sut.makeDlEntityWorkResultReport("2016/01");
        // 検証
        assertThat("今は内容がある程度", actual.getBody().length, is(not(0)));
    }

}