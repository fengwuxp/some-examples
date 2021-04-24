package com.oak.member.services.memberdepositcash;

import cn.hutool.core.date.DateUtil;
import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.SelectDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.api.model.PageInfo;
import com.oak.member.entities.MemberDepositCash;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.memberdepositcash.info.MemberDepositCashInfo;
import com.oak.member.services.memberdepositcash.req.CreateMemberDepositCashReq;
import com.oak.member.services.memberdepositcash.req.DeleteMemberDepositCashReq;
import com.oak.member.services.memberdepositcash.req.EditMemberDepositCashReq;
import com.oak.member.services.memberdepositcash.req.QueryMemberDepositCashReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * 会员账户提现记录服务
 * 2020-6-8 23:10:52
 */
@Service
@Slf4j
public class MemberDepositCashServiceImpl implements MemberDepositCashService {


    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private MemberService memberService;

    @Override
    public ApiResp<Long> create(CreateMemberDepositCashReq req) {


        MemberDepositCash entity = new MemberDepositCash();
        BeanUtils.copyProperties(req, entity);

        String sn = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
        entity.setSn(sn);
        entity.setCreateTime(new Date());
        entity.setLastUpdateTime(new Date());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberDepositCashReq req) {


        MemberDepositCash entity = jpaDao.find(MemberDepositCash.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("会员账户提现记录数据不存在");
        }

        UpdateDao<MemberDepositCash> updateDao = jpaDao.updateTo(MemberDepositCash.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新会员账户提现记录失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberDepositCashReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(MemberDepositCash.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(MemberDepositCash.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除会员账户提现记录");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberDepositCashInfo findById(Long id) {

        QueryMemberDepositCashReq queryReq = new QueryMemberDepositCashReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberDepositCashInfo> query(QueryMemberDepositCashReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberDepositCash.class, MemberDepositCashInfo.class, req);

    }

    @Override
    public List<MemberDepositCashInfo> queryByMemberDaily(Long memberId) {
        List<MemberDepositCashInfo> cashInfoList = jpaDao.selectFrom(MemberDepositCash.class, "dep")
                .eq("memberId", memberId)
                .gt("applyDate", DateUtil.beginOfDay(new Date()))
                .lte("applyDate", DateUtil.endOfDay(new Date()))
                .find(MemberDepositCashInfo.class);
        return cashInfoList;
    }

    @Override
    public Pagination<MemberDepositCashInfo> managementQuery(QueryMemberDepositCashReq req) {

        PageInfo<MemberDepositCashInfo> pageInfo = PageInfo.newInstance(req);

        SelectDao<MemberDepositCash> selectDao = jpaDao.selectFrom(MemberDepositCash.class, "dc")
                .select("dc")
                .appendJoin("left join Member m on dc.memberId = m.id");

        if (StringUtils.isNotBlank(req.getSearchName())) {
            selectDao.or()
                    .contains("dc.sn", req.getSearchName())
                    .contains("m.name", req.getSearchName())
                    .contains("m.mobilePhone", req.getSearchName());
        }
        selectDao.eq("dc.withdrawStatus", req.getWithdrawStatus());
        if( req.getId() != null ){
            selectDao.eq("dc.id", req.getId());
        }
        //按照申请时间 降序排序
        selectDao.orderBy("dc.applyDate DESC");

        pageInfo.setTotal(selectDao.count());

        List<MemberDepositCashInfo> depositCashInfos = selectDao.page(req.getQueryPage(), req.getQuerySize())
                .find(MemberDepositCashInfo.class);

        if (req.getLoadMemberInfo() && depositCashInfos != null && !depositCashInfos.isEmpty()) {
            for (MemberDepositCashInfo info : depositCashInfos) {
                MemberInfo memberInfo = memberService.findById(info.getMemberId());
                if (memberInfo != null) {
                    info.setMemberName(memberInfo.getNickName())
                            .setMemberPhone(memberInfo.getMobilePhone());
                }
            }
        }
        pageInfo.setRecords(depositCashInfos);

        return pageInfo;
    }
}
