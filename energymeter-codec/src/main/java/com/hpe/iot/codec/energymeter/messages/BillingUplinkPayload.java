	package com.hpe.iot.codec.energymeter.messages;

import com.google.gson.annotations.SerializedName;

public class BillingUplinkPayload {
	
	@SerializedName("Meter Data Type")
    private String meterDataType;
	
	@SerializedName("UTC Time")
    private String utcTime;

    @SerializedName("Cumulative Active Energy KWH")
    private Double activeEnergy;

	public String getUtcTime() {
		return utcTime;
	}

	public void setUtcTime(String utcTime) {
		this.utcTime = utcTime;
	}

	public Double getActiveEnergy() {
		return activeEnergy;
	}

	public void setActiveEnergy(Double activeEnergy) {
		this.activeEnergy = activeEnergy;
	}

	public String getMeterDataType() {
		return meterDataType;
	}

	public void setMeterDataType(String meterDataType) {
		this.meterDataType = meterDataType;
	}

}
