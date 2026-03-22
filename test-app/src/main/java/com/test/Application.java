package com.test;

import com.itranswarp.summer.boot.SummerApplication;

public class Application {
    public static void main(String[] args) throws Exception {
        SummerApplication.run(
            "src/main/resources",
            "target/classes",
            AppConfig.class,
            args
        );
    }
}
