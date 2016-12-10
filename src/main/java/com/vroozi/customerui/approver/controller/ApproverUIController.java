package com.vroozi.customerui.approver.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vroozi.customerui.profile.model.ProfileGroup;
import org.apache.commons.lang.StringUtils;
import com.vroozi.customerui.acl.model.Role;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.notification.services.NotificationService;
import com.vroozi.customerui.supplier.model.Supplier;
import com.vroozi.customerui.supplier.services.SupplierService;
import com.vroozi.customerui.user.controller.UserUIController;
import com.vroozi.customerui.user.services.UserManagementService;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vroozi.customerui.approver.services.ApproverService;
import com.vroozi.customerui.user.services.user.model.User;

import static com.vroozi.customerui.util.Consts.*;

@Controller
public class ApproverUIController {

    private final Logger LOGGER = LoggerFactory.getLogger(ApproverUIController.class);

    @Autowired
    ApproverService approverService;

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    SupplierService supplierService;

    @RequestMapping(value = "/create-new-approver", method = RequestMethod.POST)
    public String createNewCatalogApprover(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("user") User userToAdd, ModelMap modelMap) {

        String catalogId = request.getParameter("catalogId");
        String supplierId = request.getParameter("supplierId");

        User loggedInUser = SessionDataRetriever.getLoggedInUser(request);

        return createNewApprover(catalogId, supplierId, userToAdd, loggedInUser, modelMap, request);
    }

    private String createNewApprover(String catalogId, String supplierId, User userToAdd, User loggedInUser, ModelMap modelMap, HttpServletRequest request) {
        boolean exitingUser =false;

        try{
            User approverUser = userManagementService.getUserByUserName(userToAdd.getUsername(), loggedInUser.getUnitId());

            if(((approverUser != null) && !createAuthorized(loggedInUser))
                    || ((approverUser != null) && !editAuthorized(loggedInUser, userToAdd))) {

                LOGGER.error("Insufficient rights to create user.");
                return "Insufficient rights to create user.";
            }

            if(approverUser == null) { //Add new user
                userToAdd.setCreatedBy(loggedInUser.getUserId());
                userToAdd.setUnitId(loggedInUser.getUnitId());
                userToAdd.setCreatedOn(new Date());
                userToAdd.setResetPassword(true);
                userToAdd.setPassword(RandomStringUtils.randomAlphanumeric(16));
                userToAdd.setActive(true);
                if(userToAdd.getRoles() == null){
                    userToAdd.setRoles(new ArrayList<Role>());
                }
                addApproverRole(userToAdd.getRoles());
                // Update approver
                approverUser = userManagementService.addUpdateUser(userToAdd);

                notificationService.sendNewUserNotification(userToAdd,loggedInUser);
            } else { //Update existing user
                approverUser.setUsername(userToAdd.getUsername());
                approverUser.setFirstName(userToAdd.getFirstName());
                approverUser.setLastName(userToAdd.getLastName());
                approverUser.setLanguage(userToAdd.getLanguage());
                approverUser.setDecimalNotation(userToAdd.getDecimalNotation());
                approverUser.setDateFormat(userToAdd.getDateFormat());
                approverUser.setTimeZone(userToAdd.getTimeZone());
                approverUser.setAssignedProfiles(userToAdd.getAssignedProfiles());
                if(approverUser.getRoles() == null){
                    approverUser.setRoles(new ArrayList<Role>());
                }
                addApproverRole(approverUser.getRoles());
                // Update approver
                approverUser = userManagementService.addUpdateUser(approverUser);
            }

            List<String> approverIdsList = new ArrayList<String>();
            approverIdsList.add(approverUser.getUserId());

            if(StringUtils.isNotEmpty(catalogId)){
                approverService.associateCatalogApprovers(catalogId, approverIdsList);
            }else  if(StringUtils.isNotEmpty(supplierId)){
                Supplier supplier = supplierService.getSupplier(loggedInUser.getUnitId(), supplierId);
                addSupplierApprovers(supplier,  approverIdsList);

                //associate supplier profile to approver
                ProfileGroup group =supplierService.getSupplierProfileGroup(supplier);
                if(group != null){
                    approverUser.setAssignedProfiles(group.getAssociatedProfiles());
                    userManagementService.addUpdateUser(approverUser);
                }


            }else{
                addSupplierIdsToSession(request, approverIdsList);
            }

            renderBothSections(loggedInUser.getUnitId(), catalogId, supplierId, 1, 1, modelMap, request);

        } catch (Exception exp) {
            LOGGER.error("Error creating user ", exp);
            return FAILURE;
        }

        return (catalogId != null)? CATALOG_APPROVER_BOTH_SECTION : SUPPLIER_APPROVER_BOTH_SECTION;
    }

