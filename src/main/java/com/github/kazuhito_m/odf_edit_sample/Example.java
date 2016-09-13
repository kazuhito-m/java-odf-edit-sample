package com.github.kazuhito_m.odf_edit_sample;

import com.github.kazuhito_m.odf_edit_sample.user.dao.UserDao;
import com.github.kazuhito_m.odf_edit_sample.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 最小限のサンプルです。
 */
@Controller
@EnableAutoConfiguration
public class Example {

    @Autowired
    private UserDao userDao;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }

    @RequestMapping({"/", "/hello"})
    public String hello(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {

        List<User> users = userDao.selectAll();
        String text = "Userの件数は" + users.size() + "件です。";

        model.addAttribute("name", text);
        return "hello";
    }

}
