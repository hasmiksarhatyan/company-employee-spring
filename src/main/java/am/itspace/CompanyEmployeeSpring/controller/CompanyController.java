package am.itspace.CompanyEmployeeSpring.controller;


import am.itspace.CompanyEmployeeSpring.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import am.itspace.CompanyEmployeeSpring.repository.CompanyRepository;
import java.util.List;


@Controller
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/companies")
    public String companies(ModelMap modelMap) {
        List<Company> companies = companyRepository.findAll();
        modelMap.addAttribute("companies", companies);
        return "companies";
    }

    @GetMapping("/companies/add")
    public String addCompanyPage() {

        return "addCompany";
    }

    @PostMapping("/companies/add")
    public String addCompany(@ModelAttribute Company company){
        companyRepository.save(company);
        return "redirect:/companies";
    }

    @GetMapping("/companies/delete")
    public String delete(@RequestParam("id") int id){
        companyRepository.deleteById(id);
        return "redirect:/companies";
    }
}
