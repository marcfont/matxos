package cat.altimiras.matxos.registration.services;


import cat.altimiras.matxos.pojo.Route;
import cat.altimiras.matxos.pojo.TShirtSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class TShirtSizeServiceImpl implements TShirtSizeService {

    @Autowired
    private Environment env;

    List<TShirtSize> sizes;

    @Override
    public List<TShirtSize> getSizeAvailable() {

        if (sizes == null){
            loadSizes();
        }

        //query bd count sizes
        return sizes.stream().filter(s -> s.getStock() > 0).collect(Collectors.toList());
    }

    @Override
    public boolean isAvailable(String size) {

        if (sizes == null){
            loadSizes();
        }

        int current = 0; //read count per size

        return sizes.stream().filter(s-> size.equals(s.getId())).findFirst().get().getStock() < current;
    }

    private void loadSizes(){

        sizes = new ArrayList<>();

        int i = 1;
        boolean end = false;
        while (!end) {
            TShirtSize t = new TShirtSize();
            String id = env.getProperty("size." + i + ".id");
            if (id == null) {
                end = true;
            } else {
                t.setId(id);
                t.setName(env.getProperty("size." + i + ".name"));
                t.setStock(Integer.valueOf(env.getProperty("size." + i + ".stock")));
                sizes.add(t);
                i++;
            }
        }
    }


}
