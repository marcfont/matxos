package cat.altimiras.matxos.registration.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
public abstract  class Resource {

    @Autowired
    private Environment env;

    protected String getProperty(String key, String defaultValue){
        return env.getProperty(key, defaultValue);
    }

    protected String getProperty(String key){
        return env.getProperty(key);
    }
}
