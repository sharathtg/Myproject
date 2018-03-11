import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.hpe.iot.codec.energymeter.EnergyMeterCodec;
import com.hpe.iot.codec.energymeter.EnergyMeterDecoder;
import com.hpe.iot.codec.energymeter.EnergyMeterEncoder;

public class EnergyMeterCodecTest {
    private EnergyMeterDecoder energyMeterDecoder = new EnergyMeterDecoder();

    @Test
    public void testDecode() throws Exception{
        System.out.println("Testing decode method...");

        String encodedData = "82 20 B6 BD AE 66 F0 20 97 84 A8 20 94 5D DB 00 00 00 00 00 00 00 13 8D";
        System.out.println(encodedData);
        encodedData = encodedData.replaceAll("\\s", "").trim();
        Map<String, String> decodedMap = energyMeterDecoder.decode(encodedData);
        System.out.println(decodedMap.get("CapturedObjects"));

        //encodedData = "83 20 B6 93 68 20 B6 AF 88 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
        encodedData=  "83 20 bd da b0 20 be 7c 68 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
        System.out.println(encodedData);
        encodedData = encodedData.replaceAll("\\s", "").trim();
        decodedMap = energyMeterDecoder.decode(encodedData);
        System.out.println(decodedMap.get("CapturedObjects"));

        encodedData = "84 00 01 00 1F CA D6 40 1F CA D6 50";
        System.out.println(encodedData);
        encodedData = encodedData.replaceAll("\\s", "").trim();
        decodedMap = energyMeterDecoder.decode(encodedData);
        System.out.println(decodedMap.get("CapturedObjects"));
        
        encodedData = "85 20 19 08 39 00 00 06 6D";
        System.out.println(encodedData);
        encodedData = encodedData.replaceAll("\\s", "").trim();
        decodedMap = energyMeterDecoder.decode(encodedData);
        System.out.println(decodedMap.get("CapturedObjects"));
    }

    @Test
    public void testDecode64() {
        System.out.println("Testing decoding from Base64...");
        String encodedBase64 ="gyC92rAgvnxoAAAAAAAAAAAAAAAAAAAAAA==";
        assertEquals("8320bddab020be7c6800000000000000000000000000000000", new EnergyMeterCodec().base64ToHex(encodedBase64));
        
    }

    @Test
    public void testEncodeJsonToByteArray() throws Exception{
        System.out.println("Testing encode method...");
        String json = "{\n" +
                "\t\"Message Type\": \"GPS\",\n" +
                "\t\"Value\": 5\n" +
                "}";
        System.out.println("Plain data: " + json);
        EnergyMeterEncoder orbiwiseEncoder = new EnergyMeterEncoder();
        Map<String, byte[]> encMap = orbiwiseEncoder.encode(json);
        byte[] encData = encMap.get(EnergyMeterCodec.PAYLOAD_KEY);
        assertEquals((byte)0xAA, encData[0]);
        assertEquals((byte)0x02, encData[1]);
        assertEquals((byte)0x05, encData[2]);
    }
}
