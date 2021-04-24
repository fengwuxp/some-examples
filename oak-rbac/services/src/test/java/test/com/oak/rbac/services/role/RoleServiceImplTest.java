package test.com.oak.rbac.services.role;

import com.github.javafaker.Faker;
import com.oak.rbac.enums.PermissionValueType;
import com.oak.rbac.enums.ResourceType;
import com.oak.rbac.services.permission.PermissionService;
import com.oak.rbac.services.permission.info.PermissionInfo;
import com.oak.rbac.services.permission.req.CreatePermissionReq;
import com.oak.rbac.services.permission.req.QueryPermissionReq;
import com.oak.rbac.services.resource.ResourceService;
import com.oak.rbac.services.resource.info.ResourceInfo;
import com.oak.rbac.services.resource.req.CreateResourceReq;
import com.oak.rbac.services.resource.req.QueryResourceReq;
import com.oak.rbac.services.role.RoleService;
import com.oak.rbac.services.role.info.RoleInfo;
import com.oak.rbac.services.role.req.CreateRoleReq;
import com.oak.rbac.services.role.req.DeleteRoleReq;
import com.oak.rbac.services.role.req.EditRoleReq;
import com.oak.rbac.services.role.req.QueryRoleReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryOrderType;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.rbac.OakRbacApplicationTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * RoleServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>1月 8, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakRbacApplicationTest.class})
//@Transactional(rollbackFor = {Throwable.class})
@Slf4j
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ResourceService resourceService;

    private Faker faker = new Faker();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createRole(CreateRoleReq req)
     */
    @Test
    public void testCreateRole() throws Exception {

        this.testCreateResource();
        this.testCreatePermission();

        QueryPermissionReq queryPermissionReq = new QueryPermissionReq();
        Pagination<PermissionInfo> pagination = permissionService.queryPermission(queryPermissionReq);
        List<PermissionInfo> records = pagination.getRecords();
        Assert.assertNotNull(records);
        Assert.assertFalse(records.isEmpty());

        records.clear();

        CreateRoleReq req = new CreateRoleReq();
        req.setName(faker.name().title());
        req.setPermissionIds(records.stream().map(PermissionInfo::getId).toArray(Long[]::new));
        ApiResp<Long> resp = roleService.createRole(req);
        Assert.assertTrue(resp.getErrorMessage(), resp.isSuccess());
    }

    /**
     * Method: editRole(EditRoleReq req)
     */
    @Test
    public void testEditRole() throws Exception {
        this.testCreateRole();
        QueryPermissionReq queryPermissionReq = new QueryPermissionReq();
        queryPermissionReq.setDefaultOrderById(QueryOrderType.ASC);
        Pagination<PermissionInfo> pagination = permissionService.queryPermission(queryPermissionReq);
        List<PermissionInfo> records = pagination.getRecords();
        Assert.assertNotNull(records);
        Assert.assertFalse(records.isEmpty());


        QueryRoleReq req = new QueryRoleReq();
        req.setFetchPermission(true);
        RoleInfo roleInfo = roleService.queryRole(req).getFirst();
        Assert.assertNotNull(roleInfo);


        Set<PermissionInfo> permissions = roleInfo.getPermissions();
//        Assert.assertNotNull(permissions);
//        Assert.assertFalse(permissions.isEmpty());
        if (permissions == null) {
            permissions = new HashSet<>();
        }
        List<PermissionInfo> permissionInfos = permissions.stream().filter(permissionInfo -> permissionInfo.getId() % 2 == 0).collect(Collectors.toList());
        permissionInfos.addAll(records);

        EditRoleReq editRoleReq = new EditRoleReq();
        editRoleReq.setId(roleInfo.getId());
        editRoleReq.setName(faker.name().name());
        editRoleReq.setPermissionIds(permissionInfos.stream().map(PermissionInfo::getId).toArray(Long[]::new));
        ApiResp<Void> resp = roleService.editRole(editRoleReq);
        Assert.assertTrue(resp.isSuccess());
        log.debug("测试编辑角色{} , 权限个数：{}", resp, permissionInfos.size());

    }

    /**
     * Method: deleteRole(DeleteRoleReq req)
     */
    @Test
    public void testDeleteRole() throws Exception {

        DeleteRoleReq req = new DeleteRoleReq();
        req.setId(1L);
        ApiResp<Void> resp = roleService.deleteRole(req);
        log.debug("测试删除角色：{}", resp);
    }

    /**
     * Method: findRoleById(Long id)
     */
    @Test
    public void testFindRoleById() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: findRoleByName(String name)
     */
    @Test
    public void testFindRoleByName() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: queryRole(QueryRoleReq req)
     */
    @Test
    public void testQueryRole() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: createResource(CreateResourceReq req)
     */
    @Test
    public void testCreateResource() throws Exception {

        CreateResourceReq req = new CreateResourceReq();
        req.setCode("example");
        req.setName("用例");
        req.setType(ResourceType.URL);
        req.setModuleCode("default");
        req.setModuleName("默认");

        String[][] actions = new String[][]{
                new String[]{"创建", "/example/crate"},
                new String[]{"修改", "/example/update"},
                new String[]{"查询", "/example/query"},
                new String[]{"删除", "/example/delete"},
                new String[]{"查看", "example/detail"}
        };


        req.setPermissions(Arrays.stream(actions).map(action -> {
            CreatePermissionReq createPermissionReq = new CreatePermissionReq();
            createPermissionReq.setName(action[0]);
            createPermissionReq.setDescription(action[1]);
            createPermissionReq.setValue(action[0]);
            createPermissionReq.setHttpMethod("GET");
            createPermissionReq.setValueType(PermissionValueType.API);
            createPermissionReq.setResourceId(req.getCode());

            return createPermissionReq;
        }).toArray(CreatePermissionReq[]::new));
        resourceService.createResource(req);

    }


    /**
     * Method: createPermission(CreatePermissionReq req)
     */
    @Test
    public void testCreatePermission() throws Exception {

        QueryResourceReq queryResourceReq = new QueryResourceReq();
        Pagination<ResourceInfo> resource = resourceService.queryResource(queryResourceReq);
        ResourceInfo resourceInfo = resource.getFirst();
        if (resourceInfo == null) {
            return;
        }
//        Assert.assertNotNull(resourceInfo);

        CreatePermissionReq req = new CreatePermissionReq();
        req.setResourceId(resourceInfo.getId());
        req.setValue(resourceInfo.getOrderCode() + "/create");
        req.setName("创建" + resourceInfo.getId());
        req.setHttpMethod("GET");
        req.setValueType(PermissionValueType.API);
        req.setOrderCode(0);
        Long permissionId = permissionService.createPermission(req);

        Assert.assertNotNull(permissionId);
        log.debug("--测试创建权限-->{}", permissionId);

    }

}
