package cat.matxos.registration.services;


import cat.matxos.pojo.Registration;

public interface EmailService {

    void sendConfirmation(String race, Registration registration);
}
