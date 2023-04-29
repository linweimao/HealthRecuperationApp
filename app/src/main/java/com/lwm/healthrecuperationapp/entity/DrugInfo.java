package com.lwm.healthrecuperationapp.entity;

import cn.bmob.v3.BmobObject;

// 药品信息
public class DrugInfo extends BmobObject {

    private String nurseid; // 护工编号
    private String drugbarcode; // 药品条码
    private String drugname; // 药品名称
    private String enterprisename; // 企业名称
    private String brandname; // 品牌名称
    private String drugspecifications; // 药品规格
    private String administrationtime; // 服药时间
    private String drugsusageanddosage; // 药品用法用量

    public String getNurseid() {
        return nurseid;
    }

    public void setNurseid(String nurseid) {
        this.nurseid = nurseid;
    }

    public String getDrugbarcode() {
        return drugbarcode;
    }

    public void setDrugbarcode(String drugbarcode) {
        this.drugbarcode = drugbarcode;
    }

    public String getDrugname() {
        return drugname;
    }

    public void setDrugname(String drugname) {
        this.drugname = drugname;
    }

    public String getEnterprisename() {
        return enterprisename;
    }

    public void setEnterprisename(String enterprisename) {
        this.enterprisename = enterprisename;
    }

    public String getBrandname() {
        return brandname;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getDrugspecifications() {
        return drugspecifications;
    }

    public void setDrugspecifications(String drugspecifications) {
        this.drugspecifications = drugspecifications;
    }

    public String getAdministrationtime() {
        return administrationtime;
    }

    public void setAdministrationtime(String administrationtime) {
        this.administrationtime = administrationtime;
    }

    public String getDrugsusageanddosage() {
        return drugsusageanddosage;
    }

    public void setDrugsusageanddosage(String drugsusageanddosage) {
        this.drugsusageanddosage = drugsusageanddosage;
    }

    @Override
    public String toString() {
        return "DrugInfo{" +
                "nurseid='" + nurseid + '\'' +
                ", drugbarcode='" + drugbarcode + '\'' +
                ", drugname='" + drugname + '\'' +
                ", enterprisename='" + enterprisename + '\'' +
                ", brandname='" + brandname + '\'' +
                ", drugspecifications='" + drugspecifications + '\'' +
                ", administrationtime='" + administrationtime + '\'' +
                ", drugsusageanddosage='" + drugsusageanddosage + '\'' +
                '}';
    }
}
