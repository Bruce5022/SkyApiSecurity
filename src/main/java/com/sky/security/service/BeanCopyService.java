package com.sky.security.service;

import org.springframework.beans.BeanUtils;

public interface BeanCopyService {

    default  <T> T copyBean(Object source,Class<T> target){
        T newInstance = null;
        try {
            newInstance = target.newInstance();
            BeanUtils.copyProperties(source,newInstance);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return newInstance;
    }
}
