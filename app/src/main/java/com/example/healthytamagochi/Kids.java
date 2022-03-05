package com.example.healthytamagochi;

import com.google.type.Date;

public class Kids {

    String name, sex, kg, cm, parentID;

    public Kids(String name, String sex, String kg, String cm, String parentID, Date birth) {
        this.name = name;
        this.sex = sex;
        this.kg = kg;
        this.cm = cm;
        this.parentID = parentID;
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public String getCm() {
        return cm;
    }

    public void setCm(String cm) {
        this.cm = cm;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    Date birth;
}


