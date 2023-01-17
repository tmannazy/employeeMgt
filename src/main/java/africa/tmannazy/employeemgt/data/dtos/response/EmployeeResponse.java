package africa.tmannazy.employeemgt.data.dtos.response;

import africa.tmannazy.employeemgt.data.model.Employee;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private String message;
    private Employee employee;
}
