package cat.matxos.registration.services;

import cat.matxos.pojo.Registration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {

	private static Logger log = Logger.getLogger(EmailServiceImpl.class.getName());


	@Value("${email.user}")
	private String emailUser;

	@Value("${email.psw}")
	private String emailPsw;

	@Value("${email.host}")
	private String host;


	@Override
	public void sendConfirmation(String race, Registration registration) {
		String subject = "Ja ets un Matxo";
		String content = String.format("Hola %s<br><h2>Li comuniquem que la inscripció a %s ha estat finalitzada amb èxit</h2><br>"
						+ "<br>"
						+ "<b>Dades inscripció</b><br>"
						+ "Nom: %s <br>"
						+ "Primer cognom: %s <br>"
						+ "Segon cognom: %s <br>"
						+ "Sexe: %s <br>"
						+ "Nom dorsal: %s <br>"
						+ "Talla samarreta: %s <br>"
						+ "Email: %s <br>"
						+ "DNI/NIE:: %s <br>"
						+ "Telf: %s <br>"
						+ "Població: %s <br>"
						+ "Club: %s <br>"
						+ "FEEC: %s <br>"
						+ "Contacte emergència: %s <br>"
						+ "Solidari: %s <br>"
				,
				emptyIfNull(registration.getName()), race, emptyIfNull(registration.getName()), emptyIfNull(registration.getSurname1()), emptyIfNull(registration.getSurname2()), emptyIfNull(registration.getGender()),
				emptyIfNull(registration.getBibname()), emptyIfNull(registration.getSize()), emptyIfNull(registration.getEmail()), emptyIfNull(registration.getDni()), emptyIfNull(registration.getTelf()), emptyIfNull(registration.getTown()),
				emptyIfNull(registration.getClub()), registration.getFeec(), emptyIfNull(registration.getTelfemer()), registration.isSolidari() ? "Sí" : "No");

		sendEmail(registration.getEmail(), subject, content);
	}

	private String emptyIfNull(Object o) {
		return o == null ? "" : o.toString();
	}

	private void sendEmail(String to, String subject, String content) {
		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.user", emailUser);
		properties.setProperty("mail.password", emailPsw);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(emailUser));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject, "UTF-8");
			message.setContent(content, "text/html; charset=UTF-8");

			// Send message
			Transport transport = session.getTransport("smtps");
			transport.connect(host, 465, emailUser, emailPsw);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			log.log(Level.INFO, "Email send to :" + to);
		}
		catch (MessagingException e) {
			log.log(Level.SEVERE, "Error sending mail", e);
		}
	}


}
