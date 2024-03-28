//package sg.edu.nus.journybackend.mapper;
//
//import sg.edu.nus.journybackend.dto.KMLFileDto;
//import sg.edu.nus.journybackend.entity.KMLFile;
//
//public class KMLFileMapper {
//    public static KMLFileDto mapToKMLDto(KMLFile kmlFile) {
//        return new KMLFileDto(
//                kmlFile.getKmlFileId(),
//                kmlFile.getFileName(),
//                kmlFile.getFileData(),
//                null
//        );
//    }
//
//    public static KMLFile mapToKML(KMLFileDto kmlFileDto) {
//        return new KMLFile(
//                kmlFileDto.getKmlFileId(),
//                kmlFileDto.getFileName(),
//                kmlFileDto.getFileData(),
//                null
//        );
//    }
//}
