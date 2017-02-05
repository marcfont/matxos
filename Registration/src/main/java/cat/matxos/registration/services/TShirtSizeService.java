package cat.matxos.registration.services;

import cat.matxos.pojo.TShirtSize;

import java.util.List;

public interface TShirtSizeService {

    List<TShirtSize> getSizeAvailable(String race, boolean male);

    //boolean isAvailable(String size);
}
