package cn.edu.xmu.actuatorserver.bean;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Ming Qiu
 * @date: Created in 17:04 2020/7/31
 **/
public class Boss_constructor {
    private Car car;
    private Office office;

    @Autowired
    public Boss_constructor(Car car, Office office){
        this.car = car;
        this.office = office;
    }
}