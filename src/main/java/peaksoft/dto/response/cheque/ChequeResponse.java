package peaksoft.dto.response.cheque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.entity.MenuItem;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChequeResponse {
    private String fullName;
    private int averagePrice;
    private int service;
    private BigDecimal grandTotal;
    private List<MenuItem>menuItems;

    public ChequeResponse(String fullName, int averagePrice, int service, BigDecimal grandTotal) {
        this.fullName = fullName;
        this.averagePrice = averagePrice;
        this.service = service;
        this.grandTotal = grandTotal;
    }
}
