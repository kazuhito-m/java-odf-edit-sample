package com.github.kazuhito_m.odf_edit_sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 最小限のサンプルです。
 */
@RestController
@EnableAutoConfiguration
public class Example {
    /**
     * 「Hello World!」と表示します。
     * @return メッセージ
     */
    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }
    /**
     * 処理を実行します。
     * @param args 引数
     * @throws Exception 例外が発生した場合
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Example.class, args);
    }
}