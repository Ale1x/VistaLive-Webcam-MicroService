package dev.passarelli.vistalive.controller;

import dev.passarelli.vistalive.model.Webcam;
import dev.passarelli.vistalive.service.WebcamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WebcamController.class)
@ActiveProfiles("test")
public class WebcamControllerTest {

    @MockBean
    private WebcamService webcamService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllWebcams() throws Exception {
        List<Webcam> webcams = new ArrayList<>();
        webcams.add(new Webcam(1L, "Webcam1", "Desc1", "URL1"));
        webcams.add(new Webcam(2L, "Webcam2", "Desc2", "URL2"));

        when(webcamService.getAllWebcams()).thenReturn(webcams);

        mockMvc.perform(get("/webcams")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Webcam1"));
    }

    @Test
    void testGetWebcamById() throws Exception {
        Webcam webcam = new Webcam(1L, "Webcam1", "Desc1", "URL1");
        given(webcamService.getWebcamById(1L)).willReturn(Optional.of(webcam));

        mockMvc.perform(get("/webcams/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))  // Ensure there's no casting here
                .andExpect(jsonPath("$.name").value("Webcam1"))
                .andExpect(jsonPath("$.description").value("Desc1"))
                .andExpect(jsonPath("$.stream_url").value("URL1"));
    }


    @Test
    void testSaveWebcam() throws Exception {
        Webcam webcam = new Webcam(1L, "Webcam1", "Desc1", "URL1");
        given(webcamService.saveWebcam(any(Webcam.class))).willReturn(webcam);

        mockMvc.perform(post("/webcams")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Webcam1\",\"description\":\"Desc1\",\"stream_url\":\"URL1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Webcam1"))  // Correct usage of jsonPath without casting
                .andExpect(jsonPath("$.description").value("Desc1"))
                .andExpect(jsonPath("$.stream_url").value("URL1"));
    }


    @Test
    void testUpdateWebcam() throws Exception {
        Webcam updatedWebcam = new Webcam(1L, "Webcam1 Updated", "Desc1 Updated", "URL1 Updated");
        given(webcamService.updateWebcam(eq(1L), any(Webcam.class))).willReturn(updatedWebcam);

        mockMvc.perform(put("/webcams/1")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Webcam1 Updated\",\"description\":\"Desc1 Updated\",\"stream_url\":\"URL1 Updated\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Webcam1 Updated"));
    }


    @Test
    void testDeleteWebcam() throws Exception {
        doNothing().when(webcamService).deleteWebcam(1L);

        mockMvc.perform(delete("/webcams/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
