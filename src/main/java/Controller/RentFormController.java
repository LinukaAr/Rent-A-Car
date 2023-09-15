package Controller;


/*
   @author Linuka
   @created 9/2/23 - 1:27 PM
*/


import dto.tm.RentTm;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import db.DbConnection;
import dto.Rent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RentFormController {




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
        highlightOverdueRents();

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
    }

    private void highlightOverdueRents() {
        TableColumn<RentTm, LocalDate> returnDateColumn = new TableColumn<>("Return Date");
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        returnDateColumn.setCellFactory(columnData -> new TableCell<RentTm, LocalDate>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);

                // Check if the cell's row is associated with an overdue rent
                boolean isOverdue = false;
                if (!empty && getTableRow() != null) {
                    RentTm rent = (RentTm) getTableRow().getItem();
                    if (rent != null && rent.getReturnDate() != null) {
                        LocalDate returnDateStr = rent.getReturnDate();
                        LocalDate returnDate = rent.getReturnDate();


                        isOverdue = returnDate.isBefore(LocalDate.now());
                    }
                }

                if (isOverdue) {
                    setStyle("-fx-background-color: red; -fx-text-fill: white;");
                } else {
                    setStyle(""); // Reset the style
                }

                if (!empty && item != null) {
                    setText(item.toString());
                } else {
                    setText(null);
                }
            }
        });

        tblItem.getColumns().add(returnDateColumn);
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
            LocalDate returnDate = LocalDate.parse(resultSet.getString(6));


            var rent = new Rent(carId, description, rentalPrice, qtyOnHand, customerName, returnDate);




            itemList.add(rent);
        }


        return itemList;
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String carId = txtId.getText();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtRentalPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQuantityOnHand.getText());
        String customerName = txtCustomerName.getText();
        LocalDate returnDate = txtReturnDate.getValue();




        try {
            Connection con = DbConnection.getInstance().getConnection();


            String sql = "INSERT INTO rent VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, carId);
            pstm.setString(2, description);
            pstm.setDouble(3, unitPrice);
            pstm.setInt(4, qtyOnHand);
            pstm.setString(5, customerName);
            pstm.setObject(6, java.sql.Date.valueOf(returnDate));




            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "rent saved!").show();
            }


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }




    }


    @FXML
    void txtCodeOnAction(ActionEvent event) {
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
                LocalDate returnDate = LocalDate.parse(resultSet.getString(6));


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
        txtReturnDate.setValue(rent.getReturnDate());
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
    }






    public void btnReturnOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/rent_return_form.fxml"));
        this.root.getChildren().clear();
        this.root.getChildren().add(root);
    }


    public void btnClearOnAction(ActionEvent actionEvent) {
        txtId.setText("");
        txtDescription.setText("");
        txtQuantityOnHand.setText("");
        txtRentalPrice.setText("");
        txtCustomerName.setText("");
        txtReturnDate.setValue(LocalDate.parse(""));
    }
}
