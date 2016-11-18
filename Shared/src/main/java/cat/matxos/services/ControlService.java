package cat.matxos.services;


import cat.matxos.pojo.Control;

import java.util.List;

public interface ControlService {

    List<Control> getControls(String race);

    String getControlName(String race, String id);
}
