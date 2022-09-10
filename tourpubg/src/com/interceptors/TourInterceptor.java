package com.interceptors;

import java.time.LocalTime;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class TourInterceptor implements Interceptor {

	public boolean flag = false;
	public int count = 0;
	
	Calendar cal = Calendar.getInstance();
	int prev_minute = cal.get(Calendar.MINUTE);
	int prev_second = cal.get(Calendar.SECOND);
	String previousURL = "", previousMethod = "";

	@Override
	public void destroy() {
		System.out.println("Tour Interceptor destroyed");
	}

	@Override
	public void init() {
		System.out.println("Tour Interceptor Initialized");
	}

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
	
		LocalTime curTime = LocalTime.now();
		int curMinute = curTime.getMinute();
		int curSecond = curTime.getSecond();
		final ActionContext context = ai.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) context.get(StrutsStatics.HTTP_RESPONSE);
				
		String currentURL = request.getRequestURL().toString();
		String currentMethod = request.getMethod();
		
//		System.out.println("Prev Sec: "+prev_second+" Cur Second: "+curSecond);
		
		if(prev_minute == curMinute){
			
			if(prev_second <= curSecond && curSecond <= prev_second + 2){
				
				if(previousURL != null && previousURL != "" ){
					
					if(currentURL.equals(previousURL)){
						count++;
					}
					else{
						previousURL = request.getRequestURL().toString();
						previousMethod = request.getMethod();
					}
				}
				else{
					count++;
					previousURL = currentURL;
					previousMethod = currentMethod;
				}
			}
			else{
				prev_second = curSecond;
				count = 0;
			}
		}
		else{
			prev_minute = curMinute;
			prev_second = curSecond;
			count = 0;
		}
		
		
		System.out.println("~Tour~ Interceptor URL: "+request.getRequestURL());
		System.out.println("~Tour~ Interceptor Method: "+request.getMethod());
		System.out.println("~Tour~ Interceptor Query: "+request.getQueryString());
		System.out.println("~Tour~ Interceptor count: "+count);
		
		if(count >= 5){
			flag = true;
			count = 0;
		}
		else{
			flag = false;
		}
		
		if(flag){
			System.out.println("###Blocked by Interceptor: Tour###");
//			response.setHeader("Concurrent-Limit", "No");
			response.setStatus(409);
			flag = false;
			count = 0;
			return ai.invoke();
		}
		else{
			return ai.invoke();
		}
	
	}

}
