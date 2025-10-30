package com.square.shopping.service;

import com.square.shopping.dto.request.AddressRequest;
import com.square.shopping.entity.Address;
import com.square.shopping.exception.BusinessException;
import com.square.shopping.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Address service for address management
 */
@Service
public class AddressService {
    
    @Autowired
    private AddressRepository addressRepository;
    
    /**
     * Create address
     */
    @Transactional
    public Address createAddress(Long userId, AddressRequest request) {
        Address address = new Address();
        address.setUserId(userId);
        address.setRecipientName(request.getRecipientName());
        address.setPhone(request.getPhone());
        address.setCountry(request.getCountry());
        address.setProvince(request.getProvince());
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setStreet(request.getStreet());
        address.setPostalCode(request.getPostalCode());
        address.setIsDefault(request.getIsDefault() != null ? request.getIsDefault() : false);
        address.setAddressType(request.getAddressType() != null ? request.getAddressType() : "SHIPPING");
        
        addressRepository.insert(address);
        
        // If this is default address, clear others
        if (address.getIsDefault()) {
            addressRepository.clearDefaultExcept(userId, address.getId());
        }
        
        return address;
    }
    
    /**
     * Update address
     */
    @Transactional
    public Address updateAddress(Long addressId, Long userId, AddressRequest request) {
        Address address = addressRepository.findById(addressId);
        if (address == null) {
            throw new BusinessException("Address not found");
        }
        
        if (!address.getUserId().equals(userId)) {
            throw new BusinessException("Unauthorized access");
        }
        
        address.setRecipientName(request.getRecipientName());
        address.setPhone(request.getPhone());
        address.setCountry(request.getCountry());
        address.setProvince(request.getProvince());
        address.setCity(request.getCity());
        address.setDistrict(request.getDistrict());
        address.setStreet(request.getStreet());
        address.setPostalCode(request.getPostalCode());
        address.setIsDefault(request.getIsDefault());
        address.setAddressType(request.getAddressType());
        
        addressRepository.update(address);
        
        // If this is default address, clear others
        if (address.getIsDefault()) {
            addressRepository.clearDefaultExcept(userId, addressId);
        }
        
        return addressRepository.findById(addressId);
    }
    
    /**
     * Get user addresses
     */
    public List<Address> getUserAddresses(Long userId) {
        return addressRepository.findByUserId(userId);
    }
    
    /**
     * Get default address
     */
    public Address getDefaultAddress(Long userId) {
        return addressRepository.findDefaultByUserId(userId);
    }
    
    /**
     * Delete address
     */
    @Transactional
    public void deleteAddress(Long addressId, Long userId) {
        Address address = addressRepository.findById(addressId);
        if (address == null) {
            throw new BusinessException("Address not found");
        }
        
        if (!address.getUserId().equals(userId)) {
            throw new BusinessException("Unauthorized access");
        }
        
        addressRepository.deleteById(addressId);
    }
}
