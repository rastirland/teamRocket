package application;

import java.time.LocalDateTime;
import java.util.Date;

public class customerQuery {

	protected String queryURN;
	protected String queryusername;
	protected String queryemail;
	protected String querymessage;
	
	protected String queryType;
	protected String queryPrority;
	protected Date  queryOpenDate;
	protected Date  queryclosedDate;
	
	
	
	public customerQuery(String queryURN, String queryusername, String queryemail, String querymessage,
			String queryType, String queryPrority, Date queryOpenDate, Date queryclosedDate) {
	
		
		this.queryURN = queryURN;
		this.queryusername = queryusername;
		this.queryemail = queryemail;
		this.querymessage = querymessage;
		this.queryType = queryType;
		this.queryPrority = queryPrority;
		this.queryOpenDate = queryOpenDate;
		this.queryclosedDate = queryclosedDate;
		
	}
	public String getQueryType() {
		return queryType;
	}
//	public void setQueryType(String queryType) {
//		this.queryType = queryType;
//	}
	public String getQueryPrority() {
		return queryPrority;
	}
	public void setQueryPrority(String queryPrority) {
		this.queryPrority = queryPrority;
	}
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
	public void setQueryType(String queryType) {
		// TODO Auto-generated method stub
		
	}
	public void setQueryOpenDate(LocalDateTime localDateTime) {
		// TODO Auto-generated method stub
		
	}
	
	
}
