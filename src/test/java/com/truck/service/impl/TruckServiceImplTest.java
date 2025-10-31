package com.truck.service.impl;

import com.truck.domain.Truck;
import com.truck.domain.TruckBrand;
import com.truck.domain.TruckStatus;
import com.truck.repository.TruckRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TruckServiceImpl单元测试类
 */
class TruckServiceImplTest {
    
    private TruckRepository truckRepository;
    private TruckServiceImpl truckService;
    private Truck testTruck;

    @BeforeEach
    void setUp() {
        // 使用Mockito创建Repository的mock对象
        truckRepository = mock(TruckRepository.class);
        truckService = new TruckServiceImpl(truckRepository);
        
        // 创建测试卡车
        testTruck = new Truck();
        testTruck.setId("TEST001");
        testTruck.setBrand(TruckBrand.VOLVO);
        testTruck.setModel("FH16 750");
        testTruck.setVin("YV2AG20B8DA123456");
        testTruck.setProductionDate(LocalDate.of(2023, 3, 20));
        testTruck.setPrice(550000.0);
        testTruck.setStatus(TruckStatus.AVAILABLE);
        testTruck.setMileage(8000);
        testTruck.setEngineType("D16K");
        testTruck.setHorsepower(750);
    }

    @Test
    void testCreateTruck_Success() {
        when(truckRepository.existsByVin(testTruck.getVin())).thenReturn(false);
        when(truckRepository.save(any(Truck.class))).thenReturn(testTruck);
        
        Truck created = truckService.createTruck(testTruck);
        
        assertNotNull(created);
        assertEquals(testTruck.getModel(), created.getModel());
        verify(truckRepository, times(1)).save(testTruck);
    }

