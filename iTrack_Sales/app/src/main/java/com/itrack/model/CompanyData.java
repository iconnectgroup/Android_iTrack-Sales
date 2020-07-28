package com.itrack.model;

public class CompanyData {
			
	
	String Name;
	String Amount;
	String Count;
	String AvgSale;
	String Margin;

	public String getCount() {
		return Count;
	}

	public void setCount(String count) {
		Count = count;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getAvgSale() {
		return AvgSale;
	}

	public void setAvgSale(String avgSale) {
		AvgSale = avgSale;
	}

	public String getMargin() {
		return Margin;
	}

	public void setMargin(String margin) {
		Margin = margin;
	}

}
