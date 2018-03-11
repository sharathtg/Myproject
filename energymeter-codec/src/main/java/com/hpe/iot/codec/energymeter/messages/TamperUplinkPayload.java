package com.hpe.iot.codec.energymeter.messages;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class TamperUplinkPayload {
	
	@SerializedName("Meter Data Type")
    private String meterDataType;
	
	@SerializedName("Tamper Response Type")
    private Integer responseType;

    @SerializedName("Tamper data no. of Records")
    private Integer records;
    
    @SerializedName("Tamper Code")
    private Integer code;
    
    @SerializedName("Tamper Request Description")
    private String codeDesc;
    
    @SerializedName("Tamper Start UTC Time")
    private String startUtcTime;
    
    @SerializedName("Tamper End UTC Time")
    private String endUtcTime;

	public Integer getResponseType() {
		return responseType;
	}

	public void setResponseType(Integer responseType) {
		this.responseType = responseType;
	}

	public Integer getRecords() {
		return records;
	}

	public void setRecords(Integer records) {
		this.records = records;
	}

	public Integer getCode() {
		return code;
		}
	
		public void setCode(Integer code) {
			this.code = code;
		}
	
		public String getCodeDesc() {
			return codeDesc;
		}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getStartUtcTime() {
		return startUtcTime;
	}

	public void setStartUtcTime(String string) {
		this.startUtcTime = string;
	}

	public String getEndUtcTime() {
		return endUtcTime;
	}

	public void setEndUtcTime(String string) {
		this.endUtcTime = string;
	}

	public String getMeterDataType() {
		return meterDataType;
	}

	public void setMeterDataType(String meterDataType) {
		this.meterDataType = meterDataType;
	}

}
