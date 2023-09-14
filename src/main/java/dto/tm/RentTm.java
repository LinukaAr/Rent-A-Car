package dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*@Getter
@Setter
@ToString*/
public class RentTm {   //lombok
    private String carId;
    private String description;
    private double rentalPrice;
    private int qtyOnHand;
    private String customerName;
    private String returnDate;
}
