package ControllerLayer;
import BussinesLogic.TransitCoordinator;
import DataAccess.DAO_Generator;
import java.sql.SQLException;

public class ControllerGen {
    private static TruckController truckController;
    private static ProductController productController;
    private static SupplierController supplierController;
    private static OrderDocumentController orderDocumentController;
    private static TransitController transitController;
    private static TransitRecordController transitRecordController;
    private static TransitCoordinator transitCoordinator;


    public static TransitCoordinator getTransitCoordinator() throws SQLException, ClassNotFoundException {
        if (transitCoordinator == null)
        {
            transitCoordinator = new TransitCoordinator();
        }
        return transitCoordinator;
    }

    public static TruckController getTruckController() throws SQLException, ClassNotFoundException {
        if (truckController == null)
        {
            truckController = new TruckControllermpl(DAO_Generator.getTruckDAO());
        }
        return truckController;
    }

    public static ProductController getProductController() throws SQLException, ClassNotFoundException {
        if (productController == null)
        {
            productController = new ProductControllerImpl(DAO_Generator.getProductDAO());
        }
        return productController;
    }

    public static SupplierController getSupplierController() throws SQLException, ClassNotFoundException {
        if (supplierController == null)
        {
            supplierController = new SupplierControllerImpl(DAO_Generator.getSupplierDAO());
        }
        return supplierController;
    }

    public static OrderDocumentController getOrderDocumentController() throws SQLException, ClassNotFoundException {
        if (orderDocumentController == null)
        {
            orderDocumentController = new OrderDocumentControllerImpl(DAO_Generator.getOrderDocumentDAO(),
                    getSupplierController(),getTransitCoordinator(),getProductController());
        }
        return orderDocumentController;
    }

    public static TransitRecordController getTransitRecordController() throws SQLException, ClassNotFoundException {
        if (transitRecordController == null)
        {
            transitRecordController = new TransitRecordControllerImpl(DAO_Generator.getTransitRecordDAO());
        }
        return transitRecordController;
    }

    public static TransitController getTransitController() throws SQLException, ClassNotFoundException {
        if (transitController == null)
        {
            transitController = new TransitControllerImpl(DAO_Generator.getTransitDAO(), getTruckController(),
                    getTransitCoordinator(), getOrderDocumentController(), getTransitRecordController());
        }
        return transitController;
    }
}
