package lk.ijse.dep.pos.business.custom.impl;

import lk.ijse.dep.pos.business.custom.CustomerBO;
import lk.ijse.dep.pos.business.exception.AlreadyExistsInOrderException;
import lk.ijse.dep.pos.dao.DAOFactory;
import lk.ijse.dep.pos.dao.DAOTypes;
import lk.ijse.dep.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.pos.dao.custom.OrderDAO;
import lk.ijse.dep.pos.db.JPAUtil;
import lk.ijse.dep.pos.dto.CustomerDTO;
import lk.ijse.dep.pos.entity.Customer;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);
    private OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDER);

    @Override
    public void saveCustomer(CustomerDTO customer) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();
        customerDAO.save(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void updateCustomer(CustomerDTO customer) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();
        customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
        em.getTransaction().commit();
        em.close();


        // return customerDAO.update(new Customer(customer.getId(), customer.getName(), customer.getAddress()));
    }

    @Override
    public void deleteCustomer(String customerId) throws Exception {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();
        if (orderDAO.existsByCustomerId(customerId)) {
            throw new AlreadyExistsInOrderException("Customer already exists in an order, hence unable to delete");
        }

        customerDAO.delete(customerId);
        em.getTransaction().commit();
        em.close();


//        if (orderDAO.existsByCustomerId(customerId)) {
//            throw new AlreadyExistsInOrderException("Customer already exists in an order, hence unable to delete");
//        }
//        return customerDAO.delete(customerId);
    }

    @Override
    public List<CustomerDTO> findAllCustomers() throws Exception {

        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        customerDAO.setEntityManager(em);
        em.getTransaction().begin();
        List<Customer> alCustomers = customerDAO.findAll();
        List<CustomerDTO> dtos = new ArrayList<>();
        for (Customer customer : alCustomers) {
            dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
        }

        em.getTransaction().commit();
        return dtos;
        em.close();









//        List<Customer> alCustomers = customerDAO.findAll();
//        List<CustomerDTO> dtos = new ArrayList<>();
//        for (Customer customer : alCustomers) {
//            dtos.add(new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress()));
//        }
//        return dtos;
    }

    @Override
    public String getLastCustomerId() throws Exception {
        return customerDAO.getLastCustomerId();
    }

    @Override
    public CustomerDTO findCustomer(String customerId) throws Exception {
        Customer customer = customerDAO.find(customerId);
        return new CustomerDTO(customer.getCustomerId(),
                customer.getName(), customer.getAddress());
    }

    @Override
    public List<String> getAllCustomerIDs() throws Exception {
        List<Customer> customers = customerDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Customer customer : customers) {
            ids.add(customer.getCustomerId());
        }
        return ids;
    }
}
