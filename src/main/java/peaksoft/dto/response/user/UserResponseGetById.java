package peaksoft.dto.response.user;

import lombok.Builder;
import peaksoft.enums.Role;

import java.time.LocalDate;
@Builder
public record UserResponseGetById(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String phoneNumber,
        String email,
        Role role
) {
}
