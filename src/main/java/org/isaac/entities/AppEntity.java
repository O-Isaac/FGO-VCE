package org.isaac.entities;

public class AppEntity {
    String verCode;
    String appVer;

    public AppEntity(String verCode, String appVer) {
        this.verCode = verCode;
        this.appVer = appVer;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }
}
