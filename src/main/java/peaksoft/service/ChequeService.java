package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.cheque.ChequeResponse;
import peaksoft.dto.response.cheque.ChequeResponseSumPerDay;
import peaksoft.dto.response.cheque.ChequeRestaurantAverage;
import peaksoft.dto.response.SimpleResponse;

import java.time.LocalDate;
import java.util.List;

public interface ChequeService {
    SimpleResponse saveCheque(Long userId,ChequeRequest chequeRequest);
    ChequeResponse getByIdCheque(Long chequeId);
    SimpleResponse deleteCheque(Long chequeId);
    SimpleResponse updateCheque(Long chequeId,ChequeRequest chequeRequest);
    ChequeResponseSumPerDay countWaiterCheque(Long userId, LocalDate date);
    SimpleResponse averageChequeRestaurant(LocalDate date);

}
