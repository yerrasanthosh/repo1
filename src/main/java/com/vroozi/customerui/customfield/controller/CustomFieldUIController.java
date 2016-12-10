package com.vroozi.customerui.customfield.controller;

import com.vroozi.customerui.catalog.common.CatalogDataDisplayPopulator;
import com.vroozi.customerui.catalog.model.CatalogCustomField;
import com.vroozi.customerui.catalog.model.CatalogCustomFieldForm;
import com.vroozi.customerui.catalog.model.CatalogSummary;
import com.vroozi.customerui.catalog.model.CustomField;
import com.vroozi.customerui.catalog.model.FieldFilter;
import com.vroozi.customerui.catalog.services.CatalogService;
import com.vroozi.customerui.common.SessionDataRetriever;
import com.vroozi.customerui.config.AppConfig;
import com.vroozi.customerui.upload.service.UploadService;
import com.vroozi.customerui.util.FileUtility;
import com.vroozi.customerui.customfield.model.CustomFieldTypeMetrics;
import com.vroozi.customerui.user.services.user.model.User;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.vroozi.customerui.util.Consts.*;

/**
 * User: Muhammad Salman Nafees
 * Date: 9/18/12
 * Time: 12:58 PM
 */
@Controller
public class CustomFieldUIController {

    private final Logger LOGGER = LoggerFactory
            .getLogger(CustomFieldUIController.class);

    public static final String  FORMAT_DATE_DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy  hh:mm a z";
    private static final SimpleDateFormat dateformat = new SimpleDateFormat(FORMAT_DATE_DD_MM_YYYY_HH_MM_SS);

    @Autowired
    AppConfig appConfig;

    @Autowired
    CatalogService catalogService;

    @Autowired
    CatalogDataDisplayPopulator catalogDataDisplayPopulator;

    @Autowired
    UploadService uploadService;

    @RequestMapping("/customFields")
    public String customFields(HttpServletRequest request,
                           HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        try {
            List<CustomField> customFieldMap = catalogService.getCustomFieldListByUnitId(Integer.parseInt(user.getUnitId()));
            PagedListHolder<CustomField> customFieldHolder = new PagedListHolder<CustomField>(customFieldMap);
            customFieldHolder.setPageSize(user.getRowsPerPage());
            modelMap.addAttribute("customFields", customFieldHolder);
            modelMap.addAttribute("userName", user.getFullName());
            List<CustomFieldTypeMetrics> metrics = catalogService.getCustomFieldTypeMetrics(Integer.parseInt(user.getUnitId()));
            int totalFields = 0;
            for(CustomFieldTypeMetrics cftm : metrics){
                totalFields = totalFields + cftm.getCount();
            }
            modelMap.addAttribute("metrics", metrics);
            modelMap.addAttribute("totalFields", totalFields);
            return CUSTOMFIELDS_PAGE;
        } catch (Exception e) {
            LOGGER.error("Error retrieving profiles");
            return FAILURE;
        }
    }

    @RequestMapping(value = "/deleteCustomField", method= RequestMethod.POST)
    public String deleteCustomField(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);


        String catalogCustomFieldIds = catalogCustomFieldForm.getCatalogCustomFieldIds();

