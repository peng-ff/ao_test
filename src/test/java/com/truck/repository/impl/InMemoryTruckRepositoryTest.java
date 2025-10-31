package com.truck.repository.impl;

import com.truck.domain.Truck;
import com.truck.domain.TruckBrand;
import com.truck.domain.TruckStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * InMemoryTruckRepository单元测试类
 */
class InMemoryTruckRepositoryTest {
    
    private InMemoryTruckRepository repository;
    private Truck testTruck;

    @BeforeEach
    void setUp() {
        repository = new InMemoryTruckRepository();
        
        // 创建测试卡车
        testTruck = new Truck();
        testTruck.setId("TEST001");
        testTruck.setBrand(TruckBrand.MERCEDES_BENZ);
        testTruck.setModel("Actros 1851");
        testTruck.setVin("WDB9340151K123456");
        testTruck.setProductionDate(LocalDate.of(2023, 6, 15));
        testTruck.setPrice(450000.0);
        testTruck.setStatus(TruckStatus.AVAILABLE);
        testTruck.setMileage(5000);
        testTruck.setEngineType("OM471");
        testTruck.setHorsepower(510);
    }

    @Test
    void testSave_Success() {
        Truck saved = repository.save(testTruck);
        
        assertNotNull(saved);
        assertEquals(testTruck.getId(), saved.getId());
        assertEquals(testTruck.getVin(), saved.getVin());
        assertEquals(1, repository.count());
    }

