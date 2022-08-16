package com.slxk.gpsantu.mvp.utils;

import com.baidu.mapapi.model.LatLng;
import com.slxk.gpsantu.db.AreaModel;

import java.util.ArrayList;
import java.util.List;

public class PolygonalArea {

    public static List<LatLng> baiduListPoint; // 百度地图，添加中国区域范围值，构建多边形区域
    public static List<LatLng> taiWanListPoint; // 百度地图，添加台湾区域范围值，构建多边形区域

    public static List<LatLng> hangKongListPoint; // 百度地图，添加香港区域范围值，构建多边形区域
    public static List<LatLng> aoMenListPoint; // 百度地图，添加澳门区域范围值，构建多边形区域
    public static final String CHINA_B = "chinaBaidu";
    public static final String CHINA_G = "chinaGoogle";
    public static final String TAIWAN = "taiwan";
    public static final String HONGKANG = "hongkang";
    public static final String AOMEN = "aomen";
    public static List<AreaModel> aomen;
    public static List<AreaModel> hongkong;
    public static List<AreaModel> taiwan;
    public static List<AreaModel> china_b; //  百度地图中国边界包含港、澳、台
    public static List<AreaModel> china_g; // 谷歌地图中国边界包含港、澳、台
    public static List<LatLng> aoMenListPointFormServer = new ArrayList<>(); //数据库坐标
    public static List<LatLng> baiduListPointFormServer = new ArrayList<>();
    public static List<LatLng> baiduListGPointFormServer = new ArrayList<>(); //谷歌地图中国边界数据库坐标
    public static List<LatLng> taiWanListPointFormServer = new ArrayList<>();
    public static List<LatLng> hangKongListPointFormServer = new ArrayList<>();

