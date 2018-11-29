package com.carrefour.AWExcel;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


/**
 * Hello world!
 *
 */
public class App {
	
	private boolean isPro = false;
	
    public static void main( String[] args ) throws FileNotFoundException, IOException, JSchException
    {
        //打开OprationView
    	EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OprationView window = new OprationView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    
   /**
    * 文件的字节流读取，依靠FileInputStream
    * @return StringBuffer
    * @throws IOException
    */
    public StringBuffer readFileByByte() throws IOException {
    	File file = new File("C:\\Users\\Shiyulong\\Desktop\\opt60");
    	FileInputStream inputStream = new FileInputStream(file);
    	int a;
    	StringBuffer buffer = new StringBuffer();
    	while ((a = inputStream.read()) != -1) {
			char b = (char) a;
			buffer.append(b);
		}
    	inputStream.close();
    	return buffer;
	}
    
    /**
     * 文件的缓存字符流读取
     * @return StringBuffer
     * @throws IOException
     */
    public Map<String, String>[] readFileByChar() throws IOException {
    	Map<String, String>[] mapList = new Map[8];
    	for (int i = 1; i < 9; i++) {
    		System.out.println(System.getProperty("user.dir")+"\\download_OPT\\20181115OPT"+i*10);
    		File file = new File(System.getProperty("user.dir")+"\\download_OPT\\20181115OPT"+i*10);
    		BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				System.out.println("该文件不存在");
				break;
			}
        	StringBuffer buffer = new StringBuffer();
        	String str;
        	int record;
        	Map<String, String> map = new HashMap<>();
        	long startTime = System.nanoTime();
        	while ((str = reader.readLine()) != null) {
        		record += Integer.valueOf(str.split(":")[1].trim());
        		map.put("record", String.valueOf(record));
        		if (str.split(":")[0].trim().indexOf("IPUR") > -1) {
					map.put("IPUR", str.trim().split(":")[1]);
				} else if (str.split(":")[0].trim().indexOf("arrivalTime") > -1) {
					map.put("arrivalTime", str.split(":")[1].trim());
				}
        		buffer.append(str);
        		bufferList[i-1] = buffer;
    		}
        	System.out.println("StringBuffer耗时："+(System.nanoTime()-startTime)+"纳秒");
        	long T = System.nanoTime();
        	while ((str = reader.readLine()) != null) {
        		str += str;
    		}
        	System.out.println("String耗时："+(System.nanoTime()-T)+"纳秒");
        	reader.close();
		}
    	return bufferList;
	}
    
    public StringBuffer readFileByReader() {
    	StringBuffer buffer = new StringBuffer(); 
		return buffer;
	}
    
    
}
