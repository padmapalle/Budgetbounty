package com.financialapp.dto;

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

<<<<<<< HEAD
=======
import lombok.*;


>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Integer userId;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    private Boolean appAdmin;

    @Min(value = 0, message = "Points cannot be negative")
<<<<<<< HEAD
=======
    private String email;
    private Boolean appAdmin;
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
    private Integer points;
}
