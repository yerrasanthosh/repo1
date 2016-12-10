package com.vroozi.customerui.catalog.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import com.vroozi.customerui.catalog.model.CatalogProxy;

//@RunWith(SpringJUnit4ClassRunner.class)
//@EnableWebMvc
//@ContextConfiguration(classes=com.vroozi.customerui.config.AppConfig.class, loader=AnnotationConfigContextLoader.class)
//@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
//@ComponentScan(basePackages = "com.vroozi.customerui")
public class CatalogRestClientTest {

    //@Autowired
    //RestServiceUrl restServiceUrl;
/*
    @Configuration
    static class ContextConfiguration {

        // this bean will be injected into the OrderServiceTest class
        @Bean
        public OrderService orderService() {
            OrderService orderService = new OrderServiceImpl();
            // set properties, etc.
            return orderService;
        }
    }
*/
    //Code with Commons HttpClient
    public String doHttpClientGet(GetMethod getMethod) throws Exception {
        String body = "";

        // Get HTTP client instance
        HttpClient httpclient = new HttpClient();

        // Execute HTTP GET
        int statusCode = httpclient.executeMethod(getMethod);

        // Get Response Body
        body = getMethod.getResponseBodyAsString();

        return body;
    }

    public String doHttpData(String url){
        GetMethod getMethod = new GetMethod(url);
        String body = "";
        try{
            body = doHttpClientGet(getMethod);
        }catch(Throwable exp){
            exp.printStackTrace();
        }finally{
            if(getMethod != null){
                getMethod.releaseConnection();
            }
        }
        return body;
    }

    @Test
    public void testGetAllCatalogs() {
        String UNIT_ID = "13";

        try{
            String serviceUrl = "http://localhost:8080/catalog/api/catalogs/" + "?unitId=" + UNIT_ID;
            System.out.println(serviceUrl);
            String response = ""; //doHttpData(serviceUrl);
            System.out.println(response);
        }catch (Exception exp){
            exp.printStackTrace();
        }

        assert(true);
    }

}

