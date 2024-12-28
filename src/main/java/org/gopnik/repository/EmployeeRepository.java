package org.gopnik.repository;


import org.gopnik.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    Optional<Employee>findByUsername(String username);
    List<Employee> findByDrugstoreId(Long drugstoreId);
    Employee save(Employee employee);
}
