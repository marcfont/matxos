package cat.altimiras.matxos.registration.services;

import cat.altimiras.matxos.registration.dao.PaymentDAO;
import cat.altimiras.matxos.pojo.PaymentOrder;
import cat.altimiras.matxos.pojo.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import sis.redsys.api.ApiMacSha256;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static Logger log = Logger.getLogger(PaymentServiceImpl.class.getName());

    @Value("${payment.merchantCode}")
    private String merchandCode;

    @Value("${payment.merchantPsw}")
    private String merchantPsw;

    @Value("${payment.merchantTerminal}")
    private String merchantTerminal;

    @Value("${payment.validate.sign}")
    private boolean validate = true;

    @Value("${app.domain}")
    private String domain;

    @Autowired
    private Environment env;

    @Autowired
    private PaymentDAO paymentDAO;

    @Override
    public Map<String, String> getPaymentForm(PaymentOrder order, String race) {

        Map<String, String> formRedsys = new HashMap<>();
        try {
            ApiMacSha256 redsysApi = new ApiMacSha256();
            redsysApi.setParameter("DS_MERCHANT_AMOUNT", convertAmount(order.getAmount()));
            redsysApi.setParameter("DS_MERCHANT_ORDER", String.valueOf(order.getId()));
            redsysApi.setParameter("DS_MERCHANT_MERCHANTCODE", merchandCode);
            redsysApi.setParameter("DS_MERCHANT_CURRENCY", "978"); //EUR;
            redsysApi.setParameter("DS_MERCHANT_TRANSACTIONTYPE", "0");
            redsysApi.setParameter("DS_MERCHANT_TERMINAL", merchantTerminal);
            redsysApi.setParameter("DS_MERCHANT_MERCHANTURL", String.format("http://%s/payment/race/%s/order/%s/payment-callback", domain, race, order.getId()));
            redsysApi.setParameter("DS_MERCHANT_CONSUMERLANGUAGE", "1"); //es_ES
            redsysApi.setParameter("DS_MERCHANT_MERCHANTNAME", "CET");
            redsysApi.setParameter("DS_MERCHANT_URLOK", String.format("http://%s/payment/race/%s/order/%s/confirmation?status=OK", domain, race, order.getId()));
            redsysApi.setParameter("DS_MERCHANT_URLKO", String.format("http://%s/payment/race/%s/order/%s/confirmation?status=KO", domain, race, order.getId()));

            formRedsys.put(SIGN_VERSION_KEY, "HMAC_SHA256_V1");
            formRedsys.put(PARAMETERS_KEY, redsysApi.createMerchantParameters());
            formRedsys.put(SIGN_KEY, redsysApi.createMerchantSignature(merchantPsw));

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error generating redsys form", e);
        }

        return formRedsys;
    }

    @Override
    public PaymentOrder createOrder(Registration registration) {

        String typePrice = registration.getFeec() == null || registration.getFeec().isEmpty() ? "normal" : "federated";
        double price = Double.valueOf(env.getProperty(registration.getRace() + ".race.price." + typePrice));

        PaymentOrder order = new PaymentOrder();
        order.setAmount(price);
        order.setStatus("IN_PAYMENT");

        order = paymentDAO.save(order);

        return order;
    }

    @Override
    public boolean confirmOrderPayment(String orderId, String signature, String parameters) {
        boolean isPaid = false;
        try {

            ApiMacSha256 redsysApi = new ApiMacSha256();
            redsysApi.decodeMerchantParameters(parameters);

            //validate signature
            String generatedSign = redsysApi.createMerchantSignatureNotif(merchantPsw, parameters);
            if (!validate  || (validate && generatedSign.equals(signature))) {
                redsysApi.decodeMerchantParameters(parameters);

                PaymentOrder paymentOrder = paymentDAO.getOne(Long.valueOf(orderId));

                String response = redsysApi.getParameter("Ds_Response");
                if ("0000".equals(response)){
                    paymentOrder.setAuth_code(redsysApi.getParameter("Ds_AuthorisationCode"));
                    paymentOrder.setStatus("PAID");
                    isPaid = true;
                } else {
                    paymentOrder.setStatus("DENIED");
                }

                try {
                    paymentDAO.saveAndFlush(paymentOrder);
                } catch (Exception e){
                    log.log(Level.SEVERE, "Error updating Payment order after correct payment. order: " + paymentOrder, e);
                }

            } else {
                log.log(Level.WARNING, "Error bank sign is not valid");
                return false;
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error processing confirmation payment", e);
            isPaid = false;
        }

        return isPaid;
    }

    private String convertAmount(double amount) {

        if (amount == 0) return "0";

        String x100 = String.valueOf(amount * 100);
        int i = x100.indexOf(".");
        if (i < 0) {
            return x100;
        } else {
            return x100.substring(0, i);
        }
    }
}
