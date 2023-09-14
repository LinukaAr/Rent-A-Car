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
//import javax.print.attribute.standard.Media;
import java.io.IOException;

public class HomeFormController {

    public AnchorPane rootNode;
    public AnchorPane node;


    public void btnCarOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/car_form.fxml"));


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






    public void  btnRentOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/rent_form.fxml"));

        this.node.getChildren().clear();
        this.node.getChildren().add(root);




    }

    public void btnCustomerOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/customer_form.fxml"));

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
            e.printStackTrace();
        }
    }


    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {

        Parent loginRoot = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));

        Stage loginStage = new Stage();
        loginStage.setTitle("Login Form");
        loginStage.setScene(new Scene(loginRoot));


        Stage currentStage = (Stage) node.getScene().getWindow();
        currentStage.close();


        loginStage.show();
    }
}