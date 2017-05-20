package com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.dao;

import com.github.kazuhito_m.odf_edit_sample.App;
import com.github.kazuhito_m.odf_edit_sample.infrastructure.datasource.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {App.class})
@Transactional
public class UserDaoTest {

    @Autowired
    private UserDao sut;

    @Test
    public void ユーザを全件取得できる() {
        // 実行
        List<User> actual = sut.selectAll();
        // 検証
        assertThat(actual.isEmpty(), is(false));
    }

    @Test
    public void プライマリキーにてユーザを取得できる() {
        // 実行
        User actual = sut.selectById(1);
        // 検証
        assertThat(actual.id, is(1));
        assertThat(actual.name.contains("Miura"), is(true));
    }

}
