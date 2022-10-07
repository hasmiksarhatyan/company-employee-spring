package am.itspace.CompanyEmployeeSpring.service;

import am.itspace.CompanyEmployeeSpring.entity.Company;
import am.itspace.CompanyEmployeeSpring.entity.Role;
import am.itspace.CompanyEmployeeSpring.entity.User;
import am.itspace.CompanyEmployeeSpring.repository.CompanyRepository;
import am.itspace.CompanyEmployeeSpring.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Page<Company> findCompanyByUser(CurrentUser currentuser, Pageable pageable) {
        if (currentuser == null || currentuser.getUser().getRole() == Role.MANAGER) {
            return companyRepository.findAll(pageable);
        } else {
            return companyRepository.findAllByUser_id(currentuser.getUser().getId(),pageable);
        }
    }

    public void saveCompany(Company company) {
        companyRepository.save(company);
    }

    public void deleteById(int id, User user) {
        if ((user.getRole() == Role.MANAGER) ||
                (user.getId() == companyRepository.findById(id).get().getUser().getId())) {
            companyRepository.deleteById(id);
        }
    }

    public void addCompanySize(Company company) {
        company.setSize(company.getSize() + 1);
        companyRepository.save(company);
    }
}
