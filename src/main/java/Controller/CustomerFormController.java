package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class CustomerFormController {
    public TextField txtCustomerId;
    public TextField txtCustomerName;
    public TextField txtCustomerNic;
    public TextField txtCustomerTel;
    public TextField txtCustomerAddress;


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();
        String customerName = txtCustomerName.getText();
        String customerNic = txtCustomerNic.getText();
        String customerTel = txtCustomerTel.getText();
        String customerAddress = txtCustomerAddress.getText();


        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "INSERT INTO customer VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setString(1, customerId);
            pstm.setString(2, customerName);
            pstm.setString(3, customerNic);
            pstm.setString(4, customerTel);
            pstm.setString(5, customerAddress);

            boolean isSaved = pstm.executeUpdate() > 0;

            if(isSaved) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerNic.setText("");
        txtCustomerTel.setText("");
        txtCustomerAddress.setText("");
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    public void txtIdOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "SELECT * FROM customer WHERE customerId=?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, customerId);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()) {
                String txtCustomerId = resultSet.getString(1);
                String txtCustomerName = resultSet.getString(2);
                String txtCustomerNic = resultSet.getString(3);
                String txtCustomerTel = resultSet.getString(4);
                String txtCustomerAddress = resultSet.getString(5);

                setFields(txtCustomerId, txtCustomerName, txtCustomerNic, txtCustomerTel, txtCustomerAddress);
            } else {
                new Alert(Alert.AlertType.WARNING, "customer not found!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void setFields(String txtCustomerId, String txtCustomerName, String txtCustomerNic, String txtCustomerTel, String txtCustomerAddress) {
        this.txtCustomerId.setText(txtCustomerId);
        this.txtCustomerName.setText(txtCustomerName);
        this.txtCustomerNic.setText(txtCustomerNic);
        this.txtCustomerTel.setText(txtCustomerTel);
        this.txtCustomerAddress.setText(txtCustomerAddress);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();
        String customerName = txtCustomerName.getText();
        String customerNic = txtCustomerNic.getText();
        String customerTel = txtCustomerTel.getText();
        String customerAddress = txtCustomerAddress.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "UPDATE customer SET customerName = ?, customerNic = ?, customerTel = ?, customerAddress = ? WHERE customerId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, customerName);
            pstm.setString(2, customerNic);
            pstm.setString(3, customerTel);
            pstm.setString(4, customerAddress);
            pstm.setString(5, customerId);

            boolean isUpdated = pstm.executeUpdate() > 0;
            if(isUpdated) {
                clearFields();
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String customerId = txtCustomerId.getText();

        try {
            Connection con = DbConnection.getInstance().getConnection();

            String sql = "DELETE FROM customer WHERE customerId = ?";

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, customerId);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.CONFIRMATION, e.getMessage()).show();
        }
    }
}
