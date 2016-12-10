/**
 * 
 */
package com.vroozi.customerui.notification.services.rest;

import com.vroozi.customerui.notification.model.EmailMessage;

/**
 * @author mhabib
 *
 */
public interface NotificationRestClient {

	public String email(EmailMessage message);

}
