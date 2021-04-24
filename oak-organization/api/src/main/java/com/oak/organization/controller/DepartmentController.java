package com.oak.organization.controller;

import com.oak.organization.services.department.DepartmentService;
import com.oak.organization.services.department.info.DepartmentInfo;
import com.oak.organization.services.department.req.CreateDepartmentReq;
import com.oak.organization.services.department.req.DeleteDepartmentReq;
import com.oak.organization.services.department.req.EditDepartmentReq;
import com.oak.organization.services.department.req.QueryDepartmentReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenXiaoBin
 * on 2020-06-04
 */

@RestController
@RequestMapping("/v1/department")
@Tag(name = "部门", description = "部门管理")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    /**
     * 分页数据
     *
     * @param req QueryDepartmentReq
     * @return ApiResp<Pagination < DepartmentInfo>>
     */
    @GetMapping("/query")
    @Operation(summary = "查询Department", description = "部门")
    public ApiResp<Pagination<DepartmentInfo>> query(QueryDepartmentReq req) {
        return RestfulApiRespFactory.ok(departmentService.query(req));
    }


    /**
     * 新增保存
     *
     * @param req CreateDepartmentEvt
     * @return ApiResp
     */
    @PostMapping("/create")
    @Operation(summary = "创建Department", description = "部门")
    //@ApiLog(value = "#JSON.toJSONString(req)")
    public ApiResp<Long> create(CreateDepartmentReq req) {
        return departmentService.create(req);
    }


    /**
     * 详情
     *
     * @param id Long
     */
    @GetMapping("/{id}")
    @Operation(summary = "详情Department", description = "部门")
    public ApiResp<DepartmentInfo> detail(@PathVariable Long id) {
        return RestfulApiRespFactory.ok(departmentService.findById(id));
    }


    /**
     * 修改保存
     */
    @PutMapping("/edit")
    @Operation(summary = "编辑Department", description = "部门")
    public ApiResp<Void> edit(EditDepartmentReq req) {
        return departmentService.edit(req);
    }


    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除Department", description = "部门")
    public ApiResp<Void> delete(DeleteDepartmentReq req) {
        return departmentService.delete(req);
    }
}
