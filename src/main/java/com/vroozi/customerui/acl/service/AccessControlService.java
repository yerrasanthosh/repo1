package com.vroozi.customerui.acl.service;

import com.vroozi.customerui.user.services.user.model.User;

import java.util.Map;

/**
 * User: SURYA MANDADAPU
 * Date: 11/30/12
 * Time: 2:52 PM
 */
public interface AccessControlService {
    public Map loadUserACL(User user) throws Exception;
}
