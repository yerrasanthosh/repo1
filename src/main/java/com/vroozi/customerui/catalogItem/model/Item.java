package com.vroozi.customerui.catalogItem.model;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item extends OciCatalogItemProxy{
     private String approvedStatus;


    public String getApprovedStatus(){
        if(getOciItemStatusId()==1){
            return "Pending Approval";
        }else if(getOciItemStatusId()==2){
            return "Approved";           }
        else if(getOciItemStatusId()==3){
            return "Rejected";
        }else if(getOciItemStatusId()==4){
            return "Published";
        }
        else if(getOciItemStatusId()==5){
            return "Off-Line";
        }else{
            return "NA";
        }
    }

}
