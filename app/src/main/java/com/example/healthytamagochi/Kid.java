package com.example.healthytamagochi;

import com.google.type.Date;

public class Kid {

    String parentID;
    String name;
    String sex;
    String kg;
    String cm;
    Date birth;

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
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

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }



    public Kid(String parentID, String name, String sex, String kg, String cm, Date birth) {
        this.parentID = parentID;
        this.name = name;
        this.sex = sex;
        this.kg = kg;
        this.cm = cm;
        this.birth = birth;
    }
}


