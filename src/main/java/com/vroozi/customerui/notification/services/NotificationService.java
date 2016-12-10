/**
 * 
 */
package com.vroozi.customerui.notification.services;

import com.vroozi.customerui.notification.model.EmailMessage;
import com.vroozi.customerui.user.services.user.model.User;

/**
 * @author mhabib
 *
 */
public interface NotificationService {

	//public String email(EmailMessage message);

//	public void sendEmail(String from, String to, String subject,
//			String message);

    public void sendEmail(String subject, String message,User recipient,User sender) ;
	public void sendNewUserNotification(User userToAdd, User sender);

	public void sendResetPasswordNotification(User userToAdd, User sender);

}
