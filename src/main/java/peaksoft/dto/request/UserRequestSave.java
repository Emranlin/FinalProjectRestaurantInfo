package peaksoft.dto.request;



import jakarta.validation.constraints.*;
import lombok.Builder;

import peaksoft.enums.Role;


import java.time.LocalDate;
@Builder
public record UserRequestSave(

        @Pattern(regexp = "^[a-zA-Z]{5,20}$", message = "FirstName can only contain letters and must be between 5 and 20 characters long")

        String firstName,
        @Pattern(regexp = "^[a-zA-Z]{5,20}$", message = "LastName can only contain letters and must be between 5 and 20 characters long")

        String lastName,
        //@Min(value = 0, message = "Age must be a positive value")
        LocalDate dateOfBirth,
        @Email
        String email,
        @Digits(integer = 13, fraction = 0, message = "Phone number must have exactly 13 digits")

        String phoneNumber,
        int experience,
        @Size(min = 4, message = "Password must be at least 8 characters long")
        String password,
        Role role
) {
}
