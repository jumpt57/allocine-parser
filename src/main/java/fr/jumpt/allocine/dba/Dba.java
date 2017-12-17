package fr.jumpt.allocine.dba;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 *
 */
public class Dba {

    private final static Dba DBA = new Dba();

    private static final String PACKAGE = "fr.jumpt.allocine.models";
    private final static String DB_NAME = "allocine-data";

    private Datastore datastore;

    private Dba() {
        final Morphia morphia = new Morphia();

        morphia.mapPackage(PACKAGE);

        datastore = morphia.createDatastore(new MongoClient(), DB_NAME);

        datastore.ensureIndexes();
    }

    public static Dba get() {
        return DBA;
    }

    public Datastore getDatastore() {
        return DBA.datastore;
    }
}
