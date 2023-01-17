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
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


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
//        Employee request = Employee.builder()
//                .emailId(employeeRequest.getEmailId())
//                .firstName(employeeRequest.getFirstName())
//                .lastName(employeeRequest.getLastName())
//                .build();
        Employee request = mapper.map(employeeRequest, Employee.class);
        employeeRepository.save(request);
        return EmployeeResponse.builder().message("Employee " + request.getFirstName() + " created").build();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) throws ResourceNotFoundException {
        Employee found = searchEmployee(id);
        return EmployeeResponse.builder()
                .message(String.format("Employee with id " + id + "found"))
                .employee(found).build();
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest employeeRequest) throws ResourceNotFoundException, JsonPatchException, IOException {
        Employee found = searchEmployee(id);
        String json = objectMapper.writeValueAsString(employeeRequest);
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonMergePatch patch = JsonMergePatch.fromJson(jsonNode);
        Employee foundPatched = applyToEmployee(patch, found);

        var updateFoundEmployee = employeeRepository.save(foundPatched);
        return EmployeeResponse.builder()
                .message("Employee " + found.getFirstName() + " details updated")
                .employee(updateFoundEmployee)
                .build();
    }

    private Employee applyToEmployee(JsonMergePatch patch, Employee targetEmployee) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetEmployee, JsonNode.class));
        return objectMapper.treeToValue(patched, Employee.class);
    }

    @Override
    public Map<String, Boolean> deleteEmployee(Long id) throws ResourceNotFoundException {
        searchEmployee(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    private Employee searchEmployee(Long id) throws ResourceNotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist."));
    }
}
