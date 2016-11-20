package cat.matxos.registration.services;

import cat.matxos.pojo.TShirtSize;

import java.util.List;

public interface TShirtSizeService {

    List<TShirtSize> getSizeAvailable(boolean male);

    //boolean isAvailable(String size);
}
