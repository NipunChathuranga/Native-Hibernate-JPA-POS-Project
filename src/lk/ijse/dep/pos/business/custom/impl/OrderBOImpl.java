package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.OrderBO;

import lk.ijse.dep.pos.dao.custom.*;

import lk.ijse.dep.pos.db.HibernateUtil;
import lk.ijse.dep.pos.dto.OrderDTO;
import lk.ijse.dep.pos.dto.OrderDTO2;
import lk.ijse.dep.pos.dto.OrderDetailDTO;
import lk.ijse.dep.pos.entity.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderBOImpl implements OrderBO {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private QueryDAO queryDAO;
    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public int getLastOrderId() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();
            int lastOrderID = orderDAO.getLastOrderId();
            session.getTransaction().commit();
            return lastOrderID;
        }
//
    }

    @Override
    public void placeOrder(OrderDTO order) throws Exception {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            orderDAO.setSession(session);
            customerDAO.setSession(session);
            orderDetailDAO.setSession(session);
            session.beginTransaction();

            int oId = order.getId();
            orderDAO.save(new Order(oId, new java.sql.Date(new Date().getTime()),
                    session.load(Customer.class, order.getCustomerId())));


            for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
                System.out.println("=====================================================");
                System.out.println(orderDetail);
                System.out.println("====================================================");
                orderDetailDAO.save(new OrderDetail(oId, orderDetail.getCode(),
                        orderDetail.getQty(), orderDetail.getUnitPrice()));
                Item item = itemDAO.find(orderDetail.getCode());
                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
                itemDAO.update(item);


            }
            session.getTransaction().commit();

//        Connection connection = DBConnection.getInstance().getConnection();
//        try {
//
//            // Let's start a transaction
//            connection.setAutoCommit(false);
//
//            int oId = order.getId();
//            boolean result = orderDAO.save(new Order(oId, new java.sql.Date(new Date().getTime()),
//                    order.getCustomerId()));
//
//            if (!result) {
//                connection.rollback();
//                throw new RuntimeException("Something, something went wrong");
//            }
//
//            for (OrderDetailDTO orderDetail : order.getOrderDetails()) {
//                result = orderDetailDAO.save(new OrderDetail(oId, orderDetail.getCode(),
//                        orderDetail.getQty(), orderDetail.getUnitPrice()));
//
//                if (!result) {
//                    connection.rollback();
//                    throw new RuntimeException("Something, something went wrong");
//                }
//
//                Item item = itemDAO.find(orderDetail.getCode());
//                item.setQtyOnHand(item.getQtyOnHand() - orderDetail.getQty());
//                result = itemDAO.update(item);
//
//                if (!result) {
//                    connection.rollback();
//                    throw new RuntimeException("Something, something went wrong");
//                }
//            }
//
//            connection.commit();
//            return true;
//
//        } catch (Throwable e) {
//
//            try {
//                connection.rollback();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            e.printStackTrace();
//            return false;
//        } finally {
//            try {
//                connection.setAutoCommit(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        }

//        @Override
//        public List<OrderDTO2> getOrderInfo (String query) throws Exception {
//            List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo(query + "%");
//            List<OrderDTO2> dtos = new ArrayList<>();
//            for (CustomEntity info : ordersInfo) {
//                dtos.add(new OrderDTO2(info.getOrderId(),
//                        info.getOrderDate(), info.getCustomerId(), info.getCustomerName(), info.getOrderTotal()));
//            }
//            return dtos;
//
//        }
    }

    @Override
    public List<OrderDTO2> getOrderInfo(String query) throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            queryDAO.setSession(session);

            session.beginTransaction();

            List<CustomEntity> ordersInfo = queryDAO.getOrdersInfo(query);

            List<OrderDTO2> al = new ArrayList<>();

            for (CustomEntity customEntity : ordersInfo) {
                al.add(new OrderDTO2(customEntity.getOrderId(), customEntity.getOrderDate(), customEntity.getCustomerId(), customEntity.getCustomerName(), customEntity.getOrderTotal()));
            }

            session.getTransaction().commit();

            return al;
        }
    }

}