package test.com.oak.organization.management.organization;

import com.github.javafaker.Faker;
import com.oak.organization.entities.Organization;
import com.oak.organization.enums.OrganizationType;
import com.oak.organization.enums.StaffAccountType;
import com.oak.organization.services.organization.OrganizationService;
import com.oak.organization.services.organization.req.CreateOrganizationReq;
import com.oak.organization.services.staff.StaffService;
import com.oak.organization.services.staff.info.StaffInfo;
import com.oak.organization.services.staff.req.CreateStaffReq;
import com.oak.organization.services.staff.req.QueryStaffReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.organization.OakApplicationTest;

import java.util.Locale;

/**
 * @Classname StaffTest
 * @Description TODO
 * @Date 2020/3/23 20:17
 * @Created by 44487
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class StaffTest {

    @Autowired
    private StaffService staffService;

    @Autowired
    private OrganizationService organizations;

    private Faker faker = new Faker(Locale.CHINA);

    @Test
    public void test_createStaff(){

        /*

        注意生成 admin 需要额外设置 一条 创建者 数据

         */

        CreateOrganizationReq req = new CreateOrganizationReq();
        req.setAddress(faker.address().streetAddress())
                .setContacts(faker.name().name())
                .setOrganizationType(OrganizationType.PLATFORM)
                .setAreaId("350102")
                .setRemark("测试")
                .setAreaName("鼓楼区")
                .setName(faker.name().name())
                .setChannelCode("dddd");
        ApiResp<Organization> resp = organizations.create(req);

        //创建业务员
        CreateStaffReq createStaffReq = new CreateStaffReq();
        createStaffReq.setAccountType(StaffAccountType.SUB)
                .setUserName("ZS")
                .setOrganizationId(resp.getData().getId())
                .setOrganizationCode("sss")
                .setMobilePhone("1327592")
                .setCreatorId(1L)
                .setName("js")
                .setPassword("123456")
                .setEmail("111")
                .setChannelCode("ddddd");

        ApiResp<Long> longApiResp = staffService.create(createStaffReq);
        System.out.println("生成记录："+longApiResp.getData());

        // 获取列表信息
        QueryStaffReq queryStaffReq = new QueryStaffReq();
        Pagination<StaffInfo> list = staffService.query(queryStaffReq);

        for ( StaffInfo s : list.getRecords()) {
            System.out.println("记录对象："+s);
        }


    }


}
