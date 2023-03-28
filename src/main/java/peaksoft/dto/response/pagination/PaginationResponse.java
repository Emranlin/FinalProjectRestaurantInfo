package peaksoft.dto.response.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.dto.response.user.UserResponseGetAll;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse {
    private List<UserResponseGetAll> userResponseGetAllList;
    private int currentPage;
    private int pageSize;
}
