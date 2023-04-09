package ControllerServiceLayer;

import DataAccessLayer.SupplierRepository;
import DomainLayer.Supplier;

import java.util.Set;

public interface SupplierService {
    public Set<Supplier> getAllSuppliers();
    public Supplier findSupplierById(int supplierId);
    public SupplierRepository getSupplierRepo();
}