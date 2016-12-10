package com.vroozi.customerui.approver.services;

import com.vroozi.customerui.approver.exception.ApproverServiceException;
import com.vroozi.customerui.approver.services.rest.ApproverRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Taimur
 * Date: 8/31/12
 * Time: 7:40 AM
 */
@Service
public class ApproverServiceImpl implements ApproverService{
    private final Logger logger = LoggerFactory.getLogger(ApproverServiceImpl.class);

    @Autowired
    ApproverRestClient approverRestClient;

	@Override
    public List<String> getApproversByCatalogId(String catalogId) throws ApproverServiceException {
        try {
            return approverRestClient.getApproversByCatalogId(catalogId);

        } catch (Exception exp) {
            logger.error("Error retrieving Approves catalogId(" + catalogId + ")");
            throw new ApproverServiceException(exp);
        }
    }

    @Override
    public int associateCatalogApprovers(String catalogId, List<String> approverIds) throws ApproverServiceException {
        try {
            return approverRestClient.associateApprovers(catalogId, approverIds);
        } catch (Exception exp) {
            logger.error("Error adding Approves ");
            throw new ApproverServiceException(exp);
        }
    }

    @Override
    public int disAssociateCatalogApprovers(String catalogId, List<String> approverIds) throws ApproverServiceException {
        try {
            return approverRestClient.disAssociateApprovers(catalogId, approverIds);
        } catch (Exception exp) {
            logger.error("Error adding Approves ");
            throw new ApproverServiceException(exp);
        }
    }
}
