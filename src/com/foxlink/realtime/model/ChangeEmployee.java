package com.foxlink.realtime.model;

public class ChangeEmployee {
	    //工號
		private String EMP_ID;
		//日期
		private String SWIPE_DATE;
		//姓名
		private String NAME;
		//姓名
		private String ID;
		private String Status;
		@Override
		public String toString() {
			return "ChangeEmployee [EMP_ID=" + EMP_ID + ", SWIPE_DATE=" + SWIPE_DATE + ", NAME=" + NAME + ", ID=" + ID
					+ ", Status=" + Status + "]";
		}
		public String getStatus() {
			return Status;
		}
		public void setStatus(String status) {
			Status = status;
		}
		public String getID() {
			return ID;
		}
		public void setID(String iD) {
			ID = iD;
		}
		public String getNAME() {
			return NAME;
		}
		public void setNAME(String nAME) {
			NAME = nAME;
		}
		public String getEMP_ID() {
			return EMP_ID;
		}
		public void setEMP_ID(String eMP_ID) {
			EMP_ID = eMP_ID;
		}
		public String getSWIPE_DATE() {
			return SWIPE_DATE;
		}
		public void setSWIPE_DATE(String sWIPE_DATE) {
			SWIPE_DATE = sWIPE_DATE;
		}
	
		
}
