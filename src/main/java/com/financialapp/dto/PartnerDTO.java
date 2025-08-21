package com.financialapp.dto;

<<<<<<< HEAD
<<<<<<< HEAD
import java.util.function.IntPredicate;

=======
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
import java.util.function.IntPredicate;

>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDTO {
    private Integer partnerId;
    private String name;
    private String apiKey;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
    // Remove email, status, and id fields since they weren't in your original
    // These methods suggest they might be handled differently
    public IntPredicate getEmail() {
        return null;
    }
    
    public IntPredicate getStatus() {
        return null;
    }
    
    public IntPredicate getId() {
        return null;
    }
    
    public void setId(Integer id) {
    }
    
    public void setEmail(String email) {
    }
    
    public void setStatus(String status) {
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 3212540b9f8b79d2607519db820b5cd72cd061e4
=======
}
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
