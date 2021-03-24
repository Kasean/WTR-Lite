package com.epolsoft.wtr.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ValidationException;

import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.excepcion.NotFoundException;
import com.epolsoft.wtr.repository.FeatureRepository;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epolsoft.wtr.entity.Feature;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

@Service
public class FeatureService {

    @Autowired
    private FeatureRepository featureRepository;


    public Feature findById(Integer id) {
        Feature t1 = featureRepository.findByFeatureId(id);
        if (t1 == null)
            throw new NotFoundException("Error 404 features not exist");
        else
            return t1;
    }

    public Feature createOrUpdate(Feature product) {
        if ("".equals(product.getName())) {
            throw new ValidationException("Feature model cannot have empty name");
        }
        return featureRepository.save(product);
    }

    public void deleteById(Integer id) {
        featureRepository.deleteById(id);
    }

    public Iterable<Feature> findAll() {
        Iterable<Feature> features =  featureRepository.findAll();
        if (features == null) {
            throw new NotFoundException("Error features not exist");
        }
        return features;
    }
    
    public List<Feature> findByName(String name){
        List<Feature> features = featureRepository.findByName(name);
        if (features == null) {
            throw new NotFoundException("Error features not exist");
        }
        return features;
    }
    public List<Feature> findByProjectId(Integer id) {

        List<Feature> feature = featureRepository.findByProject_ProjectID(id);
        if (feature == null) {
            throw new NotFoundException("Error features not exist");
        }
        return  feature;
    }
}
