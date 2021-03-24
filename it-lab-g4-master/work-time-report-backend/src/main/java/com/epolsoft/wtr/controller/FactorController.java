package com.epolsoft.wtr.controller;

import java.net.URI;
import java.util.List;

import com.epolsoft.wtr.excepcion.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.epolsoft.wtr.entity.Factor;
import com.epolsoft.wtr.service.FactorService;

@RestController
@RequestMapping(path = "/factor")

public class FactorController {
//public class FactorController {
    @Autowired
    private FactorService factorService;


    @GetMapping()
    public List<Factor> getAllFactor()
    {
    	List <Factor> factors = factorService.findAll();
    	if (factors == null || factors.isEmpty()) {
            throw new NotFoundException("Factors was not founded");
        }
        return factors;
    }

    @PostMapping()
    public ResponseEntity<Object> addFactor(@RequestBody Factor factor)
    {
        factor = factorService.createOrUpdate(factor);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(factor.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{id}")
    public Factor getFactor(@PathVariable int id) {
        return factorService.findById(id);
    }
}
