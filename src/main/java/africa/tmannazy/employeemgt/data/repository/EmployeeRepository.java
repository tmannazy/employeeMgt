package africa.tmannazy.employeemgt.data.repository;

import africa.tmannazy.employeemgt.data.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
