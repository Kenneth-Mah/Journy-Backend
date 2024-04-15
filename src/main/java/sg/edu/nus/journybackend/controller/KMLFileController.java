//package sg.edu.nus.journybackend.controller;
//
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import sg.edu.nus.journybackend.dto.KMLFileDto;
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
//    private KMLFileService kmlFileService;
//
//    @PostMapping
//    public ResponseEntity<?> uploadKMLFile(@RequestBody MultipartFile file) {
//        try {
//            KMLFileDto kmlFileDto = new KMLFileDto();
//            kmlFileDto.setFileName(file.getOriginalFilename());
//            kmlFileDto.setFileData(file.getBytes());
//
//            KMLFileDto savedKMLFileDto = kmlFileService.uploadKMLFile(kmlFileDto);
//            return new ResponseEntity<>(savedKMLFileDto, HttpStatus.CREATED);
//        } catch (IOException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<?> downloadKMLFile(@PathVariable("id") Long kmlFileId) {
//        try {
//            KMLFileDto kmlFileDto = kmlFileService.downloadKMLFile(kmlFileId);
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_XML)
//                    .header("Content-Disposition", "attachment; filename=\"" + kmlFileDto.getFileName() + "\"")
//                    .body(kmlFileDto.getFileData());
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }
//}
