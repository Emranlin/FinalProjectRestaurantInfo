package peaksoft.dto.response.cheque;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChequeResponseSumPerDay {
    private String fullName;
    private int average;
}