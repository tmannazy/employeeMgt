package africa.tmannazy.employeemgt.services.implementations;

import africa.tmannazy.employeemgt.data.dtos.request.EmployeeRequest;
import africa.tmannazy.employeemgt.data.dtos.response.EmployeeResponse;
import africa.tmannazy.employeemgt.data.model.Employee;
import africa.tmannazy.employeemgt.data.repository.EmployeeRepository;
import africa.tmannazy.employeemgt.exceptions.ResourceNotFoundException;
import africa.tmannazy.employeemgt.services.interfaces.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    @Override
    public Collection<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeRequest employeeRequest) {
        Employee request = Employee.builder()
                .emailId(employeeRequest.getEmailId())
                .firstName(employeeRequest.getFirstName())
                .lastName(employeeRequest.getLastName())
                .build();
        Employee employee = mapper.map(request, Employee.class);
        var savedEmployee = employeeRepository.save(employee);
        return EmployeeResponse.builder().message("Employee " + savedEmployee.getFirstName() + " created").build();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) throws ResourceNotFoundException {
        Employee found = searchEmployee(id);
        return EmployeeResponse.builder()
                .message(String.format("Employee with id " + id + "found"))
                .employee(found).build();
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) throws ResourceNotFoundException, JsonPatchException, IOException, JsonPointerException {
        Employee found = searchEmployee(id);
        String json = objectMapper.writeValueAsString(employeeRequest);
        Employee patched = updateEmployee(found, json);
        var savedEmployee = employeeRepository.save(patched);
        return EmployeeResponse.builder()
                .message("Employee " + found.getFirstName() + " details updated")
                .employee(savedEmployee)
                .build();
    }

    private Employee updateEmployee(Employee found, String json) throws JsonProcessingException, JsonPatchException {
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonMergePatch patch = JsonMergePatch.fromJson(jsonNode);
        JsonNode patched = patch.apply(objectMapper.convertValue(found, JsonNode.class));
        return objectMapper.treeToValue(patched, Employee.class);
    }

    @Override
    public Map<String, Boolean> deleteEmployee(Long id) throws ResourceNotFoundException {
        Employee employee = searchEmployee(id);
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private Employee searchEmployee(Long id) throws ResourceNotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist."));
    }
}
