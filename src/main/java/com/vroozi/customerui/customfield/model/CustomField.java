package com.vroozi.customerui.customfield.model;

import java.util.List;
import java.util.ArrayList;

public class CustomField {
    private String id;
    private String type;
    private String name;
    private String attribute;
    private String field;
    private String displayName;
    private String description;
    private String ociMapping;
    private String priceAdder;
    private String vendorPart;

    private boolean required;
    private boolean postFilterAttribute;
    private boolean serachAble;

    private String defaultValue;
    private boolean editableMode = false;
    private boolean listView = false;
    private boolean defaultPostFilterOn;
    private String boostValue;
//    private UploadedFile customFieldImageURL;
    private List<String> customFieldList;
    private String dropDownDefaultValue;
    private String selectedVal;
//    private SelectItem[] dropDownDefault;

     private String value;
    private String dynamicPartNumber;
    private String priceModifer;

    public CustomField(){
        type="Drop Down List";
//        dropDownDefault= new SelectItem[1];
//        dropDownDefault[0] = new SelectItem("", "");

        customFieldList= new ArrayList<String>();
        customFieldList.add("");
        customFieldList.add("U:79||pover");
        customFieldList.add("T:9||po");

    }
    
    public CustomField(String id,String type,String name,String attribute,String field){
        this.id=id;
        this.type=type;
        this.name=name;
        this.attribute=attribute;
        this.field=field;

    }

    public  List<String> getCustomFieldList() {
        return customFieldList;
    }

    public void setCustomFieldList(List<String> customFieldList) {
        this.customFieldList = customFieldList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }


   

    public String getOciMapping() {
        return ociMapping;
    }

    public void setOciMapping(String ociMapping) {
        this.ociMapping = ociMapping;
    }

    public boolean isPostFilterAttribute() {
        return postFilterAttribute;
    }

    public void setPostFilterAttribute(boolean postFilterAttribute) {
        this.postFilterAttribute = postFilterAttribute;
    }

    public boolean isSerachAble() {
        return serachAble;
    }

    public void setSerachAble(boolean serachAble) {
        this.serachAble = serachAble;
    }

    public boolean isDefaultPostFilterOn() {
        return defaultPostFilterOn;
    }

    public void setDefaultPostFilterOn(boolean defaultPostFilterOn) {
        this.defaultPostFilterOn = defaultPostFilterOn;
    }

    public String getBoostValue() {
        return boostValue;
    }

    public void setBoostValue(String boostValue) {
        this.boostValue = boostValue;
    }

//    public UploadedFile getCustomFieldImageURL() {
//        return customFieldImageURL;
//    }
//
//    public void setCustomFieldImageURL(UploadedFile customFieldImageURL) {
//        this.customFieldImageURL = customFieldImageURL;
//    }
//
//    public String getDropDownDefaultValue() {
//        return dropDownDefaultValue;
//    }
//
//    public void setDropDownDefaultValue(String dropDownDefaultValue) {
//        this.dropDownDefaultValue = dropDownDefaultValue;
//    }
//
//    public SelectItem[] getDropDownDefault() {
//        return dropDownDefault;
//    }
//
//    public void setDropDownDefault(SelectItem[] dropDownDefault) {
//        this.dropDownDefault = dropDownDefault;
//    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDynamicPartNumber() {
        return dynamicPartNumber;
    }

    public void setDynamicPartNumber(String dynamicPartNumber) {
        this.dynamicPartNumber = dynamicPartNumber;
    }

    public String getPriceModifer() {
        return priceModifer;
    }

    public void setPriceModifer(String priceModifer) {
        this.priceModifer = priceModifer;
    }
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isEditableMode() {
        return editableMode;
    }

    public void setEditableMode(boolean editableMode) {
        this.priceModifer = priceModifer;
   }
//    public void addNewValue(ActionEvent e){
//        SelectItem[] arr= new SelectItem[getDropDownDefault().length+1];
//        int i=0;
//        for(SelectItem item:getDropDownDefault()){
//            arr[i]=item;
//            i++;
//        }
//        String str= value+":"+priceModifer+"||"+dynamicPartNumber;
//        arr[i]= new SelectItem(str,str);
//        setDropDownDefault(arr);
//        setDropDownDefaultValue(str);
//    }

    public String getSelectedVal() {
        return selectedVal;
    }

    public void setSelectedVal(String selectedVal) {
        this.selectedVal = selectedVal;
    }
}
