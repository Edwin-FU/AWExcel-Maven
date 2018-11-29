package com.carrefour.AWExcel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class OprationView {

	private JFrame frmWorkflow;

	/**
	 * Create the application.
	 */
	public OprationView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWorkflow = new JFrame();
		frmWorkflow.setVisible(true);
		frmWorkflow.setResizable(false);
		frmWorkflow.setTitle("workflow");
		frmWorkflow.getContentPane().setBackground(Color.WHITE);
		frmWorkflow.setBounds(100, 100, 304, 384);
		frmWorkflow.setLocationRelativeTo(null);
		frmWorkflow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWorkflow.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 268, 302);
		frmWorkflow.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 11, 268, 31);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("start");
		btnNewButton.setBounds(169, 4, 89, 23);
		panel_1.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Auto write NSA");
		lblNewLabel.setBounds(10, 8, 89, 14);
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(0, 53, 268, 31);
		panel.add(panel_2);
		
		JButton button = new JButton("start");
		button.setBounds(169, 4, 89, 23);
		panel_2.add(button);
		
		JLabel lblAutoCmWorkflow = new JLabel("Auto CM workflow");
		lblAutoCmWorkflow.setBounds(10, 8, 108, 14);
		panel_2.add(lblAutoCmWorkflow);
		
		JMenuBar menuBar = new JMenuBar();
		frmWorkflow.setJMenuBar(menuBar);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					if (arg0.getItem().equals("Test")) {
						startWorkflow(false);
					}else {
						startWorkflow(true);
					}
				}
			}
		});
		comboBox.addItem("Test");
		comboBox.addItem("Pro");
		menuBar.add(comboBox);
	}
	
	private void startWorkflow(boolean isPro) {
		Session session = null;
    	ChannelSftp channelSftp = null;
    	Properties properties = loadProperties(isPro);
    	try {
    		//连接SSH，获取session
			session = ConectSSH.connect(properties.getProperty("host"), 
					null,
					properties.getProperty("user"),
					properties.getProperty("password"));
			//打开
			Channel channel = session.openChannel("sftp");
	    	channel.connect();
	    	channelSftp = (ChannelSftp) channel;
	    	//下载
	    	ConectSSH.download(properties.getProperty("directory"), properties.getProperty("srcFile"), 
	    			System.getProperty("user.dir")+properties.getProperty("saveFilePath"), channelSftp, null);
	    	System.out.println(new App().readFileByChar()[2]);
		} catch (NumberFormatException | JSchException | IOException e) {
			if (e instanceof JSchException) {
				JOptionPane.showMessageDialog(frmWorkflow, e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
			}
		}
    	
	}
	
	public static Properties loadProperties(boolean isPro) {
    	Properties properties = new Properties();
		if (isPro) {
	    	try {
				properties.load(new FileInputStream("DUConfig\\downloadP.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			try {
				properties.load(new FileInputStream("DUConfig\\downloadT.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
}
