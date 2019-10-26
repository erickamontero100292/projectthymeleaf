package com.workday.controller;

import com.workday.configuration.PropertiesConfiguration;
import com.workday.constant.Roles;
import com.workday.entity.EntityEmployee;
import com.workday.entity.EntityRegistry;
import com.workday.helper.RegistryHelper;
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


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RegistryService registryService;

    @Autowired
    private I18nService i18nService;
    @Autowired
    private RegistryHelper registryHelper;

    @ModelAttribute("registrys")
    public List<Registry> myRegistry() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        SimpleGrantedAuthority rol = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        List<EntityRegistry> entityRegistries;
        List<Registry> registryList = new ArrayList<>();
        if (rol.getAuthority().equalsIgnoreCase(Roles.ROL_ADMIN.getRolName())) {
            entityRegistries = new ArrayList<EntityRegistry>(registryService.findAllByOrderByDateRegistryAsc());
            registryList = registryHelper.processPercentageHourWorked(entityRegistries);

        } else {
            EntityEmployee employee = employeeService.findByUser(email);
            entityRegistries = new ArrayList<EntityRegistry>(registryService.findByEmployeeByOrderByDateRegistryAsc(employee));
            registryList = registryHelper.processPercentageHourWorked(entityRegistries);
        }

        return registryList;
    }


    @GetMapping("/")
    public String index(Model model) {
        List<EntityRegistry> registrys = new ArrayList<EntityRegistry>(registryService.findAllByOrderByDateRegistryAsc());
        List<Registry> registryList = registryHelper.processPercentageHourWorked(registrys);
        model.addAttribute("registrys", registryList);
        return "list/list-registry";
    }


    @GetMapping("/list")
    public String listRegistry(Model model) {
        List<EntityRegistry> registrys = new ArrayList<EntityRegistry>(registryService.findAllByOrderByDateRegistryAsc());
        List<Registry> registryList = new ArrayList<>();
        registryList = registryHelper.processPercentageHourWorked(registrys);
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
        boolean processFail = registryHelper.processSaveRegistry(registry, bindingResult);
        if (processFail) {
            url = "create/form-registry";
        } else {
            List<EntityRegistry> registrys = new ArrayList<EntityRegistry>(registryService.findByEmployeeByOrderByDateRegistryAsc(registry.getEmployee()));
            List<Registry> registryList = new ArrayList<>();
            registryList = registryHelper.processPercentageHourWorked(registrys);
            model.addAttribute("registrys", registryList);
        }

        return url;
    }

    @PostMapping("/new/adminSubmit")
    public String submitAdminNewWorkDay(@Valid @ModelAttribute("registry") EntityRegistry registry, BindingResult bindingResult, Model model) {
        String url = "list/list-registry";
        List<EntityEmployee> employees = new ArrayList<>(employeeService.findAll());

        boolean processFail = registryHelper.processSaveRegistry(registry, bindingResult);

        if (processFail) {
            model.addAttribute("employees", employees);
            url = "create/form-registry";
        } else {

            List<EntityRegistry> registrys = new ArrayList<>(registryService.findAllByOrderByDateRegistryAsc());
            List<Registry> registryList = new ArrayList<>();
            registryList = registryHelper.processPercentageHourWorked(registrys);
            model.addAttribute("registrys", registryList);
        }

        return url;
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
