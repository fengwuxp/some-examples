package test.com.oak.cms.management.adv;

import com.oak.cms.business.adv.AdvBusinessService;
import com.oak.cms.business.adv.req.BrowseAdvReq;
import com.oak.cms.business.adv.req.CreateExtendAdvReq;
import com.oak.cms.business.adv.req.QueryAdvExtendReq;
import com.oak.cms.enums.AdvAccessType;
import com.oak.cms.enums.AdvDisplayType;
import com.oak.cms.enums.AdvType;
import com.oak.cms.services.adv.info.AdvInfo;
import com.oak.cms.services.adv.req.DeleteAdvReq;
import com.oak.cms.services.advposition.AdvPositionService;
import com.oak.cms.services.advposition.req.CreateAdvPositionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import test.com.oak.cms.OakApplicationTest;

import java.util.Date;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class AdvManagementServiceImplTest {


    @Autowired
    private AdvBusinessService advBusinessService;

    @Autowired
    private AdvPositionService advPositionService;

    @Test
    public void createAdv(){

        //创建一个广告位置
        CreateAdvPositionReq createAdvPositionReq = new CreateAdvPositionReq();

        createAdvPositionReq.setCode("adv.test.position")
                .setName("测试广告位")
                .setDescription("测试广告位信息")
                .setType(AdvType.TEXT)
                .setDisplayType( AdvDisplayType.SLIDE)
                .setWidth(750)
                .setHeight(360)
                .setPrice(0)
                .setNum(0)
                .setDefaultContent("默认广告位内容");

        //创建的广告位置信息
        ApiResp<Long> advPositionApi = advPositionService.create(createAdvPositionReq);

        //创建广告信息
        CreateExtendAdvReq createExtendAdvReq = new CreateExtendAdvReq();
        createExtendAdvReq.setApCode( "adv.test.position" )
                .setTitle("测试广告A")
                .setContent("测试广告信息内容测试---")
                .setUrl("测试跳转链接内容")
                .setStartDate(new Date())
                .setEndDate( new Date() )
                .setOrderIndex( 5 );

        ApiResp<Long> adv = advBusinessService.createAdv(createExtendAdvReq);
        Assert.isTrue( adv.isSuccess(),"操作失败" );
    }

    @Test
    public void query(){

        QueryAdvExtendReq queryAdvExtendReq = new QueryAdvExtendReq();
        queryAdvExtendReq.setApCode( "adv.test.position" );

        ApiResp<Pagination<AdvInfo>> paginationApiResp = advBusinessService.queryAdv(queryAdvExtendReq);

        Assert.isTrue( paginationApiResp.isSuccess(),"操作失败" );
    }

    @Test
    public void browseAdv(){

        String ip1 = "127.0.0.1";
        String ip2 = "127.0.0.2";


        BrowseAdvReq browseAdvReq = new BrowseAdvReq();
        browseAdvReq.setIp(ip2)
                .setAccessType( AdvAccessType.CLICK)
                .setAdvId(1L);

        ApiResp<Void> apiResp = advBusinessService.browseAdvInfo(browseAdvReq);

        Assert.isTrue(apiResp.isSuccess(),"操作失败" );

    }

    @Test
    public void deleteAdv(){

        //删除广告信息
        DeleteAdvReq deleteAdvReq = new DeleteAdvReq();
        deleteAdvReq.setId(60001L);

        ApiResp<Void> apiResp = advBusinessService.deleteAdv(deleteAdvReq);

        Assert.isTrue( apiResp.isSuccess() ,"操作失败" );

    }


}
