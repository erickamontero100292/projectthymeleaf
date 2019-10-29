package com.workday;

import com.workday.entity.*;
import com.workday.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ProjectWorkDayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectWorkDayApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(RolService rolService, UserAppService userAppService, WorkDayService workDayService, EmployeeService employeeService, RegistryService registryService) {
        return args -> {

            List<EntityRol> entityRols = saveRoles(rolService);
            List<EntityWorkday> entityWorkdays = saveWorkdays(workDayService);
            List<EntityEmployee> entityEmployees = saveUserAndEmployee(userAppService, employeeService, entityWorkdays, entityRols);
            saveRegistry(registryService,entityEmployees);


        };
    }

    private void saveRegistry(RegistryService registryService, List<EntityEmployee> entityEmployees) {



        LocalDate localDate = LocalDate.now().minusDays(10);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<EntityRegistry> entityRegistries= new ArrayList<>();
        for(int i = 0; i < 10; i++){
            for (EntityEmployee entityEmployee:entityEmployees                 ) {
                date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                EntityRegistry entityRegistry = new EntityRegistry(date,entityEmployee,5L);
                entityRegistries.add(entityRegistry);
            }
            localDate=localDate.plusDays(1);

        }
        registryService.save(entityRegistries);
    }

    private List<EntityRol> saveRoles(RolService rolService) {
        EntityRol admin = new EntityRol("ADMIN");
        EntityRol user = new EntityRol("USER");
        List<EntityRol> entityRols = Arrays.asList(admin, user);
        entityRols.forEach(rolService::save);
        return entityRols;
    }

    private List<EntityWorkday> saveWorkdays(WorkDayService workDayService) {
        EntityWorkday workday8H = new EntityWorkday("Jornada laboral 8H", 8L, 40L);
        EntityWorkday workday12H = new EntityWorkday("Jornada laboral 12H", 12L, 60L);
        List<EntityWorkday> entityWorkdays = Arrays.asList(workday8H, workday12H);
        entityWorkdays.forEach(workDayService::save);
        return entityWorkdays;
    }

    private List<EntityEmployee> saveUserAndEmployee(UserAppService userAppService, EmployeeService employeeService, List<EntityWorkday> entityWorkdays, List<EntityRol> entityRols) {

        EntityRol admin = null;
        EntityRol user = null;
        EntityWorkday workday8H = null;
        EntityWorkday workday12H = null;
        for (EntityRol entityRol : entityRols) {
            if (entityRol.getName().equals("ADMIN")) {
                admin = entityRol;
            } else {
                user = entityRol;
            }
        }
        for (EntityWorkday entityWorkday : entityWorkdays) {
            if (entityWorkday.getName().equals("Jornada laboral 8H")) {
                workday8H = entityWorkday;
            } else {
                workday12H = entityWorkday;
            }
        }
        EntityUserApp userAdmin = new EntityUserApp(new Date(), "admin", "admin", admin);
        EntityUserApp user1 = new EntityUserApp(new Date(), "pperez", "123", user);
        EntityUserApp user2 = new EntityUserApp(new Date(), "mperez", "123", user);
        List<EntityUserApp> entityUserApp = Arrays.asList(userAdmin, user1, user2);
        entityUserApp.forEach(userAppService::save);
        EntityEmployee employee = new EntityEmployee("Pepe Perez", workday8H, user1);
        EntityEmployee employee2 = new EntityEmployee("Maria Perez", workday12H, user2);

        List<EntityEmployee> entityEmployees = Arrays.asList(employee, employee2);
        entityEmployees.forEach(employeeService::save);
        return entityEmployees;
    }

}
