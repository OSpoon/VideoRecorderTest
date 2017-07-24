package com.n22.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.n22.bean.net.AppSysFileInfo;
import com.n22.bean.net.Header;
import com.n22.bean.net.JsonBean;
import com.n22.bean.net.JsonBeanResp;
import com.n22.bean.net.MessageServerReq;
import com.n22.bean.net.Package;
import com.n22.bean.net.PackageList;
import com.n22.bean.net.PackageResp;
import com.n22.uploading.idle.BeanUtil;
import com.n22.util.encoder.Base64Tool;
import com.n22.util.encoder.Config;
import com.n22.util.encoder.ThreeDES;

import android.util.Log;


public class BeanUtilImpl implements BeanUtil {
    private String jsondata;
    private String signMD5;
    public static String BASE_URL = "http://112.74.250.71:8001/com.ifp.ipartner/interfaceChannel";// 获取token
    public static String BASE_URL_OSSUPLOAD = "http://139.196.106.75:8189/com.ifp.ipartner/interfaceChannel";

    public BeanUtilImpl() {
        init();
    }

    private void init() {
        try {
            MessageServerReq req = new MessageServerReq();
            req.tokenFlag = "1";
//			AppSysFileInfo info = new AppSysFileInfo();
//			info.tokenFlag = "1";
            Header header = Header.obtain("OSSUPLOAD", "comSerial", null);
            String beans = JsonUtil.objectToJson(req, "ossRequest");
            Package package1 = new Package(header, beans);
            PackageList list = new PackageList(package1);
            JsonBean bean2 = new JsonBean(list);
            jsondata = JsonUtil.objectToJson(bean2);
            signMD5 = MD5Util.createSignUsingMD5(jsondata, "UTF-8");
        } catch (Exception e) {
            Log.e("Impl-init:", "初始化数据失败！" + e.getLocalizedMessage());
        }
    }

    @Override
    public <T> T JsonUtilOTJ(T t) {
        return (T) JsonUtil.objectToJson(t);
    }

    @Override
    public <T> T DecodeUtil(String des) {
        try {
            return (T) ThreeDES.decode(des, Config.ORG_VALIDATE_CODE);
        } catch (Exception e) {
            Log.e("OSS-ThreeDES:", "DES解析失败！" + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public String BaseUrl() {
        return String.format(BASE_URL_OSSUPLOAD + "?sign=%s&com_id=%s", signMD5, Config.COM_ID);
    }

    @Override
    public <T> T AnalyticMessage(String body) {
        JsonBeanResp bean = JsonUtil.jsonToObject(body, JsonBeanResp.class);
        PackageResp packageResp = bean.packageList.getPackages();
        if (packageResp.getHeader().isSuccess()) {
            try {
                return (T) ThreeDES.decode(packageResp.getRequest(), Config.ORG_VALIDATE_CODE);
            } catch (Exception e) {
                Log.e("OSS-AnalyticMessage:", "数据解析失败！" + e.getLocalizedMessage());
                return null;
            }
        }
        return null;
    }

    @Override
    public String ObtainToken() {
        return jsondata;
    }

    List<AppSysFileInfo> infoList = new ArrayList<>();

    @Override
    public <T> T AssemblyData(Object obj) {

        try {
            if (obj instanceof AppSysFileInfo) {
                if (infoList != null) {
                    infoList.add((AppSysFileInfo) obj);
                } else {
                    infoList = new ArrayList<>();
                }

                return (T) infoList;
                // Map<String, String> map = new HashMap<>();
                // AppSysFileInfo app = (AppSysFileInfo) obj;
                // app.tokenFlag = "0";
                // map.put("file_path", app.fileData);
                // // app.fileData = null;
                // String jsonName = JsonUtil.objectToJson(app);
                // byte[] name = Base64.encode(jsonName.getBytes(),
                // Base64.NO_WRAP);
                // map.put("json_name", new String(name, "UTF-8"));
                // map.put("file_url", String.format("http://" + Config.getUrl()
                // + "/com.ifp.ipartner/uploadImgServlet"));// 上传本地服务器
                // return (T) map;
            }
        } catch (Exception e) {
            Log.e("Impl-Assembly:", "数据转换失败！" + e.getLocalizedMessage());
            return null;
        }
        return null;
    }

    public <T> T EncapsulationData(String json) {
        Map<String, String> map = new HashMap<>();
        String decode = Base64Tool.decode(json, "utf-8");
        try {
            AppSysFileInfo info = JsonUtil.jsonToObject(decode, AppSysFileInfo.class);
            Header header = Header.obtain("OSSUPLOAD", "comSerial", null);
            String beans = JsonUtil.objectToJson(info, "appSysFileInfo");
            Package package1 = new Package(header, beans);
            PackageList list = new PackageList(package1);
            JsonBean bean2 = new JsonBean(list);
            jsondata = JsonUtil.objectToJson(bean2);
            signMD5 = MD5Util.createSignUsingMD5(jsondata, "UTF-8");
            String url = String.format(BASE_URL_OSSUPLOAD + "?sign=%s&com_id=%s", signMD5, Config.COM_ID);
            map.put("D_Data", jsondata);
            map.put("D_Url", url);
        } catch (Exception e) {
            Log.e("Impl-EncapsulationData:", "数据转换失败！" + e.getLocalizedMessage());
        }
        return (T) map;
    }

    public <T> T EncapsulationData(Object obj) {
        Map<String, String> map = new HashMap<>();
        try {
            Header header = Header.obtain("OSSUPLOAD", "comSerial", null);
            String beans = JsonUtil.objectToJson(obj, "ossRequest");
            Package package1 = new Package(header, beans);
            PackageList list = new PackageList(package1);
            JsonBean bean2 = new JsonBean(list);
            jsondata = JsonUtil.objectToJson(bean2);
            signMD5 = MD5Util.createSignUsingMD5(jsondata, "UTF-8");
            String url = String.format(BASE_URL_OSSUPLOAD + "?sign=%s&com_id=%s", signMD5, Config.COM_ID);
            map.put("D_Data", jsondata);
            map.put("D_Url", url);
        } catch (Exception e) {
            Log.e("Impl-EncapsulationData:", "数据转换失败！" + e.getLocalizedMessage());
        }
        return (T) map;
    }

    @Override
    public <T> T JsonUtilJTO(String json, Class<T> classOfT) {
        return JsonUtil.jsonToObject(json, classOfT);
    }

}
