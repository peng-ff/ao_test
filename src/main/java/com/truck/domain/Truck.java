package com.truck.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * 卡车实体类
 * 包含卡车的基本信息：ID、品牌、型号、VIN码、生产日期、价格、状态等
 */
public class Truck {
    private String id;
    private TruckBrand brand;
    private String model;
    private String vin;
    private LocalDate productionDate;
    private Double price;
    private TruckStatus status;
    private Integer mileage;
    private String engineType;
    private Integer horsepower;

    public Truck() {
    }

    public Truck(String id, TruckBrand brand, String model, String vin, 
                 LocalDate productionDate, Double price, TruckStatus status,
                 Integer mileage, String engineType, Integer horsepower) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.vin = vin;
        this.productionDate = productionDate;
        this.price = price;
        this.status = status;
        this.mileage = mileage;
        this.engineType = engineType;
        this.horsepower = horsepower;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TruckBrand getBrand() {
        return brand;
    }

    public void setBrand(TruckBrand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TruckStatus getStatus() {
        return status;
    }

    public void setStatus(TruckStatus status) {
        this.status = status;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Integer getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return Objects.equals(id, truck.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id='" + id + '\'' +
                ", brand=" + brand +
                ", model='" + model + '\'' +
                ", vin='" + vin + '\'' +
                ", productionDate=" + productionDate +
                ", price=" + price +
                ", status=" + status +
                ", mileage=" + mileage +
                ", engineType='" + engineType + '\'' +
                ", horsepower=" + horsepower +
                '}';
    }
}
