package test.com.oak.organization;


import com.oak.codegen.controller.ServiceModelUtil;
import com.oak.organization.entities.Staff;
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

        //组织信息
//        String basePackageName = "com.oak.organization.services.organizationextendedinfo";
//
//        Map<String, Class> entityMapping = new HashMap<>();
//        entityMapping.put("organization", OrganizationInfo.class);
//        ServiceModelUtil.entity2ServiceModel(OrganizationExtendedInfo.class, entityMapping, basePackageName, targetFilePath);

        //职员 Staff信息
        String basePackageName = "com.oak.organization.services.staff";

        Map<String, Class> entityMapping = new HashMap<>();
        entityMapping.put("staff", Staff.class);
        ServiceModelUtil.entity2ServiceModel(Staff.class, entityMapping, basePackageName, targetFilePath);

    }
}
