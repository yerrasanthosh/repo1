package com.vroozi.customerui.supplier.controller;

/*

@Controller
public class SupplierUIControllerOld {
    private final Logger LOGGER = LoggerFactory
            .getLogger(SupplierUIControllerOld.class);

    public static final String  FORMAT_DATE_DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy  hh:mm a z";
    private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);

    @Autowired
    SupplierRestClient supplierRestClient;

    @Autowired
    AppConfig appConfig;

    @RequestMapping("/suppliers")
    public String suppliers(HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) {
        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        List<Supplier> supplierList = new ArrayList<Supplier>();

        try {
            supplierList = supplierRestClient.getSupplierByUnitId(user.getUnitId());
            modelMap.addAttribute("suppliers", supplierList);
            return SUPPLIER_PAGE;
        } catch (Exception e) {
            LOGGER.error("Error retrieving suppliers");
            return FAILURE;
        }
    }

    @RequestMapping("/vendor-mapping")
    public String vendorMapping(HttpServletRequest request,
                            HttpServletResponse response, ModelMap modelMap) {
        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

//        List<Supplier> supplierList = new ArrayList<Supplier>();

        try {
//            supplierList = supplierRestClient.getSupplierByUnitId(user.getUnitId());
//            modelMap.addAttribute("suppliers", supplierList);
            return VENDOR_MAPPING;
        } catch (Exception e) {
            LOGGER.error("Error retrieving suppliers");
            return FAILURE;
        }
    }

    @RequestMapping("/create-supplier")
    public String createSupplier(HttpServletRequest request,
                                 HttpServletResponse response, ModelMap modelMap) {
        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        List<Country> countriesList = new ArrayList<Country>();
        List<Language> languagesList = new ArrayList<Language>();
        List<DecimalNotation> decimalNotationList = new ArrayList<DecimalNotation>();
        List<TimeZone> timeFormatList = new ArrayList<TimeZone>();
        List<DateFormat> dateFormatList = new ArrayList<DateFormat>();
        List<SystemVendor> systemVendorList = new ArrayList<SystemVendor>();

        try {
            countriesList = supplierRestClient.getAllCountries();
            modelMap.addAttribute("countriesList", countriesList);

            languagesList = supplierRestClient.getAllLanguages();
            modelMap.addAttribute("languagesList", languagesList);

            decimalNotationList = supplierRestClient.getAllDecimalNotation();
            modelMap.addAttribute("decimalNotationList", decimalNotationList);

            timeFormatList = supplierRestClient.getAllTimeFormats();
            modelMap.addAttribute("timeFormatList", timeFormatList);

            dateFormatList = supplierRestClient.getAllDateFormats();
            modelMap.addAttribute("dateFormatList", dateFormatList);

            List<ApproverProxy> approverList = supplierRestClient.getApproverList(user.getUnitId());
            modelMap.addAttribute("approversForCompany", (Object) approverList);

            List<SupplierAttributes> attributesForCompany = supplierRestClient.getCompanyAttributes(user.getUnitId());
            modelMap.addAttribute("attributsForCompany", (Object) attributesForCompany);
//
            return SUPPLIER_CREATION_PAGE;
        } catch (Exception e) {
            LOGGER.error("Error retrieving suppliers");
            return FAILURE;
        }
    }


    @RequestMapping("/edit-supplier")
    public String createSupplierEdit(HttpServletRequest request,
                            HttpServletResponse response, ModelMap modelMap,@RequestParam( value="companyId") String supplierId,@RequestParam( value="dunsNumber") String dunsNumber) {
        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        List<Country> countriesList = new ArrayList<Country>();
        List<Language> languagesList = new ArrayList<Language>();
        List<DecimalNotation> decimalNotationList = new ArrayList<DecimalNotation>();
        List<TimeZone> timeFormatList = new ArrayList<TimeZone>();
        List<DateFormat> dateFormatList = new ArrayList<DateFormat>();
        List<SystemVendor> systemVendorList = new ArrayList<SystemVendor>();
        List<CompanyUser> companyUserList = new ArrayList<CompanyUser>();

        try {
            countriesList = supplierRestClient.getAllCountries();
            modelMap.addAttribute("countriesList", countriesList);

            languagesList = supplierRestClient.getAllLanguages();
            modelMap.addAttribute("languagesList", languagesList);

            decimalNotationList = supplierRestClient.getAllDecimalNotation();
            modelMap.addAttribute("decimalNotationList", decimalNotationList);

            timeFormatList = supplierRestClient.getAllTimeFormats();
            modelMap.addAttribute("timeFormatList", timeFormatList);

            dateFormatList = supplierRestClient.getAllDateFormats();
            modelMap.addAttribute("dateFormatList", dateFormatList);

            List<ApproverProxy> approverList = supplierRestClient.getApproverList(user.getUnitId());
            modelMap.addAttribute("approversForCompany", (Object) approverList);

            List<SupplierAttributes> attributesForCompany = supplierRestClient.getCompanyAttributes(user.getUnitId());
            modelMap.addAttribute("attributsForCompany", (Object) attributesForCompany);

            List<VendorMapping> vendorMappings = supplierRestClient.getVendorMappingByDunsAndUnitId(user.getUnitId(), dunsNumber);
            modelMap.addAttribute("systemVendors", (Object) vendorMappings);

            companyUserList = supplierRestClient.getAllCompanyUsersBySupplierId(supplierId);
            modelMap.addAttribute("companyUserList", (Object) companyUserList);

            if(StringUtils.isNotBlank(supplierId)){
                Supplier supplierDetails = supplierRestClient.getCompanyDetails(supplierId, dunsNumber);
                modelMap.addAttribute("supplierDetails", (Object) supplierDetails);
            }

            //modelMap.addAttribute("catalogs", catalogs);

            return SUPPLIER_EDIT_PAGE;
        } catch (Exception e) {
            LOGGER.error("Error retrieving suppliers");
            return FAILURE;
        }
    }
    @RequestMapping("/edit-companyUser")
    public String getCompanyUser(HttpServletRequest request,
                                 HttpServletResponse response, ModelMap modelMap) {
        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
        try {
            modelMap.addAttribute("companyUserDetails", supplierRestClient.getComanyUser(request.getParameter("emailAddress"), request.getParameter("supplierId")));
            return COMPANY_USER_PAGE_VIEW;
        } catch (Exception e) {
            LOGGER.error("Error retrieving suppliers");
            return FAILURE;
        }
    }

    private User getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || StringUtils.isEmpty(session.getId())) {
            return null;
        }

        User user = (User) session.getAttribute("user");
        if (user == null || StringUtils.isEmpty(user.getSessionId())
                || StringUtils.isEmpty(user.getUnitId())) {
            return null;
        }
        return user;
    }

    @RequestMapping(value = "/activeSuppliers", method = RequestMethod.POST)
    public String activeSuppliers(HttpServletRequest request,
                                 HttpServletResponse response,
                                 @RequestParam(value="active") boolean active,
                                 @RequestParam( value="supplierIds") Integer[] supplierIds) {

        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            supplierRestClient.updateSupplierStatus(active, Arrays.asList(supplierIds));
            return "redirect:"+SUPPLIER_PAGE;
        } catch (Exception exp) {
            LOGGER.error("Error deleting supplierIds (" + supplierIds + ") ", exp);
            return FAILURE;
        }

    }

    @RequestMapping(value = "/deleteSupplier", method = RequestMethod.POST)
    public String deleteSuppliers(HttpServletRequest request,
//                                  HttpServletResponse response, @RequestParam( value="catalogId") String catalogId,
                                  @RequestParam( value="suppliersIds") Integer[] suppliersIds, ModelMap modelMap) {

        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }

        try {
            supplierRestClient.deleteSelectedSuppliers(Arrays.asList(suppliersIds));

//            List<Supplier> allProfiles = getSuppliers(suppliersIds, user);
//            modelMap.addAttribute("profilesForCompany", (Object) allProfiles);

            return SUPPLIER_PAGE;
        } catch (Exception exp) {
            LOGGER.error("Error deleting suppliersIds (" + suppliersIds + ") ", exp);
            return FAILURE;
        }

    }
    @RequestMapping(value = "/addSupplier", method = RequestMethod.POST)
    public String addApprover(HttpServletRequest request,
                              @ModelAttribute("supplier") Supplier supplier, ModelMap modelMap) {

        try {
            User user = getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            try {
                supplier.setCreatedOn(dateformat.format(new Date()));
                supplier.setUnitId(user.getUnitId());
                supplier.setCreatedBy(user.getFullName());
                supplier.setActive(true);


                if(supplierRestClient.getSupplierByDuns(supplier.getDunsNumber()).size()>0){
                    LOGGER.error("Duns Number already exists ");
                    modelMap.addAttribute("errorMessage", "Duns already exists");
                    return SUPPLIER_PAGE;

                }

                supplier = supplierRestClient.addSupplier(supplier);
                List<Supplier> allSuppliers = supplierRestClient.getSupplierByUnitId(user.getUnitId());
                modelMap.addAttribute("suppliers", (Object) allSuppliers);
                return SUPPLIER_PAGE;
            } catch (Exception exp) {
                LOGGER.error("Error adding Suppliers ", exp);
                return FAILURE;
            }
        } catch (Exception exp) {
            LOGGER.error("Error creating profile ", exp);
            return FAILURE;
        }
    }

    @RequestMapping(value = "/editSupplier", method = RequestMethod.POST)
    public String editSupplier(HttpServletRequest request,
                              @ModelAttribute("supplier") Supplier supplier, ModelMap modelMap) {

        try {
            User user = getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            try {

                //supplier.setCreatedOn(dateformat.format(new Date()));
                supplier.setUnitId(user.getUnitId());
                supplier.setCreatedBy(user.getFullName());
                supplier.setActive(true);

                supplier = supplierRestClient.editSupplier(supplier);
                List<Supplier> allSuppliers = supplierRestClient.getSupplierByUnitId(user.getUnitId());
                modelMap.addAttribute("suppliers", (Object) allSuppliers);
                return SUPPLIER_PAGE;
            } catch (Exception exp) {
                LOGGER.error("Error adding Suppliers ", exp);
                return FAILURE;
            }
        } catch (Exception exp) {
            LOGGER.error("Error creating profile ", exp);
            return FAILURE;
        }
    }

    @RequestMapping(value = "/addVendorId", method = RequestMethod.POST)
    public String addVendorId(HttpServletRequest request,
                              @ModelAttribute("vendorMapping") VendorMapping vendorMapping, ModelMap modelMap) {

        try {
            User user = getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            try {
                vendorMapping.setCreatedOn(dateformat.format(new Date()));
                vendorMapping.setUnitId(Integer.parseInt(user.getUnitId()));
                vendorMapping.setActive("true");
                vendorMapping.setVendorDuns(request.getParameter("dunsNumber"));
                vendorMapping.setSupplierId(Integer.parseInt(request.getParameter("supplierId")));

                vendorMapping = supplierRestClient.addVendorMapping(vendorMapping);
                List<VendorMapping> allVendorMappings = supplierRestClient.getVendorMappingByDunsAndUnitId(vendorMapping.getVendorNumber(),vendorMapping.getVendorDuns());
                modelMap.addAttribute("systemVendors", (Object) allVendorMappings);
                return "redirect:"+SUPPLIER_EDIT_PAGE;
            } catch (Exception exp) {
                LOGGER.error("Error creating vendor mapping ", exp);
                return FAILURE;
            }
        } catch (Exception exp) {
            LOGGER.error("Error creating vendor mapping ", exp);
            return FAILURE;
        }
    }
    @RequestMapping(value = "/addCompanyUser", method = RequestMethod.POST)
    public String addCompanyUser(HttpServletRequest request,
                              @ModelAttribute("companyUser") CompanyUser companyUser, ModelMap modelMap) {

        try {
            User user = getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            try {
                companyUser.setRole("Supplier Admin");
                companyUser.setCreatedOn(dateformat.format(new Date()));
                companyUser.setUnitId(user.getUnitId());
                companyUser.setCreatedBy(user.getFullName());
                companyUser.setSupplierId(request.getParameter("companyId"));
                companyUser.setActive("true");
                companyUser.setCompanyName(request.getParameter("companyName"));

                companyUser = supplierRestClient.addCompanyUser(companyUser);
                List<CompanyUser> allCompanyUsersBySupplierId = supplierRestClient.getAllCompanyUsersBySupplierId(companyUser.getSupplierId());
                modelMap.addAttribute("companyUserList", (Object) allCompanyUsersBySupplierId);
                return SUPPLIER_EDIT_PAGE;
            } catch (Exception exp) {
                LOGGER.error("Error Adding Company Users ", exp);
                return FAILURE;
            }
        } catch (Exception exp) {
            LOGGER.error("Error adding Company Users ", exp);
            return FAILURE;
        }
    }
    @RequestMapping(value = "/editCompanyUser", method = RequestMethod.POST)
    public String editCompanyUser(HttpServletRequest request,
                                 @ModelAttribute("companyUser") CompanyUser companyUser,ModelMap modelMap) {

        try {
            User user = getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            try {
                companyUser.setRole("Supplier Admin");
                companyUser.setCompanyUserId(Integer.parseInt(request.getParameter("row")));
                companyUser.setCreatedOn(dateformat.format(new Date()));
                companyUser.setUnitId(user.getUnitId());
                companyUser.setCreatedBy(user.getFullName());
                companyUser.setCompanyName(request.getParameter("emailAddress"));
                companyUser.setActive(request.getParameter("unitId"));

                companyUser = supplierRestClient.editCompanyUser(companyUser);
                List<CompanyUser> allCompanyUsersBySupplierId = supplierRestClient.getAllCompanyUsersBySupplierId(companyUser.getSupplierId());
                modelMap.addAttribute("companyUserList", (Object) allCompanyUsersBySupplierId);

                return SUPPLIER_EDIT_PAGE;
            } catch (Exception exp) {
                LOGGER.error("Error editing Company Users ", exp);
                return FAILURE;
            }
        } catch (Exception exp) {
            LOGGER.error("Error editing Company Users", exp);
            return FAILURE;
        }
    }

    @RequestMapping("/company-attributes")
    public String getCompanyAttributes(HttpServletRequest request,
                                 HttpServletResponse response, ModelMap modelMap) {
        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
        try {

            List<SupplierAttributes> attributesForCompany = supplierRestClient.getCompanyAttributes(user.getUnitId());
            modelMap.addAttribute("attributsForCompany", (Object) attributesForCompany);

            return COMPANY_ATTRIBUTES_PAGE;
        } catch (Exception e) {
            LOGGER.error("Error retrieving suppliers");
            return FAILURE;
        }
    }

    @RequestMapping(value = "edit-company-attributes", method = RequestMethod.POST)
    public String editCompanyAttributes(HttpServletRequest request,
                                  @ModelAttribute("companyAttributes") SupplierAttributes companyAttributes,ModelMap modelMap) {

        try {
            User user = getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            try {
                companyAttributes.setCreatedOn(dateformat.format(new Date()));
                companyAttributes.setUnitId(Integer.parseInt(user.getUnitId()));
                companyAttributes.setCreatedBy(user.getFullName());
                companyAttributes.setActive(companyAttributes.getActive());
                companyAttributes = supplierRestClient.editCompanyAttributes(companyAttributes);
                List<SupplierAttributes> allCompanyAttributesbyUnitId = supplierRestClient.getCompanyAttributes(String.valueOf(companyAttributes.getUnitId()));
                modelMap.addAttribute("attributsForCompany", (Object) allCompanyAttributesbyUnitId);

                return SUPPLIER_EDIT_PAGE;
            } catch (Exception exp) {
                LOGGER.error("Error editing Company Attributes ", exp);
                return FAILURE;
            }
        } catch (Exception exp) {
            LOGGER.error("Error editing Company Attributes", exp);
            return FAILURE;
        }
    }

    @RequestMapping(value = "/uploadVendorMapping" , method=RequestMethod.POST)
    public String uploadVendorMappings(HttpServletRequest request , @ModelAttribute VendorMappingGroupForm vendorMappingGroupForm){
        VendorMappingFilePost vendorGroupPost = new VendorMappingFilePost();

        if(vendorMappingGroupForm.getVendorMappingFile() != null && !vendorMappingGroupForm.getVendorMappingFile().isEmpty()) {
            if(writeToFile(vendorMappingGroupForm.getVendorMappingFile().getBytes(), vendorMappingGroupForm.getVendorMappingFile().getOriginalFilename())){
                vendorGroupPost.setCatalogSourcePath(appConfig.fileUploadPath + "/" +"Template_Vendor_Mapping.xlsx");
            }
        }


        try{
            User user = getLoggedInUser(request);
            vendorGroupPost.setCatalogSourcePath(appConfig.fileUploadPath + "/" + vendorMappingGroupForm.getVendorMappingFile().getOriginalFilename());
            vendorGroupPost.setSubmitter(Integer.parseInt(user.getUserId()));
            vendorGroupPost.setUnitId(Integer.parseInt(user.getUnitId()));
            supplierRestClient.uploadMaterialGroup(vendorGroupPost);


        }catch (Exception e) {
            e.printStackTrace();
        }
        return VENDOR_MAPPING;
    }
    private boolean writeToFile(byte[] source, String destFileName){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(appConfig.fileUploadPath + "/" + destFileName));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            bufferedOutputStream.write(source);bufferedOutputStream.flush();bufferedOutputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
//            logger.error("Error in writing to file(" + destFileName + ")", exception);
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/createCompanyAttribute", method = RequestMethod.POST)
    public String addCompanyAttribute(HttpServletRequest request,
                              @ModelAttribute("companyAttributes") SupplierAttributes companyAttributes, ModelMap modelMap) {

        try {
            User user = getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }

            try {
                companyAttributes.setCreatedOn(dateformat.format(new Date()));
                companyAttributes.setUnitId(Integer.parseInt(user.getUnitId()));
                companyAttributes.setCreatedBy(user.getFullName());
                companyAttributes.setActive("true");

                companyAttributes = supplierRestClient.addCompanyAttributes(companyAttributes);
                List<SupplierAttributes> allCompanyAttributes = supplierRestClient.getCompanyAttributes(user.getUnitId());
                modelMap.addAttribute("attributsForCompany", (Object) allCompanyAttributes);
                return COMPANY_ATTRIBUTES_PAGE;
            } catch (Exception exp) {
                LOGGER.error("Error adding COMPANY_ATTRIBUTES ", exp);
                return FAILURE;
            }
        } catch (Exception exp) {
            LOGGER.error("Error adding COMPANY_ATTRIBUTES ", exp);
            return FAILURE;
        }
    }

    @RequestMapping("/getVendorIdDetails")
    public String getVendorIdDetails(HttpServletRequest request,
                                 HttpServletResponse response, ModelMap modelMap) {
        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
        try {
            modelMap.addAttribute("vendorIdDetails", supplierRestClient.getVendorDetails(request.getParameter("vendorName"),
                                                                                            request.getParameter("systemId"),
                                                                                                request.getParameter("clientId")));
            return SYSTERM_VENDOR_PAGE_VIEW;
        } catch (Exception e) {
            LOGGER.error("Error retrieving suppliers");
            return FAILURE;
        }
    }

    @RequestMapping(value = "/editSystemVendor", method = RequestMethod.POST)
    public String editSystemVendor(HttpServletRequest request,
                                  @ModelAttribute("systemVendor") VendorMapping systemVendor,ModelMap modelMap) {

        try {
            User user = getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            try {
                systemVendor.setUnitId(Integer.parseInt(user.getUnitId()));
                systemVendor.setCreatedOn(dateformat.format(new Date()));
                systemVendor.setActive(request.getParameter("active"));
                systemVendor.setRow(Long.parseLong(request.getParameter("row")));
                systemVendor.setVendorName(request.getParameter("vendorName"));
                systemVendor.setVendorDuns(request.getParameter("vendorDuns"));
                systemVendor = supplierRestClient.editSystemVendor(systemVendor,request.getParameter("vendorIdOld"),request.getParameter("systemIdOld"),request.getParameter("clientIdOld"));
                List<VendorMapping> allCompanyUsersBySupplierId = supplierRestClient.getVendorMappingByDunsAndUnitId(systemVendor.getVendorNumber(),systemVendor.getVendorDuns());
                modelMap.addAttribute("systemVendors", (Object) allCompanyUsersBySupplierId);

                return SUPPLIER_EDIT_PAGE;
            } catch (Exception exp) {
                LOGGER.error("Error editing Company Users ", exp);
                return FAILURE;
            }
        } catch (Exception exp) {
            LOGGER.error("Error editing Company Users", exp);
            return FAILURE;
        }
    }

    @RequestMapping("/getAttributeDetails")
    public String getAttributeDetails(HttpServletRequest request,
                                     HttpServletResponse response, ModelMap modelMap) {
        User user = getLoggedInUser(request);
        if (user == null) {
            return INVALID_SESSION_PAGE;
        }
        try {
            modelMap.addAttribute("companyAttributeDetails", supplierRestClient.getAttributeDetails(request.getParameter("attributeId"),request.getParameter("unitId")));
            return COMPANY_ATTRIBUTES_VIEW;
        } catch (Exception e) {
            LOGGER.error("Error retrieving vendor Detail");
            return FAILURE;
        }
    }

    @RequestMapping(value = "/editAttributeDetails", method = RequestMethod.POST)
    public String editAttributeDetails(HttpServletRequest request,
                                   @ModelAttribute("companyAttribute") SupplierAttributes companyAttribute,ModelMap modelMap) {

        try {
            User user = getLoggedInUser(request);
            if (user == null) {
                return INVALID_SESSION_PAGE;
            }
            try {
                companyAttribute.setUnitId(Integer.parseInt(request.getParameter("unitId")));
                companyAttribute.setCreatedOn(dateformat.format(new Date()));
                companyAttribute.setActive(request.getParameter("active"));
                companyAttribute.setAttributeId(Integer.parseInt(request.getParameter("attributeId")));

                companyAttribute = supplierRestClient.editCompanyAttributes(companyAttribute);
                List<SupplierAttributes> allCompanyAttributes = supplierRestClient.getCompanyAttributes(String.valueOf(companyAttribute.getUnitId()));
                modelMap.addAttribute("attributsForCompany", (Object) allCompanyAttributes);

                return COMPANY_ATTRIBUTES_ASSOCIATE_PAGE;
            } catch (Exception exp) {
                LOGGER.error("Error editing Company Attributes ", exp);
                return FAILURE;
            }
        } catch (Exception exp) {
            LOGGER.error("Error editing Company Attributes", exp);
            return FAILURE;
        }
    }


}

*/