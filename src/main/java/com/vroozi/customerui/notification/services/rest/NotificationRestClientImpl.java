/**
 * 
 */
package com.vroozi.customerui.notification.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.vroozi.customerui.exception.AdminUIException;
import com.vroozi.customerui.notification.model.EmailMessage;
import com.vroozi.customerui.util.RestServiceUrl;

/**
 * @author mhabib
 *
 */
@Component
public class NotificationRestClientImpl implements NotificationRestClient {

	@Autowired
	RestServiceUrl restServiceUrl;


	@Override
	public String email(EmailMessage message) {
		try{
            new RestTemplate().postForLocation(restServiceUrl.getNotificationMessagePath(), message);
        }catch (Exception exp) {
            throw new AdminUIException(exp);
        }
		return "ok";
	}

}
