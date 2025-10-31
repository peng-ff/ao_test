package com.truck.domain;

/**
 * 欧洲七大卡车品牌枚举
 * 包含Mercedes-Benz, Volvo, Scania, MAN, DAF, Iveco, Renault
 */
public enum TruckBrand {
    MERCEDES_BENZ("Mercedes-Benz", "梅赛德斯-奔驰"),
    VOLVO("Volvo", "沃尔沃"),
    SCANIA("Scania", "斯堪尼亚"),
    MAN("MAN", "曼恩"),
    DAF("DAF", "达夫"),
    IVECO("Iveco", "依维柯"),
    RENAULT("Renault", "雷诺");

    private final String englishName;
    private final String chineseName;

    TruckBrand(String englishName, String chineseName) {
        this.englishName = englishName;
        this.chineseName = chineseName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    @Override
    public String toString() {
        return englishName + " (" + chineseName + ")";
    }
}
