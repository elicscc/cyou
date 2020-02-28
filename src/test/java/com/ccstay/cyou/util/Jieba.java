package com.ccstay.cyou.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Jieba {
    @Autowired
    private  JiebaTest jiebaTest;
    @Test
    public void  s(){
        jiebaTest.testCutForSearch();
    }
}
