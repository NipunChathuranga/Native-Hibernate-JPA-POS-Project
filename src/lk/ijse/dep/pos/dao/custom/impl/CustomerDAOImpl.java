package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;

import lk.ijse.dep.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.pos.entity.Customer;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
@Component
public class CustomerDAOImpl extends CrudDAOImpl<Customer, String> implements CustomerDAO {

    @Override
    public String getLastCustomerId() throws Exception {

        Query nativeQuery = entityManager.createNativeQuery("SELECT customerId FROM Customer ORDER BY customerId DESC LIMIT 1");
        return nativeQuery.getResultList().size() > 0 ? (String) nativeQuery.getSingleResult() : null;


    }

}
