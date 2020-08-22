package org.example;

import org.example.service.Produce;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class SpringbootTest {

    @Autowired
    public Produce produce;

    @Test
    public void test(){
        produce.produceMsg();
    }
}
