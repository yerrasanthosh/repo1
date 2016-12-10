package com.vroozi.customerui.home.service;

import com.vroozi.customerui.home.service.model.SummaryCard;
import com.vroozi.customerui.user.services.user.model.User;

/**
 * User: SURYA MANDADAPU
 * Date: 10/27/12
 * Time: 1:32 PM
 */
public interface HomeService {

    public SummaryCard getSummary(User user);

}
