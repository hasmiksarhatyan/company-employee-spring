package am.itspace.CompanyEmployeeSpring.repository;


import am.itspace.CompanyEmployeeSpring.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Page<Company> findAllByUser_id(int userId, Pageable pageable);

}
