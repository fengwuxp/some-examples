package test.com.oak.member.services.member;

import com.oak.member.logic.enums.OpenType;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.QueryMemberReq;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.member.OakApplicationTest;

@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class MemberServiceImplTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void create() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByNo() {
    }

    @Test
    public void findByInviteCode() {
    }

    @Test
    public void query() {
        QueryMemberReq req = new QueryMemberReq();
//        req.setOpenType(OpenType.WE_CHAT_OPEN);
        req.setNotOpenType(OpenType.WE_CHAT_OPEN);
        Pagination<MemberInfo> pagination = memberService.query(req);
        log.info("{}", pagination);
    }

    @Test
    public void checkByOpenId() {
    }

    @Test
    public void checkByUserName() {
    }

    @Test
    public void checkByMobilePhone() {
    }

    @Test
    public void findByNameOrPhone() {
    }
}