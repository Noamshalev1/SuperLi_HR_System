package UiLayer;
import ControllerServiceLayer.*;
import DataAccessLayer.*;
import DomainLayer.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PresentationSystem {
    public static void main(String[] args) {
        PresentationSystem ps = new PresentationSystem();
        Scanner scanner = new Scanner(System.in);

        DriverRepository primeDriverRepo = new DriverRepositoryImpl();
        DriverService primeDriverService = new DriverServiceImpl(primeDriverRepo);

        TruckRepository primeTruckRepo = new TruckRepositoryImpl();
        TruckService primeTruckService = new TruckServiceImpl(primeTruckRepo);
        TruckController truckController = new TruckController(primeTruckService);

        StoreRepository primeStoreRepo = new StoreRepositoryImpl();
        StoreService primeStoreService = new StoreServiceImpl(primeStoreRepo);

        SupplierRepository primeSupplierRepo = new SupplierRepositoryImpl();
        SupplierService primeSupplierService = new SupplierServiceImpl(primeSupplierRepo);

        ProductRepository primeProductRepo = new ProductRepositoryImpl();
        ProductService primeProductService = new ProductServiceImpl(primeProductRepo);

        OrderDocumentRepository primeOrderDocRepo = new OrderDocumentRepositoryImpl();
        OrderDocumentService primeOrderDocService = new OrderDocumentServiceImpl(primeOrderDocRepo,
                primeSupplierService,primeStoreService,primeProductService);
        OrderDocumentController orderDocumentController = new OrderDocumentController(primeOrderDocService,
                primeProductService);

        TransitRecordRepository primeTransitRecordRepo = new TransitRecordsRepositoryImpl();
        TransitRecordService primeTransitRecordService = new TransitRecordServiceImpl(primeTransitRecordRepo);

        TransitRepository primeTransitRepo = new TransitRepositoryImpl();
        TransitService primeTransitService = new TransitServiceImpl(primeTransitRepo, primeTruckService,
                primeDriverService, primeOrderDocService, primeTransitRecordService);
        TransitController transitController = new TransitController(primeTransitService);


        Set<Qualification> s1 = new HashSet<>();
        Set<Qualification> s2 = new HashSet<>();
        Set<Qualification> sT3 = new HashSet<>();
        Set<Qualification> sT4 = new HashSet<>();
        s1.add(Qualification.C);
        s1.add(Qualification.C1);
        s1.add(Qualification.COOLER);

        s2.add(Qualification.C1);

        sT3.add(Qualification.C);
        sT3.add(Qualification.COOLER);

        sT4.add(Qualification.C1);

        Truck t1 = new Truck("123", TruckModel.LARGETRUCK, 5000, 10000, sT3);
        Truck t2 = new Truck("321", TruckModel.SMALLTRUCK, 100, 2000, sT4);

        Driver d1 = new Driver(1, "Moshe Mor",s1);
        Driver d2 = new Driver(2, "Dani Lev",s2);

        Product p1 = new Product(1,"Banana");
        Product p2 = new Product(2,"Apple");
        Product p3 = new Product(3,"Orange");

        Supplier sup1 = new Supplier("Jerusalem", Area.Center, "David", "0523333333", 1);
        Supplier sup2 = new Supplier("Hiafa", Area.North, "Shlomi", "0524444444", 2);
        Supplier parkSup = new Supplier("Logistical warehouse", Area.Center,"Michael", "0525555555", 50);

        Store parkSro = new Store("Logistical warehouse", Area.Center,"Michael", "0525555555", 50);
        Store sro1 = new Store("Bash", Area.South, "Miri", "0526666666", 111);
        Store sro2 = new Store("Mevaseret", Area.Center, "Regev", "0527777777", 112);


        OrderDocument o11 = new OrderDocument(sup1, sro1);
        OrderDocument o12 = new OrderDocument(sup1, sro2);
        OrderDocument o21 = new OrderDocument(sup2, sro1);
        OrderDocument o22 = new OrderDocument(sup2, sro2);

        Map<Product, Double> m1 = new HashMap<>();
        Map<Product, Double> m2 = new HashMap<>();
        Map<Product, Double> m3 = new HashMap<>();
        Map<Product, Double> m4 = new HashMap<>();

        m1.put(p1, 5000.0);
        m1.put(p2, 5000.0);
        o11.setProductsList(m1);
        o11.setWeight(10000);

        m2.put(p2, 2000.0);
        o12.setProductsList(m2);
        o12.setWeight(2000);

        m3.put(p1, 1100.0);
        m3.put(p2, 1000.0);
        o21.setProductsList(m3);
        o21.setWeight(2100);

        m4.put(p3, 1000.0);
        o22.setProductsList(m4);
        o22.setWeight(1000);

        String dateString = "11-04-2023";
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try{
            date = format.parse(dateString);
        }catch (ParseException e){
            System.out.println();
        }
        Transit deli1 = new Transit(date, t2, d2);
        deli1.addOrderDoc(o22);

        primeTruckRepo.saveTruck(t1);
        primeTruckRepo.saveTruck(t2);
        primeOrderDocRepo.saveOrderDocument(o11);
        primeOrderDocRepo.saveOrderDocument(o12);
        primeOrderDocRepo.saveOrderDocument(o21);
        primeOrderDocRepo.saveOrderDocument(o22);
        primeTransitRepo.saveTransit(deli1);

        primeDriverRepo.saveDriver(d1);
        primeDriverRepo.saveDriver(d2);
        primeProductRepo.saveProduct(p1);
        primeProductRepo.saveProduct(p2);
        primeProductRepo.saveProduct(p3);
        primeSupplierRepo.saveSupplier(sup1);
        primeSupplierRepo.saveSupplier(sup2);
        primeSupplierRepo.saveSupplier(parkSup);
        primeStoreRepo.saveStore(parkSro);
        primeStoreRepo.saveStore(sro1);
        primeStoreRepo.saveStore(sro2);


        ps.preRunData(scanner,primeTransitRepo, primeTruckRepo, primeOrderDocRepo);
        ps.switchMenu(scanner, truckController,orderDocumentController, transitController);
    }

    public void switchMenu(Scanner scanner,TruckController truckC,OrderDocumentController orderDocC, TransitController transitC){
//        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            displayMainMenu();
            choice = scanner.nextInt();
            if (scanner.hasNextLine()) scanner.nextLine();
            switch (choice) {
                case 1: // create new transit
                    transitC.createNewTransit(scanner);
                    break;
                case 2: // open update transit menu
                    int ch2;
                    do{
                        displayUpdateTransitMenu();
                        ch2 = scanner.nextInt();
                        if (scanner.hasNextLine()) scanner.nextLine();
                        handleUpdateTransitMenu(ch2, scanner, transitC, orderDocC);
                    } while (ch2<0 || ch2>6);
                    break;
                case 3: // open manage truck menu
                    int ch3;
                do {
                    displayTruckManagerMenu();
                    ch3 = scanner.nextInt();
                    if (scanner.hasNextLine()) scanner.nextLine();
                    handleTruckManagerMenu(ch3, scanner, truckC);
                } while (ch3<0 || ch3>3);
                    break;
                case 4: //manage documents
                    int ch4;
                    do{
                        displayDocumentManagerMenu();
                        ch4 = scanner.nextInt();
                        if (scanner.hasNextLine()) scanner.nextLine();
                        handleDocumentManagerMenu(ch4, transitC, orderDocC);
                    } while (ch4<0 || ch4>3);
                    break;
                case 5: //manage orders
                    int ch5;
                    do {
                        displayOrderManagerMenu();
                        ch5 = scanner.nextInt();
                        if (scanner.hasNextLine()) scanner.nextLine();
                        handleOrderManagerMenu(scanner, ch5, orderDocC);
                    } while (ch5<0 || ch5>3);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        } while (choice != 0);
        scanner.close();
    }

    public void displayMainMenu(){
        System.out.println("-----Main Menu-----");
        System.out.println("Please select an option: ");
        System.out.println("1. Create new transit ");
        System.out.println("2. Update transit ");
        System.out.println("3. Manage trucks ");
        System.out.println("4. Manage documents ");
        System.out.println("5. Manage orders ");
        System.out.println("0. Exit");
    }
    public void displayTruckManagerMenu(){
        System.out.println("-----Truck Manager-----");
        System.out.println("1. Add a new truck ");
        System.out.println("2. Remove truck ");
        System.out.println("3. Show all trucks ");
        System.out.println("0. Back to main menu ");
    }

    public void displayOrderManagerMenu(){
        System.out.println("-----Manage Orders-----");
        System.out.println("1. Create a new Order ");
        System.out.println("2. Show pending orders (by area) ");
        System.out.println("3. Edit order ");
        System.out.println("0. Back to main menu");
    }
    public void displayUpdateTransitMenu(){
        System.out.println("-----Update Transit-----");
        System.out.println("1. print transit details ");
        System.out.println("2. print pending orders ");
        System.out.println("3. Add order to transit ");
        System.out.println("4. Remove order from transit ");
        System.out.println("5. Replace truck ");
        System.out.println("6. Start transit ");
        System.out.println("0. Back to main menu ");
    }

    public void displayEditOrderMenu(){
        System.out.println("-----Edit Order-----");
        System.out.println("1. Add products to an order ");
        System.out.println("2. Change the amount of a product ");
        System.out.println("3. Remove products ");
        System.out.println("0. Back to main menu");
    }

    public void displayDocumentManagerMenu(){
        System.out.println("-----Manage Documents-----");
        System.out.println("1. Show pending orders (by area) ");
        System.out.println("2. Show completed orders ");
        System.out.println("3. Show Transit records ");
        System.out.println("0. Back to main menu");
    }

    public void handleDocumentManagerMenu(int ch4, TransitController transitC, OrderDocumentController orderDocC){
        switch (ch4) {
            case 1:
                orderDocC.printPendingOrderDocs();
                break;
            case 2:
                orderDocC.printCompletedOrderDocs();
                break;
            case 3:
                transitC.printAllTransitRecords();
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    public void handleTruckManagerMenu(int ch3, Scanner scanner, TruckController truckC){
        switch (ch3) {
            case 1:
                truckC.createNewTruck(scanner);
                break;
            case 2:
                truckC.removeTruckByPlate(scanner);
                break;
            case 3:
                truckC.printAllTrucks();
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }
    public void handleUpdateTransitMenu(int ch2, Scanner scanner, TransitController transitC, OrderDocumentController orderDocC){
        switch (ch2){
            case 1:
                transitC.printTransitDetails(scanner);
                break;
            case 2:
                orderDocC.printPendingOrderDocs();
                break;
            case 3: transitC.addOrderToTransit(scanner);
                break;
            case 4:
                transitC.removeOrderFromTransit(scanner);
                break;
            case 5:
                transitC.replaceTransitTruck(scanner);
            case 6:
                transitC.beginTransit(scanner);
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    public void handleOrderManagerMenu(Scanner scanner, int ch5, OrderDocumentController orderDocC){
        switch (ch5) {
            case 1: // create new order
                orderDocC.createNewOrderDocument(scanner);
                break;
            case 2: //prints all orders
                orderDocC.printPendingOrderDocs();
                break;
            case 3: //open edit order menu
                int ch53;
                do {
                    displayEditOrderMenu();
                    ch53 = scanner.nextInt();
                    if (scanner.hasNextLine()) scanner.nextLine();
                    handleEditOrderMenu(scanner, ch53, orderDocC);
                } while (ch53<0 || ch53>3);
                break;
            case 0:
                System.out.println("Going back...");
                break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }

    public void handleEditOrderMenu(Scanner scanner, int ch53, OrderDocumentController orderDocC){
        switch (ch53) {
            case 1:
                orderDocC.addProductToOrder(scanner);
                break;
            case 2:
                orderDocC.updateProductAmount(scanner);
                break;
            case 3:
                orderDocC.removeProductFromOrder(scanner);
                break;
            case 0:
                System.out.println("Going back...");
                break;
        }
    }

    public void preRunData(Scanner scanner, TransitRepository transitRepository,
                           TruckRepository truckRepository, OrderDocumentRepository orderDocumentRepository){
        String ans;
        do{
            System.out.println("Would you like to start the system with built in data ? (Y/N) ");
            ans = scanner.nextLine();
            switch (ans.toUpperCase()){
                case "Y":
                    return;
                case "N":
                    transitRepository.getTransitsSet().clear();
                    truckRepository.getTrucksSet().clear();
                    orderDocumentRepository.getOrderDocsSet().clear();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }while (!(ans.equalsIgnoreCase("y")) && !(ans.equalsIgnoreCase("n")));
    }
}


