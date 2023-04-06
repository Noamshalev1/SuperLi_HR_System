import java.util.List;
import java.util.Set;

public class TruckServiceImpl implements TruckService {
    private final TruckRepository truckRepo;

    TruckServiceImpl(TruckRepository truckRepo){
        this.truckRepo = truckRepo;
    }

    @Override
    public Truck createTruck(String plateNumber, int iModel, int[] iQArr, double truckWeight, double maxWeight) {
        TruckModel model = TruckModel.values()[iModel];
        Truck truck = new Truck(plateNumber, model, truckWeight, maxWeight);
        for (int i : iQArr) {
            truck.addToQSet(Qualification.values()[i]);
        }
        return truck;
    }

    @Override
    public boolean removeTruckByPlateNumber(String tPlateNumber) {
        Truck truckToRemove = this.truckRepo.findTruckByPlateNumber(tPlateNumber);
        if (truckToRemove != null){
            this.truckRepo.removeTruck(truckToRemove);
            return true;
        }
        return false;
    }

    @Override
    public Truck findTruckByPlate(String tPlateNumber) {
        return this.truckRepo.findTruckByPlateNumber(tPlateNumber);
    }


    @Override
    public Set<Truck> getAllTrucks() {
        return this.truckRepo.findAllTrucks();
    }

    @Override
    public TruckRepository getTruckRepo() {
        return this.truckRepo;
    }
}