package cz.legat.library.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    private String name;
    private String username;
    private String email;
    private List<Post> posts;
}
