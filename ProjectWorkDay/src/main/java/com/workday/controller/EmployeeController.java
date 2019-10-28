package com.workday.controller;

import com.workday.entity.EntityEmployee;
import com.workday.entity.EntityUserApp;
import com.workday.helper.EmployeeHelper;
import com.workday.services.EmployeeService;
import com.workday.services.I18nService;
import com.workday.services.WorkDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/create/employee")
public class EmployeeController {

    public static final String CREATE_FORM_EMPLOYEE = "create/form-employee";
    public static final String EMPLOYEES = "employees";
    public static final String WORKDAYS = "workdays";
    public static final String EMPLOYEE = "employee";
    public static final String LIST_LIST_EMPLOYEE = "list/list-employee";

    @Autowired
    private WorkDayService workdayService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    EmployeeHelper employeeHelper;

    @Autowired
    private I18nService i18nService;

    @GetMapping("/")
    public String index(Model model) {
        List<EntityEmployee> employees = new ArrayList<>(employeeService.findAll());

        model.addAttribute(EMPLOYEES, employees);
        return LIST_LIST_EMPLOYEE;
    }

    @GetMapping("/list")
    public String listEmployee(Model model) {
        List<EntityEmployee> employees = new ArrayList<>(employeeService.findAll());
        model.addAttribute(EMPLOYEES, employees);
        return LIST_LIST_EMPLOYEE;
    }


    @GetMapping("/new")
    public String newEmployee(Model model) {

        model.addAttribute(EMPLOYEE, new EntityEmployee());
        model.addAttribute(WORKDAYS, workdayService.findAll());
        return CREATE_FORM_EMPLOYEE;
    }

    @PostMapping("/new/submit")
    public String newEmployee(@Valid @ModelAttribute(EMPLOYEE) EntityEmployee employee, BindingResult bindingResult, Model model) {
        String url = LIST_LIST_EMPLOYEE;
        EntityUserApp userApp = employee.getUser();
        userApp = employeeHelper.saveUser(bindingResult, userApp);
        if (bindingResult.hasErrors()) {
            model.addAttribute(WORKDAYS, workdayService.findAll());
            url = CREATE_FORM_EMPLOYEE;
        } else {
            employee.setUser(userApp);
            employeeService.save(employee);
            List<EntityEmployee> employees = new ArrayList<>(employeeService.findAll());
            model.addAttribute(EMPLOYEES, employees);
        }


        return url;
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") Long id, Model model) {

        String url = "";
        EntityEmployee employee = employeeService.findById(id);

        if (employee != null) {
            model.addAttribute(EMPLOYEE, employee);
            model.addAttribute(WORKDAYS, workdayService.findAll());
            url = CREATE_FORM_EMPLOYEE;

        } else {
            url = "redirect:/create/employee/";
        }
        return url;
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id, Model model) {

        String url = "";
        EntityEmployee employee = employeeService.findById(id);


        if (employee != null) {
            employeeService.delete(id);
            url = "redirect:/create/employee/";
        }
        return url;
    }

    @GetMapping("/delete/show/{id}")
    public String showModalDeleteEmployee(@PathVariable("id") Long id, Model model) {

        EntityEmployee employee = employeeService.findById(id);
        String deleteMessage = "";
        if (employee != null)
            deleteMessage = i18nService.getMessage("employee.delete.message", new Object[]{employee.getName()});
        else
            return "redirect:/admin/producto/?error=true";

        model.addAttribute("delete_url", "/create/employee/delete/" + id);
        model.addAttribute("delete_title", i18nService.getMessage("label.deleteEmployee"));
        model.addAttribute("delete_message", deleteMessage);
        return "fragments/modal::modal_delete";
    }


}
