package jmm.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class ArffReaderWindowManager {
	
	JFrame frame = new JFrame("Arff File Reader");
	public JPanel cpane = new JPanel(new BorderLayout());
	public JLabel SLabel = new JLabel("Arff File Reader");
	JProgressBar pBar = new JProgressBar(1, 500);
	JPanel spane = new JPanel(new BorderLayout());
	JPanel npane = new JPanel(new BorderLayout());
	Component NTVStrut = Box.createVerticalStrut(20);
	Component NLHStrut = Box.createHorizontalStrut(40);
	Component NRHStrut = Box.createHorizontalStrut(40);
	
	
	public ArffReaderWindowManager() {
		
	}
	
	public void CreateWindow() {
		frame.setBounds(600, 400, 400, 299);
		frame.setVisible(true);
		frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		frame.setContentPane(cpane);
		cpane.add(spane, BorderLayout.SOUTH);
		cpane.add(npane, BorderLayout.NORTH);
		cpane.add(SLabel, BorderLayout.SOUTH);
		cpane.setVisible(true);
		npane.add(pBar, BorderLayout.CENTER);
		npane.add(NTVStrut, BorderLayout.NORTH);
		pBar.setVisible(true);
		frame.setBounds(600, 400, 400, 300);
		npane.add(NLHStrut, BorderLayout.WEST);
		npane.add(NRHStrut, BorderLayout.EAST);
		
	}
	
	public void setProgress(int val) {
		if (val <= 500) {
			for (int i = 1; i < val; i++) {
				pBar.setValue(i);
			}
		}
		
	}
	
	public void addProgress(int val) {
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
	
	public void addProgress(double val) {
		int pVal = pBar.getValue();
		if ((val + pVal) <= 500) {
			for (int i = 1; i < (val); i++) {
				pBar.setValue(pVal + i);
			}
		}
	}
	
	public void setProgress(double val) {
		if (val <= 500) {
			for (int i = 1; i < val; i++) {
				pBar.setValue(i);
			}
		}	
	}
	
}
