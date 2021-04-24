//package com.oak.member.business.authentication;
//
//
//import com.oak.member.logic.authentication.MemberAuthenticationFailureHandler;
//import com.oak.member.logic.authentication.MemberAuthenticationSuccessHandler;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.security.access.event.AbstractAuthorizationEvent;
//import org.springframework.security.access.event.AuthorizedEvent;
//import org.springframework.security.core.Authentication;
//
//import java.util.*;
//
///**
// * @author wxup
// */
//@Setter
//@Slf4j
//public class OakAuthenticationEventPublisher implements ApplicationEventPublisher, ApplicationContextAware, InitializingBean {
//
//
//    private ApplicationContext applicationContext;
//
//    private List<MemberAuthenticationSuccessHandler> authenticationSuccessHandlers;
//
//    private List<MemberAuthenticationFailureHandler> authenticationFailureHandlers;
//
//    @Override
//    public void publishEvent(Object event) {
//        if (log.isDebugEnabled()) {
//            log.debug("广播登录事件 {}", event);
//        }
//
//        if (event instanceof AuthorizedEvent) {
//            Authentication authentication = ((AuthorizedEvent) event).getAuthentication();
//            for (MemberAuthenticationSuccessHandler handler : this.authenticationSuccessHandlers) {
//                handler.onAuthenticationSuccess(authentication);
//            }
//        } else {
//            AbstractAuthorizationEvent authorizationEvent = (AbstractAuthorizationEvent) event;
//            for (MemberAuthenticationFailureHandler handler : this.authenticationFailureHandlers) {
//                handler.onAuthenticationFailure(null);
//            }
//        }
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        ApplicationContext applicationContext = this.applicationContext;
//        if (this.authenticationSuccessHandlers == null) {
//            try {
//                List<MemberAuthenticationSuccessHandler> authenticationSuccessHandlers = new ArrayList<>(applicationContext.getBeansOfType(MemberAuthenticationSuccessHandler.class).values());
//                authenticationSuccessHandlers.sort(Comparator.comparingInt(MemberAuthenticationSuccessHandler::getOrder));
//                this.authenticationSuccessHandlers = authenticationSuccessHandlers;
//            } catch (BeansException e) {
//                e.printStackTrace();
//                log.warn("初始化authenticationSuccessHandlers异常", e);
//                this.authenticationSuccessHandlers = Collections.emptyList();
//            }
//        }
//        if (this.authenticationFailureHandlers == null) {
//            try {
//                List<MemberAuthenticationFailureHandler> authenticationFailureHandlers = new ArrayList<>(applicationContext.getBeansOfType(MemberAuthenticationFailureHandler.class).values());
//                authenticationFailureHandlers.sort(Comparator.comparingInt(MemberAuthenticationFailureHandler::getOrder));
//                this.authenticationFailureHandlers = authenticationFailureHandlers;
//            } catch (BeansException e) {
//                e.printStackTrace();
//                log.warn("初始化authenticationFailureHandlers异常", e);
//                this.authenticationFailureHandlers = Collections.emptyList();
//            }
//        }
//    }
//}
