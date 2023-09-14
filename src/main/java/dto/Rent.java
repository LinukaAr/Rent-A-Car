package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Rent {
    private String carId;
    private String description;
    private double rentalPrice;
    private int qtyOnHand;
    private String customerName;
    private String returnDate;
}