    @RequestMapping(value = "/addApprovers", method = RequestMethod.POST)
    public String addCatalogApprovers(HttpServletRequest request, HttpServletResponse response,
            @RequestParam( value="approverIds") List<String> approverIds,
            @RequestParam( value="catalogId") String catalogId, ModelMap modelMap) {

     try{
        User user = SessionDataRetriever.getLoggedInUser(request);

        approverService.associateCatalogApprovers(catalogId, approverIds);
        renderBothSections(user.getUnitId(), catalogId, null, 1, 1, modelMap, request);

    } catch (Exception exp) {
        LOGGER.error("Error creating user ", exp);
        return FAILURE;
    }

    return CATALOG_APPROVER_BOTH_SECTION;
}

    @RequestMapping(value = "/addSupplierApprovers", method = RequestMethod.POST)
    public String addSupplierApprovers(HttpServletRequest request, HttpServletResponse response,
            @RequestParam( value="approverIds") List<String> approverIds,
            @RequestParam( value="supplierId") String supplierId, ModelMap modelMap) {

        try{
        User user = SessionDataRetriever.getLoggedInUser(request);

        if(StringUtils.isNotEmpty(supplierId)){
            Supplier supplier = supplierService.getSupplier(user.getUnitId(), supplierId);
            addSupplierApprovers(supplier, approverIds);
            //associate supplier profile to approver
            ProfileGroup group =supplierService.getSupplierProfileGroup(supplier);
            if(group != null){
                List <User> users = userManagementService.getUsersByIds(supplier.getUnitId(),approverIds);
                for(User companyUser: users){
                    companyUser.setAssignedProfiles(group.getAssociatedProfiles());
                    userManagementService.addUpdateUser(companyUser);
                }
            }

        }else{
            addSupplierIdsToSession(request, approverIds);
        }

        renderBothSections(user.getUnitId(), null, supplierId, 1, 1, modelMap, request);

    } catch (Exception exp) {
        LOGGER.error("Error creating user ", exp);
        return FAILURE;
    }

        return SUPPLIER_APPROVER_BOTH_SECTION;
    }

    @RequestMapping(value = "/removeCatalogApprovers", method = RequestMethod.POST)
    public String removeCatalogApprovers(HttpServletRequest request, HttpServletResponse response,
            @RequestParam( value="catalogId") String catalogId,
            @RequestParam( value="approverIds") List<String> approverIds, ModelMap modelMap) {

        try {
            User user = SessionDataRetriever.getLoggedInUser(request);
            approverService.disAssociateCatalogApprovers(catalogId, approverIds);
            renderBothSections(user.getUnitId(), catalogId, null, 1, 1, modelMap, request);
        } catch (Exception exp) {
            LOGGER.error("Error removing catalog approvers ", exp);
            return FAILURE;
        }

        return CATALOG_APPROVER_BOTH_SECTION;
    }

    @RequestMapping(value = "/removeSupplierApprovers", method = RequestMethod.POST)
    public String removeSupplierApprovers(HttpServletRequest request, HttpServletResponse response,
            @RequestParam( value="supplierId") String supplierId,
            @RequestParam( value="approverIds") List<String> approverIds, ModelMap modelMap) {

        try {
            LOGGER.info("Removing Supplier Approver: {}", supplierId);
            User user = SessionDataRetriever.getLoggedInUser(request);

            Supplier supplier = null;
            if(StringUtils.isNotEmpty(supplierId)){
                supplier = supplierService.getSupplier(user.getUnitId(), supplierId);
                removeSupplierApprovers(supplier, approverIds);
                removeSupplierIdsFromSession(request, approverIds);
            }

            renderBothSections(user.getUnitId(), null, supplierId, 1, 1, modelMap, request);

        } catch (Exception exp) {
            LOGGER.error("Error removing supplier approvers ", exp);
            return FAILURE;
        }

        return SUPPLIER_APPROVER_BOTH_SECTION;
    }


