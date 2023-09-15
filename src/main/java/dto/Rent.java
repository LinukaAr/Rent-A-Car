package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rent {
    private String carId;
    private String description;
    private double rentalPrice;
    private int qtyOnHand;
    private String customerName;
    private LocalDate returnDate;
}