package com.conny.tv.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Desc:
 * Created by zhanghui on 2017/11/17.
 */

@Entity
public class PersonBean implements Serializable {
    public static final long serialVersionUID = 536871008L;
    
    @Id(autoincrement = true)
    public Long _id;
    public String name;
    public int age;
    public String adderss;

    public PersonBean() {

    }


    @Generated(hash = 1559831820)
    public PersonBean(Long _id, String name, int age, String adderss) {
        this._id = _id;
        this.name = name;
        this.age = age;
        this.adderss = adderss;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdderss() {
        return this.adderss;
    }

    public void setAdderss(String adderss) {
        this.adderss = adderss;
    }


    public Long get_id() {
        return this._id;
    }



    public void set_id(Long _id) {
        this._id = _id;
    }
}
