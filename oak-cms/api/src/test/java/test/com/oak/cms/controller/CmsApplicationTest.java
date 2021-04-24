package test.com.oak.cms.controller;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import test.com.oak.cms.controller.config.TestConfig;

import java.io.File;


@SpringBootApplication(
        scanBasePackages = {"com.wuxp", "test.com.oak", "com.levin.commons.dao"})
@Import(TestConfig.class)
@ActiveProfiles("dev")
@EntityScan(basePackages = {"com.wuxp", "com.oak", "com.oaknt"})
@Configuration
public class CmsApplicationTest {

    public static void main(String[] args) {

        System.out.println(CmsApplicationTest.class.getSimpleName() + " WORK DIR:" + new File("").getAbsolutePath());

        SpringApplication.run(CmsApplicationTest.class, args);

    }

}
