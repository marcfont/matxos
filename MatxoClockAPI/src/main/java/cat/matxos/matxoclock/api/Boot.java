package cat.matxos.matxoclock.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@PropertySource({
        "file:matxoclockapi.properties",
        "file:race.properties"
})
@ComponentScan(basePackages={"cat.matxos"})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages ={"cat.matxos.dao"})
@EntityScan("cat.matxos.*")
public class Boot extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Boot.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(Boot.class, args);
    }
}

