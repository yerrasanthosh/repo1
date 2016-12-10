package com.vroozi.customerui.approver.services.rest;

import java.util.List;

import com.vroozi.customerui.approver.exception.ApproverServiceException;
//import com.vroozi.customerui.approver.model.ApproverProxy;

/**
 * User: Administrator
 * Date: 8/31/12
 * Time: 5:57 PM
 */
public interface ApproverRestClient {
    List<String> getApproversByCatalogId( String catalogId) throws Exception;
    int associateApprovers(String catalogId, List<String> userIds) throws Exception;
    int disAssociateApprovers(String catalogId, List<String> userIds) throws Exception;
}
