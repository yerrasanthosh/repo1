/**
 * 
 */
package com.vroozi.customerui.notification.services;

import com.vroozi.customerui.company.model.CompanySettings;
import com.vroozi.customerui.company.service.CompanyService;
import com.vroozi.customerui.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vroozi.customerui.notification.model.EmailMessage;
import com.vroozi.customerui.notification.services.rest.NotificationRestClient;
import com.vroozi.customerui.user.services.user.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mhabib
 *
 */
@Component
public class NotificationServiceImpl implements NotificationService {

	private final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    AppConfig appConfig;
	
    @Autowired
    private NotificationRestClient notificationRestClient;

    @Autowired
    CompanyService companyService;

//	private String email(EmailMessage message  ) {
//		return notificationRestClient.email(message);
//	}

	@Override
    public void sendEmail(String subject, String message,User recipient,User sender) {

		EmailMessage email = new EmailMessage();
		email.setRecipient(new String[]{recipient.getUsername()});
		email.setSender(sender.getUsername());
		email.setSubject(subject);
		email.setMessage(message);

        email.setMailBox("INBOX");
        email.setUnitId(recipient.getUnitId());
        email.setUserId(recipient.getUserId());
        email.setDeleted(false);
        email.setMessageRead(false);

        notificationRestClient.email(email);
	}
	
	@Override
	public void sendResetPasswordNotification(User updatedUser, User creator) {
		try {
            StringBuilder message = new StringBuilder();

            message.append("Dear ").append(updatedUser.getFirstName()).append(" ").append(updatedUser.getLastName()).append(", <br/><br/>")
            .append("The password for your account ").append(updatedUser.getUsername())
            .append(" has been successfully reset. You can login with your new password by simply clicking this link or pasting it into the URL field of your browser ")
            .append(appConfig.environmentUrl).append("<br/><br/>")
            .append("Password: ").append(updatedUser.getPassword()).append("<br/><br/>")
            .append("If you believe you have received this email in error, or that an unauthorized person has accessed your account, please inform support@vroozi.com")
            .append("<br/><br/>Thank you. ");
            
			
//			sendEmail(creator.getUsername(), updatedUser.getUsername(),
//					"Your smartOCI Account password has been reset", message.toString());

            sendEmail("Your smartOCI Account password has been reset", message.toString(),updatedUser,creator);

        } catch (Exception e) {
			LOGGER.error("Failed to send reset password notification for user: "+updatedUser.getUsername(), e);
		}
	}
	
	@Override
	public void sendNewUserNotification(User newUser, User creator) {
		try {

            CompanySettings companySettings = companyService.getCompanySettingsByUnitId(creator.getUnitId());
            StringBuilder message = new StringBuilder();
            message.append("Dear ").append(newUser.getFirstName()).append(" ").append(newUser.getLastName()).append(",<br/><br/>")
            .append("Congratulations!  You have been registered to access the smartOCI Marketplace. <br/><br/>")
            .append("In order to log in with your credentials below, simply click on this link or paste into the URL field of your browser ")
            .append(appConfig.environmentUrl).append("<br/><br/>")
            .append("Username: ").append(newUser.getUsername()).append("<br/><br/>")
            .append("Password: ").append(newUser.getPassword()).append("<br/><br/>")
            .append("Thank you. ").append("<br/><br/>");
            
			//sendEmail(creator.getUsername(), newUser.getUsername(),"Welcome to smartOCI Marketplace!",message.toString());
            sendEmail("Welcome to smartOCI Marketplace!",message.toString(),newUser,creator);
		} catch (Exception e) {
			LOGGER.error("Failed to send new user notification for: "+newUser.getUsername(), e);
		}
	}

}
