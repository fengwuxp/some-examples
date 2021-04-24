package test.com.oak.api.services.operationlog;

import com.oak.api.services.log.OperationalLogService;
import com.oak.api.services.log.info.OperationalLogInfo;
import com.oak.api.services.log.req.QueryOperationalLogReq;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import test.com.oak.api.OakApplicationTest;

/**
 * @Classname OperationalLogServiceTest
 * @Description TODO
 * @Date 2020/5/22 10:17
 * @Created by 44487
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class OperationalLogServiceTest {

    @Autowired
   private OperationalLogService operationalLogService;


    @Test
   public void queryOperationalLogTest(){

       QueryOperationalLogReq req = new QueryOperationalLogReq();

       Pagination<OperationalLogInfo> pagination = operationalLogService.queryOperationalLog(req);

       Assert.isTrue( pagination.getRecords().size() > 0 ,"操作失败" );

   }

}
