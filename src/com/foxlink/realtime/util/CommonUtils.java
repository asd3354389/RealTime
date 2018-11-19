package com.foxlink.realtime.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.google.gson.JsonObject;

public class CommonUtils {
	private static Logger logger=Logger.getLogger(CommonUtils.class);
	
	/*解析txt file中的內容並轉換成List<String>*/
	public List<String> ParseTXTFile(String filePath)throws Exception{
		List<String> ParseFile2List=null;
		try {
			FileReader input=new FileReader(filePath);
			BufferedReader bufferReader=new BufferedReader(input);
			String txtLine=null;
			ParseFile2List=new ArrayList<String>();
			while((txtLine=bufferReader.readLine())!=null) {
				ParseFile2List.add(txtLine);
			}
			bufferReader.close();
			input.close();
		}

		catch(Exception ex) {
			logger.error("Parse txt file is failed ",ex);
		}
		return ParseFile2List;
	}
	
	
	public Date ConvertString2SQLDate(String dateTime)throws Exception {
		Date SQLDate=null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(dateTime!=null && !dateTime.isEmpty()) {
				java.util.Date parsed = format.parse(dateTime);
				SQLDate=new Date(parsed.getTime());
			}
			else {
				throw new CustomException("日期字串錯誤，請確認");
			}
		}
		catch(ParseException e) {
			format = new SimpleDateFormat("yyyy/MM/dd");
			if(dateTime!=null && !dateTime.isEmpty()) {
				java.util.Date parsed = format.parse(dateTime);
				SQLDate=new Date(parsed.getTime());
			}
			else {
				throw new CustomException("日期字串錯誤，請確認");
			}
		}
		catch(Exception ex) {
			logger.error("ConvertString2SQLDate is failed",ex);
		}
		return SQLDate;
	}
	
	public String ProduceNoDataFoundJsonString(String modelName) {
		JsonObject NoDataFound=null;
		try {
			NoDataFound=new JsonObject();
			NoDataFound.addProperty("ErrorCode", 500);
			NoDataFound.addProperty("ErroMessage", "找不到 "+modelName+" 相關的資料");
		}
		catch(Exception ex) {
			logger.error("ProduceNoDataFoundJsonString is failed",ex);
		}
		return NoDataFound.toString();
	}
	
}
