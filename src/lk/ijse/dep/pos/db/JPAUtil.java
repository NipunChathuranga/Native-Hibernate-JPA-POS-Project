package lk.ijse.dep.pos.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JPAUtil {

    private static EntityManagerFactory emf = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory(){
        File propFile = new File("resources/application.properties");
        try(FileInputStream fis = new FileInputStream(propFile)) {

            Properties properties = new Properties();
            properties.load(fis);
            return Persistence.createEntityManagerFactory("dep4",properties);




        } catch (Exception e) {
            Logger.getLogger("lk.ijse.dep.pos.db.JPAUtil").log(Level.SEVERE, null,e);
            System.exit(1);
            return null;
        }
    }


    public static  EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }












}
