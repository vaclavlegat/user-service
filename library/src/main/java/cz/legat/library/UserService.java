package cz.legat.library;

import cz.legat.library.model.Post;
import cz.legat.library.model.User;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String USERS_ENDPOINT = "/users/{id}";
    private static final String POSTS_ENDPOINT = "/posts?userId={id}";

    @Autowired
    private RestTemplate restTemplate;

    private User fetchUser(@NonNull Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<User> userResponse = restTemplate.exchange(BASE_URL + USERS_ENDPOINT, HttpMethod.GET, entity, User.class, userId);

        if (userResponse.getStatusCode() == HttpStatus.OK) {
            return userResponse.getBody();
        }

        return null;
    }

    private List<Post> fetchPostsForUser(@NonNull Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Post>> postsResponse = restTemplate.exchange(BASE_URL + POSTS_ENDPOINT, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Post>>() {
        }, userId);

        if (postsResponse.getStatusCode() == HttpStatus.OK) {
            return postsResponse.getBody();
        }

        return new ArrayList<>();
    }

    public User fetchUserWithPosts(@NonNull Long userId) {
        User user = fetchUser(userId);

        if (user != null) {
            List<Post> posts = fetchPostsForUser(userId);
            user.setPosts(posts);
        }

        return user;
    }


}
