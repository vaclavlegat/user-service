package cz.legat.app;

import cz.legat.library.UserService;
import cz.legat.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(String... args) {

        if (args.length == 1) {

            try {
                Long userId = Long.parseLong(args[0]);
                User user = userService.fetchUserWithPosts(userId);
                System.out.println(user);
            } catch (NumberFormatException e) {
                System.err.println("Arg must be a number.");
            }

        }

    }
}
