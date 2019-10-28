package com.workday.helper;

import com.workday.entity.EntityUserApp;
import com.workday.services.UserAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;


@Component
public class EmployeeHelper {


    @Autowired
    private UserAppService userAppService;

    public EntityUserApp saveUser(BindingResult bindingResult, EntityUserApp userApp) {
        try {

            userApp = userAppService.save(userApp);

        } catch (DataIntegrityViolationException dive) {
            bindingResult.rejectValue("user", "error.user.exist");
        } catch (Exception e) {
            bindingResult.rejectValue("user", "error.unexpected");
        }
        return userApp;
    }
}
