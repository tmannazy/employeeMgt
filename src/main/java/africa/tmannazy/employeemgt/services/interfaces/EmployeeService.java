package africa.tmannazy.employeemgt.services.interfaces;

import africa.tmannazy.employeemgt.data.dtos.request.EmployeeRequest;
import africa.tmannazy.employeemgt.data.dtos.response.EmployeeResponse;
import africa.tmannazy.employeemgt.data.model.Employee;
import africa.tmannazy.employeemgt.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatchException;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public interface EmployeeService {
    Collection<Employee> getAllEmployees();

    EmployeeResponse createEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse getEmployeeById(Long id) throws ResourceNotFoundException;

    EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) throws ResourceNotFoundException, JsonPatchException, IOException, JsonPointerException;

    Map<String, Boolean> deleteEmployee(Long id) throws ResourceNotFoundException;

}
