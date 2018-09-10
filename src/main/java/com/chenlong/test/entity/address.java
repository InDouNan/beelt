package com.chenlong.test.entity;

import java.util.Date;
import java.util.List;

public class address {
     private String address;
     private String type;
     public String getAddress(){
           return this.address;
     }
     public void setAddress(String address){
           this.address = address;
     }
     public String getType(){
           return this.type;
     }
     public void setType(String type){
           this.type = type;
     }


@Override
    public String toString() {
        return "address{" +
            "  address:" + address + "  type:" + type + 
        "}";
    }
  }
