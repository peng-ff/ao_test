package com.truck.domain;

/**
 * 卡车状态枚举
 * 定义卡车的可用状态：在售、已售、维修中、已报废
 */
public enum TruckStatus {
    AVAILABLE("在售"),
    SOLD("已售"),
    MAINTENANCE("维修中"),
    SCRAPPED("已报废");

    private final String description;

    TruckStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description;
    }
}
