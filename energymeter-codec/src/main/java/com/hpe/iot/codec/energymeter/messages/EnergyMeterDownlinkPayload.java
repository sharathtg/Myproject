package com.hpe.iot.codec.energymeter.messages;

import com.google.gson.annotations.SerializedName;

public class EnergyMeterDownlinkPayload {
	
	@SerializedName("Message Type")
    private MessageType messageType;

    @SerializedName("Value")
    private byte value;

    public EnergyMeterDownlinkPayload(MessageType messageType, byte value) {
        this.messageType = messageType;
        this.value = value;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

}
