package com.chenlong.test.entity;

import java.util.Date;
import java.util.List;

public class order {
     private String car_model;
     private String gps_imei;
     private String id_number;
     private String orderno;
     private List<address> addressIndex;
     private String car_frame_number;
     private String [] phone;
     private String src;
     private String car_number;
     private String name;
     public String getCar_model(){
           return this.car_model;
     }
     public void setCar_model(String car_model){
           this.car_model = car_model;
     }
     public String getGps_imei(){
           return this.gps_imei;
     }
     public void setGps_imei(String gps_imei){
           this.gps_imei = gps_imei;
     }
     public String getId_number(){
           return this.id_number;
     }
     public void setId_number(String id_number){
           this.id_number = id_number;
     }
     public String getOrderno(){
           return this.orderno;
     }
     public void setOrderno(String orderno){
           this.orderno = orderno;
     }
     public List<address> getAddressIndex(){
           return this.addressIndex;
     }
     public void setAddressIndex(List<address> addressIndex){
           this.addressIndex = addressIndex;
     }
     public String getCar_frame_number(){
           return this.car_frame_number;
     }
     public void setCar_frame_number(String car_frame_number){
           this.car_frame_number = car_frame_number;
     }
     public String [] getPhone(){
           return this.phone;
     }
     public void setPhone(String [] phone){
           this.phone = phone;
     }
     public String getSrc(){
           return this.src;
     }
     public void setSrc(String src){
           this.src = src;
     }
     public String getCar_number(){
           return this.car_number;
     }
     public void setCar_number(String car_number){
           this.car_number = car_number;
     }
     public String getName(){
           return this.name;
     }
     public void setName(String name){
           this.name = name;
     }


@Override
    public String toString() {
        return "order{" +
            "  car_model:" + car_model + "  gps_imei:" + gps_imei + "  id_number:" + id_number + "  orderno:" + orderno + "  addressIndex:" + addressIndex + "  car_frame_number:" + car_frame_number + "  phone:" + phone + "  src:" + src + "  car_number:" + car_number + "  name:" + name + 
        "}";
    }
  }
