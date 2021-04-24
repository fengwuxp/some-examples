package test.com.oak.messages.pusher;

import com.oak.api.enums.ClientType;
import com.oak.messages.TemplateMessagePusher;
import com.oak.messages.enums.SendMode;
import com.oak.messages.pusher.request.SimplePushTemplateMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import test.com.oak.message.OakApplicationTest;

import java.text.MessageFormat;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(classes = {OakApplicationTest.class})
public class DefaultTemplateMessagePusherTest {


    @Autowired
    private TemplateMessagePusher templateMessagePusher;

    @Test
    @Transactional
    public void pushTemplate() {

        SimplePushTemplateMessageRequest request = new SimplePushTemplateMessageRequest();
        request.setClientType(ClientType.ANDROID.name());
        request.setMemberId(1L);
        request.setNamedParams(new HashMap<>());
        request.setSendMode(SendMode.ASYNC);
        request.setTemplateId("CANCEL_ORDER");
        request.setPositionParams(new String[]{"20190128993", "no"});
        templateMessagePusher.pushMessage(request);
    }

    @Test
    public void replaceTemplateContent() {

        String template = "测试 ==>{name} age: {age} sex   {sex}";
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "18");
        map.put("sex", "男");
        StringSubstitutor sub = new StringSubstitutor(map, "{", "}", '{');
        template = sub.replace(template);
        System.out.println(template);

        String format = MessageFormat.format("测试 {0} => {1}", new String[]{"hh", "22"});
        System.out.println(format);
    }
}