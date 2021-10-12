package com.slash.slash.controllers;

import com.slash.slash.exceptions.CompanyAlreadyExists;
import com.slash.slash.exceptions.CompanyDoesNotExist;
import com.slash.slash.exceptions.CompanyHasNoName;
import com.slash.slash.exceptions.ProductListIsNotEmpty;
import com.slash.slash.models.Company;
import com.slash.slash.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/company")
    public ResponseEntity<?> addCompany(Company company) throws CompanyAlreadyExists, CompanyHasNoName {
        Company createdCompany = companyService.addCompany(company);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @DeleteMapping("/company")
    public ResponseEntity<?> deleteCompany(String companyName) throws CompanyDoesNotExist, ProductListIsNotEmpty {
        companyService.deleteCompany(companyName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/company")
    public ResponseEntity<?> editCompany(String companyName, Company newCompany) throws CompanyDoesNotExist, CompanyAlreadyExists {
        Company company = companyService.editCompany(companyName, newCompany);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @GetMapping("/company")
    public ResponseEntity<?> listCompanies() {
        List<Company> companyList = companyService.listCompanies();
        return new ResponseEntity<>(companyList, HttpStatus.OK);
    }

    @GetMapping("/company/name")
    public ResponseEntity<?> retrieveCompanyByName(String name) {
        Company company = companyService.retrieveCompanyByName(name);
        return new ResponseEntity<>(company, HttpStatus.OK);


    }
}
