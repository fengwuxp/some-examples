package com.oak.organization.controller;

import com.oak.organization.management.staff.StaffManagementService;
import com.oak.organization.management.staff.dto.QueryStaffCompanyDto;
import com.oak.organization.services.organization.info.OrganizationInfo;
import com.oak.organization.services.staff.StaffService;
import com.oak.organization.services.staff.info.StaffInfo;
import com.oak.organization.services.staff.req.CreateStaffReq;
import com.oak.organization.services.staff.req.DeleteStaffReq;
import com.oak.organization.services.staff.req.EditStaffReq;
import com.oak.organization.services.staff.req.QueryStaffReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/staff")
@Tag(name = "员工", description = "员工管理")
@Slf4j
public class StaffController {


    @Autowired
    private StaffService staffService;

    @Autowired
    private StaffManagementService staffManagementService;

    /**
     * 分页数据
     *
     * @param req  QueryStaffReq
     * @return  ApiResp<Pagination<StaffInfo>>
     */
    @GetMapping("/query")
    @Operation(summary = "查询Staff", description = "员工")
    public ApiResp<Pagination<StaffInfo>> query(QueryStaffReq req) {
        return RestfulApiRespFactory.ok(staffService.query(req));
    }


    /**
     * 新增保存
     *
     * @param req   CreateStaffEvt
     * @return ApiResp
     */
    @PostMapping("/create")
    @Operation(summary = "创建Staff", description = "员工")
    //@ApiLog(value = "#JSON.toJSONString(req)")
    public ApiResp<Long> create(CreateStaffReq req) {
        return staffService.create(req);
    }



    /**
    * 详情
    *
    * @param id Long
    */
    @GetMapping("/{id}")
    @Operation(summary = "详情Staff", description = "员工")
    public ApiResp<StaffInfo> detail(@PathVariable Long id) {
        return RestfulApiRespFactory.ok(staffService.findById(id));
     }


    /**
     * 修改保存
     */
     @PutMapping("/edit")
     @Operation(summary = "编辑Staff", description = "员工")
     public ApiResp<Void> edit(EditStaffReq req) {
         return staffService.edit(req);
    }


    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除Staff", description = "员工")
    public ApiResp<Void> delete(DeleteStaffReq req) {
        return staffService.delete(req);
    }

    @GetMapping("/resetps/{id}")
    @Operation(summary = "重置员工密码", description = "员工")
    public ApiResp<Void> resetStaffPassword(@PathVariable Long id){
        return staffService.resetStaffPassword(id);
    }

    @GetMapping("/get_staff_belong_company")
    @Operation(summary = "获取业务员关联公司（直属）", description = "（直属）获取业务员关联公司，查询内容包括公司状态，公司类型")
    public ApiResp<Pagination<OrganizationInfo>> getStaffBelongCompany(QueryStaffCompanyDto companyDto){
        return RestfulApiRespFactory.ok(staffManagementService.getStaffBelongCompany(companyDto));
    }


}
