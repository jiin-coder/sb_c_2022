package com.kja.exam.demo.vo;

import lombok.Getter;

public class ResultData<DT>{
	
	@Getter
	private String resultCode;

	@Getter
	private String msg;
	
	@Getter
	private DT data1;
	
	
	private ResultData() {
		
	}
	
	public static ResultData from(String resultCode, String msg) {
		return from(resultCode, msg, null);
	}
	
	public static <DT> ResultData<DT> from(String resultCode, String msg, DT data1) {
		ResultData<DT> rd = new ResultData<DT>();
		rd.resultCode = resultCode;
		rd.msg = msg;
		rd.data1 = data1;
		
		return rd;
	}
	
	
	public boolean isSuccess() {
		return resultCode.startsWith("S-");
	}
	
	public boolean isFail() {
		return isSuccess() == false;
	}

	public static <DT> ResultData<DT> newData(ResultData joinRd, DT newData) {
		return from(joinRd.getResultCode(), joinRd.getMsg(), newData);
	}

	
}