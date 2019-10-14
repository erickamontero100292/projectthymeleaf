package com.workday.controller;

import com.workday.model.Employee;
import com.workday.model.UserApp;
import com.workday.services.EmployeeService;
import com.workday.services.UserAppService;
import com.workday.services.WorkDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    private UserAppService userAppService;

    @GetMapping("/")
    public String index(Model model) {
        List<Employee> employees = new ArrayList<>(employeeService.findAll());

        model.addAttribute(EMPLOYEES, employees);
        return LIST_LIST_EMPLOYEE;
    }

    @GetMapping("/list")
    public String listEmployee(Model model) {
        List<Employee> employees = new ArrayList<>(employeeService.findAll());
        model.addAttribute(EMPLOYEES, employees);
        return LIST_LIST_EMPLOYEE;
    }


    @GetMapping("/new")
    public String newEmployee(Model model) {

        model.addAttribute(EMPLOYEE, new Employee());
        model.addAttribute(WORKDAYS, workdayService.findAll());
        return CREATE_FORM_EMPLOYEE;
    }

    @PostMapping("/new/submit")
    public String newEmployee(@Valid @ModelAttribute(EMPLOYEE) Employee employee, BindingResult bindingResult, Model model) {
        String url = LIST_LIST_EMPLOYEE;
        UserApp userApp = employee.getUser();
        userApp = saveUser(bindingResult, userApp);
        if (bindingResult.hasErrors()) {
            model.addAttribute(WORKDAYS, workdayService.findAll());
            url = CREATE_FORM_EMPLOYEE;
        } else {
            employee.setUser(userApp);
            employeeService.save(employee);
            List<Employee> employees = new ArrayList<>(employeeService.findAll());
            model.addAttribute(EMPLOYEES, employees);
        }


        return url;
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") Long id, Model model) {

        String url = "";
        Employee employee = employeeService.findById(id);

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
        Employee employee = employeeService.findById(id);


        if (employee != null) {
            employeeService.delete(id);
            url = "redirect:/create/employee/";
        }
        return url;
    }

    private UserApp saveUser(BindingResult bindingResult, UserApp userApp) {
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
