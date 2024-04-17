//package sg.edu.nus.journybackend.controller;
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import sg.edu.nus.journybackend.entity.KMLFile;
//import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
//import sg.edu.nus.journybackend.service.KMLFileService;
//
//import java.io.IOException;
//
//@CrossOrigin("*")
//@AllArgsConstructor
//@RestController
//@RequestMapping("/api/kml_files")
//public class KMLFileController {
//
//    @Autowired
//    private KMLFileService kmlFileService;
//
//    @PostMapping("/{postId}")
//    public ResponseEntity<?> uploadKMLFileToFileSystem(@PathVariable("postId") Long postId, @RequestBody MultipartFile file) {
//        try {
//            KMLFile savedKMLFile = kmlFileService.storeIntoFileSystem(postId, file);
//
//            return new ResponseEntity<>(savedKMLFile, HttpStatus.CREATED);
//        } catch (IOException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<?> retrieveKMLFile(@PathVariable("id") Long fileId) {
//        try {
//            KMLFile kmlFile = kmlFileService.downloadKMLFileUsingFileId(fileId);
//            kmlFile.setPost(null);
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_XML)
//                    .header("Content-Disposition", "attachment; filename=\"" + kmlFile.getFileName() + "\"")
//                    .body(kmlFile.getFileData());
//        } catch (IOException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//
//}
