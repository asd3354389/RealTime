package com.foxlink.realtime.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.dialect.function.VarArgsSQLFunction;
import org.hibernate.mapping.Array;

import com.foxlink.realtime.DAO.ThreeMergeOneDAO;
import com.foxlink.realtime.model.ThreeMergeOne;
import com.foxlink.realtime.model.ThreeMergeOneGetNum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ThreeMergeOneService {

	private static Logger logger = Logger.getLogger(ThreeMergeOneService.class);
	private ThreeMergeOneDAO threeMergeOneDAO;

	
	public void setThreeMergeOneDAO(ThreeMergeOneDAO threeMergeOneDAO) {
		this.threeMergeOneDAO = threeMergeOneDAO;
	}

	public boolean export(String startDate, String endDate, String type, String data, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String[] title= {"EMP_ID","NAME","SWIPECARDTIME","RECORD_TYPE"};
		String name = "";
		if(type.equals("empid")){
			name+="依工號"+data;
		}else if(type.equals("depid")) {
			name+="依部門代碼"+data;
		}
		else if(type.equals("costid")) {
			name+="依費用代碼"+data;
		}
		String fileName = "三合一刷卡記錄"+name+"("+startDate.replace(":", "_")+"-"+endDate.replace(":", "_")+").xls";
//		System.out.println("456");
		List<ThreeMergeOne> result = threeMergeOneDAO.searchData(startDate,endDate,type,data);
		if(result.size()>0) {
			List<ThreeMergeOneGetNum> dataNum = threeMergeOneDAO.searchNum(startDate,endDate,type,data);
			int num = 0;
			if(type.equals("empid")) {
				 num = 1;
			}else if(type.equals("depid")){
				 num =  dataNum.size();
			}else if(type.equals("costid")){
				 num =  dataNum.size();
			}
			
//			System.out.println(num);
//			System.out.println(result.size());
			HSSFWorkbook wb = new HSSFWorkbook();
			for(int k=0;k<num;k++) {
//				String sheetName = String.valueOf(k)+dataNum.get(k).getName();
				String sheetName =dataNum.get(k).getEMP_ID();
//				System.out.println(sheetName);
				int c =0;
				List temp = new ArrayList();
				for(int z = 0;z<result.size();z++) {
					if(result.get(z).getName().equals(dataNum.get(k).getName())) {		
						c++;
						temp.add(result.get(z));			
					}
				}
//				System.out.println("123"+temp);
				String [][] content = new String[temp.size()][];	
//				System.out.println("456"+content.length);
				for(int b=0;b<c;b++) {
					content[b] = new String[title.length];
					content[b][0]=((ThreeMergeOne) temp.get(b)).getEmpId();
					content[b][1]=((ThreeMergeOne) temp.get(b)).getName();
					content[b][2]=((ThreeMergeOne) temp.get(b)).getSwipeCardTime();
					content[b][3]=((ThreeMergeOne) temp.get(b)).getRecord_Type();
//					System.out.println(b+":"+content[b]);
				}
				
				getHSSFWorkbook(sheetName, title, content, wb);
			}
			try {
				setResponseHeader(response, fileName);
//				File targetFile = new File("D:/RecordData");
//				if (!targetFile.exists()) {
//					targetFile.mkdirs();
//				}
				OutputStream os = response.getOutputStream();
				wb.write(os);
//				os.flush();
				os.close();
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return true;
		}else {
			return false;
		}

	

		
//		return result;
	}
	
    public void getHSSFWorkbook(String sheetName,String []title,String [][]values, HSSFWorkbook wb){

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
//        if(wb == null){
//            wb = new HSSFWorkbook();
//        }

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
//        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        
        sheet.setColumnWidth(0,12*256);
        sheet.setColumnWidth(1,10*256);
        sheet.setColumnWidth(2,30*256);
        sheet.setColumnWidth(3,20*256);
        
        //声明列对象
        HSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }

        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
//        return wb;
    }
    
    public void setResponseHeader(HttpServletResponse response, String fileName) {
//    	System.out.print(123);
        try {
//            try {
//                fileName = new String(fileName,"GBK");
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+new String( fileName.getBytes("UTF-8"), "ISO8859-1"));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	public boolean judgeDownload(String startDate, String endDate, String type, String data) {
		// TODO Auto-generated method stub
		List<ThreeMergeOne> result = threeMergeOneDAO.searchData(startDate,endDate,type,data);
		if (result.size()>0) {
			return true;
		}else {
			return false;
		}
		
	}
}
