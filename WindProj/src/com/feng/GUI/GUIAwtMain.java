package com.feng.GUI;

import java.awt.Button;
import java.awt.Component;
import java.awt.Container;//容器
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
/**
 * FlowLayout流式布局管理器（从左到右的顺序，Panel默认的布局管理器）
 * BorderLayout边界布局管理器（东南西北中，Frame默认的布局管理器）
 * GridLayout网格布局管理器
 * CradleLayout卡片布局管理器
 * GridBagLayout网格包布管理器
 */
public class GUIAwtMain {
	public static void main(String[] args) {
		Frame f=new Frame("我的窗口");
		//距离x和y的距离
		f.setLocation(200,300);
		//大小
		f.setSize(500, 500);
		
		//布局
		f.setLayout(new FlowLayout());
		
		//按钮
		Button b=new Button("123");
		f.add(b);
		
		//监听
		f.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		//可见
		f.setVisible(true);
	}
}
