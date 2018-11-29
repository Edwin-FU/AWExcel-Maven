package com.carrefour.AWExcel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Edwin 
 * 2018年11月10日上午9:16:49
 */
public class Dialog extends JFrame implements ActionListener {
	JPanel pancel = new JPanel();
	JButton[] button = new JButton[] {new JButton("确定"),new JButton("取消"),new JButton("消息")};
	JLabel label1 = new JLabel("label");
	JLabel label2 = new JLabel("label");
	/* 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == button[0]) {
			JOptionPane.showMessageDialog(this, "点击确定", "title", JOptionPane.INFORMATION_MESSAGE);
			label1.setText("你选择确定？");
		}
	}
	
	/**
	 * 构造方法：初始化属性
	 */
	public Dialog() {
		pancel.setLayout(new GridLayout(1, 4));
		for (int i = 0; i < button.length; i++) {
			pancel.add(button[i]);
			button[i].addActionListener(this);
		}
		this.add(pancel);
		this.add(label1,BorderLayout.SOUTH);
		this.add(label2,BorderLayout.NORTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("dialog");
		this.setBounds(100, 100, 480, 100);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new Dialog();
	}
}
