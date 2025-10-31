package com.truck.controller;

import com.truck.domain.Truck;
import com.truck.domain.TruckBrand;
import com.truck.domain.TruckStatus;
import com.truck.service.TruckService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * 卡车控制器
 * 处理用户交互和输入输出，调用服务层完成业务逻辑
 */
public class TruckController {
    
    private final TruckService truckService;
    private final Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TruckController(TruckService truckService) {
        this.truckService = truckService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * 显示主菜单
     */
    public void showMainMenu() {
        while (true) {
            System.out.println("\n========== 欧洲七大卡车管理系统 ==========");
            System.out.println("1. 添加卡车");
            System.out.println("2. 查看所有卡车");
            System.out.println("3. 根据ID查询卡车");
            System.out.println("4. 根据VIN码查询卡车");
            System.out.println("5. 根据品牌查询卡车");
            System.out.println("6. 根据状态查询卡车");
            System.out.println("7. 更新卡车信息");
            System.out.println("8. 删除卡车");
            System.out.println("9. 品牌统计");
            System.out.println("10. 状态统计");
            System.out.println("11. 价格区间查询");
            System.out.println("0. 退出系统");
            System.out.println("========================================");
            System.out.print("请选择操作: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1 -> addTruck();
                    case 2 -> listAllTrucks();
                    case 3 -> findTruckById();
                    case 4 -> findTruckByVin();
                    case 5 -> findTrucksByBrand();
                    case 6 -> findTrucksByStatus();
                    case 7 -> updateTruck();
                    case 8 -> deleteTruck();
                    case 9 -> showBrandStatistics();
                    case 10 -> showStatusStatistics();
                    case 11 -> findTrucksByPriceRange();
                    case 0 -> {
                        System.out.println("感谢使用，再见！");
                        return;
                    }
                    default -> System.out.println("无效的选择，请重新输入！");
                }
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入数字！");
            } catch (Exception e) {
                System.out.println("操作失败: " + e.getMessage());
            }
        }
    }

    /**
     * 添加卡车
     */
    private void addTruck() {
        System.out.println("\n---------- 添加卡车 ----------");
        
        try {
            Truck truck = new Truck();
            
            // 选择品牌
            truck.setBrand(selectBrand());
            
            // 输入型号
            System.out.print("请输入型号: ");
            truck.setModel(scanner.nextLine().trim());
            
            // 输入VIN码
            System.out.print("请输入VIN码: ");
            truck.setVin(scanner.nextLine().trim());
            
            // 输入生产日期
            System.out.print("请输入生产日期 (格式: yyyy-MM-dd): ");
            String dateStr = scanner.nextLine().trim();
            if (!dateStr.isEmpty()) {
                truck.setProductionDate(LocalDate.parse(dateStr, DATE_FORMATTER));
            }
            
            // 输入价格
            System.out.print("请输入价格: ");
            String priceStr = scanner.nextLine().trim();
            if (!priceStr.isEmpty()) {
                truck.setPrice(Double.parseDouble(priceStr));
            }
            
            // 选择状态
            truck.setStatus(selectStatus());
            
            // 输入里程
            System.out.print("请输入里程 (公里): ");
            String mileageStr = scanner.nextLine().trim();
            if (!mileageStr.isEmpty()) {
                truck.setMileage(Integer.parseInt(mileageStr));
            }
            
            // 输入发动机型号
            System.out.print("请输入发动机型号: ");
            truck.setEngineType(scanner.nextLine().trim());
            
            // 输入马力
            System.out.print("请输入马力: ");
            String horsepowerStr = scanner.nextLine().trim();
            if (!horsepowerStr.isEmpty()) {
                truck.setHorsepower(Integer.parseInt(horsepowerStr));
            }
            
            // 保存卡车
            Truck savedTruck = truckService.createTruck(truck);
            System.out.println("卡车添加成功！ID: " + savedTruck.getId());
            
        } catch (DateTimeParseException e) {
            System.out.println("日期格式错误，请使用 yyyy-MM-dd 格式！");
        } catch (NumberFormatException e) {
            System.out.println("数字格式错误！");
        } catch (IllegalArgumentException e) {
            System.out.println("添加失败: " + e.getMessage());
        }
    }

    /**
     * 查看所有卡车
     */
    private void listAllTrucks() {
        System.out.println("\n---------- 所有卡车列表 ----------");
        List<Truck> trucks = truckService.getAllTrucks();
        
        if (trucks.isEmpty()) {
            System.out.println("暂无卡车数据");
        } else {
            System.out.println("共找到 " + trucks.size() + " 辆卡车：");
            trucks.forEach(this::displayTruck);
        }
    }

