package cat.altimiras.matxos.registration.resources;

import cat.altimiras.matxos.pojo.PaymentOrder;
import cat.altimiras.matxos.pojo.Registration;
import cat.altimiras.matxos.registration.dao.RegistrationDAO;
import cat.altimiras.matxos.registration.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class PaymentResource extends Resource {

    private static Logger log = Logger.getLogger(PaymentResource.class.getName());

    @Value("${payment.endpoint}")
    private String endpoint;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RegistrationDAO registrationDAO;

    @PostMapping("/payment/race/{race}")
    public String payment(@PathVariable("race") String race, Model model, @RequestParam("id") String id) {
        try {
            log.log(Level.INFO, "Create payment form");
            Registration registration = registrationDAO.getOne(Long.valueOf(id));
            if (registration.getPaymentDate() == null && registration.getPaymentId() == null) {

                PaymentOrder paymentOrder = paymentService.createOrder(registration);
                Map<String, String> form = paymentService.getPaymentForm(paymentOrder, race);

                //update registration with order id
                registration.setPaymentId(String.valueOf(paymentOrder.getId()));
                registration.setPaymentDate(new Date());
                registrationDAO.saveAndFlush(registration);

                model.addAttribute("url", endpoint);
                model.addAttribute("fields", form);

                return "payment";

            } else {
                log.log(Level.WARNING, "Trying to repay an order");
                return "error";
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error starting payment", e);
            return "error";
        }
    }


    @PostMapping("/payment/race/{race}/order/{order-id}/payment-callback")
    public String callback(@PathVariable("race") String race, @PathVariable("order-id") String orderId, Model model, HttpServletRequest request) {
        log.log(Level.INFO, "Payment callback received: " + race + " orderid: " + orderId);
        try {
            log.log(Level.INFO, "Callback info. sign: " + request.getParameter(PaymentService.SIGN_KEY) + " params: " + request.getParameter(PaymentService.PARAMETERS_KEY));
            boolean isPaid = paymentService.confirmOrderPayment(orderId, request.getParameter(PaymentService.SIGN_KEY), request.getParameter(PaymentService.PARAMETERS_KEY));
            return "ok";

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error on confirmation payment", e);
            return "ok";
        }

    }
}
