package com.chenlong.test.entity;

import java.util.Date;
import java.util.List;

public class QQQ {
     private String msg;
     private int code;
     private Object data;
     public String getMsg(){
           return this.msg;
     }
     public void setMsg(String msg){
           this.msg = msg;
     }
     public int getCode(){
           return this.code;
     }
     public void setCode(int code){
           this.code = code;
     }
     public Object getData(){
           return this.data;
     }
     public void setData(Object data){
           this.data = data;
     }


@Override
    public String toString() {
        return "QQQ{" +
            "  msg:" + msg + "  code:" + code + "  data:" + data + 
        "}";
    }
  }
