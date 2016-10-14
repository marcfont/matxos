package cat.altimiras.matxos.registration.services;

import cat.altimiras.matxos.pojo.TShirtSize;

import java.util.List;

/**
 * Created by faltimiras on 26/09/16.
 */
public interface TShirtSizeService {

    List<TShirtSize> getSizeAvailable();

    boolean isAvailable(String size);
}
