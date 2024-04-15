package sg.edu.nus.journybackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kml_file")
public class KMLFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kmlFileId;

    private String fileName;
    private byte[] fileData;

    @OneToOne
    @JsonIgnore
    private Post post;
}