    /**
     * 添加中国区域范围值，构建多边形区域
     */
    public static void onAddBaiduPoint() {
        String point = "124.326919,39.841287|" +
                "124.630475,40.230192|" +
                "124.980023,40.420289|" +
                "125.053612,40.458947|" +
                "125.028316,40.520401|" +
                "125.331871,40.63438|" +
                "125.456053,40.61861|" +
                "126.042467,40.901897|" +
                "126.888743,41.73411|" +
                "127.367072,41.437131|" +
                "128.148958,41.347076|" +
                "128.323732,41.568524|" +
                "128.112163,41.961089|" +
                "128.995233,41.99541|" +
                "129.418371,42.378521|" +
                "129.841509,42.41943|" +
                "130.080674,42.90824|" +
                "130.65099,42.364879|" +
                "130.503812,42.637157|" +
                "131.147717,42.867653|" +
                "131.313293,43.366337|" +
                "131.386882,43.967324|" +
                "131.202909,44.798523|" +
                "131.938801,45.190155|" +
                "133.024241,44.916296|" +
                "134.385641,47.183241|" +
                "134.845574,47.670159|" +
                "134.73519,48.066299|" +
                "135.250314,48.434915|" +
                "134.214020, 48.379087|" +
                "133.079433,48.115602|" +
                "132.453925,47.76948|" +
                "131.221306,47.707427|" +
                "130.85336,48.078629|" +
                "130.908552,48.825163|" +
                "128.093766,49.632427|" +
                "125.830898,53.063643|" +
                "123.420852,53.60326|" +
                "120.661258,53.240599|" +
                "119.906969,52.584436|" +
                "120.698052,52.494695|" +
                "120.734847,52.05456|" +
                "119.023898,50.296998|" +
                "119.263063,50.143545|" +
                "117.956855,49.560675|" +
                "116.889812,49.858941|" +
                "115.47322,48.078629|" +
                "115.988344,47.607987|" +
                "116.797825,47.76948|" +
                "117.349744,47.520821|" +
                "117.901663,47.905734|" +
                "118.508774,47.893363|" +
                "118.545569,47.86861|" +
                "119.888571,46.843488|" +
                "119.796585,46.590415|" +
                "119.005501,46.717101|" +
                "117.791279,46.526961|" +
                "117.404936,46.310656|" +
                "116.797825,46.323404|" +
                "116.282701,45.798295|" +
                "114.700534,45.384961|" +
                "113.670285,44.759212|" +
                "112.161707,45.04687|" +
                "111.444212,44.298587|" +
                "111.959336,43.767669|" +
                "110.082812,42.569199|" +
                "107.543985,42.39216|" +
                "105.115542,41.609961|" +
                "100.405834,42.596391|" +
                "96.560799,42.772848|" +
                "95.326303,44.214771|" +
                "93.550964,44.970665|" +
                "90.920150,45.231265|" +
                "91.058130,46.617819|" +
                "87.969261,49.115857|" +
                "86.847026,49.055436|" +
                "85.724791,48.201769|" +
                "85.301653,47.05766|" +
                "83.167567,47.23339|" +
                "82.284497,45.52739|" +
                "82.652442,45.177144|" +
                "81.769372,45.346053|" +
                "79.911245,44.890145|" +
                "80.702329,43.20505|" +
                "80.003232,42.077702|" +
                "77.096459,41.05527|" +
                "76.158197,40.369298|" +
                "75.753456,40.594072|" +
                "74.925578,40.48178|" +
                "73.803343,39.605094|" +
                "73.637767,39.291284|" +
                "73.913726,38.457149|" +
                "74.612824,38.500536|" +
                "74.999167,37.31988|" +
                "77.924337,35.325072|" +
                "78.273886,34.598061|" +
                "79.101764,34.293224|" +
                "78.770613,33.987267|" +
                "79.230545,32.472038|" +
                "78.880997,32.59672|" +
                "78.421064,32.425237|" +
                "78.844202,31.18404|" +
                "81.180659,29.990271|" +
                "81.71418,30.34198|" +
                "86.092737,27.935542|" +
                "88.760345,27.951881|" +
                "88.99951,27.19772|" +
                "89.845786,28.098817|" +
                "91.538337,27.772017|" +
                "92.108653,26.769165|" +
                "93.893191,26.851709|" +
                "95.916893,28.115131|" +
                "97.223102,27.690161|" +
                "97.609445,28.375809|" +
                "98.363734,27.427807|" +
                "98.73168,26.653501|" +
                "97.646239,24.702995|" +
                "97.627842,23.842845|" +
                "98.870298,24.141773|" +
                "98.695523,23.964373|" +
                "98.907092,23.184294|" +
                "99.486607,22.997063|" +
                "99.173853,22.168431|" +
                "99.983334,21.979798|" +
                "100.158108,21.481278|" +
                "100.636438,21.455445|" +
                "101.114768,21.790910|" +
                "101.261946,21.140775|" +
                "101.859858,21.201176|" +
                "101.795468,21.803797|" +
                "101.583899,22.232680|" +
                "101.777071,22.506472|" +
                "101.924249,22.369644|" +
                "102.273798,22.386755|" +
                "102.503764,22.796776|" +
                "103.037285,22.429523|" +
                "103.359238,22.779716|" +
                "103.543211,22.574834|" +
                "103.635198,22.788246|" +
                "104.012342,22.472278|" +
                "104.251507,22.830888|" +
                "104.371089,22.668777|" +
                "104.886214,22.950214|" +
                "105.318550,23.358521|" +
                "105.870469,22.924653|" +
                "106.275210,22.882041|" +
                "106.817930,22.771186|" +
                "106.606361,22.395310|" +
                "106.781135,21.966927|" +
                "107.379047,21.606072|" +
                "108.050549,21.537234|" +
                "107.866576,20.171018|" +
                "107.803263,19.524994|" +
                "108.253997,18.764382|" +
                "108.401175,18.272939|" +
                "108.769121,17.885813|" +
                "110.101209,14.556493|" +
                "109.82525,10.162358|" +
                "108.151096,6.074665|" +
                "109.604482,3.416913|" +
                "113.136763,3.712694|" +
                "115.362836,6.737258|" +
                "117.404936,9.505473|" +
                "119.318255,14.574413|" +
                "120.164531,19.12633|" +
                "122.059452,21.693198|" +
                "122.813741,24.686186|" +
                "124.230333,28.457139|" +
                "124.855841,32.721228|" +
                "124.230333,36.56674|" +
                "124.487895,39.59086";
        String[] pointSplit = point.split("\\|");
        baiduListPoint = new ArrayList<>();
        for (String tt : pointSplit) {
            String[] tts = tt.split(",");
            baiduListPoint.add(new com.baidu.mapapi.model.LatLng(Double.parseDouble(tts[1]), Double.parseDouble(tts[0]))); //顺序 纬度，经度
        }
    }


