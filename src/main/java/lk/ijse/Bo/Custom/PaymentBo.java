package lk.ijse.Bo.Custom;

import lk.ijse.Bo.SuperBo;
import lk.ijse.Dto.PaymentDto;
import lk.ijse.Entity.Payment;

import java.io.IOException;
import java.util.List;

public interface PaymentBo extends SuperBo {
    boolean savePayment(PaymentDto paymentDto) throws IOException;
    List<Payment> getPaymentList() throws IOException;
}