    /**
     * 根据ID查询卡车
     */
    private void findTruckById() {
        System.out.println("\n---------- 根据ID查询卡车 ----------");
        System.out.print("请输入卡车ID: ");
        String id = scanner.nextLine().trim();
        
        Optional<Truck> truck = truckService.getTruckById(id);
        if (truck.isPresent()) {
            displayTruck(truck.get());
        } else {
            System.out.println("未找到该卡车");
        }
    }

    /**
     * 根据VIN码查询卡车
     */
    private void findTruckByVin() {
        System.out.println("\n---------- 根据VIN码查询卡车 ----------");
        System.out.print("请输入VIN码: ");
        String vin = scanner.nextLine().trim();
        
        Optional<Truck> truck = truckService.getTruckByVin(vin);
        if (truck.isPresent()) {
            displayTruck(truck.get());
        } else {
            System.out.println("未找到该卡车");
        }
    }

    /**
     * 根据品牌查询卡车
     */
    private void findTrucksByBrand() {
        System.out.println("\n---------- 根据品牌查询卡车 ----------");
        TruckBrand brand = selectBrand();
        
        List<Truck> trucks = truckService.getTrucksByBrand(brand);
        if (trucks.isEmpty()) {
            System.out.println("该品牌暂无卡车数据");
        } else {
            System.out.println("共找到 " + trucks.size() + " 辆 " + brand + " 卡车：");
            trucks.forEach(this::displayTruck);
        }
    }

    /**
     * 根据状态查询卡车
     */
    private void findTrucksByStatus() {
        System.out.println("\n---------- 根据状态查询卡车 ----------");
        TruckStatus status = selectStatus();
        
        List<Truck> trucks = truckService.getTrucksByStatus(status);
        if (trucks.isEmpty()) {
            System.out.println("该状态暂无卡车数据");
        } else {
            System.out.println("共找到 " + trucks.size() + " 辆 " + status + " 卡车：");
            trucks.forEach(this::displayTruck);
        }
    }

    /**
     * 更新卡车信息
     */
    private void updateTruck() {
        System.out.println("\n---------- 更新卡车信息 ----------");
        System.out.print("请输入要更新的卡车ID: ");
        String id = scanner.nextLine().trim();
        
        Optional<Truck> existingTruck = truckService.getTruckById(id);
        if (existingTruck.isEmpty()) {
            System.out.println("未找到该卡车");
            return;
        }
        
        Truck truck = existingTruck.get();
        System.out.println("当前卡车信息：");
        displayTruck(truck);
        
        try {
            // 更新品牌
            System.out.print("是否更新品牌? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                truck.setBrand(selectBrand());
            }
            
            // 更新型号
            System.out.print("是否更新型号? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.print("请输入新型号: ");
                truck.setModel(scanner.nextLine().trim());
            }
            
            // 更新状态
            System.out.print("是否更新状态? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                truck.setStatus(selectStatus());
            }
            
            // 更新价格
            System.out.print("是否更新价格? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.print("请输入新价格: ");
                truck.setPrice(Double.parseDouble(scanner.nextLine().trim()));
            }
            
            // 更新里程
            System.out.print("是否更新里程? (y/n): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                System.out.print("请输入新里程: ");
                truck.setMileage(Integer.parseInt(scanner.nextLine().trim()));
            }
            
            Truck updatedTruck = truckService.updateTruck(truck);
            System.out.println("卡车信息更新成功！");
            displayTruck(updatedTruck);
            
        } catch (NumberFormatException e) {
            System.out.println("数字格式错误！");
        } catch (IllegalArgumentException e) {
            System.out.println("更新失败: " + e.getMessage());
        }
    }

