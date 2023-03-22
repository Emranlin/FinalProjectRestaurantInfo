package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.cheque.ChequeResponse;
import peaksoft.dto.response.cheque.ChequeResponseSumPerDay;
import peaksoft.dto.response.cheque.ChequeRestaurantAverage;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.ChequeService;

@RestController
@RequestMapping("/cheques")
public class ChequeApi {
    private final ChequeService chequeService;

    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")

    @PostMapping("/{menuItemId}")
    public SimpleResponse saveCheque(@PathVariable Long menuItemId, @RequestBody ChequeRequest chequeRequest) {
        return chequeService.saveCheque(menuItemId, chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/{chequeId}")
    public ChequeResponse getById(@PathVariable Long chequeId) {
        return chequeService.getByIdCheque(chequeId);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PutMapping("/{chequeId}")
    public SimpleResponse updateCheque(@PathVariable Long chequeId, @RequestBody ChequeRequest chequeRequest) {
        return chequeService.updateCheque(chequeId, chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @DeleteMapping("/{chequeId}")
    public SimpleResponse deleteCheque(@PathVariable Long chequeId) {
        return chequeService.deleteCheque(chequeId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public ChequeResponseSumPerDay countWaiterCheque(@PathVariable Long userId) {
        return chequeService.countWaiterCheque(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public ChequeRestaurantAverage averageChequeRestaurant() {
        return chequeService.averageChequeRestaurant();
    }
}
