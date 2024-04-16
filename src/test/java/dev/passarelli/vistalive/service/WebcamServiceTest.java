package dev.passarelli.vistalive.service;

import dev.passarelli.vistalive.exception.EntityNotFoundException;
import dev.passarelli.vistalive.model.Webcam;
import dev.passarelli.vistalive.repository.WebcamRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
class WebcamServiceTest {

    @Test
    void getAllWebcamsReturnsAllWebcamsFromRepository() {
        // Arrange
        WebcamRepository mockRepository = Mockito.mock(WebcamRepository.class);
        WebcamService webcamService = new WebcamService(mockRepository);

        // Act
        webcamService.getAllWebcams();

        // Assert
        verify(mockRepository).findAll();
    }

    @Test
    void getWebcamByIdReturnsWebcamFromRepository() {
        // Arrange
        WebcamRepository mockRepository = Mockito.mock(WebcamRepository.class);
        WebcamService webcamService = new WebcamService(mockRepository);
        long id = 1L;

        // Act
        webcamService.getWebcamById(id);

        // Assert
        verify(mockRepository).findById(id);
    }

    @Test
    void saveWebcamCallsSaveOnRepository() {
        // Arrange
        WebcamRepository mockRepository = Mockito.mock(WebcamRepository.class);
        WebcamService webcamService = new WebcamService(mockRepository);
        Webcam webcam = new Webcam();

        // Act
        webcamService.saveWebcam(webcam);

        // Assert
        verify(mockRepository).save(webcam);
    }

    @Test
    void deleteWebcamThrowsExceptionWhenIdNotFound() {
        // Arrange
        WebcamRepository mockRepository = Mockito.mock(WebcamRepository.class);
        WebcamService webcamService = new WebcamService(mockRepository);
        long id = 1L;

        when(mockRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> webcamService.deleteWebcam(id));
    }

    @Test
    void updateWebcamThrowsExceptionWhenIdNotFound() {
        // Arrange
        WebcamRepository mockRepository = Mockito.mock(WebcamRepository.class);
        WebcamService webcamService = new WebcamService(mockRepository);
        long id = 1L;
        Webcam webcam = new Webcam();

        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> webcamService.updateWebcam(id, webcam));
    }
}