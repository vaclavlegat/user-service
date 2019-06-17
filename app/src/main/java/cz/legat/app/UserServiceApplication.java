package cz.legat.app;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class UserServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(UserServiceApplication.class)
                .web(args.length != 1 ? WebApplicationType.SERVLET : WebApplicationType.NONE).run(args);
    }
}
