package peaksoft.dto.response.user;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record UserResponseGetAll(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber

) {
}
