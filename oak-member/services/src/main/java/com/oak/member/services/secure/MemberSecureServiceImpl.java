package com.oak.member.services.secure;

import com.levin.commons.dao.Converter;
import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.E_MemberSecure;
import com.oak.member.entities.MemberSecure;
import com.oak.member.services.secure.info.LoginFailInfo;
import com.oak.member.services.secure.info.MemberSecureInfo;
import com.oak.member.services.secure.req.CreateMemberSecureReq;
import com.oak.member.services.secure.req.EditMemberSecureReq;
import com.oak.member.services.secure.req.QueryMemberSecureReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.basic.uuid.JdkUUIDGenerateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import static com.oak.member.constant.MemberCacheKeyConstant.LOGIN_FAIL_CACHE_NAME;


/**
 * 用户安全相关服务
 *
 * @author wxup
 */
@Service
@Slf4j
public class MemberSecureServiceImpl implements MemberSecureService {


    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private JdkUUIDGenerateStrategy uuidGenerateStrategy;

    @Override
    public ApiResp<Long> create(CreateMemberSecureReq req) {

        MemberSecure entity = new MemberSecure();
        BeanUtils.copyProperties(req, entity);

        // 生成密码盐
        String passwordSalt = uuidGenerateStrategy.uuid();
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(passwordSalt);
        entity.setLoginPassword(passwordEncoder.encode(req.getLoginPassword()))
                .setPayPassword(passwordEncoder.encode(req.getPayPassword()))
                .setPayCryptoSalt(passwordSalt)
                .setLoginCryptoSalt(passwordSalt);
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date)
                .setLoginPwdUpdateTime(date)
                .setPayPwdUpdateTime(date);

        jpaDao.save(entity);
        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberSecureReq req) {

        MemberSecure entity = jpaDao.find(MemberSecure.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("MemberSecure数据不存在");
        }

        if (StringUtils.hasText(req.getLoginPassword())) {
            // 生成密码盐
            String passwordSalt = uuidGenerateStrategy.uuid();
            PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(passwordSalt);
//            entity.setLoginPassword(passwordEncoder.encode(req.getLoginPassword()))
//                    .setLoginCryptoSalt(passwordSalt);
            req.setLoginPassword(passwordEncoder.encode(req.getLoginPassword()))
                    .setLoginCryptoSalt(passwordSalt);
        }

        if (StringUtils.hasText(req.getPayPassword())) {
            // 生成密码盐
            String passwordSalt = uuidGenerateStrategy.uuid();
            PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(passwordSalt);
//            entity.setPayPassword(passwordEncoder.encode(req.getPayPassword()))
//                    .setPayCryptoSalt(passwordSalt);
            req.setPayPassword(passwordEncoder.encode(req.getPayPassword()))
                    .setPayCryptoSalt(passwordSalt);
        }

        UpdateDao<MemberSecure> updateDao = jpaDao.updateTo(MemberSecure.class).appendByQueryObj(req);
        updateDao.set(E_MemberSecure.lastUpdateTime, new Date());
        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新MemberSecure失败");
        }

        return RestfulApiRespFactory.ok();
    }


    @Override
    public MemberSecureInfo findById(Long id) {

        QueryMemberSecureReq queryReq = new QueryMemberSecureReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberSecureInfo> query(QueryMemberSecureReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberSecure.class, MemberSecureInfo.class, req);

    }

    @Cacheable(value = LOGIN_FAIL_CACHE_NAME, key = "#memberId", condition = "#memberId!=null", unless = "#result!=null")
    @Override
    public LoginFailInfo findLoginFail(Long memberId) {
        return jpaDao.selectFrom(MemberSecure.class)
                .eq(E_MemberSecure.id, memberId)
                .findOne((Converter<MemberSecure, LoginFailInfo>) data -> {
                    LoginFailInfo obj = new LoginFailInfo();
                    obj.setMemberId(data.getId())
                            .setFailTime(data.getLastLoginFailTime() != null ? data.getLastLoginFailTime().getTime() : null)
                            .setLockTime(data.getLastLoginFailLockTime() != null ? data.getLastLoginFailLockTime().getTime() : null)
                            .setCount(0);
                    return obj;
                });
    }

    /**
     * 保存 用户登录的失败次数
     *
     * @param memberId
     * @param loginFail
     * @return
     */
    @CachePut(value = LOGIN_FAIL_CACHE_NAME, key = "#memberId", condition = "#memberId!=null", unless = "#result!=null")
    @Override
    public LoginFailInfo saveLoginFail(Long memberId, LoginFailInfo loginFail) {
        MemberSecure memberSecure = jpaDao.selectFrom(MemberSecure.class)
                .eq(E_MemberSecure.id, memberId)
                .findOne();
        memberSecure.setLastLoginFailTime(new Date(loginFail.getFailTime()));
        memberSecure.setLastLoginFailLockTime(new Date(loginFail.getLockTime()));
        memberSecure.setLoginPwdFail(loginFail.getCount());
        jpaDao.save(memberSecure);
        return loginFail;
    }


    @Override
    public int increaseLoginFailureCount(Long memberId, Integer maxLoginCount) {
        LoginFailInfo loginFail = findLoginFail(memberId);
        if (loginFail == null) {
            loginFail = new LoginFailInfo();
            loginFail.setMemberId(memberId);
            loginFail.setCount(1);
            loginFail.setFailTime(System.currentTimeMillis());
        } else {
            loginFail.setFailTime(System.currentTimeMillis());
            loginFail.setCount(loginFail.getCount() + 1);
            if (loginFail.getCount() >= maxLoginCount) {
                // 超过最大登录次数，记录锁定起始时间
                loginFail.setLockTime(System.currentTimeMillis());
            }
        }
        saveLoginFail(memberId, loginFail);
        return maxLoginCount - loginFail.getCount();
    }


    @CacheEvict(value = LOGIN_FAIL_CACHE_NAME, key = "#memberId")
    @Override
    public void resetLoginFail(Long memberId) {
        MemberSecure memberSecure = jpaDao.selectFrom(MemberSecure.class)
                .eq(E_MemberSecure.id, memberId)
                .findOne();
        memberSecure.setLastLoginFailLockTime(null);
        memberSecure.setLastLoginFailTime(null);
        memberSecure.setLoginPwdFail(0);
        jpaDao.save(memberSecure);
    }


}
