package am.itspace.CompanyEmployeeSpring.controller;


import am.itspace.CompanyEmployeeSpring.entity.Company;
import am.itspace.CompanyEmployeeSpring.entity.Role;
import am.itspace.CompanyEmployeeSpring.security.CurrentUser;
import am.itspace.CompanyEmployeeSpring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/companies")
    public String companies(@RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            ModelMap modelMap, @AuthenticationPrincipal CurrentUser currentUser) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(2);
            Page<Company> companyByUserId = companyService.findCompanyByUser(currentUser,
                    PageRequest.of(currentPage - 1, pageSize));
            modelMap.addAttribute("companies", companyByUserId);
            int totalPages = companyByUserId.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbers", pageNumbers);
            }
        return "companies";
    }

    @GetMapping("/companies/add")
    public String addCompanyPage() {

        return "addCompany";
    }

    @PostMapping("/companies/add")
    public String addCompany(@ModelAttribute Company company) {
        companyService.saveCompany(company);
        return "redirect:/companies";
    }

    @GetMapping("/companies/delete")
    public String delete(@RequestParam("id") int id, @AuthenticationPrincipal CurrentUser currentUser) {
        companyService.deleteById(id, currentUser.getUser());
        return "redirect:/companies";
    }
}
