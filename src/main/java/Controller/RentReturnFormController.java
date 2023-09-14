package Controller;

import db.DbConnection;
import dto.Rent;
import dto.tm.RentTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentReturnFormController {

    public AnchorPane root;
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQuantityOnHand;

    @FXML
    private TextField txtRentalPrice;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private DatePicker txtReturnDate;


    @FXML
    private TableColumn<?, ?> colCarId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colRentalPrice;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableView<RentTm> tblItem;

    public void initialize() throws SQLException {
        System.out.println("Rent Form Just Loaded!");

        setCellValueFactory();
        List<Rent> itemList = loadAllItems();

        setTableData(itemList);
    }

    private void setTableData(List<Rent> rentList) {
        ObservableList<RentTm> obList = FXCollections.observableArrayList();

        for (Rent rent : rentList) {
            var tm = new RentTm(rent.getCarId(), rent.getDescription(), rent.getRentalPrice(), rent.getQtyOnHand(), rent.getCustomerName(), rent.getReturnDate());
            obList.add(tm);
        }
        tblItem.setItems(obList);
    }

    private void setCellValueFactory() {
        colCarId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colRentalPrice.setCellValueFactory(new PropertyValueFactory<>("rentalPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }

    private List<Rent> loadAllItems() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM rent";
        Statement stm = con.createStatement();

        ResultSet resultSet = stm.executeQuery(sql);

        List<Rent> itemList = new ArrayList<>();

        while (resultSet.next()) {
            String carId = resultSet.getString(1);
            String description = resultSet.getString(2);
            double rentalPrice = resultSet.getDouble(3);
            int qtyOnHand = resultSet.getInt(4);
            String customerName = resultSet.getString(5);
            String returnDate = resultSet.getString(6);

            var rent = new Rent(carId, description, rentalPrice, qtyOnHand, customerName, returnDate);


            itemList.add(rent);
        }

        return itemList;
    }


    public void txtCodeOnAction(ActionEvent actionEvent) {
        String carId = txtId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM rent WHERE carId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, carId);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                carId = resultSet.getString(1);
                String description = resultSet.getString(1);
                double rentalPrice = resultSet.getDouble(3);
                int qtyOnHand = resultSet.getInt(4);
                String customerName = resultSet.getString(5);
                String returnDate = resultSet.getString(5);

                //since JDK11
                var rent = new Rent(carId, description, rentalPrice, qtyOnHand, customerName, returnDate);


                setFields(rent);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFields(Rent rent) {
        txtId.setText(rent.getCarId());
        txtDescription.setText(rent.getDescription());
        txtRentalPrice.setText(String.valueOf(rent.getRentalPrice()));
        txtQuantityOnHand.setText(String.valueOf(rent.getQtyOnHand()));
        txtCustomerName.setText(String.valueOf(rent.getCustomerName()));
        txtReturnDate.setValue(LocalDate.parse(rent.getReturnDate()));
    }

    public void btnReturnOnAction(ActionEvent actionEvent) {
        String carId = txtId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "DELETE FROM rent WHERE carId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, carId);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Rent Returned!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();
        }
    }
}