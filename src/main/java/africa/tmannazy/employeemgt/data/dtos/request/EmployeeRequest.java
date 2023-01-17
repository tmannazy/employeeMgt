package africa.tmannazy.employeemgt.data.dtos.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    private String firstName;
    private String lastName;
    private String emailId;
}
