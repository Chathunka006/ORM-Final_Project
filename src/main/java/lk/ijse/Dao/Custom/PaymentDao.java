package lk.ijse.Dao.Custom;

import lk.ijse.Dao.CrudDao;
import lk.ijse.Entity.Payment;

import java.io.IOException;

public interface PaymentDao extends CrudDao<Payment> {
    String getCurrentId() throws IOException;

}
