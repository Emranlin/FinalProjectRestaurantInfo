package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.cheque.ChequeResponse;
import peaksoft.dto.response.cheque.ChequeResponseSumPerDay;
import peaksoft.dto.response.cheque.ChequeRestaurantAverage;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@RequestMapping("/cheques")
public class ChequeApi {
    private final ChequeService chequeService;

    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")

    @PostMapping("/{userId}")
    public SimpleResponse saveCheque(@PathVariable Long userId, @RequestBody ChequeRequest chequeRequest) {
        return chequeService.saveCheque(userId, chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/{chequeId}")
    public ChequeResponse getById(@PathVariable Long chequeId) {
        return chequeService.getByIdCheque(chequeId);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PutMapping("/{chequeId}")
    public SimpleResponse updateCheque(
            @RequestParam Long userId,
            @PathVariable Long chequeId, @RequestBody ChequeRequest chequeRequest) {
        return chequeService.updateCheque(chequeId, chequeRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @DeleteMapping("/{chequeId}")
    public SimpleResponse deleteCheque(@PathVariable Long chequeId) {
        return chequeService.deleteCheque(chequeId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/count/{userId}")
    public ChequeResponseSumPerDay countWaiterCheque(@PathVariable Long userId,@RequestParam LocalDate date) {
        return chequeService.countWaiterCheque(userId,date);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/avg")
    public SimpleResponse avg(@RequestParam(required = false) LocalDate date){
        return chequeService.averageChequeRestaurant(Objects.requireNonNullElseGet(date, LocalDate::now));
    }
}
