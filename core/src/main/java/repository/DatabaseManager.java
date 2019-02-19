package repository;

import org.apache.commons.io.FileUtils;
import settings.Settings;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.File;
import java.io.IOException;

public class DatabaseManager {

    public DatabaseManager() {

    }

    public void deleteDatabase() {
        System.out.println("DatabaseManager: Deleting database...\n");
        try {
            String DATABASE_PATH = "../db";
            FileUtils.deleteDirectory(new File(DATABASE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dropALLTables() {
        System.out.println("JPARepository: drop table");
        try {
            JPARepository<Integer, Exception> jpr = new JPARepository<Integer, Exception>() {
                @Override
                protected String persistenceUnitName() {
                    return Settings.DB_PU;
                }
            };
            EntityManager em = jpr.entityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            Query cenas = em.createNativeQuery("DROP TABLE `eapli`.`CASO`, `eapli`.`CASO_OBJETOSEGURO`, `eapli`.`Celula`, `eapli`.`CELULABASE`, `eapli`.`CELULACARACTERIZADA`, `eapli`.`CELULADETALHADA`, `eapli`.`COBERTURA`, `eapli`.`ENVOLVENTE`, `eapli`.`EVENT`, `eapli`.`FATORRISCO`, `eapli`.`MATRIZ`, `eapli`.`MATRIZ_CELULABASE`, `eapli`.`OBJETOSEGURO`, `eapli`.`OBJETOSEGURO_COBERTURA`, `eapli`.`SEQUENCE`, `eapli`.`USER`, `eapli`.`VALIDACAO`");
            cenas.executeUpdate();
            tx.commit();
            em.close();
            return;
        } catch (Exception e) {
            System.out.println(e.getCause() + "\n" + e.getMessage());
            return;
        }
    }
}
