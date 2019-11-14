package lk.ijse.dep.pos.db;


import lk.ijse.dep.pos.entity.Customer;
import lk.ijse.dep.pos.entity.Item;
import lk.ijse.dep.pos.entity.Order;
import lk.ijse.dep.pos.entity.OrderDetail;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        // (1)
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
//                .configure().build();
                .loadProperties("application.properties")
                .build();

        // (2)
        Metadata metadata = new MetadataSources(standardRegistry)

                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetail.class)
                .addAnnotatedClass(Item.class)


                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        // (3)
        return metadata.getSessionFactoryBuilder()
                .build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
