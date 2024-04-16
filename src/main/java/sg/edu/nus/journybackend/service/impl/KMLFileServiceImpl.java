package sg.edu.nus.journybackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.entity.KMLFile;
import sg.edu.nus.journybackend.exception.ResourceNotFoundException;
import sg.edu.nus.journybackend.repository.KMLFileRepository;
import sg.edu.nus.journybackend.service.KMLFileService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class KMLFileServiceImpl implements KMLFileService {

    @Autowired
    private KMLFileRepository kmlFileRepository;

    private static final String BASE_PATH = System.getProperty("user.dir");
    private static final String RELATIVE_PATH = "src/main/kml/";
    private static final String FILE_PATH = Paths.get(BASE_PATH, RELATIVE_PATH).toString();

    @Override
    public KMLFile storeIntoFileSystem(MultipartFile file) throws IOException {
        String filePath = FILE_PATH + "\\" + file.getOriginalFilename();
        System.out.println(filePath);

        KMLFile kmlFile = KMLFile.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .filePath(filePath)
                .fileData(file.getBytes())
                .build();

        kmlFile = kmlFileRepository.save(kmlFile);

        file.transferTo(new File(filePath));

        if (kmlFile.getKmlFileId() != null) {
            return kmlFile;
        } else {
            throw new FileSystemException("Unable to save file");
        }
    }

    @Override
    public KMLFile downloadKMLFileUsingFileId(Long kmlFileId) throws IOException {
        KMLFile kmlFile = kmlFileRepository.findById(kmlFileId).orElseThrow(
                () -> new ResourceNotFoundException("KML file does not exist with given id : " + kmlFileId)
        );

        String path = kmlFile.getFilePath();
//        System.out.println("path: " + path);

        byte[] fileContent = java.nio.file.Files.readAllBytes(new File(path).toPath());

        kmlFile.setFileData(fileContent);
        return kmlFile;
    }

    //DB implementation
//    @Override
//    public KMLFile storeFile(MultipartFile file) throws IOException {
//        KMLFile kmlFile = KMLFile.builder()
//                .fileName(file.getOriginalFilename())
//                .fileType(file.getContentType())
//                .fileData(file.getBytes())
//                .build();
//
//        kmlFile = kmlFileRepository.save(kmlFile);
//
//        if (kmlFile.getKmlFileId() != null) {
//            return kmlFile;
//        }
//
//        return null;
//    }

//    @Override
//    public KMLFile downloadKMLFileUsingFileName(String fileName) throws IOException {
//        String path = kmlFileRepository.findByFileName(fileName).getFilePath();
//        System.out.println("path" + path);
//
//        byte[] fileContent = java.nio.file.Files.readAllBytes(new File(path).toPath());
//
//        KMLFile kmlFile = kmlFileRepository.findByFileName(fileName);
//        kmlFile.setFileData(fileContent);
//
//        return kmlFile;
//    }

}
