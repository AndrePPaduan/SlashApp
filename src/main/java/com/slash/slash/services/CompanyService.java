package com.slash.slash.services;

import com.slash.slash.exceptions.CompanyAlreadyExists;
import com.slash.slash.exceptions.CompanyDoesNotExist;
import com.slash.slash.exceptions.CompanyHasNoName;
import com.slash.slash.exceptions.ProductListIsNotEmpty;
import com.slash.slash.models.Company;
import com.slash.slash.models.Product;

import java.util.List;

public interface CompanyService {

    public Company addCompany (Company company) throws CompanyAlreadyExists, CompanyHasNoName;
    public void deleteCompany (String companyName) throws CompanyDoesNotExist, ProductListIsNotEmpty;
    public Company editCompany (String companyName, Company newCompany) throws CompanyDoesNotExist, CompanyAlreadyExists;
    public List<Company> listCompanies();
    public Company retrieveCompanyByName(String name);
    public void addProduct(String companyName, Product product);
    public void deleteProduct (String companyName, Product product);
}
