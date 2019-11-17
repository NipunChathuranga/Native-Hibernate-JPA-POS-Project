package lk.ijse.dep.pos.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`order`")
public class Order implements SuperEntity {


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails = new ArrayList<>();
    @Id
    private int id;
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "customerId", nullable = false)
    private Customer customer;

    public Order() {
    }

    public Order(int id, Date date) {
        this.id = id;
        this.date = date;
    }


    public Order(int id, Date date, Customer customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        //PK Class ekak awoth meka daaanne naa wadak nathi nisa. dammata meken  set krnna baa updatable insertable false
        //karala tiyana nisa.me method eka ona wenne cascade operation walata witarai.
        // remove eka - removeOrderDetail denneth naa cascade walata witarai ganna wenne eka. eka haraha delete krnna baaa -  kalin reason ekamai
        // ......
//        orderDetail.setOrder(this);
        orderDetails.add(orderDetail);
    }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
