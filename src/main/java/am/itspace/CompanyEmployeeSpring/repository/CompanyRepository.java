package am.itspace.CompanyEmployeeSpring.repository;


import am.itspace.CompanyEmployeeSpring.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {

}
