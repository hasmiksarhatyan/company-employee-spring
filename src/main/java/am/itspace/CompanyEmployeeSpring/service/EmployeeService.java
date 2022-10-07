package am.itspace.CompanyEmployeeSpring.service;

import am.itspace.CompanyEmployeeSpring.entity.Company;
import am.itspace.CompanyEmployeeSpring.entity.Employee;
import am.itspace.CompanyEmployeeSpring.repository.CompanyRepository;
import am.itspace.CompanyEmployeeSpring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;
    private final CompanyService companyService;
    @Value("${company.employee.spring.images.folder}")
    private String folderPath;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void saveEmployee(Employee employee, MultipartFile file) throws IOException {
        if (!file.isEmpty() && file.getSize() > 0) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File newFile = new File(folderPath + File.separator + fileName);
            file.transferTo(newFile);
            employee.setProfilePic(fileName);
        }
        employeeRepository.save(employee);
        companyService.addCompanySize(employee.getCompany());
    }


    public byte[] getEmployeeImage(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    public void deleteById(int id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Company company = employeeOptional.get().getCompany();
            company.setSize(company.getSize() - 1);
            companyRepository.save(company);
            employeeRepository.deleteById(id);
        }
    }
}
