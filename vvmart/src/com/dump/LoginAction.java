package com.dump;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{
	
	private String name, pass;
	private boolean log = false;
	
	
	@Override
	public void validate() {
		try {
			
			if(name.equals(null) || pass.equals(null)){
				return;
			}
			if(name.equals("manoj") && pass.equals("jonam123")){
				log = true;
			}
			else{
				log = false;
				addActionError("Incorrect username or password");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String execute() throws Exception {
		
		if(log){

			System.out.println("Username :"+name+"\nPassword :"+pass);
			return SUCCESS;
		}
		return ERROR;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}