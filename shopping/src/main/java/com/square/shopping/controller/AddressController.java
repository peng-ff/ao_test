package com.square.shopping.controller;

import com.square.shopping.dto.request.AddressRequest;
import com.square.shopping.dto.response.ApiResponse;
import com.square.shopping.entity.Address;
import com.square.shopping.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Address controller for address management
 */
@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "*")
public class AddressController {
    
    @Autowired
    private AddressService addressService;
    
    /**
     * Get user addresses
     */
    @GetMapping
    public ApiResponse<List<Address>> getUserAddresses(@RequestAttribute Long userId) {
        List<Address> addresses = addressService.getUserAddresses(userId);
        return ApiResponse.success(addresses);
    }
    
    /**
     * Get default address
     */
    @GetMapping("/default")
    public ApiResponse<Address> getDefaultAddress(@RequestAttribute Long userId) {
        Address address = addressService.getDefaultAddress(userId);
        return ApiResponse.success(address);
    }
    
    /**
     * Create address
     */
    @PostMapping
    public ApiResponse<Address> createAddress(
            @RequestAttribute Long userId,
            @RequestBody AddressRequest request) {
        Address address = addressService.createAddress(userId, request);
        return ApiResponse.success("Address created successfully", address);
    }
    
    /**
     * Update address
     */
    @PutMapping("/{addressId}")
    public ApiResponse<Address> updateAddress(
            @RequestAttribute Long userId,
            @PathVariable Long addressId,
            @RequestBody AddressRequest request) {
        Address address = addressService.updateAddress(addressId, userId, request);
        return ApiResponse.success("Address updated successfully", address);
    }
    
    /**
     * Delete address
     */
    @DeleteMapping("/{addressId}")
    public ApiResponse<Void> deleteAddress(
            @RequestAttribute Long userId,
            @PathVariable Long addressId) {
        addressService.deleteAddress(addressId, userId);
        return ApiResponse.success("Address deleted successfully", null);
    }
}
