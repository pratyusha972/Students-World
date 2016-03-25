package com.prgguru.jersey;

import javax.ws.rs.Path;

@Path("/gmailsender")
public class Send {
	String recepient;
	
	public Send(String recepient){
		this.recepient=recepient;
		
	}
	@Path("/sendmail")
	public int sendm()
	{
		GMailSender sender = new GMailSender("coolrakeshrakesh96@gmail.com", "rakeshrak");
		try{
		//System.out.println("sending ... ");
			generateOTP otp= new generateOTP(this.recepient);
			String otp_string = otp.nextSessionId();
			sender.sendMail("enter the otp","your otp "+ otp_string,"coolrakeshrakesh96@gmail.com",this.recepient);
			DBConnection.updateUser("otp", otp_string, false,this.recepient);
			
			System.out.println(otp_string);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return 1;
	}
}