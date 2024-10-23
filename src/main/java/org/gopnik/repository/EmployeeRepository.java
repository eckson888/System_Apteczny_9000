package org.gopnik.repository;


import org.gopnik.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long>{
    Optional<Employee>findByUsername(String username);
    Employee save(Employee employee);
}
