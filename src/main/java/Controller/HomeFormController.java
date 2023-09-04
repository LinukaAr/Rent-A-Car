package Controller;
/*
    @author Linuka
    @created 9/2/23 - 11:43 AM
*/

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;



import javax.print.attribute.standard.Media;
import java.io.IOException;

public class HomeFormController {


    public AnchorPane rootNode;
    public AnchorPane node;


    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));


        this.node.getChildren().clear();
        this.node.getChildren().add(root);
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/home_form.fxml"));


        this.node.getChildren().clear();
        this.node.getChildren().add(root);


        Stage stage = (Stage) this.node.getScene().getWindow();
        stage.setTitle("Home");
    }






    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/item_form.fxml"));

        this.node.getChildren().clear();
        this.node.getChildren().add(root);




    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/supplier_form.fxml"));

        this.node.getChildren().clear();
        this.node.getChildren().add(root);

    }

    public void btnDashboard(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/home_form.fxml"));
            Parent rootNode = loader.load();

            Scene scene = new Scene(rootNode);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);

            stage.setTitle("Home");
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately, such as showing an error message.
        }
    }




}