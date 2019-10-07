package com.jp.omo.component;

public enum ApplicationConstant {
	
	Active("1"),
	
	CouponAvailSuccessfull("Successful"),
	CouponAvailfailed("Failed"),
	
	
	
	Percentage("1"),
	fixedValue("2"),
	//1 - true , 2 - user usage limit exceeded, 3- is not applicable anymore (time validation failed)
	Applicable("1"),
	Inactive("2"),
	UserUsageLimitExceeded("3"),
	CouponCountExhausted("4"),
	CouponExpired("5"),
	CouponNotFound("6");
	
	
	
	
	public String value;
	
	ApplicationConstant(String status){
		this.value= status;
	}
	public String getStatus() {
		return value;
	}
	public void setStatus(String status) {
		this.value = status;
	}
	

}
