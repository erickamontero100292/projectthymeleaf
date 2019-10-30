package com.workday.controller;

import com.workday.constant.Roles;
import com.workday.entity.EntityEmployee;
import com.workday.entity.EntityRegistry;
import com.workday.helper.RegistryHelper;
import com.workday.model.Registry;
import com.workday.services.EmployeeService;
import com.workday.services.I18nService;
import com.workday.services.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/create/registry")
public class RegistryController {


    public static final String CREATE_FORM_REGISTRY = "create/form-registry";
    public static final String REGISTRYS = "registrys";
    public static final String LIST_LIST_REGISTRY = "list/list-registry";
    public static final String EMPLOYEES = "employees";
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RegistryService registryService;

    @Autowired
    private I18nService i18nService;
    @Autowired
    private RegistryHelper registryHelper;

    @ModelAttribute(REGISTRYS)
    public List<Registry> myRegistry() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        SimpleGrantedAuthority rol = (SimpleGrantedAuthority) SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next();
        List<EntityRegistry> entityRegistries;
        List<Registry> registryList;
        if (rol.getAuthority().equalsIgnoreCase(Roles.ROL_ADMIN.getRolName())) {
            entityRegistries = new ArrayList<>(registryService.findAllByOrderByDateRegistryAsc());
            registryList = registryHelper.processPercentageHourWorked(entityRegistries);

        } else {
            EntityEmployee employee = employeeService.findByUser(email);
            entityRegistries = new ArrayList<>(registryService.findByEmployeeByOrderByDateRegistryAsc(employee));
            registryList = registryHelper.processPercentageHourWorked(entityRegistries);
        }

        return registryList;
    }


    @GetMapping("/")
    public String index(Model model, @RequestParam(name = "q", required = false) String query) {

        List<Registry> registryList = registryHelper.getListRegistry(query);
        model.addAttribute(REGISTRYS, registryList);
        return LIST_LIST_REGISTRY;
    }


    @GetMapping("/list")
    public String listRegistry(Model model, @RequestParam(name = "q", required = false) String query) {

        List<Registry> registryList = registryHelper.getListRegistry(query);
        model.addAttribute(REGISTRYS, registryList);
        return LIST_LIST_REGISTRY;
    }

    @GetMapping("/listMyRegistry")
    public String listMyRegistry(Model model) {
        model.addAttribute(REGISTRYS);
        return LIST_LIST_REGISTRY;
    }


    @GetMapping("/new")
    public String newemployee(Model model) {

        model.addAttribute("registry", new EntityRegistry());
        model.addAttribute(EMPLOYEES, employeeService.findAll());
        return CREATE_FORM_REGISTRY;
    }

    @PostMapping("/new/submit")
    public String submitNewWorkDay(@Valid @ModelAttribute("registry") EntityRegistry registry, BindingResult bindingResult, Model model) {
        String url = LIST_LIST_REGISTRY;
        boolean processFail = registryHelper.processSaveRegistry(registry, bindingResult);
        if (processFail) {
            url = CREATE_FORM_REGISTRY;
        } else {
            List<EntityRegistry> registrys = new ArrayList<>(registryService.findByEmployeeByOrderByDateRegistryAsc(registry.getEmployee()));
            List<Registry> registryList;
            registryList = registryHelper.processPercentageHourWorked(registrys);
            model.addAttribute(REGISTRYS, registryList);
        }

        return url;
    }

    @PostMapping("/new/adminSubmit")
    public String submitAdminNewWorkDay(@Valid @ModelAttribute("registry") EntityRegistry registry, BindingResult bindingResult, Model model) {
        String url = "redirect:/create/registry/";
        List<EntityEmployee> employees = new ArrayList<>(employeeService.findAll());

        boolean processFail = registryHelper.processSaveRegistry(registry, bindingResult);

        if (processFail) {
            model.addAttribute(EMPLOYEES, employees);
            url = CREATE_FORM_REGISTRY;
        } else {

            List<EntityRegistry> registrys = new ArrayList<>(registryService.findAllByOrderByDateRegistryAsc());
            List<Registry> registryList = new ArrayList<>();
            registryList = registryHelper.processPercentageHourWorked(registrys);
            model.addAttribute(REGISTRYS, registryList);
        }

        return url;
    }

    @GetMapping("/edit/{id}")
    public String editRegistry(@PathVariable("id") Long id, Model model) {

        String url = "";
        EntityRegistry registry = registryService.findById(id);
        if (registry != null) {
            model.addAttribute("registry", registry);
            model.addAttribute(EMPLOYEES, employeeService.findAll());
            url = CREATE_FORM_REGISTRY;

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
