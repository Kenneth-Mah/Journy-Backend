package sg.edu.nus.journybackend.service;

import org.springframework.web.multipart.MultipartFile;
import sg.edu.nus.journybackend.entity.KMLFile;

import java.io.IOException;

public interface KMLFileService {
    KMLFile storeIntoFileSystem(Long postId, MultipartFile file) throws IOException;

    KMLFile downloadKMLFileUsingFileId(Long fileId) throws IOException;
}
