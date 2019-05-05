package uiCalcColor;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;

import str.WindowAction;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ColorPicker extends WindowAction {

	
    private class MouseEventListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            updateToolTip();
        }
    }

    private JPanel controlPanel = new JPanel();
    private JPanel colorPanel = new JPanel();

    private JSlider redSlider = new JSlider();
    private JSlider greenSlider = new JSlider();
    private JSlider blueSlider = new JSlider();
 
    private JLabel redLabel = new JLabel("Red:");
    private JLabel greenLabel = new JLabel("Green:");
    private JLabel blueLabel = new JLabel("Blue");

    private Toolkit toolkit = Toolkit.getDefaultToolkit();
    private Clipboard clipboard = toolkit.getSystemClipboard();

    public static final String TITLE = "Color Picker";
    public static final int SLIDER_MIN_VALUE = 0;
    public static final int SLIDER_MAX_VALUE = 255;
    public static final int START_WINDOW_WIDTH = 700;
    public static final int START_WINDOW_HEIGHT = 300;
    public static final int SLIDER_MINOR_TICK_SPACING = 5;
    public static final int PARAMETER_LABEL_WIDTH = 40;
    public static final int PARAMETER_LABEL_HEIGHT = 0;
    public static final int START_SLIDER_POSITION = 125;
    public static final int BORDER_LINE_THICKNESS = 10;

    
    @Override
    protected void onCreate() {
        setTitle(TITLE);
        setSize(START_WINDOW_WIDTH, START_WINDOW_HEIGHT);
        setLayout(new GridLayout());
        redSlider = createSlider();
        greenSlider = createSlider();
        blueSlider = createSlider();
        add(createColorPanel());
        add(createControlPanel());
        redSlider.addChangeListener(this::onSliderChange);
        greenSlider.addChangeListener(this::onSliderChange);
        blueSlider.addChangeListener(this::onSliderChange);
        MouseListener listener = new MouseEventListener();
        addMouseListener(listener);
    }

  //Панель вывода цвета
    private JPanel createColorPanel() {
        colorPanel.setLayout(new BorderLayout());
        updateColor();
        Border border = BorderFactory.createLineBorder(this.getContentPane().getBackground(), BORDER_LINE_THICKNESS);
        colorPanel.setBorder(border);
        return colorPanel;
    }

   //Обновление введенного цвета(ну или просто цвета при открытии окна)
    private void updateColor() {
        colorPanel.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
        System.out.println("ColorPicker.updateColor(), redSlider.getValue() = " + redSlider.getValue() + 
        		", greenSlider.getValue() = " + greenSlider.getValue() + ", blueSlider.getValue() = " + blueSlider.getValue());
    }

  //Регулировка цвета
    private JPanel createControlPanel() {
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.add(createPanelForOneParameter(redLabel, redSlider));
        controlPanel.add(createPanelForOneParameter(greenLabel, greenSlider));
        controlPanel.add(createPanelForOneParameter(blueLabel, blueSlider));
        return controlPanel;
    }

  //Панель для регулировки параметра
    private JPanel createPanelForOneParameter(JLabel label, JSlider slider) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        Dimension dimension = new Dimension(PARAMETER_LABEL_WIDTH, PARAMETER_LABEL_HEIGHT);
        label.setPreferredSize(dimension);

        panel.add(label, BorderLayout.LINE_START);
        panel.add(slider);

        return panel;
    }


   //Создание слайдера
    private JSlider createSlider() {
        JSlider slider = new JSlider(SLIDER_MIN_VALUE, SLIDER_MAX_VALUE);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(SLIDER_MINOR_TICK_SPACING);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(SLIDER_MAX_VALUE);
        slider.setPaintLabels(true);
        slider.setValue(START_SLIDER_POSITION);
        return slider;
    }

   //действие при изменение положения слайдера
    private void onSliderChange(ChangeEvent e) {
        updateColor();
    }

   //Обновление подсказки
    private void updateToolTip() {
        Integer red = colorPanel.getBackground().getRed();
        Integer green = colorPanel.getBackground().getGreen();
        Integer blue = colorPanel.getBackground().getBlue();
        String hex = ("#" + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue)).toUpperCase();
        colorPanel.setToolTipText(hex);
        copyToBuffer(hex);
    }

    //Копирование в буффер
    private void copyToBuffer(String text) {
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
    }

}
