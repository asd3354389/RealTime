package com.foxlink.realtime.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.dialect.function.VarArgsSQLFunction;

import com.foxlink.realtime.DAO.CountEmpByCostIDDAO;
import com.foxlink.realtime.DAO.CountEmpDAO;
import com.foxlink.realtime.model.ManPowerStatus;
import com.foxlink.realtime.model.Prod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class CountEmpByCostIDService extends Service<ManPowerStatus>{
	private static Logger logger = Logger.getLogger(CountEmpByCostIDService.class);
	private CountEmpByCostIDDAO countEmpByCostIDDAO;
	
	public void setCountEmpByCostIDDAO(CountEmpByCostIDDAO countEmpByCostIDDAO) {
		this.countEmpByCostIDDAO = countEmpByCostIDDAO;
	}
	
	@Override
	public boolean AddNewRecord(ManPowerStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateRecord(ManPowerStatus t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean DeleteRecord(String recordID, String updateUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ManPowerStatus> FindAllRecords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindRecord(ManPowerStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindAllRecords(int currentPage, String queryCritirea, String queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindQueryRecords(String userDataCostId, ManPowerStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ManPowerStatus> FindQueryRecord(String userDataCostId, int currentPage, ManPowerStatus t) {
		// TODO Auto-generated method stub
		return null;
	}

	public String ShowCountEmpList(String costid, String sDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
	/*	int sun = countEmpDAO.searchSunCount(depid,sDate);
		int night = countEmpDAO.searchNightCount(depid,sDate);*/
		List<ManPowerStatus> First=new ArrayList<ManPowerStatus>();
		List<ManPowerStatus> Second=new ArrayList<ManPowerStatus>();
		List<ManPowerStatus> Third=new ArrayList<ManPowerStatus>();
		List<ManPowerStatus> Fouth=new ArrayList<ManPowerStatus>();
		List<ManPowerStatus> Fivth=new ArrayList<ManPowerStatus>();
		
		int first_SunCount=0;     int first_SunNP=0;     int first_SunExpor=0;      int first_SunDimiss=0;        int first_SunLeave=0;       int first_SunABS=0;       int first_SunATP=0;
		int second_SunCount=0;    int second_SunNP=0;	 int second_SunExpor=0;      int second_SunDimiss=0;	   int second_SunLeave=0;      int second_SunABS=0;      int second_SunATP=0;   
		int third_SunCount=0;     int third_SunNP=0;	 int third_SunExpor=0;       int third_SunDimiss=0;        int third_SunLeave=0;       int third_SunABS=0;       int third_SunATP=0;     
		int fouth_SunCount=0;     int fouth_SunNP=0;	 int fouth_SunExpor=0;       int fouth_SunDimiss=0;        int fouth_SunLeave=0;       int fouth_SunABS=0;       int fouth_SunATP=0; 
		int fivth_SunCount=0;  	  int fivth_SunNP=0;	 int fivth_SunExpor=0;       int fivth_SunDimiss=0;        int fivth_SunLeave=0;       int fivth_SunABS=0;       int fivth_SunATP=0; 
		int first_NightCount=0;   int first_NightNP=0;	 int first_NightExpor=0;     int first_NightDimiss=0;      int first_NightLeave=0;     int first_NightABS=0;     int first_NightATP=0; 
		int second_NightCount=0;  int second_NightNP=0;  int second_NightExpor=0;  	 int second_NightDimiss=0;     int second_NightLeave=0;    int second_NightABS=0;    int second_NightATP=0; 
		int third_NightCount=0;   int third_NightNP=0;	 int third_NightExpor=0;     int third_NightDimiss=0;      int third_NightLeave=0;     int third_NightABS=0;     int third_NightATP=0; 
		int fouth_NightCount=0;   int fouth_NightNP=0;	 int fouth_NightExpor=0;     int fouth_NightDimiss=0;      int fouth_NightLeave=0;     int fouth_NightABS=0;     int fouth_NightATP=0; 
		int fivth_NightCount=0;   int fivth_NightNP=0;	 int fivth_NightExpor=0;     int fivth_NightDimiss=0;      int fivth_NightLeave=0;     int fivth_NightABS=0;     int fivth_NightATP=0;
		
		int first_SunOTLine=0;    int first_SunPlanCount=0;
		int second_SunOTLine=0;	  int second_SunPlanCount=0;
		int third_SunOTLine=0;    int third_SunPlanCount=0;
		int fouth_SunOTLine=0;    int fouth_SunPlanCount=0;
		int fivth_SunOTLine=0;    int fivth_SunPlanCount=0;
		int first_NightOTLine=0;  int first_NightPlanCount=0;
		int second_NightOTLine=0; int second_NightPlanCount=0;
		int third_NightOTLine=0;  int third_NightPlanCount=0;
		int fouth_NightOTLine=0;  int fouth_NightPlanCount=0;
		int fivth_NightOTLine=0;  int fivth_NightPlanCount=0;
		
		
		List<ManPowerStatus>reasonEmpList = countEmpByCostIDDAO.searchReasonEmpList(costid,sDate);
		//System.out.println(reasonEmpList);
		List<ManPowerStatus>offLineList = countEmpByCostIDDAO.searchOffLineList(costid,sDate);
		//System.out.println(offLineList);
		List<ManPowerStatus>countEmpList = countEmpByCostIDDAO.searchCountEmpList(costid,sDate);
		if(offLineList.size()>0) {
			for(int h=0;h<offLineList.size();h++) {
				if(offLineList.get(h).getCostId().equals("9628")) {
					if(offLineList.get(h).getShift().equals("日")){
						first_SunOTLine=offLineList.get(h).getCount();
					}else {
						first_NightOTLine=offLineList.get(h).getCount();
					}
				}else if(offLineList.get(h).getCostId().equals("9629")){
					if(offLineList.get(h).getShift().equals("日")){
						second_SunOTLine=offLineList.get(h).getCount();
					}else {
						second_NightOTLine=offLineList.get(h).getCount();
					}
				}else if(offLineList.get(h).getCostId().equals("6251")){
					if(offLineList.get(h).getShift().equals("日")){
						third_SunOTLine=offLineList.get(h).getCount();
					}else {
						third_NightOTLine=offLineList.get(h).getCount();
					}
				}else if(offLineList.get(h).getCostId().equals("6252")){
					if(offLineList.get(h).getShift().equals("日")){
						fouth_SunOTLine=offLineList.get(h).getCount();
					}else {
						fouth_NightOTLine=offLineList.get(h).getCount();
					}
				}else if(offLineList.get(h).getCostId().equals("9097")){
					if(offLineList.get(h).getShift().equals("日")){
						fivth_SunOTLine=offLineList.get(h).getCount();
					}else {
						fivth_NightOTLine=offLineList.get(h).getCount();
					}
				}
			}
		}
		
		if(reasonEmpList.size()>0) {
			for(int z=0;z<reasonEmpList.size();z++) {
				if(reasonEmpList.get(z).getCostId().equals("9628")) {
					if(reasonEmpList.get(z).getShift().equals("日")){
						first_SunPlanCount=reasonEmpList.get(z).getCount()-first_SunOTLine;
					}else {
						first_NightPlanCount=reasonEmpList.get(z).getCount()-first_NightOTLine;
					}
				}else if(reasonEmpList.get(z).getCostId().equals("9629")){
					if(reasonEmpList.get(z).getShift().equals("日")){
						second_SunPlanCount=reasonEmpList.get(z).getCount()-second_SunOTLine;
					}else {
						second_NightPlanCount=reasonEmpList.get(z).getCount()-second_NightOTLine;
					}
				}else if(reasonEmpList.get(z).getCostId().equals("6251")){
					if(reasonEmpList.get(z).getShift().equals("日")){
						third_SunPlanCount=reasonEmpList.get(z).getCount()-third_SunOTLine;
					}else {
						third_NightPlanCount=reasonEmpList.get(z).getCount()-third_NightOTLine;
					}
				}else if(reasonEmpList.get(z).getCostId().equals("6252")){
					if(reasonEmpList.get(z).getShift().equals("日")){
						fouth_SunPlanCount=reasonEmpList.get(z).getCount()-fouth_SunOTLine;
					}else {
						fouth_NightPlanCount=reasonEmpList.get(z).getCount()-fouth_NightOTLine;
					}
				}else if(reasonEmpList.get(z).getCostId().equals("9097")){
					if(reasonEmpList.get(z).getShift().equals("日")){
						fivth_SunPlanCount=reasonEmpList.get(z).getCount()-fivth_SunOTLine;
					}else {
						fivth_NightPlanCount=reasonEmpList.get(z).getCount()-fivth_NightOTLine;
					}
				}
			}
		}else {
			first_SunPlanCount=0-first_NightOTLine;
			first_NightPlanCount=0-first_NightOTLine;
			second_SunPlanCount=0-second_NightOTLine;
			second_NightPlanCount=0-second_NightOTLine;
			third_SunPlanCount=0-third_NightOTLine;
			third_NightPlanCount=0-third_NightOTLine;
			fouth_SunPlanCount=0-fouth_NightOTLine;
			fouth_NightPlanCount=0-fouth_NightOTLine;
			fivth_SunPlanCount=0-fivth_NightOTLine;
			fivth_NightPlanCount=0-fivth_NightOTLine;		
		}
		
		if(countEmpList.size()>0) {
			for(int i=0;i<countEmpList.size();i++) {
				if(countEmpList.get(i).getCostId().equals("9628")) {
					First.add(countEmpList.get(i));
				}else if(countEmpList.get(i).getCostId().equals("9629")){
					Second.add(countEmpList.get(i));
				}else if(countEmpList.get(i).getCostId().equals("6251")){
					Third.add(countEmpList.get(i));
				}else if(countEmpList.get(i).getCostId().equals("6252")){
					Fouth.add(countEmpList.get(i));
				}else if(countEmpList.get(i).getCostId().equals("9097")){
					Fivth.add(countEmpList.get(i));
				}
			}
		}	
		if(First.size()>0) {
			for(int j=0;j<First.size();j++) {
				//日班
				if(First.get(j).getClass_No()!="11") {
					first_SunCount++;
					if(First.get(j).getStatus().indexOf("4_")!=-1) {
						first_SunNP++;
					}else if(First.get(j).getStatus().indexOf("3_")!=-1) {
						first_SunExpor++;
					}else if(First.get(j).getStatus().indexOf("5_")!=-1) {
						first_SunDimiss++;
					}else if(First.get(j).getStatus().indexOf("1_")!=-1) {
						first_SunLeave++;
					}else if(First.get(j).getStatus().indexOf("2_")!=-1) {
						first_SunABS++;
					}else if(First.get(j).getStatus().indexOf("6_")!=-1) {
						first_SunATP++;
					}
				}else {
					//夜班
					first_NightCount++;
					if(First.get(j).getStatus().indexOf("4_")!=-1) {
						first_NightNP++;
					}else if(First.get(j).getStatus().indexOf("3_")!=-1) {
						first_NightExpor++;
					}else if(First.get(j).getStatus().indexOf("5_")!=-1) {
						first_NightDimiss++;
					}else if(First.get(j).getStatus().indexOf("1_")!=-1) {
						first_NightLeave++;
					}else if(First.get(j).getStatus().indexOf("2_")!=-1) {
						first_NightABS++;
					}else if(First.get(j).getStatus().indexOf("6_")!=-1) {
						first_NightATP++;
					}
				}
			}
		}
		List<Map<String,Integer>> FirstCost = new ArrayList<Map<String,Integer>>();
        Map<String,Integer>  FirstSun = new HashMap<String,Integer>();
        Map<String,Integer>  FirstNight = new HashMap<String,Integer>();
        FirstSun.put("Cost",9628);
        FirstSun.put("Class_No",3);
        FirstSun.put("Count",first_SunCount);
        FirstSun.put("NP",first_SunNP);
        FirstSun.put("Expor",first_SunExpor);
        FirstSun.put("Dimiss",first_SunDimiss);
        FirstSun.put("Leave",first_SunLeave);
        FirstSun.put("ABS",first_SunABS);
        FirstSun.put("ATP",first_SunATP);
        FirstSun.put("OTL",first_SunOTLine);
        FirstSun.put("PC",first_SunPlanCount);
        FirstCost.add( FirstSun);
        
        FirstNight.put("Cost",9628);
        FirstNight.put("Class_No",11);
        FirstNight.put("Count",first_NightCount);
        FirstNight.put("NP",first_NightNP);
        FirstNight.put("Expor",first_NightExpor);
        FirstNight.put("Dimiss",first_NightDimiss);
        FirstNight.put("Leave",first_NightLeave);
        FirstNight.put("ABS",first_NightABS);
        FirstNight.put("ATP",first_NightATP);
        FirstNight.put("OTL",first_NightOTLine);
        FirstNight.put("PC",first_NightPlanCount);
        FirstCost.add( FirstNight);

		if(Second.size()>0) {
			for(int a=0;a<Second.size();a++) {
				if(Second.get(a).getClass_No()!="11") {
					second_SunCount++;
					if(Second.get(a).getStatus().indexOf("4_")!=-1) {
						second_SunNP++;
					}else if(Second.get(a).getStatus().indexOf("3_")!=-1) {
						second_SunExpor++;
					}else if(Second.get(a).getStatus().indexOf("5_")!=-1) {
						second_SunDimiss++;
					}else if(Second.get(a).getStatus().indexOf("1_")!=-1) {
						second_SunLeave++;
					}else if(Second.get(a).getStatus().indexOf("2_")!=-1) {
						second_SunABS++;
					}else if(Second.get(a).getStatus().indexOf("6_")!=-1) {
						second_SunATP++;
					}
				}else {
					//夜班
					second_NightCount++;
					if(Second.get(a).getStatus().indexOf("4_")!=-1) {
						second_NightNP++;
					}else if(Second.get(a).getStatus().indexOf("3_")!=-1) {
						second_NightExpor++;
					}else if(Second.get(a).getStatus().indexOf("5_")!=-1) {
						second_NightDimiss++;
					}else if(Second.get(a).getStatus().indexOf("1_")!=-1) {
						second_NightLeave++;
					}else if(Second.get(a).getStatus().indexOf("2_")!=-1) {
						second_NightABS++;
					}else if(Second.get(a).getStatus().indexOf("6_")!=-1) {
						second_NightATP++;
					}
				}
			}
		}
		List<Map<String,Integer>> SecondCost = new ArrayList<Map<String,Integer>>();
        Map<String,Integer> SecondSun = new HashMap<String,Integer>();
        Map<String,Integer> SecondNight = new HashMap<String,Integer>();
        SecondSun.put("Cost",9629);
        SecondSun.put("Class_No",3);
        SecondSun.put("Count",second_SunCount);
        SecondSun.put("NP",second_SunNP);
        SecondSun.put("Expor",second_SunExpor);
        SecondSun.put("Dimiss",second_SunDimiss);
        SecondSun.put("Leave",second_SunLeave);
        SecondSun.put("ABS",second_SunABS);
        SecondSun.put("ATP",second_SunATP);
        SecondSun.put("OTL",second_SunOTLine);
        SecondSun.put("PC",second_SunPlanCount);
        SecondCost.add(SecondSun);
        
        SecondNight.put("Cost",9629);
        SecondNight.put("Class_No",11);
        SecondNight.put("Count",second_NightCount);
        SecondNight.put("NP",second_NightNP);
        SecondNight.put("Expor",second_NightExpor);
        SecondNight.put("Dimiss",second_NightDimiss);
        SecondNight.put("Leave",second_NightLeave);
        SecondNight.put("ABS",second_NightABS);
        SecondNight.put("ATP",second_NightATP);
        SecondNight.put("OTL",second_SunOTLine);
        SecondNight.put("PC",second_SunPlanCount);
        SecondCost.add(SecondNight);
        
        
        if(Third.size()>0) {
			for(int j=0;j<Third.size();j++) {
				//日班
				if(Third.get(j).getClass_No()!="11") {
					third_SunCount++;
					if(Third.get(j).getStatus().indexOf("4_")!=-1) {
						third_SunNP++;
					}else if(Third.get(j).getStatus().indexOf("3_")!=-1) {
						third_SunExpor++;
					}else if(Third.get(j).getStatus().indexOf("5_")!=-1) {
						third_SunDimiss++;
					}else if(Third.get(j).getStatus().indexOf("1_")!=-1) {
						third_SunLeave++;
					}else if(Third.get(j).getStatus().indexOf("2_")!=-1) {
						third_SunABS++;
					}else if(Third.get(j).getStatus().indexOf("6_")!=-1) {
						third_SunATP++;
					}
				}else {
					//夜班
					third_NightCount++;
					if(Third.get(j).getStatus().indexOf("4_")!=-1) {
						third_NightNP++;
					}else if(Third.get(j).getStatus().indexOf("3_")!=-1) {
						third_NightExpor++;
					}else if(Third.get(j).getStatus().indexOf("5_")!=-1) {
						third_NightDimiss++;
					}else if(Third.get(j).getStatus().indexOf("1_")!=-1) {
						third_NightLeave++;
					}else if(Third.get(j).getStatus().indexOf("2_")!=-1) {
						third_NightABS++;
					}else if(Third.get(j).getStatus().indexOf("6_")!=-1) {
						third_NightATP++;
					}
				}
			}
		}
		List<Map<String,Integer>> ThirdCost = new ArrayList<Map<String,Integer>>();
        Map<String,Integer>  ThirdSun = new HashMap<String,Integer>();
        Map<String,Integer>  ThirdNight = new HashMap<String,Integer>();
        ThirdSun.put("Cost",6251);
        ThirdSun.put("Class_No",3);
        ThirdSun.put("Count",third_SunCount);
        ThirdSun.put("NP",third_SunNP);
        ThirdSun.put("Expor",third_SunExpor);
        ThirdSun.put("Dimiss",third_SunDimiss);
        ThirdSun.put("Leave",third_SunLeave);
        ThirdSun.put("ABS",third_SunABS);
        ThirdSun.put("ATP",third_SunATP);
        ThirdSun.put("OTL",third_SunOTLine);
        ThirdSun.put("PC",third_SunPlanCount);
        ThirdCost.add( ThirdSun);
        
        ThirdNight.put("Cost",6251);
        ThirdNight.put("Class_No",11);
        ThirdNight.put("Count",third_NightCount);
        ThirdNight.put("NP",third_NightNP);
        ThirdNight.put("Expor",third_NightExpor);
        ThirdNight.put("Dimiss",third_NightDimiss);
        ThirdNight.put("Leave",third_NightLeave);
        ThirdNight.put("ABS",third_NightABS);
        ThirdNight.put("ATP",third_NightATP);
        ThirdNight.put("OTL",third_NightOTLine);
        ThirdNight.put("PC",third_NightPlanCount);
        ThirdCost.add( ThirdNight);

        if(Fouth.size()>0) {
			for(int j=0;j<Fouth.size();j++) {
				//日班
				if(Fouth.get(j).getClass_No()!="11") {
					fouth_SunCount++;
					if(Fouth.get(j).getStatus().indexOf("4_")!=-1) {
						fouth_SunNP++;
					}else if(Fouth.get(j).getStatus().indexOf("3_")!=-1) {
						fouth_SunExpor++;
					}else if(Fouth.get(j).getStatus().indexOf("5_")!=-1) {
						fouth_SunDimiss++;
					}else if(Fouth.get(j).getStatus().indexOf("1_")!=-1) {
						fouth_SunLeave++;
					}else if(Fouth.get(j).getStatus().indexOf("2_")!=-1) {
						fouth_SunABS++;
					}else if(Fouth.get(j).getStatus().indexOf("6_")!=-1) {
						fouth_SunATP++;
					}
				}else {
					//夜班
					fouth_NightCount++;
					if(Fouth.get(j).getStatus().indexOf("4_")!=-1) {
						fouth_NightNP++;
					}else if(Fouth.get(j).getStatus().indexOf("3_")!=-1) {
						fouth_NightExpor++;
					}else if(Fouth.get(j).getStatus().indexOf("5_")!=-1) {
						fouth_NightDimiss++;
					}else if(Fouth.get(j).getStatus().indexOf("1_")!=-1) {
						fouth_NightLeave++;
					}else if(Fouth.get(j).getStatus().indexOf("2_")!=-1) {
						fouth_NightABS++;
					}else if(Fouth.get(j).getStatus().indexOf("6_")!=-1) {
						fouth_NightATP++;
					}
				}
			}
		}
		List<Map<String,Integer>> FouthCost = new ArrayList<Map<String,Integer>>();
        Map<String,Integer>  FouthSun = new HashMap<String,Integer>();
        Map<String,Integer>  FouthNight = new HashMap<String,Integer>();
        FouthSun.put("Cost",6252);
        FouthSun.put("Class_No",3);
        FouthSun.put("Count",fouth_SunCount);
        FouthSun.put("NP",fouth_SunNP);
        FouthSun.put("Expor",fouth_SunExpor);
        FouthSun.put("Dimiss",fouth_SunDimiss);
        FouthSun.put("Leave",fouth_SunLeave);
        FouthSun.put("ABS",fouth_SunABS);
        FouthSun.put("ATP",fouth_SunATP);
        FouthSun.put("OTL",fouth_SunOTLine);
        FouthSun.put("PC",fouth_SunPlanCount);
        FouthCost.add( FouthSun);
        
        FouthNight.put("Cost",6252);
        FouthNight.put("Class_No",11);
        FouthNight.put("Count",fouth_NightCount);
        FouthNight.put("NP",fouth_NightNP);
        FouthNight.put("Expor",fouth_NightExpor);
        FouthNight.put("Dimiss",fouth_NightDimiss);
        FouthNight.put("Leave",fouth_NightLeave);
        FouthNight.put("ABS",fouth_NightABS);
        FouthNight.put("ATP",fouth_NightATP);
        FouthNight.put("OTL",fouth_NightOTLine);
        FouthNight.put("PC",fouth_NightPlanCount);
        FouthCost.add( FouthNight);

        if(Fivth.size()>0) {
			for(int j=0;j<Fivth.size();j++) {
				//日班
				if(Fivth.get(j).getClass_No()!="11") {
					fivth_SunCount++;
					if(Fivth.get(j).getStatus().indexOf("4_")!=-1) {
						fivth_SunNP++;
					}else if(Fivth.get(j).getStatus().indexOf("3_")!=-1) {
						fivth_SunExpor++;
					}else if(Fivth.get(j).getStatus().indexOf("5_")!=-1) {
						fivth_SunDimiss++;
					}else if(Fivth.get(j).getStatus().indexOf("1_")!=-1) {
						fivth_SunLeave++;
					}else if(Fivth.get(j).getStatus().indexOf("2_")!=-1) {
						fivth_SunABS++;
					}else if(Fivth.get(j).getStatus().indexOf("6_")!=-1) {
						fivth_SunATP++;
					}
				}else {
					//夜班
					fivth_NightCount++;
					if(Fivth.get(j).getStatus().indexOf("4_")!=-1) {
						fivth_NightNP++;
					}else if(Fivth.get(j).getStatus().indexOf("3_")!=-1) {
						fivth_NightExpor++;
					}else if(Fivth.get(j).getStatus().indexOf("5_")!=-1) {
						fivth_NightDimiss++;
					}else if(Fivth.get(j).getStatus().indexOf("1_")!=-1) {
						fivth_NightLeave++;
					}else if(Fivth.get(j).getStatus().indexOf("2_")!=-1) {
						fivth_NightABS++;
					}else if(Fivth.get(j).getStatus().indexOf("6_")!=-1) {
						fivth_NightATP++;
					}
				}
			}
		}
		List<Map<String,Integer>> FivthCost = new ArrayList<Map<String,Integer>>();
        Map<String,Integer>  FivthSun = new HashMap<String,Integer>();
        Map<String,Integer>  FivthNight = new HashMap<String,Integer>();
        FivthSun.put("Cost",9097);
        FivthSun.put("Class_No",3);
        FivthSun.put("Count",fivth_SunCount);
        FivthSun.put("NP",fivth_SunNP);
        FivthSun.put("Expor",fivth_SunExpor);
        FivthSun.put("Dimiss",fivth_SunDimiss);
        FivthSun.put("Leave",fivth_SunLeave);
        FivthSun.put("ABS",fivth_SunABS);
        FivthSun.put("ATP",fivth_SunATP);
        FivthSun.put("OTL",fivth_SunOTLine);
        FivthSun.put("PC",fivth_SunPlanCount);
        FivthCost.add( FivthSun);
        
        FivthNight.put("Cost",9097);
        FivthNight.put("Class_No",11);
        FivthNight.put("Count",fivth_NightCount);
        FivthNight.put("NP",fivth_NightNP);
        FivthNight.put("Expor",fivth_NightExpor);
        FivthNight.put("Dimiss",fivth_NightDimiss);
        FivthNight.put("Leave",fivth_NightLeave);
        FivthNight.put("ABS",fivth_NightABS);
        FivthNight.put("ATP",fivth_NightATP);
        FivthNight.put("OTL",fivth_NightOTLine);
        FivthNight.put("PC",fivth_NightPlanCount);
        FivthCost.add( FivthNight);

		
		/*System.out.println("1:"+FirstCost);
		System.out.println("2:"+SecondCost);
		System.out.println("3:"+ThirdCost);
		System.out.println("4:"+FouthCost);
		System.out.println("5:"+FivthCost);*/
		Gson gson = new GsonBuilder().serializeNulls().create();
		if(FirstCost.size() == 0 && SecondCost.size() == 0&& ThirdCost.size() == 0&& FouthCost.size() == 0&& FivthCost.size() == 0) {
			result.addProperty("StatusCode", "500");
	        result.addProperty("message", "查無數據");
		}else {
			result.addProperty("StatusCode", "200");
			result.addProperty("First", gson.toJson(FirstCost));
			result.addProperty("Second", gson.toJson(SecondCost));
			result.addProperty("Third", gson.toJson(ThirdCost));
			result.addProperty("Fouth", gson.toJson(FouthCost));
			result.addProperty("Fivth", gson.toJson(FivthCost));
			//result.addProperty("countEmpList", gson.toJson(countEmpList));
		}
		
		/*System.out.println(result.toString());*/
		return result.toString();
	}

	public String ShowCountEmpBCList(String costid, String sDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		List<String> allStatus = countEmpByCostIDDAO.FindDepidRecords(costid,sDate);
		List<ManPowerStatus>countEmpBCList = countEmpByCostIDDAO.searchCountEmpBCList(costid,sDate);
		List<Prod>pEmpList = countEmpByCostIDDAO.searchpEmpList(costid,sDate);
		Gson gson = new GsonBuilder().serializeNulls().create();
		if(countEmpBCList.size() == 0 || countEmpBCList == null) {
			result.addProperty("StatusCode", "500");
	        result.addProperty("message", "查無數據");
		}else {
			result.addProperty("StatusCode", "200");
			result.addProperty("countEmpBCList", gson.toJson(countEmpBCList));
			result.addProperty("Depid", gson.toJson(allStatus));
			result.addProperty("ProdEmp", gson.toJson(pEmpList));
		}
		//return "abc";
		return result.toString();
	}

	public String ShowABEmpList(String costid, String sDate) {
		// TODO Auto-generated method stub
		JsonObject result = new JsonObject();
		List<Prod>ABEmpList = countEmpByCostIDDAO.searchABEmpList(costid,sDate);
		//System.out.println(ABEmpList);
		Gson gson = new GsonBuilder().serializeNulls().create();
		if(ABEmpList.size() == 0 || ABEmpList == null) {
			result.addProperty("StatusCode", "500");
	        result.addProperty("message", "查無數據");
		}else {
			result.addProperty("StatusCode", "200");
			result.addProperty("ABEmp", gson.toJson(ABEmpList));
		}
		//return "abc";
		return result.toString();
	}

}