    /**
     * 删除卡车
     */
    private void deleteTruck() {
        System.out.println("\n---------- 删除卡车 ----------");
        System.out.print("请输入要删除的卡车ID: ");
        String id = scanner.nextLine().trim();
        
        Optional<Truck> truck = truckService.getTruckById(id);
        if (truck.isEmpty()) {
            System.out.println("未找到该卡车");
            return;
        }
        
        System.out.println("确认要删除以下卡车吗？");
        displayTruck(truck.get());
        System.out.print("确认删除 (y/n): ");
        
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            boolean deleted = truckService.deleteTruck(id);
            if (deleted) {
                System.out.println("卡车删除成功！");
            } else {
                System.out.println("删除失败！");
            }
        } else {
            System.out.println("已取消删除操作");
        }
    }

    /**
     * 显示品牌统计
     */
    private void showBrandStatistics() {
        System.out.println("\n---------- 品牌统计 ----------");
        Map<TruckBrand, Long> statistics = truckService.getStatisticsByBrand();
        
        System.out.println("各品牌卡车数量：");
        for (Map.Entry<TruckBrand, Long> entry : statistics.entrySet()) {
            System.out.printf("%s: %d 辆\n", entry.getKey(), entry.getValue());
        }
        System.out.println("总计: " + truckService.getTotalCount() + " 辆");
    }

    /**
     * 显示状态统计
     */
    private void showStatusStatistics() {
        System.out.println("\n---------- 状态统计 ----------");
        Map<TruckStatus, Long> statistics = truckService.getStatisticsByStatus();
        
        System.out.println("各状态卡车数量：");
        for (Map.Entry<TruckStatus, Long> entry : statistics.entrySet()) {
            System.out.printf("%s: %d 辆\n", entry.getKey(), entry.getValue());
        }
        System.out.println("总计: " + truckService.getTotalCount() + " 辆");
    }

    /**
     * 根据价格区间查询
     */
    private void findTrucksByPriceRange() {
        System.out.println("\n---------- 价格区间查询 ----------");
        
        try {
            System.out.print("请输入最低价格 (直接回车跳过): ");
            String minPriceStr = scanner.nextLine().trim();
            Double minPrice = minPriceStr.isEmpty() ? null : Double.parseDouble(minPriceStr);
            
            System.out.print("请输入最高价格 (直接回车跳过): ");
            String maxPriceStr = scanner.nextLine().trim();
            Double maxPrice = maxPriceStr.isEmpty() ? null : Double.parseDouble(maxPriceStr);
            
            List<Truck> trucks = truckService.getTrucksByPriceRange(minPrice, maxPrice);
            
            if (trucks.isEmpty()) {
                System.out.println("该价格区间暂无卡车数据");
            } else {
                System.out.println("共找到 " + trucks.size() + " 辆卡车：");
                trucks.forEach(this::displayTruck);
            }
            
        } catch (NumberFormatException e) {
            System.out.println("价格格式错误！");
        }
    }

    /**
     * 选择品牌
     */
    private TruckBrand selectBrand() {
        System.out.println("请选择品牌：");
        TruckBrand[] brands = TruckBrand.values();
        for (int i = 0; i < brands.length; i++) {
            System.out.printf("%d. %s\n", i + 1, brands[i]);
        }
        
        while (true) {
            try {
                System.out.print("请输入选项 (1-" + brands.length + "): ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= brands.length) {
                    return brands[choice - 1];
                }
                System.out.println("无效的选择，请重新输入！");
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入数字！");
            }
        }
    }

    /**
     * 选择状态
     */
    private TruckStatus selectStatus() {
        System.out.println("请选择状态：");
        TruckStatus[] statuses = TruckStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.printf("%d. %s\n", i + 1, statuses[i]);
        }
        
        while (true) {
            try {
                System.out.print("请输入选项 (1-" + statuses.length + "): ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= statuses.length) {
                    return statuses[choice - 1];
                }
                System.out.println("无效的选择，请重新输入！");
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入数字！");
            }
        }
    }

    /**
     * 显示卡车详细信息
     */
    private void displayTruck(Truck truck) {
        System.out.println("----------------------------------------");
        System.out.println("ID: " + truck.getId());
        System.out.println("品牌: " + truck.getBrand());
        System.out.println("型号: " + truck.getModel());
        System.out.println("VIN码: " + truck.getVin());
        System.out.println("生产日期: " + (truck.getProductionDate() != null ? truck.getProductionDate() : "未设置"));
        System.out.println("价格: " + (truck.getPrice() != null ? truck.getPrice() + " 元" : "未设置"));
        System.out.println("状态: " + truck.getStatus());
        System.out.println("里程: " + (truck.getMileage() != null ? truck.getMileage() + " 公里" : "未设置"));
        System.out.println("发动机: " + (truck.getEngineType() != null ? truck.getEngineType() : "未设置"));
        System.out.println("马力: " + (truck.getHorsepower() != null ? truck.getHorsepower() + " HP" : "未设置"));
        System.out.println("----------------------------------------");
    }
}
