package com.carrefour.AWExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
	
	public WriteExcel() throws FileNotFoundException, IOException
    {
    	File file = new File("C:\\Users\\Shiyulong\\Desktop\\A.xlsx");
        String[] AZ = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        
        //Header-Style-Start
        XSSFCellStyle headCellStyle = workbook.createCellStyle();
        XSSFFont headFont = workbook.createFont();
        headFont.setColor(HSSFColor.BLUE.index);
        headCellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(91,155,213)));//设置背景颜色
        headCellStyle.setFillPattern(headCellStyle.SOLID_FOREGROUND);//填充模式实线
        headCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置水平居中
        headCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置水平居中
        headCellStyle.setWrapText(true);
        headFont.setFontHeightInPoints((short)12);//设置字号
        headFont.setBold(true);//字体加粗
		headCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		headCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
		headCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		headCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		//Header-Style-End
		
		//Body-Start
		XSSFCellStyle bodyCellStyle = workbook.createCellStyle();
        XSSFFont bodyFont = workbook.createFont();
        bodyCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        bodyCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        //Body-End
        
        XSSFSheet sheet1 = workbook.createSheet("Sheet2");
        sheet1.setColumnWidth(8, 3600);//设置列宽度
        for (int i = 0; i < 11; i++) {//共创建11行
        	XSSFRow row = sheet1.createRow(i+10);
        	if(i == 0) {//固定行
        		row.createCell(8).setCellValue("Date");
        		row.createCell(9).setCellValue("OPT");
        		row.createCell(10).setCellValue("Records");
        		row.createCell(11).setCellValue("IPUR Records");
        		row.createCell(12).setCellValue("Arrival time");
        		row.createCell(13).setCellValue("Start Time");
        		row.createCell(14).setCellValue("End Time");
        		row.createCell(15).setCellValue("Elapsed Time");
        		row.createCell(16).setCellValue("Elapsed Time/Records");
        		row.createCell(17).setCellValue("Status");
        		
        		//设置head样式
        		for (int j = 0; j < 10; j++) {
        			XSSFCell cell = row.getCell(j+8);
        			cell.setCellStyle(headCellStyle);
				}
        	}else {
        		for (int j = 0; j < 9; j++) {//共创建10列
        			if (j == 0) {
        				row.createCell(j+8).setCellValue(date);
					}else if (j == 1) {
						if (i == 9) {
							row.createCell(j+8).setCellValue("OPT95");//设置OPT95
						}else if (i == 10) {
							//row.createCell(j+8).setCellValue("");
						} else {
							row.createCell(j+8).setCellValue("OPT"+(i)*10);//设置count records
						}
					}else if (j == 2 && i == 10) {
						XSSFCell cell = row.createCell(j+8);
						cell.setCellFormula("IF(K20=\"\",\"\",SUM(K12:K20))");
						cell.setCellStyle(bodyCellStyle);
						break;
					} else {
						row.createCell(j+8).setCellValue(AZ[i]);
					}
        			
        			//设置body样式
        			for (int k = 0; k < 10; k++) {
            			XSSFCell cell = row.getCell(j+8);
            			cell.setCellStyle(bodyCellStyle);
    				}
        		}
			}
		}
        workbook.write(new FileOutputStream(file));
        
        //打开
        Runtime.getRuntime().exec("C:\\Program Files (x86)\\Microsoft Office\\root\\Office16\\EXCEL.EXE C:\\Users\\Shiyulong\\Desktop\\A.xlsx");
        
    }
}
