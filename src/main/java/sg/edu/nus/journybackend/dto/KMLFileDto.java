package sg.edu.nus.journybackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KMLFileDto {
    private String id;
    private String fileName;
    private byte[] fileData;
}
