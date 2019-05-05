package forMainClass;

import javax.swing.*;

import uiCalcColor.Calculator;
import uiCalcColor.ColorPicker;

public class Start {
    public static void main(String[] args) {
        JFrame colorPicker = new ColorPicker();
        colorPicker.setVisible(true);

        JFrame calculator = new Calculator();
        calculator.setVisible(true);
    }
}
