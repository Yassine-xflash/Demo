/**
 * @author oracle
 **/
module labs.file {
    requires java.logging;
    requires labs.pm;
    provides labs.pm.service.ProductManager
            with labs.file.service.ProductFileManager;
}
