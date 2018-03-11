package com.hpe.iot.codec.energymeter.messages;

import com.google.gson.annotations.SerializedName;

public class LoadUplinkPayload {
	
	@SerializedName("Meter Data Type")
    private String meterDataType;
	
	@SerializedName("Start UTC Time")
    private String startUtcTime;

    @SerializedName("Stop UTC Time")
    private String stopUtcTime;

    @SerializedName("Load Profile Data Record 1 KWH")
    private Double dataRecord1KWH;

    @SerializedName("Load Profile Data Record 1 KVAH")
    private Double dataRecord1KVAH;

    @SerializedName("Load Profile Data Record 2 KWH")
    private Double dataRecord2KWH;

    @SerializedName("Load Profile Data Record 2 KVAH")
    private Double dataRecord2KVAH;

	public String getStartUtcTime() {
		return startUtcTime;
	}

	public void setStartUtcTime(String startUtcTime) {
		this.startUtcTime = startUtcTime;
	}

	public String getStopUtcTime() {
		return stopUtcTime;
	}

	public void setStopUtcTime(String stopUtcTime) {
		this.stopUtcTime = stopUtcTime;
	}

	public Double getDataRecord1KWH() {
		return dataRecord1KWH;
	}

	public void setDataRecord1KWH(Double dataRecord1KWH) {
		this.dataRecord1KWH = dataRecord1KWH;
	}

	public Double getDataRecord1KVAH() {
		return dataRecord1KVAH;
	}

	public void setDataRecord1KVAH(Double dataRecord1KVAH) {
		this.dataRecord1KVAH = dataRecord1KVAH;
	}

	public Double getDataRecord2KWH() {
		return dataRecord2KWH;
	}

	public void setDataRecord2KWH(Double dataRecord2KWH) {
		this.dataRecord2KWH = dataRecord2KWH;
	}

	public Double getDataRecord2KVAH() {
		return dataRecord2KVAH;
	}

	public void setDataRecord2KVAH(Double dataRecord2KVAH) {
		this.dataRecord2KVAH = dataRecord2KVAH;
	}

	public String getMeterDataType() {
		return meterDataType;
	}

	public void setMeterDataType(String meterDataType) {
		this.meterDataType = meterDataType;
	}


}
