package cat.matxos.services;


import cat.matxos.pojo.Control;

import java.util.List;

public interface ControlService {

    List<Control> getControls(String race);

    String getControlName(String race, String id);

    Control getControl(String race, String id);

    List<Control> getControlsBefore(String race, String control);

    List<Control> getControlsAfter(String race, String control);
}
