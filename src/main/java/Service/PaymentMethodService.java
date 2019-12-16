package Service;

import Dao.PaymentMethodDao;
import Entity.PaymentMethod;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class PaymentMethodService {

    private PaymentMethodDao paymentMethodDao = new PaymentMethodDao();

    public PaymentMethodService() {}

    public PaymentMethod getPaymentByID(final int id){
        return paymentMethodDao.getPaymentMethodByID(id);
    }

    public List<PaymentMethod> getAllPaymentMethods(){
        return paymentMethodDao.getAllPaymentMethods();
    }

    public void deletePaymentMethod(PaymentMethod paymentMethod) throws UnsupportedEncodingException {
        paymentMethodDao.deletePaymentMethod(paymentMethod);
    }

    public void updatePaymentMethod(PaymentMethod paymentMethod) throws UnsupportedEncodingException {
        paymentMethodDao.updatePaymentMethod(paymentMethod);
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) throws UnsupportedEncodingException {
        paymentMethodDao.addPaymentMethod(paymentMethod);
    }

}
