package com.hpe.iot.codec.energymeter;

import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import com.hpe.iot.codec.AbstractCodec;
import com.hpe.iot.codec.CodecException;
import com.hpe.iot.codec.CodecIOBase;
import com.hpe.iot.codec.energymeter.EnergyMeterEncoder;
import com.hpe.iot.codec.energymeter.EnergyMeterDecoder;

public class EnergyMeterCodec extends AbstractCodec{
	
	private static final String CODEC_DESCRIPTION = "Energy Meter Codec";
	private static final String CODEC_NAME = "${name}";
	private static final String CODEC_VERSION = "${version}";
	private static final String CODEC_ID = "${name}-${version}";
	public static final String PAYLOAD_KEY = "{dataFrame}";

	private final static Logger log = Logger.getLogger(EnergyMeterCodec.class);

	//--------------------------------------------------------------------------------
	// DECODE
	//--------------------------------------------------------------------------------
	public CodecIOBase decode(CodecIOBase codecIOBase) throws CodecException {
		log.info("Decoding Energy Meter data...");

		Map<String, String> input = codecIOBase.getCodecStringInputOutput();
		String inputString = input.get(PAYLOAD_KEY);
		log.debug("Input data: " + inputString);

		if(inputString == null || inputString.trim().isEmpty()){
			throw new CodecException("No input data");
		} else {
			inputString = inputString.replaceAll("\\s","").trim();
		}

		String encodedData = base64ToHex(inputString);
		log.debug("Base64 decoded: " + encodedData);

		Map<String, String> output = new EnergyMeterDecoder().decode(encodedData);
		codecIOBase.setCodecStringInputOutput(output);

        return codecIOBase;
	}


	//--------------------------------------------------------------------------------
	// ENCODE
	//--------------------------------------------------------------------------------
	public CodecIOBase encode(CodecIOBase codecIOBase) throws CodecException {
		log.info("Encoding energy meter data...");
		Map<String, String> input = codecIOBase.getCodecStringInputOutput();
		String plainData = input.get(PAYLOAD_KEY);

		if(plainData == null || plainData.trim().isEmpty()){
			throw new CodecException("No input data");
		}

		log.debug("Plain data: " + plainData);

		Map<String, byte[]> output = new EnergyMeterEncoder().encode(plainData);
		log.debug("Encoded data: " + output.get("payload"));
		codecIOBase.setCodecBinaryInputOutput(output);

		return codecIOBase;
	}
	
	public String getCodecDescription() throws CodecException {
		return CODEC_DESCRIPTION;
	}

	public String getCodecName() throws CodecException {
		return CODEC_NAME;

	}

	public String getCodecVersion() throws CodecException {
		return CODEC_VERSION;
	}

	public String getUniqueCodecIdentifier() throws CodecException {
		return CODEC_ID;
	}

	public String base64ToHex(String base64String) {
		byte[] decoded = Base64.decodeBase64(base64String);
		System.out.println(Hex.encodeHexString(decoded));
		return Hex.encodeHexString(decoded);

	}

	public String hextoBase64(String hexString) throws DecoderException {
		byte[] decodedHex = Hex.decodeHex(hexString.toCharArray());
		return Base64.encodeBase64String(decodedHex);
	}

}
