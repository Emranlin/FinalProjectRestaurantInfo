package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.stopList.StopListResponse;
import peaksoft.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/stopLists")
public class StopListApi {
    private final StopListService service;

    public StopListApi(StopListService service) {
        this.service = service;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PostMapping
    public SimpleResponse saveStopList(@RequestBody StopListRequest stopListRequest) {
        return service.saveStopList(stopListRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @GetMapping
    public List<StopListResponse> getAllStopList(){
        return service.getAllStopList();
    }
    @PermitAll
    @GetMapping("/{stopListId}")

    public StopListResponse getById(@PathVariable Long stopListId){
        return service.getByIdStopList(stopListId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @PutMapping("/{stopListId}")
    public SimpleResponse updateStoplist(@PathVariable Long stopListId,@RequestBody StopListRequest stopListRequest){
        return service.updateStopList(stopListId,stopListRequest);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    @DeleteMapping("/{stopListId}")
    public SimpleResponse deleteStopList(@PathVariable Long stopListId){
        return service.deleteStopList(stopListId);
    }
}
