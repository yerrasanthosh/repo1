package com.vroozi.customerui.property.model;

import java.io.Serializable;

/**
 * User: Administrator
 * Date: 9/12/12
 * Time: 2:07 PM
 */
public class Property implements Serializable{
    public static final long serialVersionUID = 2006980516486419004L;

    private int id;
    private int catalogId;
    private String value;
    private String key;
    private String seqNum;
    private boolean dynamicKey;
}
