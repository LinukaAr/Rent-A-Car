package Controller;

/*
    @author Linuka
    @created 9/3/23 - 5:28 PM
*/

import dto.tm.CarTm;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import db.DbConnection;
import dto.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarFormController {
    public AnchorPane root;
    @FXML
    private TextField txtCarId;

    @FXML
    private TextField txtCarModel;

    @FXML
    private TextField txtCarType;

    @FXML
    private TextField txtCarRental;

    @FXML
    private TextField txtCarNumber;

    @FXML
    private TextField txtQntyOnHand;


    @FXML
    private TableColumn<?, ?> colCarId;

    @FXML
    private TableColumn<?, ?> colCarModel;

    @FXML
    private TableColumn<?, ?> colCarType;

    @FXML
    private TableColumn<?, ?> colCarRental;

    @FXML
    private TableColumn<?, ?> colCarNumber;

    @FXML
    private TableColumn<?, ?> colCarqnty;

    @FXML
    private TableView<Car> tblItem;

    public void initialize() throws SQLException {
        System.out.println("Car Form Just Loaded!");

        setCellValueFactory();
        List<Car> itemList = loadAllItems();

        setTableData(itemList);
    }

    private void setTableData(List<Car> rentList) {
        ObservableList<Car> obList = FXCollections.observableArrayList();

        for(Car car : rentList) {
            var tm = new CarTm(car.getCarId(), car.getCarModel(), car.getCarType(), car.getCarRental(), car.getCarNumber(), car.getQntyOnHand());
            obList.add(tm);
        }
        tblItem.setItems(obList);
    }

    private void setCellValueFactory() {
        colCarId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        colCarModel.setCellValueFactory(new PropertyValueFactory<>("carModel"));
        colCarType.setCellValueFactory(new PropertyValueFactory<>("carType"));
        colCarRental.setCellValueFactory(new PropertyValueFactory<>("carRental"));
        colCarNumber.setCellValueFactory(new PropertyValueFactory<>("carNumber"));
        colCarqnty.setCellValueFactory(new PropertyValueFactory<>("qntyOnHand"));
    }

    private List<Car> loadAllItems() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM car";
        Statement stm = con.createStatement();

        ResultSet resultSet = stm.executeQuery(sql);

        List<Car> itemList = new ArrayList<>();

        while (resultSet.next()) {
            String carId = resultSet.getString(1);
            String carModel = resultSet.getString(2);
            String carType = resultSet.getString(3);
            double carRental = resultSet.getDouble(4);
            String CarNumber = resultSet.getString(5);
            int qntyOnHand = resultSet.getInt(6);



            var rent = new Car(carId, carModel, carType, carRental, CarNumber, qntyOnHand);


            itemList.add(rent);
        }

        return itemList;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String carId = txtCarId.getText();
        String carModel = txtCarModel.getText();
        String carType = txtCarType.getText();
        double carRental = Double.parseDouble(txtCarRental.getText());
        String CarNumber = txtCarNumber.getText();
        int qtyOnHand = Integer.parseInt(txtQntyOnHand.getText());





        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO car VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, carId);
            pstm.setString(2, carModel);
            pstm.setString(3, carType);
            pstm.setDouble(4, carRental);
            pstm.setString(5, CarNumber);
            pstm.setInt(6, qtyOnHand);





            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "car saved!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void txtCodeOnAction(ActionEvent event) {
        String carId = txtCarId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM car WHERE carId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, carId);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                carId = resultSet.getString(1);
                String carModel = resultSet.getString(2);
                String carType = resultSet.getString(3);
                double carRental = resultSet.getDouble(4);
                String CarNumber = resultSet.getString(5);
                int qntyOnHand = resultSet.getInt(6);



                //since JDK11
                var car = new Car(carId, carModel, carType, carRental, CarNumber, qntyOnHand);


                setFields(car);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFields(Car car) {
        txtCarId.setText(car.getCarId());
        txtCarModel.setText(car.getCarModel());
        txtCarType.setText(String.valueOf(car.getCarType()));
        txtCarRental.setText(String.valueOf(car.getCarRental()));
        txtCarNumber.setText(car.getCarNumber());
        txtQntyOnHand.setText(String.valueOf(car.getQntyOnHand()));



    }


    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String CarId = txtCarId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "DELETE FROM car WHERE CarId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, CarId);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "car deleted!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String carId = txtCarId.getText();
        String CarModel = txtCarModel.getText();
        String CarType = txtCarType.getText();
        double CarRental = Double.parseDouble(txtCarRental.getText());
        String carNumber = txtCarNumber.getText();
        int qntyOnHand = Integer.parseInt(txtQntyOnHand.getText());

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "UPDATE car SET CarModel = ?, CarType = ?, CarRental = ?, carNumber=?, qntyOnHand = ? WHERE carId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, CarModel);
            pstm.setString(2, CarType);
            pstm.setString(3, String.valueOf(CarRental));
            pstm.setString(4, carNumber);
            pstm.setString(5, String.valueOf(qntyOnHand));
            pstm.setString(6, carId);

            boolean isUpdated = pstm.executeUpdate() > 0;
            if(isUpdated) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "car updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {

            txtCarId.setText("");
            txtCarModel.setText("");
            txtCarType.setText("");
            txtCarRental.setText("");
            txtCarNumber.setText("");
            txtQntyOnHand.setText("");
        }
    }
