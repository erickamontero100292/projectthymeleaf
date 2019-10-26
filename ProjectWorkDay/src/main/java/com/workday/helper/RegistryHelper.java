package com.workday.helper;

import com.workday.configuration.PropertiesConfiguration;
import com.workday.constant.Roles;
import com.workday.entity.EntityEmployee;
import com.workday.entity.EntityRegistry;
import com.workday.model.Registry;
import com.workday.services.EmployeeService;
import com.workday.services.I18nService;
import com.workday.services.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RegistryHelper {


    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PropertiesConfiguration properties;

    @Autowired
    private I18nService i18nService;

    @Autowired
    private RegistryService registryService;


    public List<Registry> processPercentageHourWorked(List<EntityRegistry> entityRegistries) {
        List<Registry> registryList = new ArrayList<>();
        for (EntityRegistry entityRegistry : entityRegistries) {
            float numberDailyHour = entityRegistry.getEmployee().getWorkday().getNumberDailyHour();
            float percetange = (entityRegistry.getHours() * 100) / numberDailyHour;
            Registry registry = new Registry(entityRegistry, percetange);
            registryList.add(registry);

        }

        return registryList;
    }

    private void processSetEmployee(@Valid EntityRegistry registry, boolean hasEmployee) {
        SimpleGrantedAuthority rol = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        if (hasEmployee && rol.getAuthority().equalsIgnoreCase(Roles.ROLE_USER.getRolName())) {
            setEmployeeForRegistry(registry);
        }
    }

    private void setEmployeeForRegistry(@Valid EntityRegistry registry) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EntityEmployee employee = employeeService.findByUser(email);
        registry.setEmployee(employee);

    }

    private boolean processValidations(@Valid EntityRegistry registry, BindingResult bindingResult) {

        boolean validations;
        boolean hasEmployee;

        validateAllowsHour(registry, bindingResult);
        hasEmployee = validateHasEmployee(registry, bindingResult);
        processSetEmployee(registry, hasEmployee);
        validateWorkdedHour(registry, bindingResult);
        validateDateAfterToday(registry, bindingResult);
        validations = bindingResult.hasErrors();
        return validations;
    }

    private boolean validateDateAfterToday(@Valid EntityRegistry registry, BindingResult bindingResult) {
        Date today = new Date();
        boolean processOK = true;
        if (registry.getDateRegistry().after(today)) {
            processOK = false;
            bindingResult.rejectValue("dateRegistry", "error.registry.date");
        }
        return processOK;
    }

    private boolean validateHasEmployee(@Valid EntityRegistry registry, BindingResult bindingResult) {
        SimpleGrantedAuthority rol = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        boolean processOK = true;
        if (registry.getEmployee() == null && rol.getAuthority().equalsIgnoreCase(Roles.ROL_ADMIN.getRolName())) {
            processOK = false;
            bindingResult.rejectValue("employee", "error.registry.employee");
        }
        return processOK;
    }

    private boolean validateAllowsHour(@Valid EntityRegistry registry, BindingResult bindingResult) {
        boolean processOK = true;
        if (registry.getHours() > properties.getAllowedHours()) {
            processOK = false;
            getMessageMaxHourAllowsWorked(bindingResult);
        }

        return processOK;
    }

    private void getMessageMaxHourAllowsWorked(BindingResult bindingResult) {
        String message = i18nService.getMessage("error.user.maxHour", new Object[]{String.valueOf(properties.getAllowedHours())});
        bindingResult.rejectValue("hours", "error.registry", message);
    }

    private void getMessageMaxHourWorkedByWorkday(BindingResult bindingResult, Long hours) {
        String message = i18nService.getMessage("error.user.maxHourWorkday", new Object[]{String.valueOf(hours)});
        bindingResult.rejectValue("hours", "error.registry", message);
    }


    private boolean validateWorkdedHour(@Valid EntityRegistry registry, BindingResult bindingResult) {
        boolean processOK = true;
        if (registry.getEmployee() != null) {
            Long hours = registry.getEmployee().getWorkday().getNumberDailyHour();
            if (registry.getHours() > hours) {
                processOK = false;
                getMessageMaxHourWorkedByWorkday(bindingResult, hours);
            }
        }
        return processOK;
    }

    public boolean processSaveRegistry(@Valid EntityRegistry registry, BindingResult bindingResult) {
        boolean hasError = false;
        try {
            hasError = processValidations(registry, bindingResult);
            if (!hasError) {
                registryService.save(registry);
            }
        } catch (DataIntegrityViolationException dive) {
            bindingResult.rejectValue("dateRegistry", "error.registry.exist");
            hasError = true;
        } catch (Exception e) {
            bindingResult.rejectValue("dateRegistry", "error.unexpected");
            hasError = true;
        }

        return hasError;
    }


}
