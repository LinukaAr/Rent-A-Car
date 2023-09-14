package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car {

    private String carId;
    private String carModel;
    private String carType;
    private double carRental;
    private String CarNumber;
    private int qntyOnHand;

}