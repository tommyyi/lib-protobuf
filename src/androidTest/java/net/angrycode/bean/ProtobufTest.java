package net.angrycode.bean;

import org.junit.Before;
import org.junit.Test;

import com.laser.api360.net.TestBase;
import com.laser.toutiao.simulcast.IToutiaoSimulcastApi;
import com.laser.toutiao.simulcast.RetrofitHelper;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.converter.protobuf.ProtoConverterFactory;

/**
 * Created by 易剑锋 on 2017/8/28.
 */
public class ProtobufTest extends TestBase
{
    @Before
    public void setUp() throws Exception
    {
        super.setUp();
    }
    
    @Test
    public void protoBufferTest() throws Exception
    {
        IToutiaoSimulcastApi iToutiaoSimulcastApi = RetrofitHelper.getInstance(mContext, IToutiaoSimulcastApi.class);

        AppOuterClass.App app = AppOuterClass.App.newBuilder()
                                                 .setId("36715")
                                                 .setName("jinritoutiao")
                                                 .setVersion("4.0.2")
                                                 .build();

        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService("phone");
        DeviceOuterClass.Device device = DeviceOuterClass.Device.newBuilder()
                                                                .setDid(telephonyManager != null?telephonyManager.getDeviceId():"")
                                                                .setType(DeviceTypeOuterClass.DeviceType.PHONE)
                                                                .setOs(OsTypeOuterClass.OsType.ANDROID)
                                                                .setOsVersion(Build.VERSION.RELEASE)
                                                                .setVendor(Build.BRAND)
                                                                .setConnType(ConnectionTypeOuterClass.ConnectionType.WIFI)
                                                                .setMac(getMacAddress())
                                                                .setModel(Build.MODEL)
                                                                .setUa("Mozilla/5.0")
                                                                .setIp("118.26.232.42")
                                                                .build();

        GeoOuterClass.Geo geo = GeoOuterClass.Geo.newBuilder()
                                                 .setLatitude(39.123f)
                                                 .setLongitude(118.332f)
                                                 .build();

        SizeOuterClass.Size size = SizeOuterClass.Size.newBuilder()
                                                      .setHeight(800)
                                                      .setWidth(480)
                                                      .build();

        AdSlotOuterClass.AdSlot adSlot = AdSlotOuterClass.AdSlot.newBuilder()
                                                                .setId("4571")
                                                                .setAdtype(AdTypeOuterClass.AdType.SPLASH)
                                                                .addAcceptedSize(size)
                                                                .setPos(PositionOuterClass.Position.FULLSCREEN)
                                                                .build();

        BidRequestOuterClass.BidRequest bidRequest =
            BidRequestOuterClass.BidRequest.newBuilder()
                                           .setRequestId(System.currentTimeMillis()+"")
                                           .setApiVersion("1.3")
                                           .setApp(app)
                                           .setDevice(device)
                                           .setGeo(geo)
                                           .addAdslots(adSlot)
                                           .build();
        
        RequestBody requestBody =
            RequestBody.create(MediaType.parse("application/otcet-stream"), bidRequest.toByteArray());
        Call<ResponseBody> call = iToutiaoSimulcastApi.requestAd(requestBody);
        Response<ResponseBody> response = call.execute();

        ProtoConverterFactory protoConverterFactory= ProtoConverterFactory.create();
        Converter<ResponseBody, ?> responseBodyConverter = protoConverterFactory.responseBodyConverter(BidResponseOuterClass.BidResponse.class, null, null);

        ResponseBody body = response.body();
        if (body!=null)
        {
            BidResponseOuterClass.BidResponse convert = (BidResponseOuterClass.BidResponse) responseBodyConverter.convert(body);
        }
        waiting();
    }

    public String getMacAddress()
    {
        String macAddress = "";
        
        try
        {
            WifiManager wifimgr = (WifiManager)mContext.getSystemService("wifi");
            WifiInfo info = wifimgr.getConnectionInfo();
            if (info != null)
            {
                macAddress = info.getMacAddress();
            }
        }
        catch (Exception var5)
        {
        }
        
        return macAddress;
    }
}