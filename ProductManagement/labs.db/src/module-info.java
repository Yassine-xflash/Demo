/**
 * @author oracle
 **/
module labs.db {
    requires java.logging;
    requires labs.pm;
    requires java.sql;
    provides labs.pm.service.ProductManager with labs.db.service.ProductDBManager;
}