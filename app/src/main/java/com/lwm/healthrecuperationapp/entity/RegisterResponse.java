package com.lwm.healthrecuperationapp.entity;

import java.io.Serializable;

public class RegisterResponse implements Serializable {

   /**
    * msg : 数据库中已存在该记录
    * code : 500
    */

   private String msg;
   private int code;

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public int getCode() {
      return code;
   }

   public void setCode(int code) {
      this.code = code;
   }
}
