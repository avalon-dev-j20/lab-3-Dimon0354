package uiCalcColor;


import calculator.*;
import forMainClass.*;
import str.WindowAction;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class Calculator extends WindowAction {
	
	   private class ComponentEventListener extends ComponentAdapter {


	        @Override
	        public void componentResized(ComponentEvent e) {
	        	
	            int width = getWidth();
	            int height = getHeight();
	            
	            if (width < MIN_WINDOW_WIDTH) {
	            	
	                width = MIN_WINDOW_WIDTH;
	                
	            } else if (width > MAX_WINDOW_WIDTH) {
	            	
	                width = MAX_WINDOW_WIDTH;
	                
	            }
	            if (height < MIN_WINDOW_HEIGHT) {
	            	
	                height = MIN_WINDOW_HEIGHT;
	                
	            } else if (height >= MAX_WINDOW_HEIGHT) {
	            	
	                height = MAX_WINDOW_HEIGHT;
	                
	            }
	            setSize(width, height);
	        }
	    }

	    private CalculatorMain calculator = new CalculatorMain();
	    private Builder<Double> numberBuilder = new WorkWithNumbers();

	    private JButton[] numberButtons = new JButton[10];
	    private List<JButton> operationButtons = new ArrayList<>();

	    private JButton equallyButton = new JButton("=");
	    private JButton separatorButton = new JButton(".");
	    private JButton resetButton = new JButton("CE");

	    private JLabel label = new JLabel();

	    private Toolkit toolkit = Toolkit.getDefaultToolkit();
	    private Clipboard clipboard = toolkit.getSystemClipboard();

	    private boolean resultObtained = false;

	    public static final int GRIDLAYOUT_VERTICAL_INDENT = 10;
	    public static final int GRIDLAYOUT_HORIZONTAL_INDENT = 10;
	    public static final int BORDER_LINE_THICKNESS = 10;
	    public static final int GRIDLAYOUT_MAIN_PANEL_ROWS = 6;
	    public static final int GRIDLAYOUT_MAIN_PANEL_COLS = 1;
	    public static final int MIN_WINDOW_WIDTH = 400;
	    public static final int MIN_WINDOW_HEIGHT = 500;
	    public static final int MAX_WINDOW_WIDTH = 800;
	    public static final int MAX_WINDOW_HEIGHT = 900;
	    public static final int DIGITS_FONT_SIZE = 50;
	    public static final int BUTTON_LABEL_FONT_SIZE = 20;
	    public static final String START_DIGIT = "0";

//Тут создаются все объекты окна
	    @Override
	    protected void onCreate() {
	        initializeNumberButton(numberButtons);
	        initializeOperationListButton(operationButtons);
	        customizeLabel(label);
	        addComponentListener(new ComponentEventListener());
	        setListenersForButtons();
	        setTitle("Calculator");
	        setSize(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
	        setLayout(new GridLayout(
	                GRIDLAYOUT_MAIN_PANEL_ROWS,
	                GRIDLAYOUT_MAIN_PANEL_COLS,
	                GRIDLAYOUT_HORIZONTAL_INDENT,
	                GRIDLAYOUT_VERTICAL_INDENT));
	        JPanel mainPanel = (JPanel) getContentPane();
	        Border border = BorderFactory.createLineBorder(getBackground(), BORDER_LINE_THICKNESS);
	        mainPanel.setBorder(border);
	        add(label);
	        add(createSingleLinePanel(numberButtons[7], numberButtons[8], numberButtons[9], operationButtons.get(0)));
	        add(createSingleLinePanel(numberButtons[4], numberButtons[5], numberButtons[6], operationButtons.get(1)));
	        add(createSingleLinePanel(numberButtons[1], numberButtons[2], numberButtons[3], operationButtons.get(2)));
	        add(createSingleLinePanel(resetButton, numberButtons[0], separatorButton, operationButtons.get(3)));
	        add(createSingleLinePanel(equallyButton));
	    }

//Задается где будет отображаться результат
	    private void customizeLabel(JLabel label) {
	        updateResult(START_DIGIT);
	        label.setVerticalAlignment(JLabel.BOTTOM);
	        label.setHorizontalAlignment(JLabel.RIGHT);
	        label.setFont(new Font(null, Font.BOLD, DIGITS_FONT_SIZE));
	    }

//Книпочки
	    private void initializeNumberButton(JButton[] buttons) {
	        for (int i = 0; i < 10; i++) {
	            buttons[i] = new JButton(Integer.toString(i));
	        }
	    }

//Помоему и так понятно
	    private void initializeOperationListButton(List<JButton> buttons) {
	        for (OperationList operation : OperationList.values()) {
	            buttons.add(new JButton(operation.getTitl()));
	        }
	    }

//Действие если нажали на разделитель итд
	    private void onNumberButtonClick(ActionEvent e) {
	        numberBuilder.addNumber(e.getActionCommand());
	        updateResult(numberBuilder.toString());
	    }
	    
	 // вычисление результата
	    private void calculate() {
	        if (resultObtained) return;
	        setCalculatorArguments();
	        updateResult(calculator.calculation());
	        resultObtained = true;
	    }

//действие при нажатии на "арифметические операции"
	    private void onOperationButtonClick(ActionEvent e) {
	        calculate();
	        for (OperationList operation : OperationList.values()) {
	            if (e.getActionCommand().equals(operation.getTitl())) {
	                calculator.setOperation(operation);
	            }
	        }
	        resultObtained = false;
	    }

//равно
	    private void onEquallyButton(ActionEvent e) {
	        resultObtained = false;
	        calculate();
	    }

//сброс
	    private void onResetButton(ActionEvent e) {
	        resultObtained = false;
	        calculator.valuesReset();
	        numberBuilder.deleteNumber();
	        updateResult(START_DIGIT);
	    }

//значение аргументов, для этого и нужен CalculatorMain(и не только)
	    private void setCalculatorArguments() {
	        if (numberBuilder.emptyValueOfNumberOrNot()) return;
	        if (calculator.getFistArgument() == null) {
	            calculator.setfistArgument(numberBuilder.getNumber());
	        } else {
	            calculator.setsecondArgument(numberBuilder.getNumber());
	        }
	        numberBuilder.deleteNumber();
	    }

//тут отслеживаются все кнопки калькулятора
	    private void setListenersForButtons() {
	        for (JButton button : numberButtons) {
	            button.addActionListener(this::onNumberButtonClick);
	        }
	        for (JButton button : operationButtons) {
	            button.addActionListener(this::onOperationButtonClick);
	        }
	        separatorButton.addActionListener(this::onNumberButtonClick);
	        equallyButton.addActionListener(this::onEquallyButton);
	        resetButton.addActionListener(this::onResetButton);
	    }

// Создает однострочную панель из компонент передаваемых на вход
	    private JPanel createSingleLinePanel(Component... components) {
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(1, components.length, GRIDLAYOUT_VERTICAL_INDENT, GRIDLAYOUT_HORIZONTAL_INDENT));
	        for (Component comp : components) {
	            comp.setFont(new Font(null, Font.BOLD, BUTTON_LABEL_FONT_SIZE));
	            panel.add(comp);
	        }
	        return panel;
	    }

//Строку " String text " в буфер обмена
	    private void copyToClipboard(String text) {
	        StringSelection selection = new StringSelection(text);
	        clipboard.setContents(selection, selection);
	    }

//обновление результата
	    private void updateResult(String text) {
	        label.setText(text);
	        copyToClipboard(text);
	    }
	    
}
