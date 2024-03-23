package sg.edu.nus.journybackend.service;

import sg.edu.nus.journybackend.dto.KMLFileDto;

public interface KMLFileService {
    KMLFileDto uploadKMLFile(KMLFileDto kmlFileDto);

    KMLFileDto downloadKMLFile(String kmlFileId);
}
