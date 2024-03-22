package sg.edu.nus.journybackend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sg.edu.nus.journybackend.dto.KMLFileDto;
import sg.edu.nus.journybackend.entity.KMLFile;
import sg.edu.nus.journybackend.mapper.KMLFileMapper;
import sg.edu.nus.journybackend.repository.KMLFileRepository;
import sg.edu.nus.journybackend.service.KMLFileService;

@Service
@AllArgsConstructor
public class KMLFileServiceImpl implements KMLFileService {

    private KMLFileRepository kmlFileRepository;

    @Override
    public KMLFileDto uploadKML(KMLFileDto kmlFileDto) {
        KMLFile kmlFile = KMLFileMapper.mapToKML(kmlFileDto);
        KMLFile savedKMLFile = kmlFileRepository.save(kmlFile);
        return KMLFileMapper.mapToKMLDto(savedKMLFile);
    }
}
