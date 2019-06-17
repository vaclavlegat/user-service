package cz.legat.library;

import cz.legat.library.model.Post;
import cz.legat.library.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testExistingUser() {

        User user = new User();
        user.setName("test user");
        List<Post> posts = new ArrayList<>();
        Post post = new Post();
        post.setTitle("test post");
        posts.add(post);

        ResponseEntity<User> userResponse = new ResponseEntity<>(user, HttpStatus.OK);
        ResponseEntity<List<Post>> postResponse = new ResponseEntity<>(posts, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(User.class), anyLong()))
                .thenReturn(userResponse);
        Mockito.when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(new ParameterizedTypeReference<List<Post>>() {
        }), anyLong()))
                .thenReturn(postResponse);

        User userResult = userService.fetchUserWithPosts(1L);
        assertNotNull(userResult);
        assertEquals("test user", userResult.getName());
        assertEquals(1, userResult.getPosts().size());
        assertEquals("test post", userResult.getPosts().get(0).getTitle());
    }

    @Test
    public void testNonExistingUser() {

        ResponseEntity<User> userResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Mockito.when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(User.class), anyLong()))
                .thenReturn(userResponse);

        assertNull(userService.fetchUserWithPosts(-1L));
    }

    @SpringBootApplication
    static class TestConfiguration {
    }

}