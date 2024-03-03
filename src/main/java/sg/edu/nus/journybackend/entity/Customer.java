package sg.edu.nus.journybackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("customers")
public class Customer {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    private String password;
    private String name;
    @Indexed(unique = true)
    private String email;
}
