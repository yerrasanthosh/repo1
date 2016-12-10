package com.vroozi.customerui.customfield.model;

import java.util.List;
import java.util.ArrayList;

public class CustomFieldAutoCompleteBean {
    private List<CustomField> customField;
    private List<CustomField> selectedCustomField;

    public CustomFieldAutoCompleteBean() {
        CustomFieldTableBean bean=new CustomFieldTableBean();
        customField= new ArrayList<CustomField>();
        for(int i = 0 ; i < 20 ; i++)
            customField.add(new CustomField(bean.getRandomFieldID(), bean.getFieldTypeList(),"A"+i, "Attr"+i, bean.getFieldList()));
    }

    public List<String> complete(String query) {
        List<String> results = new ArrayList<String>();

        for(int i = 0; i < 10; i++) {
            results.add(query + i);
        }

        return results;
    }

    public List<CustomField> completePlayer(String query) {
        List<CustomField> suggestions = new ArrayList<CustomField>();

        for(CustomField p : customField) {
            if(p.getName().startsWith(query))
                suggestions.add(p);
        }
        return suggestions;
    }

    public List<CustomField> getCustomField() {
        return customField;
    }

    public List<CustomField> getSelectedCustomField() {
        return selectedCustomField;
    }

    public void setSelectedCustomField(List<CustomField> selectedCustomField) {
        this.selectedCustomField = selectedCustomField;
    }


/*
    public void handleUnselect(UnselectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unselected:" + event.getObject().toString(), null);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(submittedValue);

                for (CustomField p : customField) {
                    if (p.getId().equals(number)) {
                        return p;
                    }
                }

            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid player"));
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((CustomField) value).getId());
        }
    }
    */ 
}
			