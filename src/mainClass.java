import java.awt.AWTException;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import javax.swing.*;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class mainClass implements NativeKeyListener{
	public static boolean isClick;
	public static void main(String[] args) {
		userInterface();
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		GlobalScreen.addNativeKeyListener(new mainClass());
		}
	

	public static void click(int time_, long interval_, String mouse) throws AWTException   {
		
		Robot robot = new Robot();
		
		long time = time_ * 1000;
		long t= System.currentTimeMillis();
		long end = t+time; //millisecond
		
		while(System.currentTimeMillis() < end && isClick == true) {
			if(mouse.equalsIgnoreCase("Left Click")) {
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			} else if(mouse.equalsIgnoreCase("Right Click")){
				robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
			} else {
				robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
				robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
			}
			
				//sleep between click
			  try {
				Thread.sleep(interval_); //millisecond
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
	
	public static void stop() {
		System.out.println("Auto Clicker Has Stopped");
		isClick = false;
	}
	
	public void nativeKeyPressed(NativeKeyEvent e) {
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
    		stop();
    	}
	}

	public static void userInterface() {
		
		String[] mouseTypeStrings = {"Left Click", "Right Click", "Middle Mouse"};
		
		JFrame frame = new JFrame();
		JButton startButton = new JButton("START");
		JButton stopButton = new JButton("STOP");
		JLabel timerLabel = new JLabel();
		JLabel intervaLabel = new JLabel();
		JLabel info = new JLabel();
		JLabel info2 = new JLabel();
		JLabel credit = new JLabel();
		JLabel credit2 = new JLabel();
		JTextField timerField = new JTextField();
		JTextField intervalField = new JTextField();
		JComboBox mouseList = new JComboBox(mouseTypeStrings);
		
		frame.setSize(370, 300);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);
		
		timerLabel.setText("TIMER: ");
		intervaLabel.setText("INTERVAL: ");
		info.setText("*USE SEC ON TIMER AND MILLISECONDS ON INTERVAL*");
		info2.setText("\nPRESS \"ESC\" TO STOP");
		credit.setText("AUTO CLICKER USING JNativeHook Library");
		credit2.setText("github.com/SenpaiZero");
		
		timerField.setBounds(180, 45, 100, 20);
		intervalField.setBounds(180, 65, 100, 20);
		
		timerLabel.setBounds(80, 45, 100, 20);
		intervaLabel.setBounds(80, 65, 100, 20);
		info.setBounds(10, 5, 1000, 20);
		info2.setBounds(100, 20, 1000, 20);
		credit.setBounds(50, 200, 400, 20);
		credit2.setBounds(100, 230, 200, 20);

		mouseList.setSelectedIndex(0);
		mouseList.setBounds(120, 100, 100, 20);
		
		info2.setForeground(Color.red);
		credit2.setForeground(Color.blue);
		
		startButton.setBounds(180,130,100, 40);
		startButton.addActionListener((ActionListener) new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        try {
		        	isClick = true;
		        	int getTimerField = Integer.parseInt(timerField.getText());
		        	int getIntervalField = Integer.parseInt(intervalField.getText());
		        	frame.setState(Frame.ICONIFIED);
					click(getTimerField, getIntervalField, mouseList.getSelectedItem().toString());
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
		    }
		});
		
		stopButton.setBounds(70, 130, 100, 40);
		stopButton.addActionListener((ActionListener) new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        stop();
		    }
		});
		
		
		frame.add(startButton);
		frame.add(stopButton);
		frame.add(intervaLabel);
		frame.add(timerLabel);
		frame.add(timerField);
		frame.add(intervalField);
		frame.add(info);
		frame.add(info2);
		frame.add(mouseList);
		frame.add(credit);
		frame.add(credit2);
	}
}
