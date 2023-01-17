package africa.tmannazy.employeemgt.controller;

import africa.tmannazy.employeemgt.data.dtos.request.EmployeeRequest;
import africa.tmannazy.employeemgt.exceptions.ResourceNotFoundException;
import africa.tmannazy.employeemgt.services.interfaces.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }


    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(request));
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployeeById(id));
    }

    @PatchMapping(value = "/employees/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) throws ResourceNotFoundException, JsonPatchException, JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.deleteEmployee(id));
    }
}
