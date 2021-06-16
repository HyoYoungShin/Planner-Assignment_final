package planner;

import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

public class WindowDestroyer extends WindowAdapter {
	public void windowClosing(WindowEvent e) {
		long before_compare_time;
		long hour = 0;
		long min = 0;
		long sec = 0;
		Scanner inputStream;
		try {
			inputStream = new Scanner(new File("time.txt"));
			before_compare_time = inputStream.nextLong();
			inputStream.close();
			String one = before_compare_time / 1000 + "";
			int totalsec = Integer.parseInt(one, 10);
			String two = totalsec / 60 + "";
			int totalmin = Integer.parseInt(two, 10);
			sec = totalsec % 60;
			String three = totalmin / 60 + "";
			hour = Integer.parseInt(three, 10);
			min = totalmin % 60;

		} catch (FileNotFoundException e1) {
			;
		}

		String printString = "your study time is " + hour + "h " + min + "m " + sec + "s";

		JOptionPane.showMessageDialog(null, printString, "Message", JOptionPane.INFORMATION_MESSAGE);
		super.windowClosing(e);
	}
}
