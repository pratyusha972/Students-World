package com.prgguru.jersey;

import java.util.Random;
public final class generateOTP {
  private String recepient;

  public generateOTP(String recepient){
	  this.recepient=recepient;
  }
  
  public String nextSessionId() {
    Random RandomGenerator=new Random();
    int otp=RandomGenerator.nextInt(10457853);
    return Integer.toString(otp);
  }
}