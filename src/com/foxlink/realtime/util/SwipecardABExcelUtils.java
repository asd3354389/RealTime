package com.foxlink.realtime.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.foxlink.realtime.model.SwipecardAB;

public class SwipecardABExcelUtils {
	public String[] title = {"工號","姓名","費用代碼","組別代碼","線別代碼","部門名稱","下班超15分鐘刷卡","下班超15分鐘未刷卡","超7休一或14休一刷卡次數","異常刷卡次數"};
	public int[] cellWight = {5000 ,4000 ,4000 ,4000 ,5000,20000,5000,5000,8000,4000 };

	public InputStream creatExcel(List<SwipecardAB> swipecardABList,String sheetName) throws Exception{
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet(sheetName);
			for (int i = 0; i < cellWight.length; i++) {
				sheet.setColumnWidth(i, cellWight[i]);
			}
			
			CellRangeAddress region = new CellRangeAddress(0, 0, 0, cellWight.length - 1);
	        sheet.addMergedRegion(region);
			
			
	        HSSFFont fontStyle = wb.createFont();
	        fontStyle.setBold(true);   //加粗
	        fontStyle.setFontHeightInPoints((short)16);  //设置标题字体大小
	        
	        HSSFCellStyle cellStyle = wb.createCellStyle();
	        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
	        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
	        cellStyle.setFont(fontStyle);
	        cellStyle.setBorderBottom(BorderStyle.MEDIUM); //下边框  
	        cellStyle.setBorderLeft(BorderStyle.MEDIUM);//左边框  
	        cellStyle.setBorderTop(BorderStyle.MEDIUM);//上边框  
	        cellStyle.setBorderRight(BorderStyle.MEDIUM);//右边框 
	        
	        HSSFCellStyle cellStyleRight = wb.createCellStyle();
	        cellStyleRight.setBorderRight(BorderStyle.MEDIUM);//右边框 
	        cellStyleRight.setAlignment(HorizontalAlignment.CENTER);//水平居中
	        cellStyleRight.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
	        cellStyleRight.setFont(fontStyle);
	        
	        HSSFCellStyle cellStyleContent = wb.createCellStyle();
	        cellStyleContent.setAlignment(HorizontalAlignment.CENTER);//水平居中
	        cellStyleContent.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
	        cellStyleContent.setFont(fontStyle);
	        
	        HSSFCellStyle cellStyleBottom = wb.createCellStyle();
	        cellStyleBottom.setBorderBottom(BorderStyle.MEDIUM); 
	        cellStyleBottom.setAlignment(HorizontalAlignment.CENTER);//水平居中
	        cellStyleBottom.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
	        cellStyleBottom.setFont(fontStyle);
	        
	        HSSFCellStyle cellStyleBottomRight = wb.createCellStyle();
	        cellStyleBottomRight.setBorderBottom(BorderStyle.MEDIUM);//右边框 
	        cellStyleBottomRight.setBorderRight(BorderStyle.MEDIUM);//右边框 
	        cellStyleBottomRight.setAlignment(HorizontalAlignment.CENTER);//水平居中
	        cellStyleBottomRight.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
	        cellStyleBottomRight.setFont(fontStyle);
	        
	        // 设置字体
	        Font baseFont = wb.createFont();
	        // 字体类型
	        baseFont.setFontName("宋体");
	        // 字体大小
	        baseFont.setFontHeightInPoints((short) 10);
	        cellStyle.setFont(baseFont);
	        
	        
	        HSSFFont TitlefontStyle = wb.createFont();
	        TitlefontStyle.setBold(true);   //加粗
	        TitlefontStyle.setFontHeightInPoints((short)32);  //设置标题字体大小
	        
	        HSSFCellStyle TitlecellStyle = wb.createCellStyle();
	        TitlecellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
	        TitlecellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
	        TitlecellStyle.setFont(TitlefontStyle);
	        TitlecellStyle.setBorderBottom(BorderStyle.MEDIUM); //下边框  
	        TitlecellStyle.setBorderLeft(BorderStyle.MEDIUM);//左边框  
	        TitlecellStyle.setBorderTop(BorderStyle.MEDIUM);//上边框  
	        TitlecellStyle.setBorderRight(BorderStyle.MEDIUM);//右边框 
	        
	        HSSFRow Titlerow = sheet.createRow(0);
	        Titlerow.setHeight((short) 800);
	        HSSFCell Titlecell=Titlerow.createCell(0);
	        Titlecell.setCellValue(sheetName);
	        Titlecell.setCellStyle(TitlecellStyle);
	        
	        
			for(int i = 1;i<swipecardABList.size()+2;i++){
				HSSFRow row = sheet.createRow(i);
				if(i==1){
					row.setHeight((short) 350);
					for (int j = 0; j < title.length; j++) {
						HSSFCell cell=row.createCell(j);
						cell.setCellValue(title[j]);
						cell.setCellStyle(cellStyle);
					}
				}else{
					SwipecardAB swipecardAB = swipecardABList.get(i-2);
					HSSFCell cell=row.createCell(0);
					cell.setCellValue(swipecardAB.getId());
					HSSFCell cell1=row.createCell(1);
					cell1.setCellValue(swipecardAB.getName());
					HSSFCell cell2=row.createCell(2);
					cell2.setCellValue(swipecardAB.getCostid());
					HSSFCell cell3=row.createCell(3);
					cell3.setCellValue(swipecardAB.getDeptid());
					HSSFCell cell5=row.createCell(4);
					cell5.setCellValue(swipecardAB.getDepid());
					HSSFCell cell6=row.createCell(5);
					cell6.setCellValue(swipecardAB.getDepname());
					HSSFCell cell7=row.createCell(6);
					cell7.setCellValue(swipecardAB.getOvertime15());
					HSSFCell cell8=row.createCell(7);
					cell8.setCellValue(swipecardAB.getOutwork15());
					HSSFCell cell9=row.createCell(8);
					cell9.setCellValue(swipecardAB.getMorethen7());
					HSSFCell cell4=row.createCell(9);
					cell4.setCellValue(swipecardAB.getCount());
					
					if(i == swipecardABList.size()+1){
						cell.setCellStyle(cellStyleBottom);
						cell1.setCellStyle(cellStyleBottom);
						cell2.setCellStyle(cellStyleBottom);
						cell3.setCellStyle(cellStyleBottom);
						cell5.setCellStyle(cellStyleBottom);
						cell6.setCellStyle(cellStyleBottom);
						cell7.setCellStyle(cellStyleBottom);
						cell8.setCellStyle(cellStyleBottom);
						cell9.setCellStyle(cellStyleBottom);
						cell4.setCellStyle(cellStyleBottomRight);
					}else{
						cell.setCellStyle(cellStyleContent);
						cell1.setCellStyle(cellStyleContent);
						cell2.setCellStyle(cellStyleContent);
						cell3.setCellStyle(cellStyleContent);
						cell5.setCellStyle(cellStyleContent);
						cell6.setCellStyle(cellStyleContent);
						cell7.setCellStyle(cellStyleContent);
						cell8.setCellStyle(cellStyleContent);
						cell9.setCellStyle(cellStyleContent);
						cell4.setCellStyle(cellStyleRight);
					}
				}
			}
			
			/*
			 * FileOutputStream output=new
			 * FileOutputStream("C:\\Users\\129548\\Desktop\\"+sheetName+".xls");
			 * wb.write(output); output.flush();
			 */
			 
			
			
			  ByteArrayOutputStream baos = new ByteArrayOutputStream(); wb.write(baos);
			  baos.flush(); byte[] bt = baos.toByteArray(); 
			  InputStream is = new ByteArrayInputStream(bt, 0, bt.length); 
			  baos.close();
			  return is;
		
	}
}
