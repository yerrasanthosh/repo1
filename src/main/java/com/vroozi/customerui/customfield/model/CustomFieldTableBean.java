package com.vroozi.customerui.customfield.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.Serializable;

public class CustomFieldTableBean implements Serializable  {
    private static String[] fieldList;
    private static String[] fieldTypeList;
    private static String[] boostValueList;

    private CustomField selectedItem;
    private String dropDownDefaultValue;
    private String backupSelectedVal;
    private int index;
   
    private static String[] ociMappingGield;
    
    private CustomField customField;

    public CustomFieldTableBean(){

    }

    public static String[] getOciMappingGield() {
        return ociMappingGield;
    }

    public static void setOciMappingGield(String[] ociMappingGield) {
        CustomFieldTableBean.ociMappingGield = ociMappingGield;
    }

    public static String[] getBoostValueList() {
        return boostValueList;
    }

    public static void setBoostValueList(String[] boostValueList) {
        CustomFieldTableBean.boostValueList = boostValueList;
    }


    public String reinit(){
//        setCustomField(new CustomField());
        return "";
    }

    static {
        fieldList = new String[2];
        fieldList[0]= "Active";
        fieldList[1]= " In Active";

        fieldTypeList = new String[6];
        fieldTypeList[0]= "Input Field";
        fieldTypeList[1]= "Drop Down List";
        fieldTypeList[2]= "Fixed Value";
        fieldTypeList[3]= "Input Field Medium";
        fieldTypeList[4]= "Input Field Large";
        fieldTypeList[5]= "Flag";

        ociMappingGield =new String[6];
        ociMappingGield[0]="NEW_ITEM-LONGTEXT";
        ociMappingGield[1]="NEW_ITEM-CUST_FIELD1";
        ociMappingGield[2]="NEW_ITEM-CUST_FIELD2";
        ociMappingGield[3]="NEW_ITEM-CUST_FIELD3";
        ociMappingGield[4]="NEW_ITEM-CUST_FIELD4";
        ociMappingGield[5]="NEW_ITEM-CUST_FIELD5";

        boostValueList= new String[21];
        boostValueList[0]="10";
        boostValueList[1]="9";
        boostValueList[2]="8";
        boostValueList[3]="7";
        boostValueList[4]="6";
        boostValueList[5]="5";
        boostValueList[6]="4";
        boostValueList[7]="3";
        boostValueList[8]="2";
        boostValueList[9]="1";
        boostValueList[10]="0";
        boostValueList[11]="-1";
        boostValueList[12]="-2";
        boostValueList[13]="-3";
        boostValueList[14]="-4";
        boostValueList[15]="-5";
        boostValueList[16]="-6";
        boostValueList[17]="-7";
        boostValueList[18]="-8";
        boostValueList[19]="-9";
        boostValueList[20]="-10";

    }

//    private SelectItem[] customFieldsptions;
//    private SelectItem[] customFieldsDropDownList;
//    private List<CustomField> customFieldsSmall;
//
//    private SelectItem[] fldOptions;
//    private SelectItem[] ociMappingOptions;
//
//    private SelectItem[] boostValueOptions;
    
    CustomFieldTableBean(CustomField customField){
        this.customField=customField;
//        customFieldsSmall = new ArrayList<CustomField>();


//        customFieldsptions = createFilterOptions(fieldTypeList);
//        fldOptions = createFldOptions(fieldList) ;
       // customFieldsDropDownList = createFilterOptions(customFieldList);
//        setOciMappingOptions(createFilterOptions(ociMappingGield));
//        setBoostValueOptions(createFilterOptions(boostValueList));

    }

//    public SelectItem[] getFldOptions() {
//        return fldOptions;
//    }
//
//    public void setFldOptions(SelectItem[] fldOptions) {
//        this.fldOptions = fldOptions;
//    }
//
//    public CustomFieldTableBean() {
//        customFieldsSmall = new ArrayList<CustomField>();
//
//
//        customFieldsptions = createFilterOptions(fieldTypeList);
//        fldOptions = createFldOptions(fieldList) ;
//        //customFieldsDropDownList = createFilterOptions(customFieldList);
//    }

    public void populateRandomCustomFields(int size) {
        for(int i = 0 ; i < size ; i++)
            this.getCustomFieldsSmall().add(new CustomField(getRandomFieldID(), getFieldTypeList(), "A" + i, "Attr" + i, getFieldList()));
    }

    public List<CustomField> getCustomFieldsSmall() {
//        return customFieldsSmall;
        return null;
    }

    private int getRandomYear() {
        return (int) (Math.random() * 50 + 1960);
    }

    public String getFieldList() {
        return fieldList[1];
    }

    public String getFieldTypeList() {
        return fieldTypeList[1];
    }

    public String getRandomFieldID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
/*
    private SelectItem[] createFilterOptions(String[] data)  {
        SelectItem[] options = new SelectItem[data.length + 1];

        options[0] = new SelectItem("", "Select");
        for(int i = 0; i < data.length; i++) {
            options[i + 1] = new SelectItem(data[i], data[i]);
        }

        return options;
    }

    public SelectItem[] getCustomFieldsptions() {
        return customFieldsptions;
    }

    public SelectItem[] createFldOptions(String[] data) {

        SelectItem[] options = new SelectItem[data.length + 1];

        options[0] = new SelectItem("", "Select");
        for(int i = 0; i < data.length; i++) {
            options[i + 1] = new SelectItem(data[i], data[i]);
        }
        
        return options;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }

    public SelectItem[] getOciMappingOptions() {
        return ociMappingOptions;
    }

    public void setOciMappingOptions(SelectItem[] ociMappingOptions) {
        this.ociMappingOptions = ociMappingOptions;
    }

    public SelectItem[] getBoostValueOptions() {
        return boostValueOptions;
    }

    public void setBoostValueOptions(SelectItem[] boostValueOptions) {
        this.boostValueOptions = boostValueOptions;
    }
    public void doNothing(){

    }

    public SelectItem[] getCustomFieldsDropDownList() {
        return customFieldsDropDownList;
    }

    public void setCustomFieldsDropDownList(SelectItem[] customFieldsDropDownList) {
        this.customFieldsDropDownList = customFieldsDropDownList;
    }

    public CustomField getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(CustomField selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getDropDownDefaultValue() {
        return dropDownDefaultValue;
    }

    public void setDropDownDefaultValue(String dropDownDefaultValue) {
        this.dropDownDefaultValue = dropDownDefaultValue;
    }

    public String getBackupSelectedVal() {
        return backupSelectedVal;
    }

    public void setBackupSelectedVal(String backupSelectedVal) {
        this.backupSelectedVal = backupSelectedVal;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
*/
}