    @Test
    void testCreateTruck_NullTruck_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> truckService.createTruck(null));
    }

    @Test
    void testCreateTruck_NullBrand_ThrowsException() {
        testTruck.setBrand(null);
        assertThrows(IllegalArgumentException.class, () -> truckService.createTruck(testTruck));
    }

    @Test
    void testCreateTruck_EmptyModel_ThrowsException() {
        testTruck.setModel("");
        assertThrows(IllegalArgumentException.class, () -> truckService.createTruck(testTruck));
    }

    @Test
    void testCreateTruck_EmptyVin_ThrowsException() {
        testTruck.setVin("");
        assertThrows(IllegalArgumentException.class, () -> truckService.createTruck(testTruck));
    }

    @Test
    void testCreateTruck_DuplicateVin_ThrowsException() {
        when(truckRepository.existsByVin(testTruck.getVin())).thenReturn(true);
        
        assertThrows(IllegalArgumentException.class, () -> truckService.createTruck(testTruck));
    }

    @Test
    void testCreateTruck_AutoGenerateId() {
        testTruck.setId(null);
        when(truckRepository.existsByVin(testTruck.getVin())).thenReturn(false);
        when(truckRepository.save(any(Truck.class))).thenReturn(testTruck);
        
        truckService.createTruck(testTruck);
        
        // 验证保存时ID已被设置
        ArgumentCaptor<Truck> truckCaptor = ArgumentCaptor.forClass(Truck.class);
        verify(truckRepository).save(truckCaptor.capture());
        assertNotNull(truckCaptor.getValue().getId());
    }

    @Test
    void testCreateTruck_DefaultStatus() {
        testTruck.setStatus(null);
        when(truckRepository.existsByVin(testTruck.getVin())).thenReturn(false);
        when(truckRepository.save(any(Truck.class))).thenReturn(testTruck);
        
        truckService.createTruck(testTruck);
        
        // 验证保存时状态已被设置为AVAILABLE
        ArgumentCaptor<Truck> truckCaptor = ArgumentCaptor.forClass(Truck.class);
        verify(truckRepository).save(truckCaptor.capture());
        assertEquals(TruckStatus.AVAILABLE, truckCaptor.getValue().getStatus());
    }

    @Test
    void testGetTruckById_Success() {
        when(truckRepository.findById("TEST001")).thenReturn(Optional.of(testTruck));
        
        Optional<Truck> found = truckService.getTruckById("TEST001");
        
        assertTrue(found.isPresent());
        assertEquals(testTruck.getId(), found.get().getId());
    }

    @Test
    void testGetTruckById_NotFound() {
        when(truckRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());
        
        Optional<Truck> found = truckService.getTruckById("NONEXISTENT");
        
        assertFalse(found.isPresent());
    }

    @Test
    void testGetTruckByVin_Success() {
        when(truckRepository.findByVin("YV2AG20B8DA123456")).thenReturn(Optional.of(testTruck));
        
        Optional<Truck> found = truckService.getTruckByVin("YV2AG20B8DA123456");
        
        assertTrue(found.isPresent());
        assertEquals(testTruck.getVin(), found.get().getVin());
    }

    @Test
    void testGetAllTrucks() {
        List<Truck> trucks = Arrays.asList(testTruck);
        when(truckRepository.findAll()).thenReturn(trucks);
        
        List<Truck> result = truckService.getAllTrucks();
        
        assertEquals(1, result.size());
        assertEquals(testTruck.getId(), result.get(0).getId());
    }

    @Test
    void testGetTrucksByBrand_Success() {
        List<Truck> trucks = Arrays.asList(testTruck);
        when(truckRepository.findByBrand(TruckBrand.VOLVO)).thenReturn(trucks);
        
        List<Truck> result = truckService.getTrucksByBrand(TruckBrand.VOLVO);
        
        assertEquals(1, result.size());
        assertEquals(TruckBrand.VOLVO, result.get(0).getBrand());
    }

    @Test
    void testGetTrucksByBrand_NullBrand_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> truckService.getTrucksByBrand(null));
    }

    @Test
    void testGetTrucksByStatus_Success() {
        List<Truck> trucks = Arrays.asList(testTruck);
        when(truckRepository.findByStatus(TruckStatus.AVAILABLE)).thenReturn(trucks);
        
        List<Truck> result = truckService.getTrucksByStatus(TruckStatus.AVAILABLE);
        
        assertEquals(1, result.size());
        assertEquals(TruckStatus.AVAILABLE, result.get(0).getStatus());
    }

    @Test
    void testGetTrucksByStatus_NullStatus_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> truckService.getTrucksByStatus(null));
    }

    @Test
    void testUpdateTruck_Success() {
        when(truckRepository.findById(testTruck.getId())).thenReturn(Optional.of(testTruck));
        when(truckRepository.update(testTruck)).thenReturn(testTruck);
        
        testTruck.setPrice(580000.0);
        Truck updated = truckService.updateTruck(testTruck);
        
        assertEquals(580000.0, updated.getPrice());
        verify(truckRepository, times(1)).update(testTruck);
    }

    @Test
    void testUpdateTruck_NotFound_ThrowsException() {
        when(truckRepository.findById(testTruck.getId())).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> truckService.updateTruck(testTruck));
    }

    @Test
    void testUpdateTruck_VinConflict_ThrowsException() {
        Truck existingTruck = new Truck();
        existingTruck.setId("TEST001");
        existingTruck.setVin("OLD_VIN");
        
        Truck anotherTruck = new Truck();
        anotherTruck.setId("TEST002");
        anotherTruck.setVin("NEW_VIN");
        
        testTruck.setVin("NEW_VIN");
        
        when(truckRepository.findById(testTruck.getId())).thenReturn(Optional.of(existingTruck));
        when(truckRepository.findByVin("NEW_VIN")).thenReturn(Optional.of(anotherTruck));
        
        assertThrows(IllegalArgumentException.class, () -> truckService.updateTruck(testTruck));
    }

    @Test
    void testDeleteTruck_Success() {
        when(truckRepository.findById("TEST001")).thenReturn(Optional.of(testTruck));
        when(truckRepository.deleteById("TEST001")).thenReturn(true);
        
        boolean deleted = truckService.deleteTruck("TEST001");
        
        assertTrue(deleted);
        verify(truckRepository, times(1)).deleteById("TEST001");
    }

    @Test
    void testDeleteTruck_NotFound() {
        when(truckRepository.findById("NONEXISTENT")).thenReturn(Optional.empty());
        
        boolean deleted = truckService.deleteTruck("NONEXISTENT");
        
        assertFalse(deleted);
    }

    @Test
    void testDeleteTruck_EmptyId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> truckService.deleteTruck(""));
    }

    @Test
    void testGetStatisticsByBrand() {
        when(truckRepository.countByBrand(TruckBrand.MERCEDES_BENZ)).thenReturn(5L);
        when(truckRepository.countByBrand(TruckBrand.VOLVO)).thenReturn(3L);
        when(truckRepository.countByBrand(TruckBrand.SCANIA)).thenReturn(2L);
        when(truckRepository.countByBrand(TruckBrand.MAN)).thenReturn(0L);
        when(truckRepository.countByBrand(TruckBrand.DAF)).thenReturn(0L);
        when(truckRepository.countByBrand(TruckBrand.IVECO)).thenReturn(0L);
        when(truckRepository.countByBrand(TruckBrand.RENAULT)).thenReturn(0L);
        
        Map<TruckBrand, Long> statistics = truckService.getStatisticsByBrand();
        
        assertEquals(7, statistics.size());
        assertEquals(5L, statistics.get(TruckBrand.MERCEDES_BENZ));
        assertEquals(3L, statistics.get(TruckBrand.VOLVO));
        assertEquals(2L, statistics.get(TruckBrand.SCANIA));
        assertEquals(0L, statistics.get(TruckBrand.MAN));
    }

    @Test
    void testGetStatisticsByStatus() {
        Truck truck1 = new Truck();
        truck1.setStatus(TruckStatus.AVAILABLE);
        
        Truck truck2 = new Truck();
        truck2.setStatus(TruckStatus.AVAILABLE);
        
        Truck truck3 = new Truck();
        truck3.setStatus(TruckStatus.SOLD);
        
        when(truckRepository.findAll()).thenReturn(Arrays.asList(truck1, truck2, truck3));
        
        Map<TruckStatus, Long> statistics = truckService.getStatisticsByStatus();
        
        assertEquals(4, statistics.size());
        assertEquals(2L, statistics.get(TruckStatus.AVAILABLE));
        assertEquals(1L, statistics.get(TruckStatus.SOLD));
        assertEquals(0L, statistics.get(TruckStatus.MAINTENANCE));
        assertEquals(0L, statistics.get(TruckStatus.SCRAPPED));
    }

    @Test
    void testGetTotalCount() {
        when(truckRepository.count()).thenReturn(15L);
        
        long count = truckService.getTotalCount();
        
        assertEquals(15L, count);
    }

    @Test
    void testGetTrucksByPriceRange_BothBounds() {
        Truck truck1 = new Truck();
        truck1.setPrice(300000.0);
        
        Truck truck2 = new Truck();
        truck2.setPrice(500000.0);
        
        Truck truck3 = new Truck();
        truck3.setPrice(700000.0);
        
        when(truckRepository.findAll()).thenReturn(Arrays.asList(truck1, truck2, truck3));
        
        // 查询价格在400000到600000之间的卡车
        List<Truck> result = truckService.getTrucksByPriceRange(400000.0, 600000.0);
        
        assertEquals(1, result.size());
        assertEquals(500000.0, result.get(0).getPrice());
    }

    @Test
    void testGetTrucksByPriceRange_MinOnly() {
        Truck truck1 = new Truck();
        truck1.setPrice(300000.0);
        
        Truck truck2 = new Truck();
        truck2.setPrice(500000.0);
        
        when(truckRepository.findAll()).thenReturn(Arrays.asList(truck1, truck2));
        
        // 只设置最低价格
        List<Truck> result = truckService.getTrucksByPriceRange(400000.0, null);
        
        assertEquals(1, result.size());
        assertEquals(500000.0, result.get(0).getPrice());
    }

    @Test
    void testGetTrucksByPriceRange_MaxOnly() {
        Truck truck1 = new Truck();
        truck1.setPrice(300000.0);
        
        Truck truck2 = new Truck();
        truck2.setPrice(500000.0);
        
        when(truckRepository.findAll()).thenReturn(Arrays.asList(truck1, truck2));
        
        // 只设置最高价格
        List<Truck> result = truckService.getTrucksByPriceRange(null, 400000.0);
        
        assertEquals(1, result.size());
        assertEquals(300000.0, result.get(0).getPrice());
    }

    @Test
    void testGetTrucksByPriceRange_InvalidRange_ThrowsException() {
        // 最低价格大于最高价格
        assertThrows(IllegalArgumentException.class, 
            () -> truckService.getTrucksByPriceRange(600000.0, 400000.0));
    }

    @Test
    void testGetTrucksByPriceRange_NullPrice() {
        Truck truck1 = new Truck();
        truck1.setPrice(300000.0);
        
        Truck truck2 = new Truck();
        truck2.setPrice(null);
        
        when(truckRepository.findAll()).thenReturn(Arrays.asList(truck1, truck2));
        
        // 价格为null的卡车应该被过滤掉
        List<Truck> result = truckService.getTrucksByPriceRange(200000.0, 400000.0);
        
        assertEquals(1, result.size());
        assertEquals(300000.0, result.get(0).getPrice());
    }
}
