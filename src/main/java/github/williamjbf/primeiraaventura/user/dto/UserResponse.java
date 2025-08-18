package github.williamjbf.primeiraaventura.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
}
