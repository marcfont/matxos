package cat.altimiras.matxos.registration.resources;


import cat.altimiras.matxos.registration.dao.RegistrationDAO;
import cat.altimiras.matxos.registration.forms.RegistrationForm;
import cat.altimiras.matxos.pojo.Registration;
import cat.altimiras.matxos.registration.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class RegistrationResource extends Resource {

    private static Logger log = Logger.getLogger(RegistrationResource.class.getName());

    //https://stormpath.com/blog/tutorial-crud-spring-boot-20-minutes

    @Autowired
    private Environment env;

    @Autowired
    private ControlService controlService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private FEECService feecService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private DNIValidatorService dniValidatorService;

    @Autowired
    private TShirtSizeService tShirtSizeService;

    @Autowired
    private RegistrationDAO registrationDAO;

    @Value("${vip.enabled}")
    private boolean vipEnabled;

    @GetMapping("registration/race/{race}/registrations")
    public String registrations(@PathVariable("race") String race, Model model) {

        List<Registration> registrationList = registrationDAO.findByRaceAndPaymentIdNotNull(race);

        model.addAttribute("registrations", registrationList);
        fillModel(model, race);

        return "registrations";
    }

    @GetMapping("/registration/race/{race}/registration")
    public String registration(@PathVariable("race") String race, Model model) {
        fillModel(model, race);

        if (isFull(race)) {
            return "race_full";
        }

        if (vipEnabled) {
            return "vip_check";
        } else {
            return "registration";
        }
    }

    @PostMapping("/registration/race/{race}/registration")
    public String save(@PathVariable("race") String race, Model model, @Valid RegistrationForm registration, BindingResult bindingResult) {
        log.log(Level.INFO, "new registration");
        try {
            if (isFull(race)) {
                return "race_full";
            }

            //double check, if its vip
            if (vipEnabled && !dniValidatorService.isVIP(registration.getDni())) {
                log.log(Level.WARNING, "Trying to register and is not VIP dni:" + registration.getDni());
                return "not_vip_error";
            }

            //validate form
            if (bindingResult.hasErrors()) {
                prepareError(registration, model, bindingResult, null);
                log.log(Level.FINE, "errors on new registration");
                return "registration";
            }

            //validate dni/nie
            if (!dniValidatorService.isValid(registration.getDni())) {
                prepareError(registration, model, bindingResult, "dni");
                return "registration";
            }

            //validate feec id
            if (!feecService.isValid(registration.getFeec(), registration.getDni())) {
                prepareError(registration, model, bindingResult, "feec");
                return "registration";
            }

            //validate phone
            if (!phoneService.isValid(registration.getTelf())) {
                prepareError(registration, model, bindingResult, "telf");
                return "registration";
            }

            //store
            registration.setDni(registration.getDni().toUpperCase());

            Registration newReg = (Registration) registrationDAO.save(convert(registration));

            model.addAttribute("id", newReg.getId());
            model.addAttribute("title", getProperty(race + ".race.name"));
            model.addAttribute("race", race);
            model.addAttribute("registration", registration);
            return "registration_confirmation";

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error storing registration", e);
            return "error";
        }
    }

    @PostMapping("/registration/race/{race}/check")
    public String checkVip(@PathVariable("race") String race, Model model, @RequestParam String dni) {
        log.log(Level.FINE, "Check if dni" + dni + "is vip");
        try {
            if (dniValidatorService.isVIP(dni)) {
                RegistrationForm registration = new RegistrationForm();
                registration.setDni(dni.toUpperCase());
                model.addAttribute("registration", registration);
                fillModel(model, race);
                return "registration";
            } else {
                model.addAttribute("title", getProperty(race + ".race.name"));
                model.addAttribute("race", race);
                return "not_vip_error";
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error checking vip", e);
            return "error";
        }
    }

    @GetMapping("/registration/race/{race}/order/{order_id}/confirmation")
    public String confirmation(@PathVariable("race") String race, @PathVariable("order_id") String orderId, Model model, @RequestParam("status") String status) {
        try {
            fillModel(model, race);
            log.log(Level.INFO, "Payment confirmation race:" + race + ", orderid: " + orderId);
            if (status.equals("OK")) {
                Registration registration = registrationDAO.findByRaceAndPaymentId(race, orderId);
                log.log(Level.INFO, "Payment confirmation OK! race:" + race + ", orderid: " + orderId);
                return "end";
            } else {
                log.log(Level.WARNING, "Bank payment fail. race: " + race + ", orderid: " + orderId);
                return "error_confirmation";
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error checking confirmation payment", e);
            return "error_confirmation";
        }
    }

    private void prepareError(RegistrationForm registration, Model model, BindingResult bindingResult, String error) {
        fillModel(model, registration.getRace());

        if (error != null) {
            bindingResult.rejectValue(error, error);
            log.log(Level.INFO, error+ " not valid on new registration");
        }

        model.addAttribute("registration", registration);
        model.addAttribute("errors", bindingResult);
    }

    private void fillModel(Model model, String race) {
        model.addAttribute("title", getProperty(race + ".race.name"));
        model.addAttribute("routes", routeService.getRoutes(race));
        model.addAttribute("sizes", tShirtSizeService.getSizeAvailable());
        model.addAttribute("race", race);
    }

    private Registration convert(RegistrationForm form) {
        Registration registration = new Registration();
        registration.setBibname(form.getBibname());
        registration.setBirthdayStr(form.getBirthday());
        registration.setClub(form.getClub());
        registration.setDni(form.getDni());
        registration.setEmail(form.getEmail());
        registration.setFeec(form.getFeec());
        registration.setGender(form.getGender());
        registration.setName(form.getName());
        registration.setRace(form.getRace());
        registration.setRoute(form.getRoute());
        registration.setSize(form.getSize());
        registration.setSurname1(form.getSurname1());
        registration.setSurname2(form.getSurname2());
        registration.setTelf(form.getTelf());
        registration.setTelfemer(form.getTelfemer());
        registration.setTown(form.getTown());

        return registration;
    }

    private boolean isFull(String race) {
        try {
            return registrationDAO.countByRace(race) > Integer.valueOf(env.getProperty(race + ".race.max-registrations"));
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error checking max participants", e);
        }
        return false;

    }
}
