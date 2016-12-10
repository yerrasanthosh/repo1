package com.vroozi.customerui.acl.model;


import java.io.Serializable;

/**
 * User: SURYA MANDADAPU
 * Date: 11/29/12
 * Time: 3:40 PM
 */
public class AccessControl implements Serializable {
    private String _id;
    private String unitId;
    private Permission permission;
    private Role role;


    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ID=" + this.get_id());
        sb.append(", PERMISSION=" + this.getPermission());
        sb.append(", ROLE=" + this.getRole());
        sb.append("]");
        return sb.toString();
    }
}
