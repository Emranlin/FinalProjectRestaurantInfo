package peaksoft.dto.response.user;

import lombok.Builder;

@Builder
public record UserResponse(
        String email,
        String token

) {
}
