package com.foxlink.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.foxlink.realtime.service.WorkshopAccessABService;

@Component
@EnableScheduling
@Lazy(false)
public class WorkshopAccessABTask {
	
	@Autowired
	private WorkshopAccessABService workshopAccessABTask;
	
	@Scheduled(cron = "12 30 09 * * *")
    public void cronDay() {
		System.out.println("start");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cld = Calendar.getInstance();
    	cld.add(Calendar.DATE, -1);
    	String strDate=df.format(cld.getTime());
    	workshopAccessABTask.sendWorkshopNoAccessAB(strDate);
	}

}
