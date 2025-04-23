package via.sep4.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import via.sep4.repository.PlantExperimentRepository;
import via.sep4.model.PlantExperiment;

@RestController
@RequestMapping("/api/experiments")
public class PlantExperimentController {
    @Autowired
    private PlantExperimentRepository experimentRepository;

    @GetMapping
    public ResponseEntity<List<PlantExperiment>> getAllExperiments() {
        List<PlantExperiment> experiments = experimentRepository.findAll();
        return ResponseEntity.ok(experiments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantExperiment> getExperimentById(@PathVariable Long id) {
        Optional<PlantExperiment> experiment = experimentRepository.findById(id);
        return experiment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/species/{species}")
    public ResponseEntity<List<PlantExperiment>> getExperimentsBySpecies(@PathVariable String species) {
        List<PlantExperiment> experiments = experimentRepository.findByPlantSpecies(species);
        return ResponseEntity.ok(experiments);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PlantExperiment> getExperimentByName(@PathVariable String name) {
        Optional<PlantExperiment> experiment = experimentRepository.findByName(name);
        return experiment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PlantExperiment> createExperiment(@RequestBody PlantExperiment experiment) {
        PlantExperiment savedExperiment = experimentRepository.save(experiment);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExperiment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantExperiment> updateExperiment(@PathVariable Long id,
            @RequestBody PlantExperiment experiment) {
        if (!experimentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        experiment.setId(id);
        PlantExperiment updatedExperiment = experimentRepository.save(experiment);
        return ResponseEntity.ok(updatedExperiment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperiment(@PathVariable Long id) {
        if (!experimentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        experimentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
