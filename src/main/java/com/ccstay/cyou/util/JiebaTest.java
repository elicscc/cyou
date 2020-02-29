package com.ccstay.cyou.util;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import org.springframework.data.mongodb.core.mapping.TextScore;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
@Component
public class JiebaTest  {
    private JiebaSegmenter jiebaSegmenter=new JiebaSegmenter();

    String sentences = "北京京天威科技发展有限公司大庆车务段的装车数量";
//    protected void setUp() throws Exception {
//        WordDictionary.getInstance().init(Paths.get("conf"));
//    }

    public void testCutForSearch() {
        //System.out.println(jiebaSegmenter.sentenceProcess(sentences));
    }

}
