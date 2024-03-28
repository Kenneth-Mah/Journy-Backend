package sg.edu.nus.journybackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
    private Post post;
}
