package cat.matxos.registration.services;


import cat.matxos.pojo.TShirtSize;
import cat.matxos.dao.RegistrationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Configuration
public class TShirtSizeServiceImpl implements TShirtSizeService {

    private static Logger log = Logger.getLogger(TShirtSizeServiceImpl.class.getName());

    @Autowired
    private Environment env;

    @Autowired
    private RegistrationDAO registrationDAO;

    List<TShirtSize> sizesM;

    List<TShirtSize> sizesF;

    @Override
    public List<TShirtSize> getSizeAvailable(boolean male) {

        if (sizesM == null) {
            loadSizes();
        }

        List<TShirtSize> sizes;
        if (male){
            sizes = sizesM;
        } else {
            sizes = sizesF;
        }

        return sizes.stream().filter(s -> s.getStock() > registrationDAO.countBySizeAndAndIsCompletedIsTrue(s.getId())).collect(Collectors.toList());
    }
/*
    @Override
    public boolean isAvailable(String size) {

        if (sizes == null) {
            loadSizes();
        }

        long current = registrationDAO.countBySizeAndAndIsCompletedIsTrue(size);
        log.log(Level.INFO, "There are " + current + " " + size);
        return sizes.stream().filter(s -> size.equals(s.getId())).findFirst().get().getStock() < current;
    }
*/
    private void loadSizes() {

        sizesF = new ArrayList<>();
        sizesM = new ArrayList<>();

        int i = 1;
        boolean end = false;
        while (!end) {
            TShirtSize tm = new TShirtSize();
            TShirtSize tf = new TShirtSize();
            String id = env.getProperty("size." + i + ".id");
            if (id == null) {
                end = true;
            } else {
                tf.setId(id);
                tf.setName(env.getProperty("size." + i + ".name"));
                tf.setStock(Integer.valueOf(env.getProperty("size." + i + ".F.stock")));
                sizesF.add(tf);
                tm.setId(id);
                tm.setName(env.getProperty("size." + i + ".name"));
                tm.setStock(Integer.valueOf(env.getProperty("size." + i + ".M.stock")));
                sizesM.add(tm);
                i++;
            }
        }
    }


}
