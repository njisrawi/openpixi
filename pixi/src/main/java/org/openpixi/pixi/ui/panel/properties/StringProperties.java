package org.openpixi.pixi.ui.panel.properties;

import javax.swing.*;

import org.openpixi.pixi.ui.SimulationAnimation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A generic text field for setting integer values.
 */
public class StringProperties {

	protected SimulationAnimation simulationAnimation;
	private String name;
	private String value;

	private JLabel label;
	private JTextField textField;

	public StringProperties(SimulationAnimation simulationAnimation, String name, String initialValue)
	{
		this.simulationAnimation = simulationAnimation;
		this.name = name;
		textField = new JTextField();
		this.value = initialValue;
		textField.setText(value);
		this.update();
		//this.setValue(initialValue);
	}

	public void addComponents(Box box)
	{
		Box settingControls = Box.createVerticalBox();

		label = new JLabel(name, SwingConstants.CENTER);

		textField.setText(value);
		textField.addActionListener(new TextFieldListener());
		textField.setMaximumSize(
				new Dimension(400/*Integer.MAX_VALUE*/, textField.getPreferredSize().height));


		settingControls.add(label);
		settingControls.add(textField);
		settingControls.add(Box.createVerticalGlue());

		box.add(settingControls);
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
		textField.setText(value);
		simulationAnimation.repaint();
		this.update();
	}

	class TextFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			value = textField.getText();
			simulationAnimation.repaint();
			update();
		}
	}

	/**
	 * Can be implemented by superclass to perform any calculations
	 * if a value has been changed.
	 */
	public void update() {
	}
}
