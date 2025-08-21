package com.financialapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PARTNER")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partnerId;

    private String name;
    private String apiKey;
<<<<<<< HEAD

    // Add this method to satisfy Spring Data JPA
    public Integer getId() {
        return partnerId;
    }

    public static Object builder() {
        // TODO Auto-generated method stub
        return null;
    }
}
=======
	public static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}
}
>>>>>>> 2ac909d (Initial commit: Eclipse rewards system project)
