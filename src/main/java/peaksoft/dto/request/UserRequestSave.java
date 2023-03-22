package peaksoft.dto.request;



import lombok.Builder;
import peaksoft.enums.Role;


import java.time.LocalDate;
@Builder
public record UserRequestSave(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        int experience,
        String password,
        Role role
) {
}
