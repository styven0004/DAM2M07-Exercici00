package com.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label labelDisplay;

    // Valor acumulat de l'operació anterior
    private double acumulat = 0;

    // Operador pendent d'aplicar ('+', '-', '*', '/') o null si no n'hi ha cap
    private String operadorPendent = null;

    // Indica si l'usuari està escrivint un número nou (true) o si cal
    // començar a escriure'n un de nou perquè s'acaba de prémer un operador
    private boolean comencarNumeroNou = true;

    @FXML
    private void actionNumber(ActionEvent event) {
        Button botoPremut = (Button) event.getSource();
        String digit = botoPremut.getText();

        if (comencarNumeroNou) {
            labelDisplay.setText(digit.equals("0") ? "0" : digit);
            comencarNumeroNou = false;
        } else {
            // Evita tenir múltiples zeros a l'esquerra
            if (labelDisplay.getText().equals("0")) {
                labelDisplay.setText(digit);
            } else {
                labelDisplay.setText(labelDisplay.getText() + digit);
            }
        }
    }

    @FXML
    private void actionDecimal(ActionEvent event) {
        if (comencarNumeroNou) {
            labelDisplay.setText("0,");
            comencarNumeroNou = false;
        } else if (!labelDisplay.getText().contains(",")) {
            labelDisplay.setText(labelDisplay.getText() + ",");
        }
    }

    @FXML
    private void actionOperator(ActionEvent event) {
        Button botoPremut = (Button) event.getSource();
        String nouOperador = botoPremut.getText();

        // Si ja hi havia una operació pendent, la resolem abans de continuar
        if (operadorPendent != null && !comencarNumeroNou) {
            calcularResultat();
        } else {
            acumulat = parseDisplay();
        }

        operadorPendent = nouOperador;
        comencarNumeroNou = true;
    }

    @FXML
    private void actionEquals(ActionEvent event) {
        if (operadorPendent != null) {
            calcularResultat();
            operadorPendent = null;
            comencarNumeroNou = true;
        }
    }

    @FXML
    private void actionClear(ActionEvent event) {
        acumulat = 0;
        operadorPendent = null;
        comencarNumeroNou = true;
        labelDisplay.setText("0");
    }

    @FXML
    private void actionBackspace(ActionEvent event) {
        String textActual = labelDisplay.getText();
        if (comencarNumeroNou || textActual.length() <= 1) {
            labelDisplay.setText("0");
            comencarNumeroNou = true;
        } else {
            labelDisplay.setText(textActual.substring(0, textActual.length() - 1));
        }
    }

    @FXML
    private void actionSign(ActionEvent event) {
        double valorActual = parseDisplay();
        valorActual = -valorActual;
        labelDisplay.setText(formatNumero(valorActual));
    }

    // Aplica l'operador pendent entre l'acumulat i el valor mostrat
    private void calcularResultat() {
        double valorActual = parseDisplay();
        double resultat;

        switch (operadorPendent) {
            case "+":
                resultat = acumulat + valorActual;
                break;
            case "-":
                resultat = acumulat - valorActual;
                break;
            case "×":
                resultat = acumulat * valorActual;
                break;
            case "÷":
                if (valorActual == 0) {
                    labelDisplay.setText("Error");
                    acumulat = 0;
                    comencarNumeroNou = true;
                    return;
                }
                resultat = acumulat / valorActual;
                break;
            default:
                resultat = valorActual;
        }

        acumulat = resultat;
        labelDisplay.setText(formatNumero(resultat));
    }

    // Converteix el text de la pantalla (amb coma decimal) a double
    private double parseDisplay() {
        String text = labelDisplay.getText().replace(",", ".");
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Formata el número per mostrar-lo sense decimals innecessaris
    private String formatNumero(double valor) {
        if (valor == Math.rint(valor) && !Double.isInfinite(valor)) {
            return String.valueOf((long) valor);
        }
        String text = String.valueOf(valor);
        return text.replace(".", ",");
    }
}
