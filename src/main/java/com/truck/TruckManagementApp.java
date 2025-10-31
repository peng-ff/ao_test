package com.truck;

import com.truck.controller.TruckController;
import com.truck.repository.TruckRepository;
import com.truck.repository.impl.InMemoryTruckRepository;
import com.truck.service.TruckService;
import com.truck.service.impl.TruckServiceImpl;

/**
 * 欧洲七大卡车管理系统主程序
 * 程序入口，负责初始化各层组件并启动控制器
 */
public class TruckManagementApp {
    
    public static void main(String[] args) {
        System.out.println("正在启动欧洲七大卡车管理系统...");
        
        // 初始化各层组件：采用依赖注入的方式，便于后续扩展
        // Repository层：数据访问层
        TruckRepository truckRepository = new InMemoryTruckRepository();
        
        // Service层：业务逻辑层
        TruckService truckService = new TruckServiceImpl(truckRepository);
        
        // Controller层：控制器层
        TruckController truckController = new TruckController(truckService);
        
        System.out.println("系统启动成功！");
        
        // 启动主菜单
        truckController.showMainMenu();
    }
}
