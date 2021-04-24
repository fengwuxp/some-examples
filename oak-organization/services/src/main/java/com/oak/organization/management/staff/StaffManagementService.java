package com.oak.organization.management.staff;

import com.oak.organization.enums.ApprovalStatus;
import com.oak.organization.management.staff.dto.QueryStaffCompanyDto;
import com.oak.organization.services.organization.OrganizationService;
import com.oak.organization.services.organization.info.OrganizationInfo;
import com.oak.organization.services.organization.req.QueryOrganizationReq;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname StaffManagementService
 * @Description 业务员扩展服务
 * @Date 2020/3/24 16:48
 * @Created by 44487
 */
@Service
@Slf4j
public class StaffManagementService {

    @Autowired
    private OrganizationService organizationService;

    public Pagination<OrganizationInfo> getStaffBelongCompany(QueryStaffCompanyDto companyDto){

        QueryOrganizationReq queryOrganizationReq = new QueryOrganizationReq();

        BeanUtils.copyProperties(companyDto,queryOrganizationReq);

        queryOrganizationReq.setCreatorId(companyDto.getStaffId())
                .setStatus(getApprovalStatus(companyDto.getStatus()))
                .setType(companyDto.getType());

        Pagination<OrganizationInfo> pagination = organizationService.query(queryOrganizationReq);
        return pagination;
    }

    private ApprovalStatus getApprovalStatus(String status){
        for ( ApprovalStatus a : ApprovalStatus.values()) {
            if( a.toString().equals(status) ){
                return a;
            }
        }
        return null;
    }


}
