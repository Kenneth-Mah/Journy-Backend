package sg.edu.nus.journybackend.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sg.edu.nus.journybackend.entity.KMLFile;
import sg.edu.nus.journybackend.service.KMLFileService;

import java.io.IOException;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/kml_files")
public class KMLFileController {

    @Autowired
    private KMLFileService kmlFileService;

    @PostMapping
    public ResponseEntity<?> uploadKMLFileToFileSystem(@RequestBody MultipartFile file) {
        try {
            KMLFile savedKMLFile = kmlFileService.storeIntoFileSystem(file);

            return new ResponseEntity<>(savedKMLFile, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> retrieveKMLFile(@PathVariable("id") Long fileId) {
        try {
            KMLFile kmlFile = kmlFileService.downloadKMLFileUsingFileId(fileId);

            return new ResponseEntity<>(kmlFile, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping
//    public ResponseEntity<?> uploadKMLFile(@RequestBody MultipartFile file) {
//        try {
//            KMLFile savedKMLFile = kmlFileService.storeFile(file);
//            return new ResponseEntity<>(savedKMLFile, HttpStatus.CREATED);
//        } catch (IOException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("{fileName}")
//    public ResponseEntity<?> getKMLFile(@PathVariable("fileName") String fileName) {
//        try {
//            KMLFile kmlFile = kmlFileService.downloadKMLFileFromFileSystem(fileName);
//
//            return new ResponseEntity<>(kmlFile, HttpStatus.CREATED);
//        } catch (IOException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}
