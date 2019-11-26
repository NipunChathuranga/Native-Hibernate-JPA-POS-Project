package lk.ijse.dep.pos.dao.custom;

import lk.ijse.dep.pos.dao.CrudDAO;
import lk.ijse.dep.pos.entity.Customer;
import org.springframework.stereotype.Component;


public interface CustomerDAO extends CrudDAO<Customer, String> {

    String getLastCustomerId() throws Exception;

}
