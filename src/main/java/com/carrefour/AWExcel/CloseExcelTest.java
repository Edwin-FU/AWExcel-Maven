package com.carrefour.AWExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CloseExcelTest {
	public static void EXIT() throws FileNotFoundException, IOException {
		Runtime.getRuntime().exec("cmd /c taskkill /f /t /im EXCEL.EXE");
		File file = new File("C:\\Users\\Shiyulong\\Desktop\\A.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
		workbook.removeSheetAt(workbook.getSheetIndex("Sheet2"));
		workbook.write(new FileOutputStream(file));
//		try {
//			final Process pro = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Microsoft Office\\root\\Office16\\EXCEL.EXE C:\\Users\\Shiyulong\\Desktop\\A.xlsx");
//			
			/*new Thread(new Runnable() {
				public void run() {
					try {
						pro.waitFor();
						System.out.println(1);
						pro.destroy();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();*/
			
//			new Thread(new Runnable() {
//			public void run() {
//				try {
//					Thread.sleep(1000);
//					pro.destroy();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			}).start();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		CloseExcelTest.EXIT();
	}
}
