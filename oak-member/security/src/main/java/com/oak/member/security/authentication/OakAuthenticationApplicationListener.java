//package com.oak.member.business.authentication;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationEventPublisherAware;
//import org.springframework.context.ApplicationListener;
//import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
//
///**
// * 用于监听用户登录事件
// *
// * @author wxup
// */
//@Slf4j
//public class OakAuthenticationApplicationListener implements ApplicationListener<AbstractAuthenticationEvent>/*, ApplicationEventPublisherAware*/ {
//
//    private ApplicationEventPublisher publisher;
//
//    public OakAuthenticationApplicationListener(ApplicationEventPublisher publisher) {
//        this.publisher = publisher;
//    }
//
//    @Override
//    public void onApplicationEvent(AbstractAuthenticationEvent event) {
//        this.publish(event);
//    }
//
//    protected ApplicationEventPublisher getPublisher() {
//        return this.publisher;
//    }
//
//    protected void publish(AbstractAuthenticationEvent event) {
//        if (getPublisher() != null) {
//            getPublisher().publishEvent(event);
//        }
//    }
//}
