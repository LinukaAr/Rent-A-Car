package dto.tm;
import dto.Car;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarTm extends Car {
    private String carId;
    private String carModel;
    private String carType;
    private double carRental;
    private String CarNumber;
    private int qntyOnHand;
}
