package login;
import funcionario.FuncionarioController;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import menu.MenuBoundary;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoginBoundary extends Application {

    private TextField password = new TextField();
    private TextField txtEmail = new TextField();
    private PasswordField txtSenha = new PasswordField();
    private CheckBox check = new CheckBox();
    private Button btnEntrar = new Button("Entrar");
    private LoginController logControl = new LoginController();
    private FuncionarioController adm = new FuncionarioController();
    private Alert alertWarn = new Alert(Alert.AlertType.WARNING);
    private Alert alertMess = new Alert(Alert.AlertType.INFORMATION);
    private boolean permitido = false;
    private MenuBoundary menuTela = new MenuBoundary();
    public static void main(String[] args) {
        Application.launch(LoginBoundary.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pPane = new Pane();
        pPane.getStyleClass().add("fundo");
        btnEntrar.setStyle("-fx-background-color: #3b5998; -fx-background-radius: 20; -fx-text-fill: #fff; " +
                "-fx-font-family: \"Arial\";\n" +
                "-fx-font-size: 14pt; -fx-padding: 10 20;");
        //Image img = new Image("img/Logo.png");
        //ImageView imgLogo = new ImageView(img);
        //imgLogo.relocate(200, 15);
        //imgLogo.setFitHeight ( 320 );
        //imgLogo.setFitWidth ( 300 );

        pPane.getStylesheets().add(LoginBoundary.class.getResource("LoginStyle.css").toExternalForm());
        Scene scCeneLogin = new Scene(pPane, 450, 590);

        adm.admin();

        Label lblEmail = new Label("E-MAIL");
        Label lblSenha = new Label("SENHA");
        Label lblChek = new Label("Mostrar Senha");

        //colocando os itens no login e deixando bonito com css
        lblEmail.relocate(200, 225);
        txtEmail.relocate(125, 250);
        txtEmail.setStyle("-fx-background-color: rgb(255, 255, 255);; -fx-border-color: rgb(127, 118, 104); -" +
                "fx-background-radius: 4; -fx-border-radius: 12; -fx-padding: 10;" +
                "fx-box-sizing: border-box");
        lblSenha.relocate(200, 325);
        txtSenha.setStyle("-fx-background-color: rgb(255, 255, 255);; -fx-border-color: rgb(127, 118, 104); -" +
                "fx-background-radius: 4; -fx-border-radius: 12; -fx-padding: 10;" +
                "fx-box-sizing: border-box");
        txtSenha.relocate(125, 350);

        lblChek.relocate(180,420);
        check.relocate(150,420);

        //verificação se está selecionado o mostrar senha
        check.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val)->{
            if(check.isSelected()){
                password.setText(txtSenha.getText());
                password.setVisible(true);
                txtSenha.setVisible(false);
                return;
            }
            txtSenha.setText(password.getText());
            txtSenha.setVisible(true);
            password.setVisible(false);
        });
        password.setVisible(false);
        password.relocate(125,350);
        btnEntrar.relocate(190, 470);
        pPane.getChildren().addAll( lblEmail, txtEmail, btnEntrar, lblSenha, txtSenha, check, lblChek, password);

        //verificando Login
        btnEntrar.setOnAction((e) -> {
            permitido = logControl.validarLogin(boundaryToEntity());

            if(permitido){
                try {
                    String nvnPerm = logControl.getPermissao();
                    Stage stage = new Stage();
                    primaryStage.close();
                    menuTela.start(stage);
                    menuTela.menuComum(nvnPerm);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else{
                alertWarn.setHeaderText("Dados Incorretos");
                alertWarn.showAndWait();
            }
        });

        primaryStage.setScene(scCeneLogin);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public Login boundaryToEntity() {
        Login Lg = new Login();
        Lg.setEmail(txtEmail.getText());
        Lg.setSenha(txtSenha.getText());
        return Lg;
    }
}