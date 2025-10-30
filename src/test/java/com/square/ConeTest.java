package com.square;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 圆锥体类的单元测试
 */
public class ConeTest {
    
    private static final double DELTA = 0.0001; // 浮点数比较精度
    private Cone cone;

    @BeforeEach
    public void setUp() {
        cone = new Cone(3.0, 4.0);
    }

    @Test
    public void testConstructor() {
        assertEquals(3.0, cone.getRadius(), DELTA);
        assertEquals(4.0, cone.getHeight(), DELTA);
    }

    @Test
    public void testConstructorWithInvalidRadius() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cone(-1.0, 4.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Cone(0, 4.0);
        });
    }

    @Test
    public void testConstructorWithInvalidHeight() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cone(3.0, -1.0);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Cone(3.0, 0);
        });
    }

    @Test
    public void testSetRadius() {
        cone.setRadius(5.0);
        assertEquals(5.0, cone.getRadius(), DELTA);
    }

    @Test
    public void testSetRadiusWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            cone.setRadius(-1.0);
        });
    }

    @Test
    public void testSetHeight() {
        cone.setHeight(10.0);
        assertEquals(10.0, cone.getHeight(), DELTA);
    }

    @Test
    public void testSetHeightWithInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            cone.setHeight(0);
        });
    }

    @Test
    public void testCalculateVolume() {
        // 圆锥体积公式: V = (1/3) × π × r² × h
        // 半径=3, 高度=4
        // V = (1/3) × π × 9 × 4 = 12π ≈ 37.699
        double expectedVolume = (1.0 / 3.0) * Math.PI * 9 * 4;
        assertEquals(expectedVolume, cone.calculateVolume(), DELTA);
    }

    @Test
    public void testCalculateVolumeWithDifferentValues() {
        Cone cone2 = new Cone(5.0, 10.0);
        // V = (1/3) × π × 25 × 10 = 250π/3 ≈ 261.799
        double expectedVolume = (1.0 / 3.0) * Math.PI * 25 * 10;
        assertEquals(expectedVolume, cone2.calculateVolume(), DELTA);
    }

    @Test
    public void testCalculateBaseArea() {
        // 底面积公式: S = π × r²
        // S = π × 9 ≈ 28.274
        double expectedArea = Math.PI * 9;
        assertEquals(expectedArea, cone.calculateBaseArea(), DELTA);
    }

    @Test
    public void testCalculateLateralArea() {
        // 侧面积公式: S = π × r × l
        // l = √(r² + h²) = √(9 + 16) = 5
        // S = π × 3 × 5 = 15π ≈ 47.124
        double slantHeight = Math.sqrt(9 + 16);
        double expectedArea = Math.PI * 3 * slantHeight;
        assertEquals(expectedArea, cone.calculateLateralArea(), DELTA);
    }

    @Test
    public void testCalculateSurfaceArea() {
        // 表面积 = 底面积 + 侧面积
        double expectedArea = cone.calculateBaseArea() + cone.calculateLateralArea();
        assertEquals(expectedArea, cone.calculateSurfaceArea(), DELTA);
    }

    @Test
    public void testToString() {
        String result = cone.toString();
        assertTrue(result.contains("3.00"));
        assertTrue(result.contains("4.00"));
        assertTrue(result.contains("圆锥体"));
    }

    @Test
    public void testConeVolumeCalculatorStaticMethod() {
        double volume = ConeVolumeCalculator.calculateVolume(3.0, 4.0);
        double expectedVolume = (1.0 / 3.0) * Math.PI * 9 * 4;
        assertEquals(expectedVolume, volume, DELTA);
    }

    @Test
    public void testSmallCone() {
        Cone smallCone = new Cone(1.0, 1.0);
        double expectedVolume = (1.0 / 3.0) * Math.PI;
        assertEquals(expectedVolume, smallCone.calculateVolume(), DELTA);
    }

    @Test
    public void testLargeCone() {
        Cone largeCone = new Cone(100.0, 200.0);
        double expectedVolume = (1.0 / 3.0) * Math.PI * 10000 * 200;
        assertEquals(expectedVolume, largeCone.calculateVolume(), DELTA);
    }
}
