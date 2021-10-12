package com.slash.slash.services;

import com.slash.slash.exceptions.CompanyAlreadyExists;
import com.slash.slash.exceptions.CompanyDoesNotExist;
import com.slash.slash.exceptions.CompanyHasNoName;
import com.slash.slash.exceptions.ProductListIsNotEmpty;
import com.slash.slash.models.Company;
import com.slash.slash.models.Product;
import com.slash.slash.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company addCompany(Company company) throws CompanyAlreadyExists, CompanyHasNoName {

        List<Company> companyList = listCompanies();

        if (company.getName() == null) {
            throw new CompanyHasNoName();
        }

        for (Company savedCompany : companyList) {
            if (savedCompany.getName().equals(company.getName())) {
                throw new CompanyAlreadyExists();
            }
        }

        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(String companyName) throws CompanyDoesNotExist, ProductListIsNotEmpty {
        Company company = retrieveCompanyByName(companyName);
        if (company != null) {
            if (!company.getProductList().isEmpty()){
                throw new ProductListIsNotEmpty();
            }
            companyRepository.delete(company);
        } else {
            throw new CompanyDoesNotExist();
        }
    }

    @Override
    public Company editCompany(String companyName, Company newCompany) throws CompanyDoesNotExist, CompanyAlreadyExists {
        Company oldCompany = retrieveCompanyByName(companyName);
        if (oldCompany == null) {
            throw new CompanyDoesNotExist();
        }

        List<Company> companyList = companyRepository.findAll();
        for (Company savedCompanies : companyList) {
            if (savedCompanies.getName().equals(newCompany.getName()) && oldCompany.getId() != savedCompanies.getId()) {
                throw new CompanyAlreadyExists();
            }
        }

        oldCompany.setAddress(newCompany.getAddress());
        oldCompany.setCity(newCompany.getCity());
        oldCompany.setName(newCompany.getName());
        oldCompany.setPhoneNumber(newCompany.getPhoneNumber());

        companyRepository.save(oldCompany);

        return oldCompany;
    }

    @Override
    public List<Company> listCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company retrieveCompanyByName(String name) {
        System.out.println(name);
        List<Company> companyList = listCompanies();


        for (Company company : companyList) {
            if (company.getName().equals(name)) return company;
        }
        return null;
    }

    @Override
    public void addProduct(String companyName, Product product) {
        Company company = retrieveCompanyByName(companyName);
        company.addProduct(product);
    }

    @Override
    public void deleteProduct(String companyName, Product product) {
        Company company = retrieveCompanyByName(companyName);
        company.deleteProduct(product);
    }
}
