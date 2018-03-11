package com.hpe.iot.codec.energymeter.messages;

import com.google.gson.annotations.SerializedName;

public class InstantaneousUplinkPayload {
	
	@SerializedName("Meter Data Type")
    private String meterDataType;
	
    @SerializedName("Meter UTC Time")
    private String meterUtcTime;

    @SerializedName("Cumulative Active Energy KWH")
    private Double activeEnergy;

    @SerializedName("Cumulative Apparent Energy KVAH")
    private Double apparentEnergy;

    @SerializedName("Inst. Voltage V")
    private Double voltage;

    @SerializedName("Inst. Current A")
    private Double current;

    @SerializedName("Inst. Load")
    private Double load;

    @SerializedName("Inst. Power factor sign")
    private Double factorSign;

    @SerializedName("Inst. Power factor")
    private Double factorValue;

    @SerializedName("Inst. Frequency Hz")
    private Double frequency;

	public String getMeterUtcTime() {
		return meterUtcTime;
	}

	public void setMeterUtcTime(String meterUtcTime) {
		this.meterUtcTime = meterUtcTime;
	}

	public Double getActiveEnergy() {
		return activeEnergy;
	}

	public void setActiveEnergy(Double activeEnergy) {
		this.activeEnergy = activeEnergy;
	}

	public Double getApparentEnergy() {
		return apparentEnergy;
	}

	public void setApparentEnergy(Double apparentEnergy) {
		this.apparentEnergy = apparentEnergy;
	}

	public Double getVoltage() {
		return voltage;
	}

	public void setVoltage(Double voltage) {
		this.voltage = voltage;
	}

	public Double getCurrent() {
		return current;
	}

	public void setCurrent(Double current) {
		this.current = current;
	}

	public Double getLoad() {
		return load;
	}

	public void setLoad(Double load) {
		this.load = load;
	}

	public Double getFactorSign() {
		return factorSign;
	}

	public void setFactorSign(Double factorSign) {
		this.factorSign = factorSign;
	}

	public Double getFactorValue() {
		return factorValue;
	}

	public void setFactorValue(Double factorValue) {
		this.factorValue = factorValue;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}

	public String getMeterDataType() {
		return meterDataType;
	}

	public void setMeterDataType(String meterDataType) {
		this.meterDataType = meterDataType;
	}

}
