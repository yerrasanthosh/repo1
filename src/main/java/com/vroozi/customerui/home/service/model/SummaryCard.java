package com.vroozi.customerui.home.service.model;

/**
 * User: SURYA MANDADAPU
 * Date: 10/27/12
 * Time: 1:33 PM
 */
public class SummaryCard {

    private long totalCatalogsCount;
    private long approvalWaitCatalogsCount;
    private long publishWaitCatalogsCount;
    private long errorCatalogsCount;
    private long rejectedCatalogsCount;

    private long activeSuppliersCount;
    private long inactiveSuppliersCount;

    private long adminUsersCount;
    private long shopperUsersCount;
    private long buyerUsersCount;
    private long approverUsersCount;
    private long webSearchUsersCount;

    
    private long masterAdmin = 0;
    
    
    public long getMasterAdmin() {
		return masterAdmin;
	}

	public void setMasterAdmin(long masterAdmin) {
		this.masterAdmin = masterAdmin;
	}

	public long getBuyerUsersCount() {
		return buyerUsersCount;
	}

	public void setBuyerUsersCount(long buyerUsersCount) {
		this.buyerUsersCount = buyerUsersCount;
	}

	public long getApproverUsersCount() {
		return approverUsersCount;
	}

	public void setApproverUsersCount(long approverUsersCount) {
		this.approverUsersCount = approverUsersCount;
	}

	public long getTotalCatalogsCount() {
        return totalCatalogsCount;
    }

    public void setTotalCatalogsCount(long totalCatalogsCount) {
        this.totalCatalogsCount = totalCatalogsCount;
    }

    public long getApprovalWaitCatalogsCount() {
        return approvalWaitCatalogsCount;
    }

    public void setApprovalWaitCatalogsCount(long approvalWaitCatalogsCount) {
        this.approvalWaitCatalogsCount = approvalWaitCatalogsCount;
    }

    public long getPublishWaitCatalogsCount() {
        return publishWaitCatalogsCount;
    }

    public void setPublishWaitCatalogsCount(long publishWaitCatalogsCount) {
        this.publishWaitCatalogsCount = publishWaitCatalogsCount;
    }

    public long getErrorCatalogsCount() {
        return errorCatalogsCount;
    }

    public void setErrorCatalogsCount(long errorCatalogsCount) {
        this.errorCatalogsCount = errorCatalogsCount;
    }

    public long getActiveSuppliersCount() {
        return activeSuppliersCount;
    }

    public void setActiveSuppliersCount(long activeSuppliersCount) {
        this.activeSuppliersCount = activeSuppliersCount;
    }

    public long getInactiveSuppliersCount() {
        return inactiveSuppliersCount;
    }

    public void setInactiveSuppliersCount(long inactiveSuppliersCount) {
        this.inactiveSuppliersCount = inactiveSuppliersCount;
    }

    public long getAdminUsersCount() {
        return adminUsersCount;
    }

    public void setAdminUsersCount(long adminUsersCount) {
        this.adminUsersCount = adminUsersCount;
    }

    public long getShopperUsersCount() {
        return shopperUsersCount;
    }

    public void setShopperUsersCount(long shopperUsersCount) {
        this.shopperUsersCount = shopperUsersCount;
    }

    public long getWebSearchUsersCount() {
        return webSearchUsersCount;
    }

    public void setWebSearchUsersCount(long webSearchUsersCount) {
        this.webSearchUsersCount = webSearchUsersCount;
    }

    public long getRejectedCatalogsCount() {
        return rejectedCatalogsCount;
    }

    public void setRejectedCatalogsCount(long rejectedCatalogsCount) {
        this.rejectedCatalogsCount = rejectedCatalogsCount;
    }
}
