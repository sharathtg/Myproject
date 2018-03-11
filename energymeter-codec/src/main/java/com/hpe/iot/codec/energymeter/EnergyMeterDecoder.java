package com.hpe.iot.codec.energymeter;

import com.google.gson.Gson;
import com.hpe.iot.codec.CodecException;
import com.hpe.iot.codec.energymeter.messages.*;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class EnergyMeterDecoder {
	final static Logger log = Logger.getLogger(EnergyMeterDecoder.class);

	public Map<String, String> decode(String encodedData) throws CodecException {
		Map<String, String> decodingMap = new HashMap<>();
		byte[] encodedDataAsByteArray = hexToBin(encodedData);
		int responseId = Integer.parseInt(encodedData.substring(0, 2));
		String energyMeterPayloadAsJson = "";
		Gson gson = new Gson();
		switch (responseId) {
		case 82:
			InstantaneousUplinkPayload instUplinkPayload = generateInstObjectFromByteArray(encodedDataAsByteArray);
			energyMeterPayloadAsJson = gson.toJson(instUplinkPayload);
			break;
		case 83:
			LoadUplinkPayload loadUplinkPayload = generateLoadObjectFromByteArray(encodedDataAsByteArray);
			energyMeterPayloadAsJson = gson.toJson(loadUplinkPayload);
			break;
		case 84:
			TamperUplinkPayload tamperUplinkPayload = generateTamperObjectFromByteArray(encodedDataAsByteArray);
			energyMeterPayloadAsJson = gson.toJson(tamperUplinkPayload);
			break;
		case 85:
			BillingUplinkPayload billingUplinkPayload = generateBillingObjectFromByteArray(encodedDataAsByteArray);
			energyMeterPayloadAsJson = gson.toJson(billingUplinkPayload);
			break;
		}
		log.debug("Decoded data as JSON: " + energyMeterPayloadAsJson);
		decodingMap.put(EnergyMeterCodec.PAYLOAD_KEY, energyMeterPayloadAsJson);
		return decodingMap;
	}

	private InstantaneousUplinkPayload generateInstObjectFromByteArray(byte[] payload) {
		InstantaneousUplinkPayload instantaneousUplinkPayload = new InstantaneousUplinkPayload();
		instantaneousUplinkPayload.setMeterDataType("INSTA");
		instantaneousUplinkPayload.setMeterUtcTime(dayToDate(convert4ByteToDecimal(payload[1], payload[2], payload[3], payload[4])));
		instantaneousUplinkPayload.setActiveEnergy((double) (convert4ByteToDecimal(payload[5], payload[6], payload[7], payload[8])) / 10);
		instantaneousUplinkPayload.setApparentEnergy((double) (convert4ByteToDecimal(payload[9], payload[10], payload[11], payload[12])) / 10);
		instantaneousUplinkPayload.setVoltage(convert2ByteToDecimal(payload[13], payload[14]));
		instantaneousUplinkPayload.setCurrent(convert2ByteToDecimal(payload[15], payload[16]));
		instantaneousUplinkPayload.setLoad(convert2ByteToDecimal(payload[17], payload[18]));
		instantaneousUplinkPayload.setFactorSign((double) (0xFF & payload[19]) / 100);
		instantaneousUplinkPayload.setFactorValue(convert2ByteToDecimal(payload[20], payload[21]));
		instantaneousUplinkPayload.setFrequency(convert2ByteToDecimal(payload[22], payload[23]));
		return instantaneousUplinkPayload;
	}

	private LoadUplinkPayload generateLoadObjectFromByteArray(byte[] payload) {
		LoadUplinkPayload loadUplinkPayload = new LoadUplinkPayload();
		loadUplinkPayload.setMeterDataType("LOAD");
		loadUplinkPayload.setStartUtcTime(dayToDate(convert4ByteToDecimal(payload[1], payload[2], payload[3], payload[4])));
		loadUplinkPayload.setStopUtcTime(dayToDate(convert4ByteToDecimal(payload[5], payload[6], payload[7], payload[8])));
		loadUplinkPayload.setDataRecord1KWH((double) (convert4ByteToDecimal(payload[9], payload[10], payload[11], payload[12])) / 10);
		loadUplinkPayload.setDataRecord1KVAH((double) (convert4ByteToDecimal(payload[13], payload[14], payload[15], payload[16])) / 10);
		loadUplinkPayload.setDataRecord2KWH((double) (convert4ByteToDecimal(payload[17], payload[18], payload[19], payload[20])) / 10);
		loadUplinkPayload.setDataRecord2KVAH((double) (convert4ByteToDecimal(payload[21], payload[22], payload[23], payload[24])) / 10);

		return loadUplinkPayload;
	}

	private TamperUplinkPayload generateTamperObjectFromByteArray(byte[] payload) {
		TamperUplinkPayload tamperUplinkPayload = new TamperUplinkPayload();
		tamperUplinkPayload.setMeterDataType("TAMPER");
		tamperUplinkPayload.setResponseType(0xFF & payload[1]);
		tamperUplinkPayload.setRecords(0xFF & payload[2]);
		Integer code = 0xFF & payload[3];
		tamperUplinkPayload.setCode(code);
		switch (code) {
		case 0:
			tamperUplinkPayload.setCodeDesc("Reversal Phase and Neutral and Reversal of Line And Load");
			break;
		case 1:
			tamperUplinkPayload.setCodeDesc("Load Through Local Earth");
			break;
		case 2:
			tamperUplinkPayload.setCodeDesc("Neutral Disconnected");
			break;
		case 3:
			tamperUplinkPayload.setCodeDesc("Magnetic Tamper");
			break;
		case 4:
			tamperUplinkPayload.setCodeDesc("Meter Cover Open");
			break;
		}
		tamperUplinkPayload.setStartUtcTime(dayToDate(convert4ByteToDecimal(payload[4], payload[5], payload[6], payload[7])));
		tamperUplinkPayload.setEndUtcTime(dayToDate(convert4ByteToDecimal(payload[8], payload[9], payload[10], payload[11])));

		return tamperUplinkPayload;
	}

	private BillingUplinkPayload generateBillingObjectFromByteArray(byte[] payload) {
		BillingUplinkPayload billingUplinkPayload = new BillingUplinkPayload();
		billingUplinkPayload.setMeterDataType("BILLING");
		billingUplinkPayload.setUtcTime(dayToDate(convert4ByteToDecimal(payload[1], payload[2], payload[3], payload[4])));
		billingUplinkPayload.setActiveEnergy((double) (convert4ByteToDecimal(payload[5], payload[6], payload[7], payload[8])));

		return billingUplinkPayload;
	}

	/**
	 * Decimal equivalent of 4 bytes.
	 */
	public long convert4ByteToDecimal(byte b1, byte b2, byte b3, byte b4) {
		long intValue = ((0xFF & b1) << 24) | ((0xFF & b2) << 16) | ((0xFF & b3) << 8) | (0xFF & b4);
		return intValue;
	}

	/**
	 * Decimal equivalent of 2 bytes. The value should be divided by 100 and
	 * resultant value is the actual value.
	 */
	public double convert2ByteToDecimal(byte b1, byte b2) {
		int intValue = ((0xFF & b1) << 8) | (0xFF & b2);
		return (double) intValue / 100;
	}

	/**
	 * Method used to transform string field in bytes array.
	 * 
	 * @param payload
	 *            String field.
	 * @return bytes array.
	 */
	private static byte[] hexToBin(String payload) {
		int len = payload.length();
		byte[] byteArray = new byte[len / 2];
		char a, b;
		for (int i = 0; i < len; i += 2) {
			a = payload.charAt(i);
			b = payload.charAt(i + 1);
			byteArray[i / 2] = (byte) ((Character.digit(a, 16) << 4) + Character.digit(b, 16));
		}
		return byteArray;
	}

	public static String formatUTCToLocal(String dateInString) {

		DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatedDate = null;

		try {

			DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Date date = utcFormat.parse(dateInString);

			pstFormat.setTimeZone(TimeZone.getTimeZone("IST"));
			formatedDate = pstFormat.format(date);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return formatedDate;

	}

	public static String dayToDate(long x) {

		int yrs = 99, mon = 99, day = 99, tmp, jday, hrs, min, sec, d_sec, d_min, d_hrs, d_day, d_mon, d_yrs;

		long j, n;

		j = x / 60; /* whole minutes since 1/1/70 */
		d_sec = sec = (int) (x - (60 * j)); /* leftover seconds */
		n = j / 60;
		d_min = min = (int) (j - (60 * n));
		j = n / 24;
		d_hrs = hrs = (int) (n - (24 * j));
		// j = j + (365 + 366); /* whole days since 1/1/68 */
		day = (int) (j / ((4 * 365) + 1));

		tmp = (int) (j % ((4 * 365) + 1));
		if (tmp >= (31 + 29)) /* if past 2/29 */
			day++; /* add a leap day */
		yrs = (int) ((j - day) / 365); /* whole years since 1968 */
		jday = (int) (j - (yrs * 365)
				- day); /* days since 1/1 of current year */
		if (tmp <= 365 && tmp >= 60) /* if past 2/29 and a leap year then */
			jday++; /* add a leap day */
		yrs += 2000; /* calculate year */// venkatesh 1994 should helps us to
											// calculate from the year 1996.

		for (mon = 12; mon > 0; mon--) {
			switch (mon) {
			case 1:
				tmp = 0;
				break;
			case 2:
				tmp = 31;
				break;
			case 3:
				tmp = 59;
				break;
			case 4:
				tmp = 90;
				break;
			case 5:
				tmp = 120;
				break;
			case 6:
				tmp = 151;
				break;
			case 7:
				tmp = 181;
				break;
			case 8:
				tmp = 212;
				break;
			case 9:
				tmp = 243;
				break;
			case 10:
				tmp = 273;
				break;
			case 11:
				tmp = 304;
				break;
			case 12:
				tmp = 334;
				break;
			}
			if ((mon > 2) && (yrs % 4 == 0)) /* adjust for leap year */
				tmp++;
			if (jday >= tmp)
				break;
		}

		day = jday - tmp + 1; /* calculate day in month */
		d_day = day;
		d_mon = mon;
		d_yrs = yrs;

		StringBuffer buff = new StringBuffer();
		buff.append("");
		buff.append(d_yrs);
		buff.append("-");
		buff.append(d_mon);
		buff.append("-");
		buff.append(d_day);
		buff.append("T");
		if (Integer.toString(d_hrs).length() < 2) {
			buff.append("0");
		}
		buff.append(d_hrs);
		buff.append(":");
		if (Integer.toString(d_min).length() < 2) {
			buff.append("0");
		}
		buff.append(d_min);
		buff.append(":");
		if (Integer.toString(d_sec).length() < 2) {
			buff.append("0");
		}
		buff.append(d_sec);
		buff.append(".0Z");

		// String str= ""+d_yrs+"-"+d_mon+"-"+d_day+"
		// "+d_hrs+":"+d_min+":"+d_sec;
		System.out.println(formatUTCToLocal(buff.toString()));
		return formatUTCToLocal(buff.toString());

	}
	
	
	public static String dayToDate1(long x)
	
	{
		return null;
		
	}
}
