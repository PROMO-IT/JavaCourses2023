//package ru.promo.springapp.beanpostprocessor;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//import ru.promo.springapp.model.ITCompany;
//import ru.promo.springapp.service.VacancyService;
//
//@Component
//public class VacancyServicePostProcessor implements BeanPostProcessor {
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
////        System.out.println("before init " + beanName);
//        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
////        System.out.println("after init " + beanName);
//        if (bean instanceof VacancyService vacancyService) {
//            //do something
//        }
//        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
//    }
//}
