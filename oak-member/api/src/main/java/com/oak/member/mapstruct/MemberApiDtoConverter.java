package com.oak.member.mapstruct;

import com.oak.member.dto.MemberDTO;
import com.oak.member.services.member.info.MemberInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用来转换用户相关的数据
 *
 * @author wxup
 */
@Mapper
public interface MemberApiDtoConverter {

    MemberApiDtoConverter MAPPER = Mappers.getMapper(MemberApiDtoConverter.class);

    /**
     * 用户对象转换
     *
     * @param memberInfo
     * @return
     */
    MemberDTO memberDTO(MemberInfo memberInfo);
}
