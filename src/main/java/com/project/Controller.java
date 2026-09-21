package com.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField txtNum1;

    @FXML
    private TextField txtNum2;

    @FXML
    private Label lblResultat;

    @FXML
    private void operar(ActionEvent event) {
        try {
            double num1 = Double.parseDouble(txtNum1.getText());
            double num2 = Double.parseDouble(txtNum2.getText());
            double resultat = 0;

            Button btn = (Button) event.getSource();
            String operacio = btn.getText();

            switch (operacio) {
                case "+":
                    resultat = num1 + num2;
                    break;
                case "-":
                    resultat = num1 - num2;
                    break;
                case "*":
                    resultat = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        lblResultat.setText("Error: Divisió per zero");
                        return;
                    }
                    resultat = num1 / num2;
                    break;
            }

            lblResultat.setText("Resultat: " + resultat);

        } catch (NumberFormatException e) {
            lblResultat.setText("Error: Introdueix números vàlids");
        }
    }
}