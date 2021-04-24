package test.com.oak.member.mapstruct;

import com.oak.member.dto.MemberDTO;
import com.oak.member.mapstruct.MemberApiDtoConverter;
import com.oak.member.services.member.info.MemberInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class MemberApiDTOConverterTest {

    @Test
    public void testMemberDTO() {

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setAreaId("111").setAreaName("222").setId(1L);
        MemberDTO memberDTO = MemberApiDtoConverter.MAPPER.memberDTO(memberInfo);
        log.info("{}", memberDTO);
    }
}
