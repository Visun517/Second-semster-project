package lk.ijse.gdse71.final_project.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDto {
    private String adminId;
    private String userName;
    private String email;
    private String password;
    private String role;
}
