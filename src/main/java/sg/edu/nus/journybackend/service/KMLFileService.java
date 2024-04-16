package sg.edu.nus.journybackend.service;

import org.springframework.web.multipart.MultipartFile;
import sg.edu.nus.journybackend.entity.KMLFile;

import java.io.IOException;

public interface KMLFileService {
    // public KMLFile storeFile(MultipartFile file) throws IOException;

    public KMLFile storeIntoFileSystem(MultipartFile file) throws IOException;
    public KMLFile downloadKMLFileUsingFileId(Long fileId) throws IOException;
    // public KMLFile downloadKMLFileUsingFileName(String fileName) throws IOException;
}
