package com.epolsoft.wtr.controller;

import com.epolsoft.wtr.entity.Feature;
import com.epolsoft.wtr.entity.Project;
import com.epolsoft.wtr.service.FeatureService;

import java.util.ArrayList;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/feature")

public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @GetMapping
    public ArrayList<Feature> getAllFeatures()
    {
        ArrayList<Feature> features = new ArrayList<>();
        for(Feature feature : featureService.findAll()){
            features.add(feature);
        }
        return features;
    }

    @PostMapping
    public ResponseEntity<Object> addFeature(@RequestBody Feature feature)
    {
        feature = featureService.createOrUpdate(feature);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(feature.getFeatureId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping(path = "/{id}")
    public Feature getFeature(@PathVariable int id) {

        return featureService.findById(id);
    }
    @GetMapping(path = "/byProject/{projectID}")
    public List<Feature> getFeatureByProjectId(@PathVariable("projectID") int id) {
        return featureService.findByProjectId(id);
    }
}
