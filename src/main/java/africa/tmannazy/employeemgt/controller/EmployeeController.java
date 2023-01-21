package africa.tmannazy.employeemgt.controller;

import africa.tmannazy.employeemgt.data.dtos.request.EmployeeRequest;
import africa.tmannazy.employeemgt.exceptions.ResourceNotFoundException;
import africa.tmannazy.employeemgt.services.interfaces.EmployeeService;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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

    @PatchMapping(value = "/employees/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest request) throws ResourceNotFoundException, JsonPatchException, IOException, JsonPointerException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.deleteEmployee(id));
    }
}
