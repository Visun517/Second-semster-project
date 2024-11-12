package lk.ijse.gdse71.final_project.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminTm {
    private String adminId;
    private String userName;
    private String email;
    private String password;
    private String role;
}
