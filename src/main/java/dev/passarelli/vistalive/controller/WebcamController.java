package dev.passarelli.vistalive.controller;

import dev.passarelli.vistalive.exception.EntityNotFoundException;
import dev.passarelli.vistalive.model.Webcam;
import dev.passarelli.vistalive.service.WebcamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webcams")
public class WebcamController {

    private final WebcamService webcamService;

    @Autowired
    public WebcamController(WebcamService webcamService) {
        this.webcamService = webcamService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Webcam>> getAllWebcams() {
        return ResponseEntity.ok(webcamService.getAllWebcams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Webcam> getWebcamById(@PathVariable long id) {
        return webcamService.getWebcamById(id)
                .map(webcam -> ResponseEntity.ok(webcam))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Webcam> saveWebcam(@Valid @RequestBody Webcam webcam) {
        return ResponseEntity.ok(webcamService.saveWebcam(webcam));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebcam(@PathVariable long id) {
        try {
            webcamService.deleteWebcam(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Webcam> updateWebcam(@PathVariable long id, @RequestBody Webcam webcam) {
        try {
            Webcam updatedWebcam = webcamService.updateWebcam(id, webcam);
            return ResponseEntity.ok(updatedWebcam);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
