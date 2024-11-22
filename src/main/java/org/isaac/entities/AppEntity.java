package org.isaac.entities;

import org.isaac.commands.CheckVerCode;
import org.isaac.utils.MyLogger;

public class AppEntity {
    String verCode;
    String appVer;

    private final MyLogger LOGGER = new MyLogger(AppEntity.class);

    public AppEntity(String verCode, String appVer) {
        this.verCode = verCode;
        this.appVer = appVer;
        this.LOGGER.info("AppVer=" + appVer + ", VerCode=" + verCode);
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
