package am.itspace.CompanyEmployeeSpring.controller;


import am.itspace.CompanyEmployeeSpring.entity.Company;
import am.itspace.CompanyEmployeeSpring.entity.Employee;
import am.itspace.CompanyEmployeeSpring.service.CompanyService;
import am.itspace.CompanyEmployeeSpring.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @GetMapping("/employees")
    public String employees(ModelMap modelMap) {
        List<Employee> employees = employeeService.findAll();
        modelMap.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/employees/add")
    public String addEmployeePage(ModelMap modelMap) {
        List<Company> companies = companyService.findAll();
        modelMap.addAttribute("companies", companies);
        return "addEmployee";
    }

    @PostMapping("/employees/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam("employeeImage") MultipartFile file) throws IOException {

        employeeService.saveEmployee(employee, file);

        return "redirect:/employees";
    }

    @GetMapping(value = "/employees/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("fileName") String fileName) throws IOException {
        return employeeService.getEmployeeImage(fileName);
    }

    @GetMapping("/employees/delete")
    public String delete(@RequestParam("id") int id) {

        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
