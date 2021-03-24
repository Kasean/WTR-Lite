package com.epolsoft.wtr.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import com.epolsoft.wtr.entity.User;
import com.epolsoft.wtr.excepcion.NotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epolsoft.wtr.entity.Factor;
import com.epolsoft.wtr.repository.FactorRepository;

@Service
public class FactorService {
    
    @Autowired
    private FactorRepository factorRepository;

    @Transactional
    public List<Factor> findAll() {

        List<Factor> factors = factorRepository.findAll();
        if (factors == null) {
            throw new NotFoundException("Error factors not exist");
        }
        return factors;
    }

    public List<Factor> findByName(String factorName){
        if (factorName == null) {
            throw new NotFoundException("Error name was not imputed");
        }
        List<Factor> factor = factorRepository.findByName(factorName);
        return factor;
    }
    
    public Factor createOrUpdate(Factor factor) {
        if ("".equals(factor.getName())) {
            throw new ValidationException("Factor cannot have empty name");
        }
        return factorRepository.save(factor);
    }
    
    public Factor findById(Integer id){

        Factor factor = factorRepository.findOneById(id);
        if (factor == null){
            throw new NotFoundException("Error factor not");
        }
        return factor;
    }
}
