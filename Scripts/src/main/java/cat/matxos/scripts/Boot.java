package cat.matxos.scripts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@Configuration
//@EnableAutoConfiguration

@PropertySource({
        "file:registration.properties",
})

@ComponentScan(basePackages={"cat.matxos"})
@EnableJpaRepositories(basePackages ={"cat.matxos.dao", "cat.matxos.registration.dao"})
@EntityScan("cat.matxos.*")
@SpringBootApplication
public class Boot  {

    public static void main(String[] args) {

        SpringApplication springApplication =
                new SpringApplicationBuilder()
                        .sources(Boot.class)
                        .web(false)
                        .build();

        ConfigurableApplicationContext context =springApplication.run(args);

        String race;
        try {
            if (args[0].equals("bib")){
                race = args[1];
                int startBib = Integer.valueOf(args[2]);

                BibAssign bibAssign = context.getBean(BibAssign.class);
                bibAssign.assign(startBib, race);
            } else if (args[0].equals("feec")){
                race = args[1];
                String pathOutput = args[2];
                FEECChecker checker = context.getBean(FEECChecker.class);
                checker.check(race, pathOutput);
            } else if (args[0].equals("route")) {
                race = args[1];
                RouteChecker routeChecker = context.getBean(RouteChecker.class);
                routeChecker.check(race);
            } else {
                System.err.println("NO task");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
