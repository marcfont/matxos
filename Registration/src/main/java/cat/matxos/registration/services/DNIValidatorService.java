package cat.matxos.registration.services;


public interface DNIValidatorService {

    boolean isValid(String dni);

    boolean isVIP(String dni);

}
