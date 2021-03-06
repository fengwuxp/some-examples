package test.com.oak.rbac;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.File;


@SpringBootApplication(
        scanBasePackages = {"com.oak", "com.wuxp", "test.com.oak", "com.levin.commons.dao"})
@Import(TestConfig.class)
@EntityScan(basePackages = {"com.wuxp", "com.oak"})
@Configuration
public class OakApplicationTest {

    public static void main(String[] args) {

        System.out.println(OakApplicationTest.class.getSimpleName() + " WORK DIR:" + new File("").getAbsolutePath());

        SpringApplication.run(OakApplicationTest.class, args);

    }

}
