package com.workday.controller;

import com.workday.configuration.PropertiesConfiguration;
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
        List<EntityRegistry> entityRegistries;
        List<Registry> registryList = new ArrayList<>();
        if (rol.getAuthority().equalsIgnoreCase(ROLE_ADMIN)) {
            entityRegistries = new ArrayList<EntityRegistry>(registryService.findAllByOrderByDateRegistryAsc());
            registryList =processPercentageHourWorked(entityRegistries );

        } else {
            EntityEmployee employee = employeeService.findByUser(email);
            entityRegistries = new ArrayList<EntityRegistry>(registryService.findByEmployeeByOrderByDateRegistryAsc(employee));
            registryList= processPercentageHourWorked(entityRegistries);
        }

        return registryList;
    }

    private  List<Registry> processPercentageHourWorked(List<EntityRegistry> entityRegistries) {
            List<Registry> registryList = new ArrayList<>();
        for (EntityRegistry entityRegistry : entityRegistries) {
            float numberDailyHour = entityRegistry.getEmployee().getWorkday().getNumberDailyHour();
            float percetange = (entityRegistry.getHours() * 100) / numberDailyHour;
            Registry registry = new Registry(entityRegistry, percetange);
            registryList.add(registry);

        }

        return registryList;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<EntityRegistry> registrys = new ArrayList<EntityRegistry>(registryService.findAllByOrderByDateRegistryAsc());
        List<Registry> registryList = processPercentageHourWorked(registrys);
        model.addAttribute("registrys", registryList);
        return "list/list-registry";
    }


    @GetMapping("/list")
    public String listRegistry(Model model) {
        List<EntityRegistry> registrys = new ArrayList<EntityRegistry>(registryService.findAllByOrderByDateRegistryAsc());
        List<Registry> registryList = new ArrayList<>();
        registryList= processPercentageHourWorked(registrys);
        model.addAttribute("registrys", registryList);
        return "list/list-registry";
    }

    @GetMapping("/listMyRegistry")
    public String listMyRegistry(Model model) {
        model.addAttribute("registrys");
        return "list/list-registry";
    }


    @GetMapping("/new")
    public String newemployee(Model model) {

        model.addAttribute("registry", new EntityRegistry());
        model.addAttribute("employees", employeeService.findAll());
        return "create/form-registry";
    }

    @PostMapping("/new/submit")
    public String submitNewWorkDay(@Valid @ModelAttribute("registry") EntityRegistry registry, BindingResult bindingResult, Model model) {
        String url = "list/list-registry";
        boolean processFail = processSaveRegistry(registry, bindingResult);
        if (processFail) {
            url = "create/form-registry";
        } else {
            List<EntityRegistry> registrys = new ArrayList<EntityRegistry>(registryService.findByEmployeeByOrderByDateRegistryAsc(registry.getEmployee()));
            List<Registry> registryList = new ArrayList<>();
            registryList = processPercentageHourWorked(registrys);
            model.addAttribute("registrys", registryList);
        }

        return url;
    }

    @PostMapping("/new/adminSubmit")
    public String submitAdminNewWorkDay(@Valid @ModelAttribute("registry") EntityRegistry registry, BindingResult bindingResult, Model model) {
        String url = "list/list-registry";
        List<EntityEmployee> employees = new ArrayList<>(employeeService.findAll());

        boolean processFail = processSaveRegistry(registry, bindingResult);

        if (processFail) {
            model.addAttribute("employees", employees);
            url = "create/form-registry";
        } else {

            List<EntityRegistry> registrys = new ArrayList<>(registryService.findAllByOrderByDateRegistryAsc());
            List<Registry> registryList = new ArrayList<>();
            registryList = processPercentageHourWorked( registrys);
            model.addAttribute("registrys", registryList);
        }

        return url;
    }

    private boolean processSaveRegistry(@Valid EntityRegistry registry, BindingResult bindingResult) {
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

    private void processSetEmployee(@Valid EntityRegistry registry, boolean hasEmployee) {
        SimpleGrantedAuthority rol = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        if (hasEmployee && rol.getAuthority().equalsIgnoreCase("ROLE_USER")) {
            setEmployeeForRegistry(registry);
        }
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
        if (registry.getEmployee() == null && rol.getAuthority().equalsIgnoreCase(ROLE_ADMIN)) {
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


    private void setEmployeeForRegistry(@Valid EntityRegistry registry) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        EntityEmployee employee = employeeService.findByUser(email);
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
        EntityRegistry registry = registryService.findById(id);
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
    public String deleteRegistry(@PathVariable("id") Long id) {

        String url = "";
        EntityRegistry registry = registryService.findById(id);

        if (registry != null) {
            registryService.delete(id);
            url = "redirect:/create/registry/";
        }
        return url;
    }

    @GetMapping("/delete/show/{id}")
    public String showModalDeleteEmployee(@PathVariable("id") Long id, Model model) {

        EntityRegistry registry = registryService.findById(id);
        String deleteMessage = "";
        if (registry != null)
            deleteMessage = i18nService.getMessage("registry.delete.message", new Object[]{registry.getDateRegistry()});
        else
            return "redirect:/admin/producto/?error=true";

        model.addAttribute("delete_url", "/create/registry/delete/" + id);
        model.addAttribute("delete_title", i18nService.getMessage("label.deleteWorkday"));
        model.addAttribute("delete_message", deleteMessage);
        return "fragments/modal::modal_delete";
    }


}
