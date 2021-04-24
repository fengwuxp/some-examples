package test.com.oak.member.services;

import com.wuxp.basic.uuid.sn.SnGenerateStrategy;
import org.springframework.stereotype.Component;

@Component
public class MockSnGenerateStrategy implements SnGenerateStrategy {

    @Override
    public String nextSn(SnType type) {
        return "";
    }
}
