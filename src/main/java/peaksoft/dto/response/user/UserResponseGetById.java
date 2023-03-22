package peaksoft.dto.response.user;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record UserResponseGetById(
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String phoneNumber
) {
}
