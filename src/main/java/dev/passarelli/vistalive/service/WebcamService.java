package dev.passarelli.vistalive.service;

import dev.passarelli.vistalive.exception.EntityNotFoundException;
import dev.passarelli.vistalive.model.Webcam;
import dev.passarelli.vistalive.repository.WebcamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class WebcamService {

    private final WebcamRepository webcamRepository;

    @Autowired
    public WebcamService(WebcamRepository webcamRepository) {
        this.webcamRepository = webcamRepository;
    }

    public Iterable<Webcam> getAllWebcams() {
        return webcamRepository.findAll();
    }

    public Optional<Webcam> getWebcamById(long id) {
        return webcamRepository.findById(id);
    }

    @Transactional
    public Webcam saveWebcam(Webcam webcam) {
        return webcamRepository.save(webcam);
    }

    @Transactional
    public void deleteWebcam(long id) {
        if (!webcamRepository.existsById(id)) {
            throw new EntityNotFoundException("Webcam con ID " + id + " non trovata");
        }
        webcamRepository.deleteById(id);
    }

    @Transactional
    public Webcam updateWebcam(long id, Webcam newWebcamDetails) {
        return webcamRepository.findById(id).map(existingWebcam -> {
            existingWebcam.setName(newWebcamDetails.getName());
            existingWebcam.setDescription(newWebcamDetails.getDescription());
            existingWebcam.setStream_url(newWebcamDetails.getStream_url());
            return webcamRepository.save(existingWebcam);
        }).orElseThrow(() -> new EntityNotFoundException("Webcam con ID " + id + " non trovata"));
    }
}
