package ControllerLayer;

import DataAccessLayer.TruckDAO;
import DomainLayer.Truck;
import DomainLayer.TruckModel;

import java.util.Set;

public class TruckControllermpl implements TruckController {
    private final TruckDAO truckDAO;
    public TruckControllermpl(TruckDAO truckDAO){
        this.truckDAO = truckDAO;
    }

    // TODO change to saveTruck - setup creation of a truck only in the DB !!
    @Override
    public Truck createTruck(String plateNumber, int iModel, int[] iQArr, double truckWeight, double maxWeight) {
        TruckModel model = TruckModel.values()[iModel];
        Truck truck = new Truck(plateNumber, model, truckWeight, maxWeight);
        for (int i : iQArr) {
            truck.addLToLSet(BussinesLogic.License.values()[i]);
        }
        truckDAO.saveTruck(truck);
        return truck;
    }
    @Override
    public boolean removeTruckByPlateNumber(String tPlateNumber) {
        Truck truckToRemove = this.truckDAO.findTruckByPlateNumber(tPlateNumber);
        if (truckToRemove == null)return false;
        this.truckDAO.removeTruck(truckToRemove);
        return true;
        }
    @Override
    public Truck findTruckByPlate(String tPlateNumber) {
        return this.truckDAO.findTruckByPlateNumber(tPlateNumber);
    }

    @Override
    public void showAllTrucks() {
        Set<Truck> trucks = truckDAO.getTrucksSet();
        for (Truck t : trucks){
            t.printTruck();
        }
    }

    @Override
    public boolean showTruckByPlate(String tPlateNumber) {
        Truck truckToShow = findTruckByPlate(tPlateNumber);
        if (truckToShow == null)return false;
        truckToShow.printTruck();
        return true;
        }
    @Override
    public boolean transferLoadV2(Truck smallerTruck, Truck biggerTruck){
        if (biggerTruck.getMaxCarryWeight() >= smallerTruck.getCurrentWeight())
        {
            biggerTruck.loadTruck(smallerTruck.getCurrentWeight());
            smallerTruck.setCurrentLoadWeight(0);
            return true;
        }
        return false;
    }

    //TODO delete this method.
    @Override
    public void transferLoad(Truck smallerTruck, Truck biggerTruck){
        biggerTruck.loadTruck(smallerTruck.getCurrentWeight());
        smallerTruck.setCurrentLoadWeight(0);
    }
    //TODO better practice is not to use this method.
    @Override
    public TruckDAO getTruckDAO() {
        return this.truckDAO;
    }
}