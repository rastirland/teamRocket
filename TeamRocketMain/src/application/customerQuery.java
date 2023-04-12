package application;

import java.util.Date;

public class customerQuery {

	protected String queryURN;
	protected String queryusername;
	protected String queryemail;
	protected String querymessage;
	protected Date  queryOpenDate;
	protected Date  queryclosedDate;
	
	
	
	public String getQueryURN() {
		return queryURN;
	}
	public void setQueryURN(String queryURN) {
		this.queryURN = queryURN;
	}
	public String getQueryusername() {
		return queryusername;
	}
	public void setQueryusername(String queryusername) {
		this.queryusername = queryusername;
	}
	public String getQueryemail() {
		return queryemail;
	}
	public void setQueryemail(String queryemail) {
		this.queryemail = queryemail;
	}
	public String getQuerymessage() {
		return querymessage;
	}
	public void setQuerymessage(String querymessage) {
		this.querymessage = querymessage;
	}
	public Date getQueryOpenDate() {
		return queryOpenDate;
	}
	public void setQueryOpenDate(Date queryOpenDate) {
		this.queryOpenDate = queryOpenDate;
	}
	public Date getQueryclosedDate() {
		return queryclosedDate;
	}
	public void setQueryclosedDate(Date queryclosedDate) {
		this.queryclosedDate = queryclosedDate;
	}
	
	
}
