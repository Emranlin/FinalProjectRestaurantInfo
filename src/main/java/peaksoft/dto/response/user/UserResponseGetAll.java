package peaksoft.dto.response.user;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record UserResponseGetAll(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String phoneNumber,
        int experience
) {
}
