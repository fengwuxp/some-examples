package com.oak.rbac.management.menu;

import com.oak.rbac.management.menu.req.*;
import com.oak.rbac.services.menu.MenuService;
import com.oak.rbac.services.menu.info.MenuInfo;
import com.oak.rbac.services.menu.req.CreateMenuReq;
import com.oak.rbac.services.menu.req.DeleteMenuReq;
import com.oak.rbac.services.menu.req.EditMenuReq;
import com.oak.rbac.services.menu.req.QueryMenuReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 管理业务服务实现
 *
 * @author chenPC
 */
@Service
@Slf4j
public class MenuManagementServiceImpl implements MenuManagementService {
    @Autowired
    private MenuService menuService;

    @Override
    public Pagination<MenuInfo> getMenuInfo(QueryMenuInfoReq queryMenuInfoReq) {
        QueryMenuReq queryMenuReq = new QueryMenuReq();
        BeanUtils.copyProperties(queryMenuInfoReq, queryMenuReq);
        return menuService.query(queryMenuReq);
    }

    @Override
    public ApiResp<Long> addMenuInfo(AddMenuInfoReq addMenuInfoReq) {
        CreateMenuReq createMenuReq = new CreateMenuReq();
        BeanUtils.copyProperties(addMenuInfoReq, createMenuReq);
        ApiResp<Long> addMenu = menuService.create(createMenuReq);
        if (!addMenu.isSuccess()) {
            return RestfulApiRespFactory.error(addMenu.getErrorMessage());
        }
        return addMenu;
    }

    @Override
    public ApiResp<Void> updateMenuInfo(UpdateMenuInfoReq updateMenuInfoReq) {

        MenuInfo menuInfo = menuService.findById(updateMenuInfoReq.getId());
        if (menuInfo == null) {
            return RestfulApiRespFactory.error("菜单信息不存在");
        }

        EditMenuReq editMenuReq = new EditMenuReq();
        BeanUtils.copyProperties(updateMenuInfoReq, editMenuReq);
        ApiResp<Void> updateMenu = menuService.edit(editMenuReq);

        if (!updateMenu.isSuccess()) {
            return RestfulApiRespFactory.error(updateMenu.getErrorMessage());
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delMenuInfo(DelMenuInfoReq delMenuInfoReq) {
        DeleteMenuReq deleteMenuReq = new DeleteMenuReq();
        MenuInfo menuInfo = menuService.findById(delMenuInfoReq.getId());
        if (menuInfo == null) {
            return RestfulApiRespFactory.error("该菜单不存在");
        }
        if (menuInfo.getDeleted()) {
            return RestfulApiRespFactory.error("很抱歉，该菜单已经被删除了");
        }
        BeanUtils.copyProperties(delMenuInfoReq, deleteMenuReq);
        ApiResp<Void> delMenuInfo = menuService.delete(deleteMenuReq);
        if (!delMenuInfo.isSuccess()) {
            return RestfulApiRespFactory.error(delMenuInfo.getErrorMessage());
        }
        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> batchAddMenuInfo(BatchAddMenuReq req) {

        AddMenuInfoReq[] menuInfoReqs = req.getAddMenuInfoReqs();
        if (menuInfoReqs == null || 0 == menuInfoReqs.length) {
            return RestfulApiRespFactory.error("批量添加菜单信息为空！");
        }

        List<AddMenuInfoReq> reqList = Arrays.asList(menuInfoReqs);

        //遍历添加
        for (AddMenuInfoReq menuInfoReq : reqList) {
            addMenuWithParentId(null, menuInfoReq);
        }

        return RestfulApiRespFactory.ok();
    }

    /**
     * 遍历添加对象，带有父类ID编号
     *
     * @param parentId
     * @param menu
     */
    private void addMenuWithParentId(Long parentId, AddMenuInfoReq menu) {

        if (menu == null) {
            return;
        }
        menu.setParentId(parentId);
        //自动判断是否是叶节点
        menu.setLeaf(menu.getSubMenuList() == null || 0 == menu.getSubMenuList().length);

        ApiResp<Long> apiResp = addMenuInfo(menu);
        boolean needCreateNext = apiResp.isSuccess() && menu.getSubMenuList() != null &&
                menu.getSubMenuList().length > 0;
        if (needCreateNext) {
            //创建下级
            for (AddMenuInfoReq subMenu : menu.getSubMenuList()) {
                addMenuWithParentId(apiResp.getData(), subMenu);
            }
        }
    }

}