    /**
     * 台湾多边形区域
     */
    public static void onTaiWanPoint() {
        taiWanListPoint = new ArrayList<>();
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(25.767311, 121.56077));
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(23.218193, 118.528543));
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(20.588635, 120.714823));
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(22.621177, 122.912088));
        taiWanListPoint.add(new com.baidu.mapapi.model.LatLng(25.182142, 123.428446));
    }


    /**
     * 香港多边形区域
     */
    public static void onHangKongPoint() {
        hangKongListPoint = new ArrayList<>();
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.434589, 113.881899));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.464518, 113.946289));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.511535, 114.01068));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.507262, 114.056673));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.541447, 114.116464));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.554265, 114.167057));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.554265, 114.236047));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.567081, 114.374026));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.549992, 114.466013));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.443141, 114.479811));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.37899, 114.512006));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.15637, 114.512006));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.143516, 113.900296));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.216342, 113.822108));
        hangKongListPoint.add(new com.baidu.mapapi.model.LatLng(22.30625, 113.868101));
    }

    /**
     * 澳门多边形区域
     */
    public static void onAoMenPoint() {
        aoMenListPoint = new ArrayList<>();
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.105423, 113.553636));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.141362, 113.554065));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.151696, 113.545997));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.180947, 113.531063));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.203041, 113.540504));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.209875, 113.538272));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.210431, 113.539688));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.210789, 113.543443));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.210113, 113.546276));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.213768, 113.548765));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.214047, 113.549752));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.213927, 113.552541));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.207252, 113.574171));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.201968, 113.576617));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.201412, 113.586230));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.195412, 113.586745));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.133292, 113.604447));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.105939, 113.575265));
        aoMenListPoint.add(new com.baidu.mapapi.model.LatLng(22.105422, 113.553636));
    }

    /**
     * 获取澳门范围经纬度多边形
     *
     * @return
     */
    public static List<LatLng> getAoMenListPoint() {
        if (aomen == null) aomen = Utils.getAreaDataForName(AOMEN);
        if (aomen != null && aomen.size() > 0) {
            if (aoMenListPointFormServer.size() == 0) {
                for (AreaModel model : aomen) {
                    aoMenListPointFormServer.add(new com.baidu.mapapi.model.LatLng(Double.parseDouble(model.getLat()), Double.parseDouble(model.getLon()))); //顺序 纬度，经度
                }
            }
            return aoMenListPointFormServer;
        }
        return aoMenListPoint;
    }

    /**
     * 获取百度地图中国范围经纬度多边形
     *
     * @return
     */
    public static List<com.baidu.mapapi.model.LatLng> getBaiduListPoint() {
        if (china_b == null) china_b = Utils.getAreaDataForName(CHINA_B);
        if (china_b != null && china_b.size() > 0) {
            if (baiduListPointFormServer.size() == 0) {
                for (AreaModel model : china_b) {
                    baiduListPointFormServer.add(new com.baidu.mapapi.model.LatLng(Double.parseDouble(model.getLat()), Double.parseDouble(model.getLon()))); //顺序 纬度，经度
                }
            }
            return baiduListPointFormServer;
        }
        return baiduListPoint;
    }

    /**
     * 获取谷歌地图中国范围经纬度多边形
     *
     * @returnG
     */
    public static List<com.baidu.mapapi.model.LatLng> getBaiduGListPoint() {
        if (china_g == null) china_g = Utils.getAreaDataForName(CHINA_G);
        if (china_g != null && china_g.size() > 0) {
            if (baiduListGPointFormServer.size() == 0) {
                for (AreaModel model : china_g) {
                    baiduListGPointFormServer.add(new com.baidu.mapapi.model.LatLng(Double.parseDouble(model.getLat()), Double.parseDouble(model.getLon()))); //顺序 纬度，经度
                }
            }
            return baiduListGPointFormServer;
        }
        return baiduListPoint;
    }

    /**
     * 获取台湾范围经纬度多边形
     *
     * @return
     */
    public static List<com.baidu.mapapi.model.LatLng> getTaiWanListPoint() {
        if (taiwan == null) taiwan = Utils.getAreaDataForName(TAIWAN);
        if (taiwan != null && taiwan.size() > 0) {
            if (taiWanListPointFormServer.size() == 0) {
                for (AreaModel model : taiwan) {
                    taiWanListPointFormServer.add(new com.baidu.mapapi.model.LatLng(Double.parseDouble(model.getLat()), Double.parseDouble(model.getLon()))); //顺序 纬度，经度
                }
            }
            return taiWanListPointFormServer;
        }
        return taiWanListPoint;
    }

    /**
     * 获取香港范围经纬度多边形
     *
     * @return
     */
    public static List<com.baidu.mapapi.model.LatLng> getHangKongListPoint() {
        if (hongkong == null) hongkong = Utils.getAreaDataForName(HONGKANG);
        if (hongkong != null && hongkong.size() > 0) {
            if (hangKongListPointFormServer.size() == 0) {
                for (AreaModel model : hongkong) {
                    hangKongListPointFormServer.add(new com.baidu.mapapi.model.LatLng(Double.parseDouble(model.getLat()), Double.parseDouble(model.getLon()))); //顺序 纬度，经度
                }
            }
            return hangKongListPointFormServer;
        }
        return hangKongListPoint;
    }

    //0国外  1国内  2香港  3澳门  4台湾(本土） 5金门县(台湾)
    public byte IsNeedCorrectCoordinate(double lon, double lat) {
        //在中国区域画了13个标准多边形，大部分地区在多边形里面，少部分边角及香港、澳门、台湾（包括金门县）需要地理图形校验
        byte inChina = 0;
        //最大范围判断，不需要纠偏的
        if (lon < 73.000 || lon > 135.500)
            return inChina;
        if (lat < 18 || lat > 54.00)
            return inChina;

        inChina = 1;
        //较大范围判断，必须纠偏的
        //中原：[98.8, 41.65], [98.8, 25.35], [123.5, 25.35], [123.5, 41.65]
        if (lon >= 98.8 && lon <= 124.15 && lat >= 25.35 && lat <= 41.65) {
            return inChina;
        }
        //云南，广东，广西：[99.6, 25.35], [99.6, 23.4], [118.21, 23.4], [118.21, 25.35], [99.6, 25.35]
        if (lon >= 99.6 && lon <= 118.21 && lat >= 23.4 && lat <= 25.35) {
            return inChina;
        }
        //深圳东莞惠州： [113.54, 23.4], [113.54, 22.565], [118.21, 22.565], [118.21, 23.4], [113.54, 23.4]
        if (lon >= 113.54 && lon <= 118.21 && lat >= 22.565 && lat <= 23.4) {
            return inChina;
        }
        //海南：[108.0, 23.4], [108.0, 18.0], [113.54, 18.0], [113.54, 23.4], [108.0, 23.4]
        if (lon >= 108.0 && lon <= 113.54 && lat >= 18.0 && lat <= 23.4) {
            return inChina;
        }
        //西部新疆西藏青海三省交汇：[79.45, 41.82], [79.45, 31.05], [98.8, 31.05], [98.8, 41.82], [79.45, 41.82]
        if (lon >= 79.45 && lon <= 98.8 && lat >= 31.05 && lat <= 41.82) {
            return inChina;
        }
        //新疆喀什 [75.37, 40.28], [75.37, 36.7], [79.45, 36.7], [79.45, 40.28], [75.37, 40.28]
        if (lon >= 75.37 && lon <= 79.45 && lat >= 36.7 && lat <= 40.28) {
            return inChina;
        }
        //新疆乌鲁木齐[82.90, 46.88], [82.90, 44.6], [90.74, 44.6], [90.74, 46.88], [82.90, 46.88]
        if (lon >= 82.90 && lon <= 90.74 && lat >= 44.6 && lat <= 46.88) {
            return inChina;
        }
        //新疆克拉玛依[80.78, 44.6],[80.78, 41.82], [94.5, 41.82], [94.5, 44.6],[80.78, 44.6],
        if (lon >= 80.78 && lon <= 94.5 && lat >= 41.82 && lat <= 44.6) {
            return inChina;
        }
        //西藏拉萨 [84.68, 31.05], [84.68, 28.70], [98.8, 28.70], [98.8, 31.05], [84.68, 31.05]
        if (lon >= 84.68 && lon <= 98.8 && lat >= 28.70 && lat <= 31.05) {
            return inChina;
        }
        //东北中心 [120.05, 49.35], [120.05, 42.12], [129.15, 42.12], [129.15, 49.35], [120.05, 49.35]
        if (lon >= 120.05 && lon <= 129.15 && lat >= 42.12 && lat <= 49.35) {
            return inChina;
        }
        //东北西边，内蒙东 [111.95, 44.75], [111.95, 41.65], [120.05, 41.65], [120.05, 44.75], [111.95, 44.75]
        if (lon >= 111.95 && lon <= 120.05 && lat >= 41.65 && lat <= 44.75) {
            return inChina;
        }
        //东北东边 [129.15, 47.68], [129.15, 45.42], [133.35, 45.42], [133.35, 47.68], [129.15, 47.68]
        if (lon >= 129.15 && lon <= 133.35 && lat >= 45.42 && lat <= 47.68) {
            return inChina;
        }
        //东北北边 [120.80, 53.12], [120.80, 49.35], [125.65, 49.35], [125.65, 53.12], [120.80, 53.14]
        if (lon >= 120.80 && lon <= 125.65 && lat >= 49.35 && lat <= 53.12) {
            return inChina;
        }
        //香港 [113.93, 22.472], [113.93, 22.19], [114.40, 22.19], [114.40, 22.472], [113.93, 22.472]
        if (lon >= 113.93 && lon <= 114.40 && lat >= 22.19 && lat <= 22.472) {
            return 2;
        }
        //台湾 [120.00, 25.35], [120.00, 21.85], [122.08, 21.85], [122.08, 25.35], [120.00, 25.35]
        if (lon >= 120.00 && lon <= 122.08 && lat >= 21.85 && lat <= 25.35) {
            return 4;
        }
//
////        //其他边边角角需要精细判断
//        S2Point point = S2LatLng.FromDegrees(lat, lon).ToPoint();
//        bool atChina = ChinaBoundary.Contains(point);
//        if (atChina) {
//            inChina = (byte) CoordinateArea.China;
//            if (HongKongBoundary.Contains(point)) {
//                inChina = (byte) CoordinateArea.ChinaHongkong;
//            } else if (AoMenBoundary.Contains(point)) {
//                inChina = (byte) CoordinateArea.ChinaAomen;
//            } else if (TaiWanBoundary.Contains(point)) {
//                inChina = (byte) CoordinateArea.ChinaTaiwan;
//            } else if (JinMenBoundary.Contains(point)) {
//                inChina = (byte) CoordinateArea.ChinaJinmen;
//            }
//        } else {
//            inChina = (byte) CoordinateArea.Foreign;
//        }

        return inChina;
    }

}