    @RequestMapping(value="/goToPageCatalogApprover", method=RequestMethod.POST)
    public String goToPageCatalogApprovers(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="catalogId", required=true) String catalogId,
            @RequestParam(value="pageNum", required=true ) Integer pageNum, ModelMap modelMap){
        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            renderBothSections(user.getUnitId(), catalogId, null, pageNum, 1, modelMap, request);
        }catch (Exception exp){
            LOGGER.error("Error pagination of catalog approvers pageNum(" + pageNum  + ")" );
        }

        return CATALOG_APPROVER_BOTH_SECTION;
    }

    @RequestMapping(value="/goToPageSupplierApprover", method=RequestMethod.POST)
    public String goToPageSupplierApprovers(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value="supplierId", required=true) String supplierId,
            @RequestParam(value="pageNum", required=true ) Integer pageNum, ModelMap modelMap){
        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            renderBothSections(user.getUnitId(), null, supplierId, pageNum, 1, modelMap, request);
        }catch (Exception exp){
            LOGGER.error("Error pagination of supplier approvers pageNum(" + pageNum  + ")" );
        }

        return SUPPLIER_APPROVER_BOTH_SECTION;
    }

    @RequestMapping(value="/goToPageNonCatalogApprover", method=RequestMethod.POST)
    public String goToPageNonCatalogApprover(HttpServletRequest request, HttpServletResponse response,
            @RequestParam( value="catalogId", required=true) String catalogId,
            @RequestParam(value="pageNum", required=true ) Integer pageNum, ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            renderBothSections(user.getUnitId(), catalogId, null, 1, pageNum, modelMap, request);

        }catch (Exception exp){
            LOGGER.error("Error pagination of catalog approvers pageNum(" + pageNum  + ")" );
        }

        return CATALOG_APPROVER_BOTH_SECTION;
    }

    @RequestMapping(value="/goToPageNonSupplierApprover", method=RequestMethod.POST)
    public String goToPageNonSupplierApprover(HttpServletRequest request, HttpServletResponse response,
            //@RequestParam( value="supplierId", required=true) String supplierId,
            @RequestParam(value="pageNum", required=true ) Integer pageNum, ModelMap modelMap){

        try{
            User user = SessionDataRetriever.getLoggedInUser(request);
            renderBothSections(user.getUnitId(), null, null, 1, pageNum, modelMap, request);

        }catch (Exception exp){
            LOGGER.error("Error pagination of supplier approvers pageNum(" + pageNum  + ")" );
        }

        return SUPPLIER_APPROVER_BOTH_SECTION;
    }

    // Helper methods
    public void renderBothSections(String companyCode, String catalogId, String supplierId, Integer catalogSupplierApproverPageNum, Integer approverPageNum, ModelMap modelMap, HttpServletRequest request){

    	User user = SessionDataRetriever.getLoggedInUser(request);
    	List<User> allApproverss = getAllApprover(companyCode);
        List<User> approverUsers = null;

        if(StringUtils.isNotEmpty(catalogId)){
            List<String> approverIds = getCatalogApproverIds(catalogId);
            approverUsers = subtractUsers(allApproverss, approverIds);
        }else if(StringUtils.isNotEmpty(supplierId)){
            Supplier supplier = supplierService.getSupplier(companyCode, supplierId);
            approverUsers = subtractUsers(allApproverss, supplier.getApproverIds());
        }else {
            List<String> approverIds = getSupplierIdsFromSession(request);
            if(approverIds != null && approverIds.size() > 0){
                approverUsers = subtractUsers(allApproverss, approverIds);
            }
        }

        if(approverUsers != null){
	    	List<String> supplierApproverIds = new ArrayList<String>();	
	    	for (User user2: approverUsers) {
				supplierApproverIds.add(user2.getUserId());
			}
	    	addSupplierIdsToSession(request, supplierApproverIds);
        }
    	
    	
        //renderApproversSection(approverPageNum, getAllApprover(companyCode,user.getUserId()), modelMap);
        renderApproversSection(approverPageNum, allApproverss, modelMap, user.getRowsPerPage());
        renderCatalogSupplierApproversSection(catalogSupplierApproverPageNum, approverUsers, modelMap,  user.getRowsPerPage());
    }

    private void renderCatalogSupplierApproversSection(int pageNum, List<User> catalogSupplierApproverUsers, ModelMap modelMap, int pageSize){


        if(catalogSupplierApproverUsers == null || catalogSupplierApproverUsers.size() == 0){
            modelMap.addAttribute("totalApproverCount", new Integer(0));
            modelMap.addAttribute("approverCurrentPageNum", new Integer(0));
            modelMap.addAttribute("approverTotalPageNum", 0);
            modelMap.addAttribute("approverPageFirstItemNum", 0);
            modelMap.addAttribute("approverPageLastItemNum", new Integer(0));
            modelMap.addAttribute("approversForCatalogSupplier", new ArrayList<User>());

            return;
        }

        int totalApproverCount = catalogSupplierApproverUsers.size();
        modelMap.addAttribute("totalApproverCount", new Integer(totalApproverCount));

        if(totalApproverCount <= (pageNum-1)*pageSize){
            pageNum = 1; // Reset to 1 or go back one page
        }
        modelMap.addAttribute("approverCurrentPageNum", new Integer(pageNum));


        int approverCatalogTotalPageNum = ( (totalApproverCount % pageSize) == 0)? totalApproverCount/pageSize : totalApproverCount/pageSize + 1;
        modelMap.addAttribute("approverTotalPageNum", approverCatalogTotalPageNum);

        int approverPageFirstItemNum = (pageNum-1)*pageSize + 1;
        modelMap.addAttribute("approverPageFirstItemNum", approverPageFirstItemNum);

        int approverPageLastItemNum = (pageNum*pageSize < totalApproverCount)? approverPageFirstItemNum + pageSize-1 : totalApproverCount;
        modelMap.addAttribute("approverPageLastItemNum", new Integer(approverPageLastItemNum));

        List<User> pagedApproverList =  catalogSupplierApproverUsers.subList(approverPageFirstItemNum-1, approverPageLastItemNum);

        modelMap.addAttribute("approversForCatalogSupplier", pagedApproverList);
    }

    private void renderApproversSection(int pageNum, List<User> approverUsers, ModelMap modelMap,int pageSize){
        int totalNonApproverCount = approverUsers.size();
        modelMap.addAttribute("totalNonApproverCount", new Integer(totalNonApproverCount));

        if(totalNonApproverCount <= (pageNum-1)*pageSize){
            pageNum = 1; // Reset to 1 or go back one page
        }
        modelMap.addAttribute("nonApproverCurrentPageNum", new Integer(pageNum));
        
        int approverTotalPageNum = ( (totalNonApproverCount % pageSize) == 0)? totalNonApproverCount/pageSize : totalNonApproverCount/pageSize + 1;
        modelMap.addAttribute("nonApproverTotalPageNum", approverTotalPageNum);

        int nonApproverPageFirstItemNum = (pageNum-1)*pageSize + 1;
        modelMap.addAttribute("nonApproverPageFirstItemNum", nonApproverPageFirstItemNum);

        int nonApproverPageLastItemNum = (pageNum*pageSize < totalNonApproverCount)? nonApproverPageFirstItemNum + pageSize-1 : totalNonApproverCount;
        modelMap.addAttribute("nonApproverPageLastItemNum", new Integer(nonApproverPageLastItemNum));

        List<User> pagedApproverList = new ArrayList<User>();
        pagedApproverList =  approverUsers.subList(nonApproverPageFirstItemNum-1, nonApproverPageLastItemNum);

        modelMap.addAttribute("nonApprovers", pagedApproverList);
        modelMap.addAttribute("pageSize", pageSize);
    }

    private void addApproverRole(List<Role> approverRoles){
        for(Role role: approverRoles){
            if(role == Role.APPROVER){
                return;
            }
        }
        approverRoles.add(Role.APPROVER);
    }

    private boolean editAuthorized(User user, User userToAdd) {
        if(user.getRoles().contains(Role.MASTER_ADMINISTRATOR) || ((user.getRoles().contains(Role.ADMINISTRATOR)
                && !user.getUsername().equals(userToAdd.getUsername()) ))) {
            return true;
        }
        return false;
    }
    private boolean createAuthorized(User user) {
        if(user.getRoles().contains(Role.ADMINISTRATOR)
                || user.getRoles().contains(Role.MASTER_ADMINISTRATOR)) {
            return true;
        }
        return false;
    }


    private List<User> getAllApprover(String unitId){
        List<User> approvers = new ArrayList<User>();
        try{
            approvers = userManagementService.getApproverListForCompany(unitId);
        }catch (Exception exp){
            LOGGER.error("Error retrieving Approvers for unitId(" + unitId + ") ", exp);
        }
        return approvers;
    }

    private List<User> getAllApprover(String unitId,String userId){
        List<User> approvers = new ArrayList<User>();
        try{
            approvers = userManagementService.getApproverListForUser(unitId,userId);
        }catch (Exception exp){
        	LOGGER.error("Error retrieving Approvers", exp);
        }
        return approvers;
    }

    
    private List<String> getCatalogApproverIds(String catalogId){
        List<String> catalogApproverIds = new ArrayList<String>();
        try{
            catalogApproverIds = approverService.getApproversByCatalogId(catalogId);
        }catch (Exception exp){
            LOGGER.error("Error retrieving Approvers for catalog(" + catalogId + ") ", exp);
        }
        return catalogApproverIds;
    }

    private List<User> subtractUsers(List<User> users, List<String> ids){
        if(users == null || users.size() == 0 || ids == null || ids.size() == 0){
            return new ArrayList<User>();
        }

        List<User> remainderCatalogs = new ArrayList<User>();
        for(String userId: ids) {
            for(User user: users){
                if(user.getUserId().equals(userId)){
                    remainderCatalogs.add(user);
                    users.remove(user);
                    break;
                }

            }
        }
        return remainderCatalogs;
    }

    private void removeSupplierApprovers(Supplier supplier, List<String> approverIds){
        List<String> existingSupplierApprovers = supplier.getApproverIds();
        if(existingSupplierApprovers == null || existingSupplierApprovers.isEmpty()) {
            return;
        }

        for(String approverId: approverIds){
            if(findApprover(existingSupplierApprovers, approverId)){
                existingSupplierApprovers.remove(approverId);
            }
        }

        supplier.setApproverIds(existingSupplierApprovers);

        supplierService.editSupplier(supplier);

    }

    private void addSupplierApprovers(Supplier supplier, List<String> approverIds) throws Exception{
        List<String> existingSupplierApprovers = supplier.getApproverIds();

        if(existingSupplierApprovers == null) {
            existingSupplierApprovers = new ArrayList<String>();
        }

        List<String> toBeAddApproverIds = new ArrayList<String>();
        for(String approverId: approverIds){
            if(!findApprover(existingSupplierApprovers, approverId)){
                toBeAddApproverIds.add(approverId);
            }
        }

        existingSupplierApprovers.addAll(toBeAddApproverIds);

        supplier.setApproverIds(existingSupplierApprovers);



        supplierService.editSupplier(supplier);
    }

    private boolean findApprover(List<String> existingApprovers, String approverId){
        for(String existingApprover: existingApprovers){
            if(approverId.equals(existingApprover))return true;
        }
        return false;
    }


    public List<String> getSupplierIdsFromSession(HttpServletRequest request){
        Set<String> supplierApproverIdSet = (Set<String>)request.getSession().getAttribute("supplierApproverIds");
        if(supplierApproverIdSet == null) supplierApproverIdSet = new HashSet<String>();
        return new ArrayList<String>(supplierApproverIdSet);
    }
    public void addSupplierIdsToSession(HttpServletRequest request, List<String> supplierApproverIds){
        Set<String> supplierApproverIdSet = (Set<String>)request.getSession().getAttribute("supplierApproverIds");
        if(supplierApproverIdSet == null) supplierApproverIdSet = new HashSet<String>();
        supplierApproverIdSet.addAll(supplierApproverIds);
        request.getSession().setAttribute("supplierApproverIds", supplierApproverIdSet);
    }
    public void removeSupplierIdsFromSession(HttpServletRequest request, List<String> supplierApproverIds){
        Set<String> supplierApproverIdSet = (Set<String>)request.getSession().getAttribute("supplierApproverIds");
        if(supplierApproverIdSet == null) supplierApproverIdSet = new HashSet<String>();
        supplierApproverIdSet.removeAll(supplierApproverIds);
        request.getSession().setAttribute("supplierApproverIds", supplierApproverIdSet);
    }
    public void deleteSupplierIdsFromSession(HttpServletRequest request){
        request.getSession().removeAttribute("supplierApproverIds");
    }

}
