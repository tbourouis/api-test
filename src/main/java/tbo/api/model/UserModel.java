package tbo.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
@Setter
public class UserModel {

    private UUID userId;
    private String username;
    private String password;
    private String avatar;
    private LocalDate birthdate;

}
