package jmm.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class RandomFileWindowManager {
	
	static int l;
	BorderLayout bLay = new BorderLayout();
	BorderLayout NBLay = new BorderLayout();
	BorderLayout SBLay = new BorderLayout();
	JPanel cPane = new JPanel(bLay);
	JPanel nPane = new JPanel(NBLay);
	JPanel sPane = new JPanel(SBLay);
	Component LHStrut = Box.createHorizontalStrut(60);
	Component RHStrut = Box.createHorizontalStrut(60);
	Component NLHStrut = Box.createHorizontalStrut(100);
	Component NRHStrut = Box.createHorizontalStrut(0);
	Component NTVStrut = Box.createVerticalStrut(20);
	JFrame frame = new JFrame("The Random Arff File Generator!!");
	JLabel label = new JLabel("Generating Attributes...");
	public JProgressBar pBar;
	JButton CancelButton = new JButton("Cancel");
	Dimension PrefferedSizeCancelButton = new Dimension(100, 20);
	ButtonListner bl = new ButtonListner();
	
	public class ButtonListner extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(1);
		}
	}
	
	public RandomFileWindowManager(int m) {
		l = m;
		pBar = new JProgressBar(1, l);
	}
	
	public void CreateWindow() {
		int sh = this.GetScreenWorkingHeight();
		int sw = RandomFileWindowManager.GetScreenWorkingWidth();
		frame.setBounds((sw / 2) - 300, (sh / 2) - 75, 599, 150);
		frame.setVisible(true);
		frame.setContentPane(cPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pBar.setVisible(true);
		pBar.setSize(450, 100);
		cPane.add(pBar, BorderLayout.CENTER);
		cPane.add(LHStrut, BorderLayout.WEST);
		cPane.add(RHStrut, BorderLayout.EAST);
		cPane.add(label, BorderLayout.NORTH);
		cPane.setVisible(true);
		frame.setBounds((sw / 2) - 300, (sh / 2) - 75, 600, 150);
		cPane.add(nPane, BorderLayout.NORTH);
		nPane.add(label, BorderLayout.CENTER);
		nPane.add(NTVStrut, BorderLayout.NORTH);
		nPane.add(NLHStrut, BorderLayout.WEST);
		nPane.add(NRHStrut, BorderLayout.EAST);
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		label.setSize(300, 10);
		cPane.add(CancelButton, BorderLayout.SOUTH);
		CancelButton.setBounds(CancelButton.getX(), CancelButton.getY() + 40, 100, 20);
		CancelButton.addActionListener(bl);
		CancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		CancelButton.setToolTipText("Cancels The Program, Leaving Written Records Where They Are");
	}
	
	public void setProgress(int val) throws InterruptedException {
		if (val <= 500) {
			for (int i = 1; i < val; i++) {
				pBar.setValue(i);
			}
		}
		
	}
	
	public void addProgress(int val) throws InterruptedException {
		int pVal = pBar.getValue();
		if ((val + pVal) <= 500) {
			for (int i = 1; i < (val); i++) {
				pBar.setValue(pVal + i);
			}
		}
	}
	
	public int getProgress() {
		return pBar.getValue();
	}
	
	public void addProgress(double val) throws InterruptedException {
		int pVal = pBar.getValue();
		if ((val + pVal) <= 500) {
			for (int i = 1; i < (val); i++) {
				pBar.setValue(pVal + i);
			}
		}
	}
	
	public void setProgress(double val) throws InterruptedException {
		if (val <= 500) {
			for (int i = 1; i < val; i++) {
				pBar.setValue(i);
			}
		}	
	}
	
	public JLabel getLabel() {
		return label;
	}
	
	public static int GetScreenWorkingWidth() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int GetScreenWorkingHeight() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}
}
