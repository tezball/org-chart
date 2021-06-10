package com.example.orgchart;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrgChartApplicationTests {

    @Test
    @SuppressWarnings("squid:S2699")
    void contextLoads() {
    }

    @Test
    @SuppressWarnings("squid:S2699")
    void startMainSuccess() {
        OrgChartApplication.main(new String[0]);
    }
}