    @Test
    void testSave_NullTruck_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }

    @Test
    void testSave_EmptyId_ThrowsException() {
        testTruck.setId("");
        assertThrows(IllegalArgumentException.class, () -> repository.save(testTruck));
    }

    @Test
    void testFindById_Success() {
        repository.save(testTruck);
        
        Optional<Truck> found = repository.findById("TEST001");
        
        assertTrue(found.isPresent());
        assertEquals(testTruck.getId(), found.get().getId());
        assertEquals(testTruck.getModel(), found.get().getModel());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Truck> found = repository.findById("NONEXISTENT");
        assertFalse(found.isPresent());
    }

    @Test
    void testFindByVin_Success() {
        repository.save(testTruck);
        
        Optional<Truck> found = repository.findByVin("WDB9340151K123456");
        
        assertTrue(found.isPresent());
        assertEquals(testTruck.getId(), found.get().getId());
    }

    @Test
    void testFindByVin_NotFound() {
        Optional<Truck> found = repository.findByVin("NONEXISTENT");
        assertFalse(found.isPresent());
    }

    @Test
    void testFindAll_Empty() {
        List<Truck> trucks = repository.findAll();
        assertTrue(trucks.isEmpty());
    }

    @Test
    void testFindAll_MultipleTrucks() {
        repository.save(testTruck);
        
        Truck truck2 = new Truck();
        truck2.setId("TEST002");
        truck2.setBrand(TruckBrand.VOLVO);
        truck2.setModel("FH16");
        truck2.setVin("YV2AG20B8DA123456");
        truck2.setStatus(TruckStatus.AVAILABLE);
        repository.save(truck2);
        
        List<Truck> trucks = repository.findAll();
        
        assertEquals(2, trucks.size());
    }

    @Test
    void testFindByBrand_Success() {
        repository.save(testTruck);
        
        Truck volvoTruck = new Truck();
        volvoTruck.setId("TEST002");
        volvoTruck.setBrand(TruckBrand.VOLVO);
        volvoTruck.setModel("FH16");
        volvoTruck.setVin("YV2AG20B8DA123456");
        volvoTruck.setStatus(TruckStatus.AVAILABLE);
        repository.save(volvoTruck);
        
        List<Truck> mercedesTrucks = repository.findByBrand(TruckBrand.MERCEDES_BENZ);
        List<Truck> volvoTrucks = repository.findByBrand(TruckBrand.VOLVO);
        
        assertEquals(1, mercedesTrucks.size());
        assertEquals(1, volvoTrucks.size());
        assertEquals(TruckBrand.MERCEDES_BENZ, mercedesTrucks.get(0).getBrand());
    }

    @Test
    void testFindByStatus_Success() {
        repository.save(testTruck);
        
        Truck soldTruck = new Truck();
        soldTruck.setId("TEST002");
        soldTruck.setBrand(TruckBrand.SCANIA);
        soldTruck.setModel("R450");
        soldTruck.setVin("YS2R4X40005123456");
        soldTruck.setStatus(TruckStatus.SOLD);
        repository.save(soldTruck);
        
        List<Truck> availableTrucks = repository.findByStatus(TruckStatus.AVAILABLE);
        List<Truck> soldTrucks = repository.findByStatus(TruckStatus.SOLD);
        
        assertEquals(1, availableTrucks.size());
        assertEquals(1, soldTrucks.size());
        assertEquals(TruckStatus.AVAILABLE, availableTrucks.get(0).getStatus());
    }

    @Test
    void testUpdate_Success() {
        repository.save(testTruck);
        
        testTruck.setModel("Actros 1858");
        testTruck.setPrice(480000.0);
        
        Truck updated = repository.update(testTruck);
        
        assertEquals("Actros 1858", updated.getModel());
        assertEquals(480000.0, updated.getPrice());
    }

    @Test
    void testUpdate_NonExistent_ThrowsException() {
        Truck newTruck = new Truck();
        newTruck.setId("NONEXISTENT");
        newTruck.setBrand(TruckBrand.MAN);
        newTruck.setModel("TGX");
        newTruck.setVin("WMA06XZZ1HW123456");
        
        assertThrows(IllegalArgumentException.class, () -> repository.update(newTruck));
    }

    @Test
    void testUpdate_VinChanged() {
        repository.save(testTruck);
        
        String oldVin = "WDB9340151K123456"; // 保存原始VIN码的值
        String newVin = "WDB9340151K654321";
        testTruck.setVin(newVin);
        
        repository.update(testTruck);
        
        // 旧VIN码应该找不到
        assertFalse(repository.findByVin(oldVin).isPresent());
        
        // 新VIN码应该能找到
        Optional<Truck> found = repository.findByVin(newVin);
        assertTrue(found.isPresent());
        assertEquals(testTruck.getId(), found.get().getId());
    }

    @Test
    void testDeleteById_Success() {
        repository.save(testTruck);
        assertEquals(1, repository.count());
        
        boolean deleted = repository.deleteById("TEST001");
        
        assertTrue(deleted);
        assertEquals(0, repository.count());
        assertFalse(repository.findById("TEST001").isPresent());
        assertFalse(repository.findByVin("WDB9340151K123456").isPresent());
    }

    @Test
    void testDeleteById_NotFound() {
        boolean deleted = repository.deleteById("NONEXISTENT");
        assertFalse(deleted);
    }

    @Test
    void testCount() {
        assertEquals(0, repository.count());
        
        repository.save(testTruck);
        assertEquals(1, repository.count());
        
        Truck truck2 = new Truck();
        truck2.setId("TEST002");
        truck2.setBrand(TruckBrand.DAF);
        truck2.setModel("XF 480");
        truck2.setVin("XLRAE75PC0E123456");
        truck2.setStatus(TruckStatus.AVAILABLE);
        repository.save(truck2);
        
        assertEquals(2, repository.count());
    }

    @Test
    void testCountByBrand() {
        assertEquals(0, repository.countByBrand(TruckBrand.MERCEDES_BENZ));
        
        repository.save(testTruck);
        
        Truck truck2 = new Truck();
        truck2.setId("TEST002");
        truck2.setBrand(TruckBrand.MERCEDES_BENZ);
        truck2.setModel("Arocs 2651");
        truck2.setVin("WDB9530451K123456");
        truck2.setStatus(TruckStatus.AVAILABLE);
        repository.save(truck2);
        
        Truck volvoTruck = new Truck();
        volvoTruck.setId("TEST003");
        volvoTruck.setBrand(TruckBrand.VOLVO);
        volvoTruck.setModel("FH16");
        volvoTruck.setVin("YV2AG20B8DA123456");
        volvoTruck.setStatus(TruckStatus.AVAILABLE);
        repository.save(volvoTruck);
        
        assertEquals(2, repository.countByBrand(TruckBrand.MERCEDES_BENZ));
        assertEquals(1, repository.countByBrand(TruckBrand.VOLVO));
        assertEquals(0, repository.countByBrand(TruckBrand.SCANIA));
    }

    @Test
    void testExistsByVin() {
        assertFalse(repository.existsByVin("WDB9340151K123456"));
        
        repository.save(testTruck);
        
        assertTrue(repository.existsByVin("WDB9340151K123456"));
        assertFalse(repository.existsByVin("NONEXISTENT"));
    }
}
