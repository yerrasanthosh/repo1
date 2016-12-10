package com.vroozi.customerui.profile.model;

import java.util.List;
import java.util.ArrayList;

public class ProfileList {
    private String companyCode;
    private int userId;
    private List<Profile> profilesList;

    public ProfileList(int userId) {
        setUserId(userId);
        
        profilesList = new ArrayList<Profile>();
        Profile profile1 = new Profile();

        profile1.setProfileId(1);
        profile1.setProfileName("ProfileA");
        profile1.setProfileDesc("ProfileA Description");

        Profile profile2 = new Profile();
        profile2.setProfileId(2);
        profile2.setProfileName("ProfileB");
        profile2.setProfileDesc("ProfileB Description");

        this.profilesList.add(profile1);
        this.profilesList.add(profile2);
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Profile> getProfilesList() {
        return profilesList;
    }

    public void setProfilesList(List<Profile> profilesList) {
        this.profilesList = profilesList;
    }
}
