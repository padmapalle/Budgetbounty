package com.financialapp.dto;

<<<<<<< HEAD
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

=======
import lombok.*;


>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
<<<<<<< HEAD

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    private Boolean appAdmin;

    @Min(value = 0, message = "Points cannot be negative")
=======
    private String email;
    private Boolean appAdmin;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
    private Integer points;
}
