package com.zhaogang.other.serializer;

import com.zhaogang.other.serializer.util.ProtostuffUtils;
import java.util.Date;

import org.apache.curator.framework.CuratorFramework;

import com.zhaogang.other.excel.read.dto.DemoData;
import com.zhaogang.other.zk.curator.factory.ZKFactory;
import org.apache.zookeeper.data.Stat;

/**
 * @author weiguo.liu
 * @date 2021/3/31
 * @description
 */
public class Main {
    public static void main(String[] args) throws Exception {
        DemoData data = new DemoData();
        data.setString("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VW5pdFdheQEAE2dldFB1cmNoYXNlUXVhbnRpdHkBABFnZXRQdXJjaGFzZVdlaWdodAEADWdldFdlaWdodFVuaXQBAA9nZXRRdWFudGl0eVVuaXQBABBnZXRWYWx1YXRpb25Vbml0AQAUZ2V0SW5jbHVzaXZlVGF4UHJpY2UBABVnZXRJbmNsdXNpdmVUYXhBbW91bnQBAAxnZXRQYWNrYWdlTm8BAAxnZXRTdG9yYWdlTm8BABFnZXRTdG9ja1RyYW5zcG9ydAEADHNldEl0ZW1UaXRsZQEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAC3NldE1hdGVyaWFsAQAQc2V0U3BlY2lmaWNhdGlvbgEADHNldFN0ZWVsTWlsbAEADnNldEl0ZW1Vbml0V2F5AQATc2V0UHVyY2hhc2VRdWFudGl0eQEAEXNldFB1cmNoYXNlV2VpZ2h0AQANc2V0V2VpZ2h0VW5pdAEAD3NldFF1YW50aXR5VW5pdAEAEHNldFZhbHVhdGlvblVuaXQBABRzZXRJbmNsdXNpdmVUYXhQcmljZQEAFXNldEluY2x1c2l2ZVRheEFtb3VudAEADHNldFBhY2thZ2VObwEADHNldFN0b3JhZ2VObwEAEXNldFN0b2NrVHJhbnNwb3J0AQAGZXF1YWxzAQAVKExqYXZhL2xhbmcvT2JqZWN0OylaAQABbwEAEkxqYXZhL2xhbmcvT2JqZWN0OwEABW90aGVyAQAOdGhpcyRpdGVtVGl0bGUBAA9vdGhlciRpdGVtVGl0bGUBAA10aGlzJG1hdGVyaWFsAQAOb3RoZXIkbWF0ZXJpYWwBABJ0aGlzJHNwZWNpZmljYXRpb24BABNvdGhlciRzcGVjaWZpY2F0aW9uAQAOdGhpcyRzdGVlbE1pbGwBAA9vdGhlciRzdGVlbE1pbGwBABB0aGlzJGl0ZW1Vbml0V2F5AQARb3RoZXIkaXRlbVVuaXRXYXkBABV0aGlzJHB1cmNoYXNlUXVhbnRpdHkBABZvdGhlciRwdXJjaGFzZVF1YW50aXR5AQATdGhpcyRwdXJjaGFzZVdlaWdodAEAFG90aGVyJHB1cmNoYXNlV2VpZ2h0AQAPdGhpcyR3ZWlnaHRVbml0AQAQb3RoZXIkd2VpZ2h0VW5pdAEAEXRoaXMkcXVhbnRpdHlVbml0AQASb3RoZXIkcXVhbnRpdHlVbml0AQASdGhpcyR2YWx1YXRpb25Vbml0AQATb3RoZXIkdmFsdWF0aW9uVW5pdAEAFnRoaXMkaW5jbHVzaXZlVGF4UHJpY2UBABdvdGhlciRpbmNsdXNpdmVUYXhQcmljZQEAF3RoaXMkaW5jbHVzaXZlVGF4QW1vdW50AQAYb3RoZXIkaW5jbHVzaXZlVGF4QW1vdW50AQAOdGhpcyRwYWNrYWdlTm8BAA9vdGhlciRwYWNrYWdlTm8BAA50aGlzJHN0b3JhZ2VObwEAD290aGVyJHN0b3JhZ2VObwEAE3RoaXMkc3RvY2tUcmFuc3BvcnQBABRvdGhlciRzdG9ja1RyYW5zcG9ydAEADVN0YWNrTWFwVGFibGUHARMHAVIBAAhjYW5FcXVhbAEACGhhc2hDb2RlAQADKClJAQAFUFJJTUUBAAFJAQAGcmVzdWx0AQAKJGl0ZW1UaXRsZQEACSRtYXRlcmlhbAEADiRzcGVjaWZpY2F0aW9uAQAKJHN0ZWVsTWlsbAEADCRpdGVtVW5pdFdheQEAESRwdXJjaGFzZVF1YW50aXR5AQAPJHB1cmNoYXNlV2VpZ2h0AQALJHdlaWdodFVuaXQBAA0kcXVhbnRpdHlVbml0AQAOJHZhbHVhdGlvblVuaXQBABIkaW5jbHVzaXZlVGF4UHJpY2UBABMkaW5jbHVzaXZlVGF4QW1vdW50AQAKJHBhY2thZ2VObwEACiRzdG9yYWdlTm8BAA8kc3RvY2tUcmFuc3BvcnQBAAh0b1N0cmluZwEAClNvdXJjZUZpbGUBACZQdXJjaGFzZU9yZGVyQ29tbW9kaXR5V2l0aG91dFNwZWMuamF2YQEAO0xjb20vYWxpYmFiYS9leGNlbC9hbm5vdGF0aW9uL3dyaXRlL3N0eWxlL0NvbnRlbnRSb3dIZWlnaHQ7AQA4TGNvbS9hbGliYWJhL2V4Y2VsL2Fubm90YXRpb24vd3JpdGUvc3R5bGUvSGVhZFJvd0hlaWdodDsDAAAAFAEASGNvbS9pbnRlcm5ldC9wdXJjaGFzZW1hbmFnZXIvZHRvL2V4Y2VsL1B1cmNoYXNlT3JkZXJDb21tb2RpdHlXaXRob3V0U3BlYwcBUwwBVAFVAQAI5rWL6K+VWksBAAtvd19oanNrYXM4OQcBVgwBVwFYDACuAK8MAEIAQwwAVABDDABZAEMMAF0AQwwAYgBDDABpAEMMAHYAQwwAfgBDDACCAEMMAIcAQwwAjABDDACSAEMMAJgAQwwAmwBDDACgAEMMAPgA0wwAsgCzBwFSDADSANMMALQAswwAtQCzDAC2ALMMALcAswwAuACzDAC5ALMMALoAswwAuwCzDAC8ALMMAL0AswwAvgCzDAC/ALMMAMAAswwAwQCzDAD5APoBABdqYXZhL2xhbmcvU3RyaW5nQnVpbGRlcgEALFB1cmNoYXNlT3JkZXJDb21tb2RpdHlXaXRob3V0U3BlYyhpdGVtVGl0bGU9DAFZAVoBAAssIG1hdGVyaWFsPQEAECwgc3BlY2lmaWNhdGlvbj0BAAwsIHN0ZWVsTWlsbD0BAA4sIGl0ZW1Vbml0V2F5PQEAEywgcHVyY2hhc2VRdWFudGl0eT0BABEsIHB1cmNoYXNlV2VpZ2h0PQEADSwgd2VpZ2h0VW5pdD0BAA8sIHF1YW50aXR5VW5pdD0BABAsIHZhbHVhdGlvblVuaXQ9AQAULCBpbmNsdXNpdmVUYXhQcmljZT0BABUsIGluY2x1c2l2ZVRheEFtb3VudD0BAAwsIHBhY2thZ2VObz0BAAwsIHN0b3JhZ2VObz0BABEsIHN0b2NrVHJhbnNwb3J0PQEAASkMAQ0AswEAJmNvbS9pbnRlcm5ldC90cmFjZS9jbGllbnQvZHRvL0V4Y2VsUm93AQATamF2YS9sYW5nL0V4Y2VwdGlvbgEAEGphdmEvbGFuZy9PYmplY3QBADBjb20vaW50ZXJuZXQvdHJhY2UvZW51bXMvZXhjZWwvRXhjZWxUZW1wbGF0ZVR5cGUBABhTQUxFU19PUkRFUl9XSE9MRV9JTVBPUlQBADJMY29tL2ludGVybmV0L3RyYWNlL2VudW1zL2V4Y2VsL0V4Y2VsVGVtcGxhdGVUeXBlOwEAJmNvbS9pbnRlcm5ldC90cmFjZS9zZGsvdXRpbC9FeGNlbFV0aWxzAQAPZ2V0SW1wb3J0U2NyaXB0AQB7KExqYXZhL2xhbmcvQ2xhc3M7TGNvbS9pbnRlcm5ldC90cmFjZS9lbnVtcy9leGNlbC9FeGNlbFRlbXBsYXRlVHlwZTtMamF2YS9sYW5nL1N0cmluZztMamF2YS9sYW5nL1N0cmluZzspTGph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BSq0ABWwAAAAAgCoAAAABgABAAAAoQCpAAAADAABAAAABQCwALEAAAABAMIAwwABAKcAAAA6AAIAAgAAAAYqK7UAB7EAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgBCAEMAAQABAMQAwwABAKcAAAA6AAIAAgAAAAYqK7UACLEAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgBUAEMAAQABAMUAwwABAKcAAAA6AAIAAgAAAAYqK7UACbEAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgBZAEMAAQABAMYAwwABAKcAAAA6AAIAAgAAAAYqK7UACrEAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgBdAEMAAQABAMcAwwABAKcAAAA6AAIAAgAAAAYqK7UAC7EAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgBiAEMAAQABAMgAwwABAKcAAAA6AAIAAgAAAAYqK7UADLEAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgBpAEMAAQABAMkAwwABAKcAAAA6AAIAAgAAAAYqK7UADbEAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgB2AEMAAQABAMoAwwABAKcAAAA6AAIAAgAAAAYqK7UADrEAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgB+AEMAAQABAMsAwwABAKcAAAA6AAIAAgAAAAYqK7UAD7EAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgCCAEMAAQABAMwAwwABAKcAAAA6AAIAAgAAAAYqK7UAELEAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgCHAEMAAQABAM0AwwABAKcAAAA6AAIAAgAAAAYqK7UAEbEAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgCMAEMAAQABAM4AwwABAKcAAAA6AAIAAgAAAAYqK7UAErEAAAACAKgAAAAGAAEAAAAcAKkAAAAWAAIAAAAGALAAsQAAAAAABgCS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");
        data.setDoubleData(234.56);
        data.setDate(new Date());

        byte[] bytes = ProtostuffUtils.ser(data);

        save2ZK(bytes);

        DemoData deser = ProtostuffUtils.deser(getData(), DemoData.class);
        System.out.println(deser);
    }

    private static void save2ZK(byte[] bytes) throws Exception {
        CuratorFramework curatorFramework = ZKFactory.get();

        Stat stat1 = curatorFramework.checkExists().forPath("/liuwg-test/template1");
        Stat stat2 = curatorFramework.checkExists().forPath("/liuwg-test/template1/protostuff");

        curatorFramework.create().forPath("/liuwg-test/template1/protostuff", bytes);
    }

    private static byte[] getData() throws Exception {
        CuratorFramework curatorFramework = ZKFactory.get();
        return curatorFramework.getData().forPath("/liuwg-test/template1/protostuff");
    }
}
