package com.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class ReqCounter
 */
@WebFilter("/ReqCounter")
public class ReqCounter implements Filter {

    /**
     * Default constructor. 
     */
    public ReqCounter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
    
    int count;
    FilterConfig fg;
    Calendar cal = Calendar.getInstance();
	int cur_minute = cal.get(Calendar.MINUTE);
		
	public void destroy() {
		this.fg = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		count++;
		ServletContext sc = fg.getServletContext();
		sc.setAttribute("counter", count);
		
//		System.out.println("Current Minute: "+cal.get(Calendar.MINUTE));
//		System.out.println("Current Second: "+cal.get(Calendar.SECOND));
//		System.out.println("Current Request Count: "+sc.getAttribute("counter"));
//		System.out.println("Current URL: "+req.getRequestURL() +"?"+req.getQueryString());
//		System.out.println("Current Method: "+req.getMethod());
		
		LocalTime curTime = LocalTime.now();
		int temp = curTime.getMinute();
		
		if(cur_minute != temp){
			
			cur_minute = temp;
			count = 1;
			
			sc.setAttribute("counter", count);
			
			if(res.containsHeader("Request-Counter")){
				res.setHeader("Request-Counter", "No");
				chain.doFilter(request, response);
			}
			else{
				res.setHeader("Request-Counter", "No");
				chain.doFilter(request, response);
			}
			
			
		}
		else{
			
			if(count > 30){
				count = 1;
				
				if(res.containsHeader("Request-Counter")){
					res.setHeader("Request-Counter", "Limit was exceeded");
					chain.doFilter(request, response);	
				}
				else{
					res.setHeader("Request-Counter", "Limit was exceeded");
					chain.doFilter(request, response);
				}
				
			}
			else{
				res.setHeader("Request-Counter", "No");
				chain.doFilter(request, response);
			}
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		this.fg = fConfig;
		System.out.println("Tourpubg filter initialized");
	}

}
