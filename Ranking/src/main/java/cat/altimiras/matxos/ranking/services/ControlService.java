package cat.altimiras.matxos.ranking.services;


import cat.altimiras.matxos.pojo.Control;

import java.util.List;

public interface ControlService {

    List<Control> getControls(String race);

    String getControlName(String id);
}
