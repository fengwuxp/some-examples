package test.com.oak.cms.management.channel;

import com.oak.cms.business.channel.ChannelBusinessService;
import com.oak.cms.business.channel.req.CreateChannelExtendReq;
import com.oak.cms.business.channel.req.QueryChannelExtendReq;
import com.oak.cms.enums.ChannelNextMode;
import com.oak.cms.services.channel.info.ChannelInfo;
import com.oak.cms.services.channel.req.DeleteChannelReq;
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

/**
 * @Classname ChannelTest
 * @Description 栏目测试
 * @Date 2020/5/31 17:37
 * @Created by 44487
 */

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class ChannelTest {

    @Autowired
    private ChannelBusinessService channelBusinessService;

    /**
     * 查询
     */
    @Test
    public void queryChannel( ){

        QueryChannelExtendReq queryChannelExtendReq = new QueryChannelExtendReq();
        queryChannelExtendReq.setParentCode("common.problem.code");

        ApiResp<Pagination<ChannelInfo>> apiResp = channelBusinessService.queryChannel(queryChannelExtendReq);

        Assert.isTrue( apiResp.isSuccess(),"操作失败" );

    }

    /**
     * 添加
     */
    @Test
    public void createChannel( ){

        CreateChannelExtendReq req = new CreateChannelExtendReq();
        req.setParentCode("CHANNEL_GENERAL_USER_AGREEMENT")
                .setName("游你定挂团报名须知及注意事项")
                .setNextMode(ChannelNextMode.ARTICLE_LIST)
                .setCode("XXXXX_TEST");

        ApiResp<Long> apiResp = channelBusinessService.createChannel(req);

        Assert.isTrue( apiResp.isSuccess(),"操作失败" );

    }


    /**
     * 修改
     */
    @Test
    public void editChannel( ){
        // 30004
//        EditChannelExtendReq req =  new EditChannelExtendReq();
//        req.setId( 30004L )
//                .setName("测试栏目CC");
//
//        ApiResp<Void> apiResp = channelBusinessService.editChannel(req);
//
//        Assert.isTrue( apiResp.isSuccess(),"操作失败" );

    }

    /**
     * 删除
     */
    @Test
    public void deleteChannel( ){

        DeleteChannelReq deleteChannelReq = new DeleteChannelReq();
        deleteChannelReq.setId(6L);

        ApiResp<Void> apiResp = channelBusinessService.deleteChannel(deleteChannelReq);

        Assert.isTrue( apiResp.isSuccess(),"操作失败" );

    }


    /**
     * 详细信息
     */
    @Test
    public void getDetail( ){

    }

    @Test
    public void articleNumberChange(){

//        ApiResp<Void> apiResp = channelBusinessService.articleNumberChange(30002L,false);
//
//        Assert.isTrue( apiResp.isSuccess(),apiResp.getErrorMessage() );

    }

}
