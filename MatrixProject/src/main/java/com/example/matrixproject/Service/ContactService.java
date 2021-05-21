package com.example.matrixproject.Service;

import com.example.matrixproject.Dao.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    ContactRepository employeeRepository;

    @Autowired
    public ContactService(ContactRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }


}