        try {
            catalogService.deleteCustomFields(catalogCustomFieldIds);
            List<CustomField> customFieldList = catalogService.getCustomFieldListByUnitId(Integer.parseInt(user.getUnitId()));
            PagedListHolder<CustomField> customFieldHolder = new PagedListHolder<CustomField>(customFieldList);
            customFieldHolder.setPageSize(user.getRowsPerPage());
            modelMap.addAttribute("customFields", customFieldHolder);
            List<CustomFieldTypeMetrics> metrics = catalogService.getCustomFieldTypeMetrics(Integer.parseInt(user.getUnitId()));
            int totalFields = 0;
            for(CustomFieldTypeMetrics cftm : metrics){
                totalFields = totalFields + cftm.getCount();
            }
            modelMap.addAttribute("metrics", metrics);
            modelMap.addAttribute("totalFields", totalFields);


        } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
            return FAILURE;
        }

        return MANAGE_CUSTOM_FIELD_SECTION;
    }
    
    @RequestMapping(value = "/getFieldsByType", method= RequestMethod.POST)
    public String getFieldsByType(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        
        FieldFilter filter = new FieldFilter();
        filter.setType(getStringFromList(catalogCustomFieldForm.getCustomFieldIdList()));
        filter.setSearch(catalogCustomFieldForm.getSearch().equalsIgnoreCase("Search Custom Fields") ? "" : catalogCustomFieldForm.getSearch());
        filter.setStatus(catalogCustomFieldForm.getStatus() == null ? "" : catalogCustomFieldForm.getStatus());
        List<CustomField> customFieldList = null;
        
        try {
        	if(filter.getType() != null && filter.getType().length() > 0){
        		customFieldList = catalogService.getCustomFieldsByFieldTypes(Integer.parseInt(user.getUnitId()), filter);
        		
        	} else {
        		customFieldList = new ArrayList<CustomField>();
        	}
        	PagedListHolder<CustomField> customFieldHolder = new PagedListHolder<CustomField>(customFieldList);
        	customFieldHolder.setPageSize(user.getRowsPerPage());
        	if(!catalogCustomFieldForm.getOrderBy().equalsIgnoreCase("")) {
        		customFieldHolder.setSort(new MutableSortDefinition(catalogCustomFieldForm.getOrderBy(), true, true));
        		customFieldHolder.resort();
    		}
        	
    		modelMap.addAttribute("customFields", customFieldHolder);
    		
          } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
            return FAILURE;
        }

        return MANAGE_CUSTOM_FIELD_SECTION;
    }
    
    @RequestMapping(value = "/filterCatalogs", method= RequestMethod.POST)
    public String filterCatalogs(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);


        String searchText = catalogCustomFieldForm.getSearch().equalsIgnoreCase("Search Catalogs") ? "" : catalogCustomFieldForm.getSearch();
        String order = "";
        if(catalogCustomFieldForm.getOrderBy() != null){
            if(catalogCustomFieldForm.getOrderBy().equalsIgnoreCase("Catalog Name")){
                order = "name";
            } else if(catalogCustomFieldForm.getOrderBy().equalsIgnoreCase("Catalog Type")){
                order = "catalogTypeDesc";
            }
        }

        
        try {
        	List<CatalogSummary> catalogList = catalogService.getAllCatalogs(user.getUnitId());
        	List<CatalogSummary> newList = new ArrayList<CatalogSummary>();
        	PagedListHolder<CatalogSummary> catalogFieldHolder = null;
        	
        	if(searchText.length() > 0){
        		for(CatalogSummary cs : catalogList){
        			if(cs.getName().toLowerCase().contains(searchText.toLowerCase())){
        				newList.add(cs);
        			}
        		}
        		catalogFieldHolder = new PagedListHolder<CatalogSummary>(newList);
        	} else {
        		catalogFieldHolder = new PagedListHolder<CatalogSummary>(catalogList);
        	}
        	if(!order.equalsIgnoreCase("")) {
        		catalogFieldHolder.setSort(new MutableSortDefinition(order, true, true));
        		catalogFieldHolder.resort();
    		}
        	catalogFieldHolder.setPageSize(user.getRowsPerPage());
        	modelMap.addAttribute("catalogList", catalogFieldHolder);
    		
          } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
            return FAILURE;
        }

        return LIGHTBOXCATALOGS_PAGE;
    }
    
	@RequestMapping(value = "/navigateCustFieldPage", method= RequestMethod.POST)
	public String navigateCustFieldPage(HttpServletRequest request, @RequestParam(value = "page") String page, @RequestParam(value = "currPage") String currPage, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, HttpServletResponse response, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);

		CustomField cf = new CustomField();
        
        FieldFilter filter = new FieldFilter();
        filter.setType(getStringFromList(catalogCustomFieldForm.getCustomFieldIdList()));
        filter.setSearch(catalogCustomFieldForm.getSearch().equalsIgnoreCase("Search Custom Fields") ? "" : catalogCustomFieldForm.getSearch());
        filter.setStatus(catalogCustomFieldForm.getStatus() == null ? "" : catalogCustomFieldForm.getStatus());
        List<CustomField> customFieldList = null;
        
        try {
        	if(filter.getType() != null && filter.getType().length() > 0){
        		customFieldList = catalogService.getCustomFieldsByFieldTypes(Integer.parseInt(user.getUnitId()), filter);
        		
        	} else {
        		customFieldList = new ArrayList<CustomField>();
        	}
        	PagedListHolder<CustomField> customFieldHolder = new PagedListHolder<CustomField>(customFieldList);
        	
        	if(!catalogCustomFieldForm.getOrderBy().equalsIgnoreCase("")) {
        		customFieldHolder.setSort(new MutableSortDefinition(catalogCustomFieldForm.getOrderBy(), true, true));
        		customFieldHolder.resort();
    		}

            customFieldHolder.setPage(Integer.parseInt(currPage));
            customFieldHolder.setPageSize(user.getRowsPerPage());
        	
        	if(page.equals("next")) {
        		customFieldHolder.nextPage();
    		} else if(page.equals("prev")) {
    			customFieldHolder.previousPage();
    		} else if(page.equals("last")) {
    			customFieldHolder.previousPage();
    		} else if(page.equals("first")) {
    			customFieldHolder.previousPage();
    		} else {
    			try {
    				int p = Integer.parseInt(page);
    				customFieldHolder.setPage(p);	
    			} catch (Exception e) {
    				LOGGER.error("Invalid page", e);
    			}
    		}
        	customFieldHolder.setPageSize(user.getRowsPerPage());
    		modelMap.addAttribute("customFields", customFieldHolder);
    		
          } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
            return FAILURE;
        }
		
		return MANAGE_CUSTOM_FIELD_SECTION;
		
	}
	
	@RequestMapping(value = "/navigateCatalogPage", method= RequestMethod.POST)
	public String navigateCatalogPage(HttpServletRequest request, @RequestParam(value = "page") String page, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, HttpServletResponse response, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);

        try {
        	PagedListHolder<CatalogSummary> catalogFieldHolder = getCatalogsToAddInCustomField(catalogCustomFieldForm.getCatalogIds(), user.getUnitId(),catalogCustomFieldForm.getSearch() );
        	catalogFieldHolder.setPageSize(user.getRowsPerPage());
        	if(page.equals("next")) {
        		catalogFieldHolder.nextPage();
    		} else if(page.equals("prev")) {
    			catalogFieldHolder.previousPage();
    		} else if(page.equals("last")) {
    			catalogFieldHolder.previousPage();
    		} else if(page.equals("first")) {
    			catalogFieldHolder.previousPage();
    		} else {
    			try {
    				int p = Integer.parseInt(page);
    				catalogFieldHolder.setPage(p);	
    			} catch (Exception e) {
    				LOGGER.error("Invalid page", e);
    			}
    		}
        	modelMap.addAttribute("catalogList", catalogFieldHolder);
        	modelMap.addAttribute("catIdList", catalogCustomFieldForm.getCatalogIds());
    		
          } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
            return FAILURE;
        }
		
		return LIGHTBOXCATALOGS_PAGE;
		
	}
	
	@RequestMapping(value = "/navigateCustFieldCatalogPage", method= RequestMethod.POST)
	public String navigateCustFieldCatalogPage(HttpServletRequest request, @RequestParam(value = "customfieldid", required=false) String customFieldId, @RequestParam(value = "page") String page, @RequestBody String catalogIds, ModelMap modelMap) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		
		try {
			List<CatalogSummary> catalogList = catalogService.getCatalogsByIds(catalogIds);
			PagedListHolder<CatalogSummary> catalogFieldHolder = new PagedListHolder<CatalogSummary>(catalogList);
			catalogFieldHolder.setPageSize(user.getRowsPerPage());
			
			if (page.equals("next")) {
				catalogFieldHolder.nextPage();
			} else if (page.equals("prev")) {
				catalogFieldHolder.previousPage();
			} else if (page.equals("last")) {
				catalogFieldHolder.previousPage();
			} else if (page.equals("first")) {
				catalogFieldHolder.previousPage();
			} else {
				try {
					int p = Integer.parseInt(page);
					catalogFieldHolder.setPage(p);
				} catch (Exception e) {
					LOGGER.error("Invalid page", e);
				}
			}
			modelMap.addAttribute("catalogList", catalogFieldHolder);
			modelMap.addAttribute("catIdList", catalogIds);
		} catch (Exception exception) {
			LOGGER.error("Error in deleting custom fields", exception);
			return FAILURE;
		}

		return ASSOCIATE_CATALOGS_PAGE;
		
	}
	@RequestMapping(value = "/postFilterCountExceeded", method= RequestMethod.POST)
	public @ResponseBody String postFilterCountExceeded(HttpServletRequest request, @RequestParam(value = "idString") String idString, HttpServletResponse response) {
		User user = SessionDataRetriever.getLoggedInUser(request);

    		if(catalogService.getPostFilterCountByCompany(Integer.parseInt(user.getUnitId())) +
    				catalogService.getInactivePostFilterCountByIds(idString) > Integer.parseInt(appConfig.maxPostFilters)){
        		return String.valueOf("exceeded");

    	}
		
		return "";
	}
	
	@RequestMapping(value = "/customFieldExists", method= RequestMethod.POST)
	public @ResponseBody String customFieldExists(HttpServletRequest request, @RequestParam(value = "name") String name, @RequestParam(value = "fType") String fType, @RequestParam(value = "postFilter") boolean postFilter, HttpServletResponse response) {
		User user = SessionDataRetriever.getLoggedInUser(request);
		if (user == null) {
			return INVALID_SESSION_PAGE;
		}
		boolean exists = false;
        
        try {
        	exists = catalogService.customFieldExists(Integer.parseInt(user.getUnitId()), name, fType);
            if(!exists && postFilter){
            	if(catalogService.getPostFilterCountByCompany(Integer.parseInt(user.getUnitId())) < Integer.parseInt(appConfig.maxPostFilters)){
            		return String.valueOf(exists);
            	} else {
            		return "exceeded";
            	}
            }
    		
          } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
            return FAILURE;
        }
		
		return String.valueOf(exists);
		
	}
	
    @RequestMapping(value = "/activateDeactivateCustomFields", method= RequestMethod.POST)
    public String activateDeactivateCustomFields(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        String catalogCustomFieldIds = catalogCustomFieldForm.getCatalogCustomFieldIds();
        
        try {
            catalogService.activateDeactivateCustomFields(catalogCustomFieldIds, catalogCustomFieldForm.isActive());
            List<CustomField> customFieldList = catalogService.getCustomFieldListByUnitId(Integer.parseInt(user.getUnitId()));
            PagedListHolder<CustomField> customFieldHolder = new PagedListHolder<CustomField>(customFieldList);
            customFieldHolder.setPageSize(user.getRowsPerPage());
            modelMap.addAttribute("customFields", customFieldHolder);

        } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
            return FAILURE;
        }

        return MANAGE_CUSTOM_FIELD_SECTION;
    }

    @RequestMapping(value = "/addCatalogsToCustomField", method= RequestMethod.POST)
    public String addCatalogsToCustomField(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        
    	  User user = SessionDataRetriever.getLoggedInUser(request);
    	String newCatalogIds = catalogCustomFieldForm.getCatalogCustomFieldIds();
        String catalogIds = catalogCustomFieldForm.getCatalogIds();
        
        try {
        	String customFieldId = catalogCustomFieldForm.getCustomFieldId();
        	if(customFieldId!=null && customFieldId.length()>0) {
        		String[] catIds = newCatalogIds.split(",");
        		CustomField customField = catalogService.getCustomField(customFieldId);
	            for (String catalogId: catIds){
	                CatalogCustomField catalogCustomField = new CatalogCustomField(customField);
	                catalogCustomField.setCatalogId(catalogId);
	                catalogCustomField.setCustomFieldId(customField.getId());
	                catalogService.addCatalogCustomField(catalogCustomField);
	            }
        	}
            if(catalogIds != null && catalogIds.length()>0) {
            	newCatalogIds = catalogIds + "," + newCatalogIds;
            }
            List<CatalogSummary> catalogList = catalogService.getCatalogsByIds(newCatalogIds);
            PagedListHolder<CatalogSummary> catalogFieldHolder = new PagedListHolder<CatalogSummary>(catalogList);
            catalogFieldHolder.setPageSize(user.getRowsPerPage());
            
            modelMap.addAttribute("catalogList", catalogFieldHolder);
            modelMap.addAttribute("catIdList", newCatalogIds);
        } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
            return FAILURE;
        }

        return ASSOCIATE_CATALOGS_PAGE;
    }

    @RequestMapping(value = "/removeCatalogs", method= RequestMethod.POST)
    public String removeCatalogs(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);


        List<String> removeCatIdList = catalogCustomFieldForm.getCatalogCustomFieldId();

        List<String> fetchCatIdList = new ArrayList<String>();
        
        String catIds = catalogCustomFieldForm.getCatalogIds();
        String[] arr = catIds.split(",");
        for (String removeId : removeCatIdList){
            arr = removeElements(arr, removeId);
            if(catalogCustomFieldForm.getFieldId() != null && catalogCustomFieldForm.getFieldId().length() > 0){
                try {
                    catalogService.deleteCatalogCustomFieldByIdAndCatId(catalogCustomFieldForm.getFieldId(), removeId);
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        
        String catalogIds = getStringFromArray(arr);
        
        try {
            List<CatalogSummary> catalogList = catalogIds != "" ? catalogService.getCatalogsByIds(catalogIds) : new ArrayList<CatalogSummary>();
            PagedListHolder<CatalogSummary> catalogFieldHolder = new PagedListHolder<CatalogSummary>(catalogList);
            catalogFieldHolder.setPageSize(user.getRowsPerPage());
            modelMap.addAttribute("catalogList", catalogFieldHolder);
            modelMap.addAttribute("catIdList", catalogIds);
        } catch (Exception exception) {
            LOGGER.error("Error in deleting custom fields", exception);
            return FAILURE;
        }

        return ASSOCIATE_CATALOGS_PAGE;
    }

    @RequestMapping("/createCustomFields")
    public String createCustomFields(HttpServletRequest request,
                               HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        try {

            modelMap.addAttribute("userName", user.getFullName());
            return CREATE_CUSTOMFIELDS_PAGE;
        } catch (Exception e) {
            LOGGER.error("Error retrieving profiles");
            return FAILURE;
        }
    }

    @RequestMapping(value = "/addCustomField", method=RequestMethod.POST)
    public String addCustomField(HttpServletRequest request, @ModelAttribute CustomField customField,@ModelAttribute FileUtility customFieldFileUtility, BindingResult bindingResult, ModelMap modelMap) {

        User user = SessionDataRetriever.getLoggedInUser(request);

        try {
            customField.setUnitId(user.getUnitId());

            if(customField.getFieldType().equalsIgnoreCase("flag") && customFieldFileUtility.getIconFile() != null && !customFieldFileUtility.getIconFile().isEmpty()) {
                String ext = customFieldFileUtility.getIconFile().getOriginalFilename().substring(customFieldFileUtility.getIconFile().getOriginalFilename().lastIndexOf("."), customFieldFileUtility.getIconFile().getOriginalFilename().length());
                customField.setIcon(ext.trim());
            }

            if(!customField.getFieldType().equalsIgnoreCase("list")){
                customField.setDropDownValues(null);
            } else {
                customField.getDropDownValues().remove(0);
            }
          customField.setActive(true);
            customField = catalogService.addCustomField(customField);
            if(customField.getFieldType().equalsIgnoreCase("flag") && customFieldFileUtility.getIconFile() != null && !customFieldFileUtility.getIconFile().isEmpty()) {
                if(!uploadService.writeToFile(customFieldFileUtility.getIconFile().getInputStream(), customField.getIcon().trim(), appConfig.flagIconUploadPath)){
                    LOGGER.error("error uploading flag icon.");
                }
            }
            for (String catalogId: customField.getCatalogIds()){
              if(!catalogId.equals("undefined")) {
                CatalogCustomField catalogCustomField = new CatalogCustomField(customField);
                catalogCustomField.setCatalogId(catalogId);
                catalogCustomField.setCustomFieldId(customField.getId());
                catalogService.addCatalogCustomField(catalogCustomField);
              }
            }
            modelMap.addAttribute("userName", user.getFullName());
            List<CustomFieldTypeMetrics> metrics = catalogService.getCustomFieldTypeMetrics(Integer.parseInt(user.getUnitId()));
            int totalFields = 0;
            for(CustomFieldTypeMetrics cftm : metrics){
                totalFields = totalFields + cftm.getCount();
            }
            modelMap.addAttribute("metrics", metrics);
            modelMap.addAttribute("totalFields", totalFields);
            List<CustomField> customFieldList = catalogService.getCustomFieldListByUnitId(Integer.parseInt(user.getUnitId()));
            PagedListHolder<CustomField> customFieldHolder = new PagedListHolder<CustomField>(customFieldList);
            customFieldHolder.setPageSize(user.getRowsPerPage());
            modelMap.addAttribute("customFields", customFieldHolder);
        } catch (Exception exception) {
            LOGGER.error("Error in adding custom fields", exception);
            return FAILURE;
        }

        return CUSTOMFIELDS_PAGE;
    }

    @RequestMapping(value = "/updateCustomField", method=RequestMethod.POST)
    public String updateCustomField(HttpServletRequest request, @ModelAttribute CustomField customField,@ModelAttribute FileUtility customFieldFileUtility, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        try {
            customField.setUnitId(user.getUnitId());
            customField.setId(customField.getFieldId());
            customField.setFieldId(null);
            if(customField.getFieldType().equalsIgnoreCase("flag") && customFieldFileUtility.getIconFile() != null && !customFieldFileUtility.getIconFile().isEmpty()) {
            	String ext = customFieldFileUtility.getIconFile().getOriginalFilename().substring(customFieldFileUtility.getIconFile().getOriginalFilename().lastIndexOf("."), customFieldFileUtility.getIconFile().getOriginalFilename().length());
                if(uploadService.writeToFile(customFieldFileUtility.getIconFile().getInputStream(), customField.getId() + ext, appConfig.flagIconUploadPath)){
                    customField.setIcon(customField.getId()+ext.trim());
                }
            }
            if(!customField.getFieldType().equalsIgnoreCase("list")){
                customField.setDropDownValues(null);
            } else {
                customField.getDropDownValues().remove(0);
            }
            catalogService.updateCustomField(customField);
//            catalogService.deleteCatalogCustomFieldByCustomFieldId(customField.getId());
            for (String catalogId: customField.getCatalogIds()){
                CatalogCustomField catalogCustomField = new CatalogCustomField(customField);
                catalogCustomField.setCatalogId(catalogId);
                catalogCustomField.setCustomFieldId(customField.getId());
                if(catalogService.getCustomFieldByCatIdFieldName(catalogId,customField.getFieldName()) == null){
                    catalogService.addCatalogCustomField(catalogCustomField);
                }

            }

            List<CustomFieldTypeMetrics> metrics = catalogService.getCustomFieldTypeMetrics(Integer.parseInt(user.getUnitId()));
            int totalFields = 0;
            for(CustomFieldTypeMetrics cftm : metrics){
                totalFields = totalFields + cftm.getCount();
            }
            modelMap.addAttribute("metrics", metrics);
            modelMap.addAttribute("totalFields", totalFields);

            List<CustomField> customFieldList = catalogService.getCustomFieldListByUnitId(Integer.parseInt(user.getUnitId()));
            PagedListHolder<CustomField> customFieldHolder = new PagedListHolder<CustomField>(customFieldList);
            customFieldHolder.setPageSize(user.getRowsPerPage());
            modelMap.addAttribute("userName", user.getFullName());
            modelMap.addAttribute("customFields", customFieldHolder);
        } catch (Exception exception) {
            LOGGER.error("Error in adding custom fields", exception);
            return FAILURE;
        }

        return CUSTOMFIELDS_PAGE;
    }

    @RequestMapping(value = "/addCatalogs", method=RequestMethod.POST)
    public String addCatalogs(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CatalogCustomFieldForm catalogCustomFieldForm, BindingResult bindingResult, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        try {
        	String catIdList = catalogCustomFieldForm.getCatalogIds();
        	PagedListHolder<CatalogSummary> catalogFieldHolder = getCatalogsToAddInCustomField(catIdList, user.getUnitId(), null);
        	catalogFieldHolder.setPageSize(user.getRowsPerPage());
            modelMap.addAttribute("catalogList", catalogFieldHolder);
            modelMap.addAttribute("catIdList", catalogCustomFieldForm.getCatalogIds());

        } catch (Exception exception) {
            LOGGER.error("Error in adding custom fields", exception);
            return FAILURE;
        }

        return LIGHTBOXCATALOGS_PAGE;
    }

	private PagedListHolder<CatalogSummary> getCatalogsToAddInCustomField(
			String catIdList, String unitId, String searchText) {
		String[] customFieldCatalogIds = {};
    	if(catIdList!=null) {
    		customFieldCatalogIds = catIdList.split(",");
    	}
		Set<String> catalogIdSet = new HashSet<String>(customFieldCatalogIds.length);
		for (String catalogId : customFieldCatalogIds) {
			catalogIdSet.add(catalogId);
		}
		
		List<CatalogSummary> catalogList = catalogService.getAllCatalogs(unitId);
		List<CatalogSummary> filteredCatalogList = new ArrayList<CatalogSummary>(catalogList.size());
		filterSearchedResult(catalogList, searchText);
		for (CatalogSummary catalog : catalogList) {
			if(!catalogIdSet.contains(catalog.getCatalogId())) {
				filteredCatalogList.add(catalog);
			}
		}
		
		PagedListHolder<CatalogSummary> catalogFieldHolder = new PagedListHolder<CatalogSummary>(filteredCatalogList);
		return catalogFieldHolder;
	}

    @RequestMapping(value = "/editCustomFieldPage")
    public String editCustomFieldPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, @RequestParam String customFieldId) {
        User user = SessionDataRetriever.getLoggedInUser(request);


        try {
            CatalogCustomFieldForm catalogCustomFieldForm = new CatalogCustomFieldForm();
//            CatalogCustomField catalogCustomField = catalogService.getCatalogCustomField(customFieldId);
            CustomField customField = catalogService.getCustomField(customFieldId);

//            catalogCustomFieldForm.setCatalogCustomField(catalogCustomField);
            catalogCustomFieldForm.setCustomField(customField);
            modelMap.addAttribute("customField", catalogCustomFieldForm);

            List<CatalogSummary> catalogList = catalogService.getCatalogsByCustomFieldId(customFieldId);
            PagedListHolder<CatalogSummary> catalogFieldHolder = new PagedListHolder<CatalogSummary>(catalogList.size() == 0 ? new ArrayList<CatalogSummary>() : catalogList);
            modelMap.addAttribute("catalogList", catalogFieldHolder);
            String catIds = "";
            if(catalogList != null && catalogList.size() > 0){
                for(CatalogSummary cat : catalogList){
                    catIds = catIds+cat.getCatalogId()+",";
                }
                catIds = catIds.substring(0,catIds.length()-1);
            }
            modelMap.addAttribute("catIdList", catIds);
            modelMap.addAttribute("userName", user.getFullName());
            modelMap.addAttribute("disableStyle","disabled=disabled");
        } catch (Exception exception) {
            LOGGER.error("Error in adding custom fields", exception);
            return FAILURE;
        }

        return EDIT_CUSTOMFIELDS_PAGE;
    }

    @RequestMapping(value = "/populateDropDown")
    public String populateDropDown(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        User user = SessionDataRetriever.getLoggedInUser(request);

        try {

        } catch (Exception exception) {
            LOGGER.error("Error in adding custom fields", exception);
            return FAILURE;
        }

        return EDIT_CUSTOMFIELDS_PAGE;
    }



    public String[] removeElements(String[] input, String deleteMe) {
        List result = new LinkedList();

        for(String item : input)
            if(!deleteMe.equals(item))
                result.add(item);

        return (String[])result.toArray(input);
    }

    public String getStringFromArray(String[] a){
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < a.length; i++) {
            if(a[i] != null){
                result.append( a[i] );
                result.append(",");
            }
        }

        String str = result.toString();
        if(str != null && str.length() > 0){
            return str.substring(0, str.length()-1);
        } else {
            return "";
        }

    }

  public void filterSearchedResult(List<CatalogSummary> catalogList, String searchText) {
    if (StringUtils.isNotBlank(searchText) && CollectionUtils.isNotEmpty(catalogList)) {
      Iterator<CatalogSummary> iterator = catalogList.iterator();
      while (iterator.hasNext()) {
        if (!iterator.next().getName().toLowerCase().contains(searchText.toLowerCase())) {
          iterator.remove();
        }
      }
    }
  }
    
    public String getStringFromList(List<String> strList) {

        StringBuilder strBuffer = new StringBuilder();

        int size = strList.size();

        for(int i = 0; i < size; i++) {
            if(i > 0) strBuffer.append(",");
            strBuffer.append(strList.get(i));
        }

        return strBuffer.toString();
    }
}
