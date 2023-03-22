package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record UserLoginRequest(
        String email,
        String password
) {
}
