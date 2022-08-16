package com.slxk.gpsantu.mvp.model.bean;

import com.slxk.gpsantu.mvp.model.entity.BaseBean;

import java.util.List;

/**
 * 边界区域返回
 */
public class AreaResultBean extends BaseBean {
    /**
     * errcode : 0
     * error_message :
     * version : 1
     * data : [{"name":"china","value":[{"lat":"39.841287","lon":"124.326919"},{"lat":"40.230192","lon":"124.630475"},{"lat":"39.59086","lon":"124.487895"}]},{"name":"hongkong","value":[{"lat":"22.434589","lon":"113.881899"}]},{"name":"taiwan","value":[{"lat":"25.767311","lon":"121.56077"}]},{"name":"aomen","value":[{"lat":"22.105423","lon":"113.553636"},{"lat":"22.141362","lon":"113.554065"}]}]
     */
    private String version;
    private List<DataBean> data;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : china
         * value : [{"lat":"39.841287","lon":"124.326919"},{"lat":"40.230192","lon":"124.630475"},{"lat":"39.59086","lon":"124.487895"}]
         */

        private String name;
        private List<ValueBean> value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ValueBean> getValue() {
            return value;
        }

        public void setValue(List<ValueBean> value) {
            this.value = value;
        }

        public static class ValueBean {
            /**
             * lat : 39.841287
             * lon : 124.326919
             */

            private String lat;
            private String lon;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }
        }
    }
}
