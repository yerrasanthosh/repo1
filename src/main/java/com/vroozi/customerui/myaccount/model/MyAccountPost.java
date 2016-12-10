package com.vroozi.customerui.myaccount.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.vroozi.customerui.profile.model.ProfileGroup;
import com.vroozi.customerui.supplier.model.Country;
import com.vroozi.customerui.supplier.model.DateFormat;
import com.vroozi.customerui.supplier.model.DecimalNotation;
import com.vroozi.customerui.supplier.model.Language;
import com.vroozi.customerui.supplier.model.RecordsPerPage;
import com.vroozi.customerui.supplier.model.State;
import com.vroozi.customerui.supplier.model.TimeZone;
import com.vroozi.customerui.user.services.user.model.User;
import com.vroozi.customerui.user.services.user.model.UserDetail;



/**
 * MyAccount Information post object
 * @author Khansaj
 *
 */
public class MyAccountPost implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4735099657027935740L;

    private List<Country> countriesList = new ArrayList<Country>();
    private List<State> stateList = new ArrayList<State>();
    private List<Language> languagesList = new ArrayList<Language>();
    private List<DecimalNotation> decimalNotationList = new ArrayList<DecimalNotation>();
    private List<TimeZone> timeFormatList = new ArrayList<TimeZone>();
    private List<DateFormat> dateFormatList = new ArrayList<DateFormat>();
    private List<TimeZone> timeZoneList = new ArrayList<TimeZone>();
    private List<ProfileGroup> contentViewGroups = new ArrayList<ProfileGroup>();
    
    private List<RecordsPerPage> rowsPerPageList = new ArrayList<RecordsPerPage>();
    
    private User user = new User();
    private UserDetail detail = new UserDetail();
        
    
    public List<RecordsPerPage> getRowsPerPageList() {
		return rowsPerPageList;
	}
	public void setRowsPerPageList(List<RecordsPerPage> rowsPerPageList) {
		this.rowsPerPageList = rowsPerPageList;
	}
	public List<TimeZone> getTimeZoneList() {
		return timeZoneList;
	}
	public void setTimeZoneList(List<TimeZone> timeZoneList) {
		this.timeZoneList = timeZoneList;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserDetail getDetail() {
		return detail;
	}
	public void setDetail(UserDetail detail) {
		this.detail = detail;
	}
	public List<Country> getCountriesList() {
		return countriesList;
	}
	public void setCountriesList(List<Country> countriesList) {
		this.countriesList = countriesList;
	}
	public List<State> getStateList() {
		return stateList;
	}
	public void setStateList(List<State> stateList) {
		this.stateList = stateList;
	}
	public List<Language> getLanguagesList() {
		return languagesList;
	}
	public void setLanguagesList(List<Language> languagesList) {
		this.languagesList = languagesList;
	}
	public List<DecimalNotation> getDecimalNotationList() {
		return decimalNotationList;
	}
	public void setDecimalNotationList(List<DecimalNotation> decimalNotationList) {
		this.decimalNotationList = decimalNotationList;
	}
	public List<TimeZone> getTimeFormatList() {
		return timeFormatList;
	}
	public void setTimeFormatList(List<TimeZone> timeFormatList) {
		this.timeFormatList = timeFormatList;
	}
	public List<DateFormat> getDateFormatList() {
		return dateFormatList;
	}
	public void setDateFormatList(List<DateFormat> dateFormatList) {
		this.dateFormatList = dateFormatList;
	}
    public List<ProfileGroup> getContentViewGroups() {
        return contentViewGroups;
    }
    public void setContentViewGroups(List<ProfileGroup> contentViewGroups) {
        this.contentViewGroups = contentViewGroups;
    }
	
	
}
