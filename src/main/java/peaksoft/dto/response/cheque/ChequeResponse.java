package peaksoft.dto.response.cheque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.dto.response.menuItem.MenuItemForCheque;
import peaksoft.entity.MenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChequeResponse {
    private Long id;
    private String fullName;
    private List<MenuItemForCheque> meals;
    private int averagePrice;
    private int service;
    private BigDecimal grandTotal;

    public ChequeResponse(Long id, String fullName, int averagePrice, int service, BigDecimal grandTotal) {
        this.id = id;
        this.fullName = fullName;
        this.averagePrice = averagePrice;
        this.service = service;
        this.grandTotal = grandTotal;
    }

}
