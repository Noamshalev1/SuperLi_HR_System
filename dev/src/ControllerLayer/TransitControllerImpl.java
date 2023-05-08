package ControllerLayer;

import DataAccessLayer.TransitDAO;
import DomainLayer.*;
import ExceptionsPackage.QualificationsException;
import ExceptionsPackage.UiException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class TransitControllerImpl implements TransitController {
    private final TransitDAO transitDAO;
    private final TruckController truckController;
    private final DriverController driverController;
    private final OrderDocumentController orderDocController;
    private final TransitRecordController transitRecordController;

    public TransitControllerImpl(TransitDAO transitDAO, TruckController truckController,
                                 DriverController driverController, OrderDocumentController orderDocController,
                                 TransitRecordController transitRecordController) {
        this.transitDAO = transitDAO;
        this.truckController = truckController;
        this.driverController = driverController;
        this.orderDocController = orderDocController;
        this.transitRecordController = transitRecordController;

    }

    @Override
    public Transit createTransit(String dateString, String truckPlateNumber, int driverId) throws UiException, QualificationsException {
        Date transitDate;
        try {
            transitDate = createDateObj(dateString);
        } catch (ParseException e) {
            throw new UiException("Invalid date format " + dateString + "\t" + "The correct format is: dd/mm/yyyy ");
        }
        Truck truckForTransit = truckController.findTruckByPlate(truckPlateNumber);
        if (truckForTransit == null) {
            throw new UiException("Truck's plate number not found: " + truckPlateNumber);
        }


        // TODO Driver NoamResponsibility(String driverId, Set<Licenses> forDrivingTruck, Date transitDate);
        Driver driverForTransit = driverController.findDriverByID(driverId);
        if (driverForTransit == null) {
            throw new UiException("Driver id not found: " + driverId);
        }
        boolean driverCanDriveTruckFlag = isDriverAllowToDriveTruck(truckForTransit, driverForTransit);
        if (!driverCanDriveTruckFlag){
            throw new QualificationsException("Driver lack certain qualifications for driving the chosen truck");
        }


        Transit newTransit = new Transit(transitDate, truckForTransit, driverForTransit);
        return newTransit;
    }
    @Override
    public Transit findTransitByID(int transitId) {
        return this.transitDAO.findTransitByID(transitId);
    }
    @Override
    public Set<Transit> getTransitsSet() {
        return this.transitDAO.getTransitsSet();
    }
    @Override
    public TransitDAO getTransitDAO() {
        return this.transitDAO;
    }
    @Override
    public boolean showTransitByID(int transitId) {
        Transit transitToShow = findTransitByID(transitId);
        if(transitToShow==null) return false;
        transitToShow.printTransit();
        return true;
    }
    @Override
    public int replaceTransitTruck(int transitId, String newTruckPlate) {
        Transit transitToUpdate = findTransitByID(transitId);
        if (transitToUpdate == null) return -2; //transit fail
        Truck otherTruck = truckController.findTruckByPlate(newTruckPlate);
        if (otherTruck == null) return -1; //truck fail
        Driver currentDriver = transitToUpdate.getDriver();
        boolean qualifiedDriverFlag = isDriverAllowToDriveTruck(otherTruck,currentDriver);
        if (!qualifiedDriverFlag) return 0; //driver fail
        transitToUpdate.setTruck(otherTruck);
        return 1;// successes
    }
    public int replaceTransitDriver(int transitId, int newDriverId, String truckPlate){
        Driver otherDriver = driverController.findDriverByID(newDriverId);
        // TODO Driver NoamResponsibility(String newDriverId, Set<Licenses> forDrivingTruck, Date transitDate);

        if (otherDriver == null) return -1; //fail to find driver
        Truck newTruck = truckController.findTruckByPlate(truckPlate);
        Transit transitToUpdate = findTransitByID(transitId);
        boolean qualifiedDriverFlag = isDriverAllowToDriveTruck(newTruck,otherDriver);
        if (!qualifiedDriverFlag) return 0; // driver is not qualified
        //driver is qualified
        transitToUpdate.setDriver(otherDriver);
        return 1;
    }
    @Override
    public OrderDocumentController getOrderDocController() {
        return orderDocController;
    }
    @Override
    public TransitRecordController getTransitRecordController() {
        return transitRecordController;
    }
    @Override
    public boolean isValidWeight(Transit currentTransit, OrderDocument orderDocument) {
        Truck currentTruck = currentTransit.getTruck();
        double maxCarry = currentTruck.getMaxCarryWeight();
        double currentTransitWeight = currentTransit.calcOrdersWeight();
        double weightToAdd = orderDocument.getTotalWeight();
        double newCapacity = maxCarry-(currentTransitWeight+weightToAdd);
        if (newCapacity < 0){
            System.out.println("Exceeding truck's max weight");
            System.out.printf("available capacity is: %.2f %n", (maxCarry-currentTransitWeight));
            System.out.println("Order was not added to transit");
            return false;
        }
        return true;
    }
    public Date createDateObj(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date transitDate = dateFormat.parse(dateString);
        return transitDate;
    }
    @Override
    public boolean isDriverAllowToDriveTruck(Truck truck, Driver driver){
        Set <License> truckQualiSet = truck.getTruckLicenses();
        Set <License> driverLicenseSet = driver.getLicenses();
        return (driverLicenseSet.containsAll(truckQualiSet));
    }
    public void moveTransitToFinished(Transit completedTransit){
        this.transitDAO.moveToCompleted(completedTransit);
    }
    public boolean transferLoad(Truck smallTruck, Truck biggerTruck){
        boolean validTransfer = truckController.transferLoadV2(smallTruck, biggerTruck);
        if (validTransfer){
            System.out.println("Transfer load form truck: " + smallTruck.getPlateNumber() + " to truck: " + biggerTruck.getPlateNumber());
            return true;
        }
        System.out.println("Chosen truck is too small, please try again.. ");
        return false;
    }
}