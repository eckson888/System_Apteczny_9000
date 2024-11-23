package org.gopnik.service;


import jakarta.transaction.Transactional;
import org.gopnik.model.Cart;
import org.gopnik.model.Drugstore;
import org.gopnik.model.Employee;
import org.gopnik.model.Role;
//import org.gopnik.repository.DrugstoreDatabase;
import org.gopnik.repository.EmployeeRepository;
import org.gopnik.repository.IDrugstoreDatabase;
import org.gopnik.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Optional<Employee> findByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    @Transactional
    public Employee getCurrentEmployee() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        return employeeRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public Long getCurrentDrugstoreId(){
        return getCurrentEmployee().getDrugstoreId();
    }

    @Transactional
    public String registerEmployee(Employee employee) {
        if (employeeRepository.findByUsername(employee.getUsername()).isPresent()) {
            return "Pracownik z takim loginem juz istnieje";
        }

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setCart(new Cart());
        Role employeeRole = roleRepository.findByName("EMPLOYEE").orElse(null);

        if (employeeRole != null) {
            employee.getRoles().add(employeeRole);
        } else {
            Role role = new Role();
            role.setName("EMPLOYEE");
            employee.getRoles().add(role);
            roleRepository.save(role);
        }
        employeeRepository.save(employee);
        return "Sukces";
    }

    @Transactional
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Transactional
    public List<Employee> getAll() {
        return this.employeeRepository.findAll();
    }




}
