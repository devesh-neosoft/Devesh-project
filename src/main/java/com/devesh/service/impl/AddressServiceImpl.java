package com.devesh.service.impl;

import com.devesh.entities.Address;
import com.devesh.exception.CodeException;
import com.devesh.exception.ErrorCode;
import com.devesh.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
@Slf4j
public class AddressServiceImpl {

    @Autowired
    public AddressRepository addressRepository;

    @Transactional(propagation = Propagation.NESTED, rollbackFor = {Exception.class})
    public void addAddress(Address address) {
        try {
            addressRepository.save(address);

            if (address.getName().isEmpty() || address.getName().length() < 3) {
                throw new CodeException("Student name is too short", ErrorCode.COMMON);
            }
        } catch (Exception e) {
           log.error("Exception occurred ");
        }
    }

}
