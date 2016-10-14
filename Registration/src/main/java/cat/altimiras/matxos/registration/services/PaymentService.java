package cat.altimiras.matxos.registration.services;


import cat.altimiras.matxos.pojo.PaymentOrder;
import cat.altimiras.matxos.pojo.Registration;

import java.util.Map;

public interface PaymentService {

    String SIGN_KEY = "Ds_Signature";
    String PARAMETERS_KEY = "Ds_MerchantParameters";
    String SIGN_VERSION_KEY = "Ds_SignatureVersion";

    PaymentOrder createOrder(Registration registration);

    Map<String, String> getPaymentForm(PaymentOrder order, String race);

    boolean confirmOrderPayment(String orderId, String singature, String parameters);
}
