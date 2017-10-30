package cat.matxos.registration.resources;


import cat.matxos.dao.RegistrationDAO;
import cat.matxos.pojo.Registration;
import cat.matxos.registration.forms.RegistrationForm;
import cat.matxos.registration.services.DNIValidatorService;
import cat.matxos.registration.services.PhoneService;
import cat.matxos.registration.services.TShirtSizeService;
import cat.matxos.services.FEECService;
import cat.matxos.services.RouteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	@Value("${solidari.enabled}")
	private boolean solidariEnabled;

	@GetMapping("registration/race/{race}/registrations")
	public String registrations(@PathVariable("race") String race, Model model) {

		List<Registration> registrationList = registrationDAO.findByRaceAndIsCompleted(race, true);

		model.addAttribute("registrations", registrationList);
		model.addAttribute("available", getAvailable(race));
		fillModel(model, race);

		return "registrations";
	}

	@GetMapping(value = "/registration/race/{race}/available", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@CrossOrigin(origins = {"http://localhost:8080", "http://matxos17.weebly.com", "http://www.matxos.cat"})
	public Available available(@PathVariable("race") String race, Model model) {

		Available available = new Available(0);
		try {
			available.setC(getAvailable(race));
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "Error getting available", e);
		}
		return available;
	}

	private long getAvailable(String race) {
		if (isRegistrationEnabled(race)) {
			long max = Long.valueOf(env.getProperty(race + ".race.max-registrations"));
			long c = registrationDAO.countByRaceAndIsCompletedIsTrue(race);
			long a = max - c;
			return a < 0 ? 0 : a;
		}
		else {
			return 0;
		}
	}

	class Available {
		long c;

		public Available(long c) {
			this.c = c;
		}

		public long getC() {
			return c;
		}

		public void setC(long c) {
			this.c = c;
		}
	}

	@GetMapping("/registration/race/{race}/registration")
	public String registration(@PathVariable("race") String race, Model model) {
		fillModel(model, race);

		if (!isRegistrationEnabled(race) || isFull(race)) {
			return "race_full";
		}

		if (vipEnabled) {
			return "vip_check";
		}
		else {
			return "registration";
		}
	}

	@PostMapping("/registration/race/{race}/registration")
	public String save(@PathVariable("race") String race, Model model, @Valid RegistrationForm registration, BindingResult bindingResult) {
		log.log(Level.INFO, "new registration");
		try {
			if (!isRegistrationEnabled(race) || isFull(race)) {
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

			//min age
			if (!minAge(race, registration.getBirthday())) {
				prepareError(registration, model, bindingResult, "birthday");
				return "registration";
			}

			//store
			formatFields(registration);

			Registration newReg = (Registration) registrationDAO.save(convert(registration));

			model.addAttribute("id", newReg.getId());
			model.addAttribute("title", getProperty(race + ".race.name"));
			model.addAttribute("race", race);
			model.addAttribute("registration", registration);
			model.addAttribute("solidari", solidariEnabled);
			return "registration_confirmation";

		}
		catch (Exception e) {
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
			}
			else {
				model.addAttribute("title", getProperty(race + ".race.name"));
				model.addAttribute("race", race);
				return "not_vip_error";
			}
		}
		catch (Exception e) {
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
			}
			else {
				log.log(Level.WARNING, "Bank payment fail. race: " + race + ", orderid: " + orderId);
				return "error_confirmation";
			}
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "Error checking confirmation payment", e);
			return "error_confirmation";
		}
	}

	private void prepareError(RegistrationForm registration, Model model, BindingResult bindingResult, String error) {
		fillModel(model, registration.getRace());

		if (error != null) {
			bindingResult.rejectValue(error, error);
			log.log(Level.INFO, error + " not valid on new registration");
		}

		model.addAttribute("registration", registration);
		model.addAttribute("errors", bindingResult);
	}

	private void fillModel(Model model, String race) {
		model.addAttribute("title", getProperty(race + ".race.name"));
		model.addAttribute("routes", routeService.getRoutes(race));
		model.addAttribute("sizesM", tShirtSizeService.getSizeAvailable(race, true));
		model.addAttribute("sizesF", tShirtSizeService.getSizeAvailable(race, false));
		model.addAttribute("solidari", solidariEnabled);
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
		registration.setSolidari(form.getSolidari());
		return registration;
	}

	private boolean isFull(String race) {
		try {
			return registrationDAO.countByRaceAndIsCompletedIsTrue(race) >= Integer.valueOf(env.getProperty(race + ".race.max-registrations"));
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "Error checking max participants", e);
		}
		return false;

	}

	private boolean minAge(String race, String birthdayStr) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");

			String minAgeStr = env.getProperty(race + ".min-age");
			if (minAgeStr == null || minAgeStr.isEmpty()) {
				return true;
			}
			Date minAge = formatter.parse(minAgeStr);
			Date birthday = formatter.parse(birthdayStr);

			return minAge.after(birthday);
		}
		catch (Exception e) {
			log.log(Level.SEVERE, "Error checking min age", e);
		}
		return false;
	}

	private boolean isRegistrationEnabled(String race) {
		String enabled = env.getProperty(race + ".race.registration.enabled");
		return Boolean.valueOf(enabled);
	}

	private void formatFields(RegistrationForm registration) {
		registration.setName(StringUtils.capitalize(registration.getName()));
		registration.setSurname1(StringUtils.capitalize(registration.getSurname1()));
		registration.setSurname2(StringUtils.capitalize(registration.getSurname2()));
		registration.setTown(StringUtils.capitalize(registration.getTown()));
		registration.setBibname(StringUtils.capitalize(registration.getBibname()));

		registration.setDni(registration.getDni().toUpperCase());
	}

}
