package sg.edu.nus.journybackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sg.edu.nus.journybackend.dto.KMLFileDto;
import sg.edu.nus.journybackend.service.KMLFileService;

import java.io.IOException;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/kml_files")
public class KMLFileController {

    private KMLFileService kmlFileService;

    @PostMapping
    public ResponseEntity<?> uploadKMLFile(@RequestBody MultipartFile file) {
        try {
            KMLFileDto kmlFileDto = new KMLFileDto();
            kmlFileDto.setFileName(file.getOriginalFilename());
            kmlFileDto.setFileData(file.getBytes());

            KMLFileDto savedKMLFileDto = kmlFileService.uploadKML(kmlFileDto);
            return new ResponseEntity<>(savedKMLFileDto, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
