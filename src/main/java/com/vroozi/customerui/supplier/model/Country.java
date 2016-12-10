package com.vroozi.customerui.supplier.model;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * Created by IntelliJ IDEA.
 * User: rashidha
 * Date: 9/13/12
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {
    private int id;
    private String name;
    private String iso2;
    private String iso3;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }
}
