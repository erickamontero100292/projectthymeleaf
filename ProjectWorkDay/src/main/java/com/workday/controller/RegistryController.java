package com.workday.controller;

import com.workday.configuration.PropertiesConfiguration;
import com.workday.model.Employee;
import com.workday.model.Registry;
import com.workday.services.EmployeeService;
import com.workday.services.I18nService;
import com.workday.services.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/create/registry")
public class RegistryController {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RegistryService registryService;

    @Autowired
    private PropertiesConfiguration properties;

    @Autowired
    private I18nService i18nService;

    @ModelAttribute("registrys")
    public List<Registry> myRegistry() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        SimpleGrantedAuthority rol = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        List<Registry> registrys;
        if (rol.getAuthority().equalsIgnoreCase(ROLE_ADMIN)) {
            registrys = new ArrayList<Registry>(registryService.findAllByOrderByDateRegistryAsc());
        } else {
            Employee employee = employeeService.findByUser(email);
            registrys = new ArrayList<Registry>(registryService.findByEmployeeByOrderByDateRegistryAsc(employee));
        }

        return registrys;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Registry> registrys = new ArrayList<Registry>(registryService.findAllByOrderByDateRegistryAsc());

        model.addAttribute("registrys", registrys);
        return "list/list-registry";
    }


    @GetMapping("/list")
    public String listRegistry(Model model) {
        List<Registry> registrys = new ArrayList<Registry>(registryService.findAllByOrderByDateRegistryAsc());

        model.addAttribute("registrys", registrys);
        return "list/list-registry";
    }

    @GetMapping("/listMyRegistry")
    public String listMyRegistry(Model model) {
        model.addAttribute("registrys");
        return "list/list-registry";
    }


    @GetMapping("/new")
    public String newemployee(Model model) {

        model.addAttribute("registry", new Registry());
        model.addAttribute("employees", employeeService.findAll());
        return "create/form-registry";
    }

    @PostMapping("/new/submit")
    public String submitNewWorkDay(@Valid Registry registry, BindingResult bindingResult, Model model) {
        String url = "list/list-registry";
        boolean processFail = processSaveRegistry(registry, bindingResult);
        if (processFail) {
            url = "create/form-registry";
        } else {
            List<Registry> registrys = new ArrayList<Registry>(registryService.findByEmployeeByOrderByDateRegistryAsc(registry.getEmployee()));
            model.addAttribute("registrys", registrys);
        }

        return url;
    }

    @PostMapping("/new/adminSubmit")
    public String submitAdminNewWorkDay(@Valid Registry registry, BindingResult bindingResult, Model model) {
        String url = "list/list-registry";
        List<Employee> employees = new ArrayList<>(employeeService.findAll());

        boolean processFail = processSaveRegistry(registry, bindingResult);

        if (processFail) {
            model.addAttribute("employees", employees);
            url = "create/form-registry";
        } else {

            List<Registry> registrys = new ArrayList<>(registryService.findAllByOrderByDateRegistryAsc());
            model.addAttribute("registrys", registrys);
        }

        return url;
    }

    private boolean processSaveRegistry(@Valid Registry registry, BindingResult bindingResult) {
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

    private boolean processValidations(@Valid Registry registry, BindingResult bindingResult) {

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

    private void processSetEmployee(@Valid Registry registry, boolean hasEmployee) {
        SimpleGrantedAuthority rol = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        if (hasEmployee && rol.getAuthority().equalsIgnoreCase("ROLE_USER")) {
            setEmployeeForRegistry(registry);
        }
    }


    private boolean validateDateAfterToday(@Valid Registry registry, BindingResult bindingResult) {
        Date today = new Date();
        boolean processOK = true;
        if (registry.getDateRegistry().after(today)) {
            processOK = false;
            bindingResult.rejectValue("dateRegistry", "error.registry.date");
        }
        return processOK;
    }

    private boolean validateHasEmployee(@Valid Registry registry, BindingResult bindingResult) {
        SimpleGrantedAuthority rol = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        boolean processOK = true;
        if (registry.getEmployee() == null && rol.getAuthority().equalsIgnoreCase(ROLE_ADMIN)) {
            processOK = false;
            bindingResult.rejectValue("employee", "error.registry.employee");
        }
        return processOK;
    }

    private boolean validateAllowsHour(@Valid Registry registry, BindingResult bindingResult) {
        boolean processOK = true;
        if (registry.getHours() > properties.getAllowedHours()) {
            processOK = false;
            getMessageMaxHourAllowsWorked(bindingResult);
        }

        return processOK;
    }

    private boolean validateWorkdedHour(@Valid Registry registry, BindingResult bindingResult) {
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


    private void setEmployeeForRegistry(@Valid Registry registry) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeService.findByUser(email);
        registry.setEmployee(employee);

    }

    private void getMessageMaxHourAllowsWorked(BindingResult bindingResult) {
        String message = i18nService.getMessage("error.user.maxHour", new Object[]{String.valueOf(properties.getAllowedHours())});
        bindingResult.rejectValue("hours", "error.registry", message);
    }

    private void getMessageMaxHourWorkedByWorkday(BindingResult bindingResult, Long hours) {
        String message = i18nService.getMessage("error.user.maxHourWorkday", new Object[]{String.valueOf(hours)});
        bindingResult.rejectValue("hours", "error.registry", message);
    }


    @GetMapping("/edit/{id}")
    public String editRegistry(@PathVariable("id") Long id, Model model) {

        String url = "";
        Registry registry = registryService.findById(id);

        if (registry != null) {
            model.addAttribute("registry", registry);
            model.addAttribute("employees", employeeService.findAll());
            url = "create/form-registry";

        } else {
            url = "redirect:/create/registry/";
        }
        return url;
    }

    @GetMapping("/delete/{id}")
    public String deleteRegistry(@PathVariable("id") Long id, Model model) {

        String url = "";
        Registry workday = registryService.findById(id);

        if (workday != null) {
            registryService.delete(id);
            url = "redirect:/create/registry/";
        }
        return url;
    }

}
