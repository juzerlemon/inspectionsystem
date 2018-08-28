package ae.gov.dubaipolice.inspectionpoints.enums;

public enum YearList {
	TWOKTEN(2010),
	TWOKELEVEN(2011),
	TWOKTWELVE(2012),
	TWOKTHIRTEEN(2013),
	TWOKFOURTEEN(2014),
	TWOKFIFTEEN(2015),
	TWOKSIXTEEN(2016),
	TWOKSEVENTEEN(2017),
	TWOKEIGHTEEN(2018),
	TWOKNINTENN(2019),
	TWOKTWENTY(2020),
	TWOKTWENTYONE(2021),
	TWOKTWENTYTWO(2022),
	TWOKTWENTYTHREE(2023),
	TWOKTWENTYFOUR(2024),
	TWOKTWENTYFIVE(2025),
	TWOKTWENTYSIX(2026)
	;
	
	private Integer year;
	
	private YearList(Integer year){
		this.year=year;
	}
	public Integer getYear() {
		return year;
	}
}
