package model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Wifi {
	
    public static class WifiInfoResponse {
        @SerializedName("TbPublicWifiInfo")
        private TbPublicWifiInfo tbPublicWifiInfo;

        public TbPublicWifiInfo getTbPublicWifiInfo() {
            return tbPublicWifiInfo;
        }

        public void setTbPublicWifiInfo(TbPublicWifiInfo tbPublicWifiInfo) {
            this.tbPublicWifiInfo = tbPublicWifiInfo;
        }
    }

    public static class TbPublicWifiInfo {
        @SerializedName("list_total_count")
        private int listTotalCount;

        @SerializedName("RESULT")
        private Result result;

        @SerializedName("row")
        private List<WifiInfo> row;

        public int getListTotalCount() {
            return listTotalCount;
        }

        public void setListTotalCount(int listTotalCount) {
            this.listTotalCount = listTotalCount;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public List<WifiInfo> getRow() {
            return row;
        }

        public void setRow(List<WifiInfo> row) {
            this.row = row;
        }
    }

    public static class Result {
        @SerializedName("CODE")
        private String code;

        @SerializedName("MESSAGE")
        private String message;

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }
    
    public static class WifiInfo {
        @SerializedName("X_SWIFI_MGR_NO")
        private String managerNo;

        @SerializedName("X_SWIFI_WRDOFC")
        private String wrdOfc;

        @SerializedName("X_SWIFI_MAIN_NM")
        private String mainName;

        @SerializedName("X_SWIFI_ADRES1")
        private String address1;

        @SerializedName("X_SWIFI_ADRES2")
        private String address2;

        @SerializedName("X_SWIFI_INSTL_FLOOR")
        private String installationFloor;

        @SerializedName("X_SWIFI_INSTL_TY")
        private String installationType;

        @SerializedName("X_SWIFI_INSTL_MBY")
        private String installationMby;

        @SerializedName("X_SWIFI_SVC_SE")
        private String serviceSe;

        @SerializedName("X_SWIFI_CMCWR")
        private String cmcwr;

        @SerializedName("X_SWIFI_CNSTC_YEAR")
        private String cnstcYear;

        @SerializedName("X_SWIFI_INOUT_DOOR")
        private String inOutDoor;

        @SerializedName("X_SWIFI_REMARS3")
        private String remars3;

        @SerializedName("LAT")
        private String latitude;

        @SerializedName("LNT")
        private String longitude;

        @SerializedName("WORK_DTTM")
        private String workDateTime;

        public String getManagerNo() {
            return managerNo;
        }

        public String getWrdOfc() {
            return wrdOfc;
        }

        public String getMainName() {
            return mainName;
        }

        public String getAddress1() {
            return address1;
        }

        public String getAddress2() {
            return address2;
        }

        public String getInstallationFloor() {
            return installationFloor;
        }

        public String getInstallationType() {
            return installationType;
        }

        public String getInstallationMby() {
            return installationMby;
        }

        public String getServiceSe() {
            return serviceSe;
        }

        public String getCmcwr() {
            return cmcwr;
        }

        public String getCnstcYear() {
            return cnstcYear;
        }

        public String getInOutDoor() {
            return inOutDoor;
        }

        public String getRemars3() {
            return remars3;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getWorkDateTime() {
            return workDateTime;
        }
    }
    
	
}
