package com.hpe.iot.codec.energymeter;

import com.google.gson.Gson;
import com.hpe.iot.codec.CodecException;
import com.hpe.iot.codec.energymeter.messages.EnergyMeterDownlinkPayload;
import com.hpe.iot.codec.energymeter.EnergyMeterCodec;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class EnergyMeterEncoder {
	
	private final static Logger log = Logger.getLogger(EnergyMeterEncoder.class);

    public Map<String, byte[]> encode(String plainData) throws CodecException {
        Gson gson = new Gson();
        EnergyMeterDownlinkPayload downlinkPayload = gson.fromJson(plainData, EnergyMeterDownlinkPayload.class);
        byte[] encData = generateByteArrayFromObject(downlinkPayload);
        Map<String, byte[]> encMap = new HashMap<>();
        encMap.put(EnergyMeterCodec.PAYLOAD_KEY, encData);
        return encMap;
    }

    private byte[] generateByteArrayFromObject(EnergyMeterDownlinkPayload payload) {
        byte[] encdata = new byte[3];
        if (payload == null) {
            log.warn("No data to encode");
        } else {
            encdata[0] = (byte) 0xAA;
            switch (payload.getMessageType()) {
                case GPS:
                    encdata[1] = 0x02;
                    break;
                case PERIODIC:
                    encdata[1] = 0x01;
                    break;
                default:
                    encdata[1] = 0x00;
                    break;
            }
            encdata[2] = payload.getValue();
        }

        return encdata;
    }

}
