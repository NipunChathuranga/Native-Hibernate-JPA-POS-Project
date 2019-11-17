package lk.ijse.dep.pos.dao.custom.impl;

import lk.ijse.dep.pos.dao.CrudDAOImpl;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.entity.Order;
import org.hibernate.Session;

import java.util.List;

public class OrderDAOImpl extends CrudDAOImpl<Order,Integer> implements OrderDAO {



    @Override
    public int getLastOrderId() throws Exception {

        Integer i = (Integer) session.createNativeQuery("SELECT id FROM `Order` ORDER BY id DESC LIMIT 1").uniqueResult();
        return (i == null) ? 0 : i;

    }

    @Override
    public boolean existsByCustomerId(String cId) throws Exception {

        System.out.println(session.createNativeQuery("SELECT * FROM `Order` WHERE id=?1")
                .setParameter(1, cId).uniqueResult());
        System.out.println("============================================================================");
        return session.createNativeQuery("SELECT * FROM `Order` WHERE customer_id=?1")
                .setParameter(1, cId).uniqueResult() != null;


    }




}
