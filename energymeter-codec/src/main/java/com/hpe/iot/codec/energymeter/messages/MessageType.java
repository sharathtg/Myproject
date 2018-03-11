package com.hpe.iot.codec.energymeter.messages;

import com.google.gson.annotations.SerializedName;

public enum MessageType {
    @SerializedName("PERIODIC")
    PERIODIC,

    @SerializedName("GPS")
    GPS
}
