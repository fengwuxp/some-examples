package test.com.oak.member;


import com.oak.api.entities.system.ClientChannel;
import com.oak.codegen.controller.ServiceModelUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OakSpringCodeGeneratorTest {

    @Test
    public void testCodegenService() throws Exception {
        String[] outPaths = {"codegen-result", "src", "main", "java"};
        String targetFilePath = Paths.get(System.getProperty("user.dir")).resolveSibling(String.join(File.separator, outPaths)).toString();

        File file = new File(targetFilePath);
        if (file.exists()) {
            boolean delete = file.delete();
            log.debug("删除原有的输出目录{}", delete);
        }

        String basePackageName = "com.oak.api.services.client";
        Map<String, Class> entityMapping = new HashMap<>();
//        entityMapping.put("area", AreaInfo.class);
        ServiceModelUtil.entity2ServiceModel(ClientChannel.class, entityMapping, basePackageName, targetFilePath);
    }
}
