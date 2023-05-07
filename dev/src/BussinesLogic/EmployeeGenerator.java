package BussinesLogic;

/**
 * this class creates new employee in the system
 */
public class EmployeeGenerator {

    public Employee CreateEmployee(String firstName, String lastName, String id, String bankAccount, double salary, String empTermsPath, String startDate){
        return new Employee(firstName,lastName,id,bankAccount,salary,empTermsPath,startDate);
    }

    public Driver CreateDriver(String firstName, String lastName, String id, String bankAccount, double salary, String empTermsPath, String startDate){
        return new Driver(firstName,lastName,id,bankAccount,salary,empTermsPath,startDate);
    }

    public Driver CreateCreateDriver(Employee employee){
        return new Driver(employee.getFirstName(),employee.getLastName(), employee.getId(), employee.getBankAccount(), employee.getSalary(), employee.getEmpTerms(), employee.getStartDate());
    }

}
