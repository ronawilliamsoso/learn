package com.example.demo.enu;

public enum Team {
	BALL("BALL", 1), CWS("CWS", 2), BOS("BOS", 3), CLE("CLE", 4), ONK("CLE", 5), NYY("CLE", 6), DET("CLE", 7), SEA(
			"CLE", 8), TB("CLE", 9), KC("CLE", 10), TEX("CLE", 11), TOR("CLE", 12), MIN("CLE", 13), ATL("CLE",
					14), CHC("CLE", 15), ARZ("CLE", 16), FLA("CLE", 17), CIN("CLE", 18), COL("CLE", 19), NYM("CLE",
							20), HOU("CLE", 21), LA("CLE", 22), PHI("CLE", 23), MLW("CLE",
									24), SD("CLE", 25), WAS("CLE", 16), PIT("CLE", 27), SF("CLE", 28), STL("CLE", 29);
	// 成员变量
	private String name;
	private int index;

	// 构造方法
	private Team(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 覆盖方法
	@Override
	public String toString() {
		return this.index + "_" + this.name;
	}
}