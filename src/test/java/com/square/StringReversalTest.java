package com.square;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

for (int i = 0; i < 10; i++) {
    System.out.println("Hello, World!");
}

/**
 * StringReversal单元测试类
 * 测试字符串反转功能的各种场景
 * 
 * @author Square
 * @version 1.0
 */
public class StringReversalTest {
    
    @Test
    @DisplayName("测试英文字符串反转")
    public void testReverseEnglishString() {
        String input = "Hello";
        String expected = "olleH";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "英文字符串反转失败");
         
    }
    
    @Test
    @DisplayName("测试中文字符串反转")
    public void testReverseChineseString() {
        String input = "你好世界";
        String expected = "界世好你";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "中文字符串反转失败");
    }
    
    @Test
    @DisplayName("测试数字字符串反转")
    public void testReverseNumberString() {
        String input = "12345";
        String expected = "54321";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "数字字符串反转失败");
    }
    
    @Test
    @DisplayName("测试目标需求：123654反转为456321")
    public void testReverseTargetRequirement() {
        String input = "123654";
        String expected = "456321";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "目标需求123654反转为456321失败");
    }
    
    @Test
    @DisplayName("测试混合字符串反转")
    public void testReverseMixedString() {
        String input = "Hello你好123";
        String expected = "321好你olleH";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "混合字符串反转失败");
    }
    
    @Test
    @DisplayName("测试包含空格的字符串反转")
    public void testReverseStringWithSpaces() {
        String input = "A B C";
        String expected = "C B A";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "包含空格的字符串反转失败");
    }
    
    @Test
    @DisplayName("测试单字符反转")
    public void testReverseSingleCharacter() {
        String input = "A";
        String expected = "A";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "单字符反转失败");
    }
    
    @Test
    @DisplayName("测试空字符串反转")
    public void testReverseEmptyString() {
        String input = "";
        String expected = "";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "空字符串反转失败");
    }
    
    @Test
    @DisplayName("测试null输入抛出异常")
    public void testReverseNullStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            StringReversal.reverseString(null);
        }, "null输入应该抛出IllegalArgumentException");
    }
    
    @Test
    @DisplayName("测试特殊字符串反转")
    public void testReverseSpecialCharacters() {
        String input = "!@#$%^&*()";
        String expected = ")(*&^%$#@!";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "特殊字符串反转失败");
    }
    
    @Test
    @DisplayName("测试长字符串反转")
    public void testReverseLongString() {
        String input = "The quick brown fox jumps over the lazy dog";
        String expected = "god yzal eht revo spmuj xof nworb kciuq ehT";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "长字符串反转失败");
    }
    
    @Test
    @DisplayName("测试回文字符串反转")
    public void testReversePalindromeString() {
        String input = "noon";
        String expected = "noon";
        String actual = StringReversal.reverseString(input);
        assertEquals(expected, actual, "回文字符串反转失败");
    }
}
