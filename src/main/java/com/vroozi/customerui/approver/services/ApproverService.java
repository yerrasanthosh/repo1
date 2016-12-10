package com.vroozi.customerui.approver.services;

import java.util.List;
import com.vroozi.customerui.approver.exception.ApproverServiceException;
import com.vroozi.customerui.supplier.model.Supplier;

/**
 * User: Taimur
 * Date: 8/31/12
 * Time: 7:39 AM
 */
public interface ApproverService {
    List<String> getApproversByCatalogId( String catalogId) throws ApproverServiceException;
    int associateCatalogApprovers(String catalogId, List<String> userIds) throws ApproverServiceException;
    int disAssociateCatalogApprovers(String catalogId, List<String> userIds) throws ApproverServiceException;
}
