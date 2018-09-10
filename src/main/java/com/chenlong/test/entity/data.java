package com.chenlong.test.entity;

import java.util.Date;
import java.util.List;

public class data {
     private List<order> orderIndex;
     public List<order> getOrderIndex(){
           return this.orderIndex;
     }
     public void setOrderIndex(List<order> orderIndex){
           this.orderIndex = orderIndex;
     }


@Override
    public String toString() {
        return "data{" +
            "  orderIndex:" + orderIndex + 
        "}";
    }
  }
