package sg.edu.nus.journybackend.mapper;

import sg.edu.nus.journybackend.dto.KMLFileDto;
import sg.edu.nus.journybackend.entity.KMLFile;

public class KMLFileMapper {
    public static KMLFileDto mapToKMLDto(KMLFile kmlFile) {
        return new KMLFileDto(
                kmlFile.getId(),
                kmlFile.getFileName(),
                kmlFile.getFileData()
        );
    }

    public static KMLFile mapToKML(KMLFileDto kmlFileDto) {
        return new KMLFile(
                kmlFileDto.getId(),
                kmlFileDto.getFileName(),
                kmlFileDto.getFileData()
        );
    }
}
