import java.util.*;
import java.util.stream.Collectors;

class Employee {
    String id;
    String name;
    String mobileNumber;
    String department;
    String gender;

    public Employee(String id, String name, String mobileNumber, String department, String gender) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.department = department;
        this.gender = gender;
    }
}

class CollectionManager {
    private Map<String, List<Employee>> collections = new HashMap<>();
    public static List<Employee> staticData = new ArrayList<>();  


    public static void initializeStaticData() {
        staticData.add(new Employee("E02001", "John", "1234560123", "IT", "Male"));
        staticData.add(new Employee("E02002", "Jane", "4567890123", "HR", "Female"));
        staticData.add(new Employee("E02003", "Alice", "4567890123", "HR", "Female"));
        staticData.add(new Employee("E02004", "Mark", "3456789012", "IT", "Male"));
        staticData.add(new Employee("E02005", "Alice", "4567890123", "Finance", "Female"));
        staticData.add(new Employee("E02006", "Alice", "4567890123", "IT", "Female"));
    }

 
    public void createCollection(String collectionName) {
        collections.put(collectionName, new ArrayList<>());
        System.out.println("Collection created: " + collectionName);
        System.out.println();  // Add space after collection creation
    }


    public void addEmployee(String collectionName, Employee employee) {
        if (collections.containsKey(collectionName)) {
            collections.get(collectionName).add(employee);
        }
    }


    public void indexData(String collectionName, String excludeColumn) {
        if (collections.containsKey(collectionName)) {
            List<Employee> employees = collections.get(collectionName);
            System.out.println("Indexing data for collection: " + collectionName + " (Excluding column: " + excludeColumn + ")");
            for (Employee emp : employees) {
                System.out.println("Employee: ID=" + emp.id + ", Name=" + emp.name +
                        ((!"Department".equalsIgnoreCase(excludeColumn)) ? ", Department=" + emp.department : "") +
                        ((!"Gender".equalsIgnoreCase(excludeColumn)) ? ", Gender=" + emp.gender : "") +
                        ", Mobile=" + emp.mobileNumber);  // Display full mobile number
            }
            System.out.println();  // Add space after indexing
        }
    }


    public void searchByColumn(String collectionName, String columnName, String columnValue, String excludeColumn) {
        if (collections.containsKey(collectionName)) {
            List<Employee> employees = collections.get(collectionName);
            System.out.println("Searching in collection: " + collectionName + " where " + columnName + "=" + columnValue);
            List<Employee> filtered = employees.stream()
                    .filter(emp -> {
                        switch (columnName) {
                            case "Department": return emp.department.equalsIgnoreCase(columnValue);
                            case "Gender": return emp.gender.equalsIgnoreCase(columnValue);
                            case "ID": return emp.id.equalsIgnoreCase(columnValue);
                            case "Name": return emp.name.equalsIgnoreCase(columnValue);
                            default: return false;
                        }
                    }).collect(Collectors.toList());

            filtered.forEach(emp -> System.out.println("Found Employee: ID=" + emp.id + ", Name=" + emp.name +
                    ((!"Department".equalsIgnoreCase(excludeColumn)) ? ", Department=" + emp.department : "") +
                    ((!"Gender".equalsIgnoreCase(excludeColumn)) ? ", Gender=" + emp.gender : "") +
                    ", Mobile=" + emp.mobileNumber)); // Display full mobile number
            System.out.println();  // Add space after search
        }
    }

 
    public int getEmpCount(String collectionName) {
        if (collections.containsKey(collectionName)) {
            int count = collections.get(collectionName).size();
            System.out.println("Total Employees in " + collectionName + ": " + count);
            System.out.println();  // Add space after getting employee count
            return count;
        }
        return 0;
    }


    public void delEmpById(String collectionName, String employeeId) {
        if (collections.containsKey(collectionName)) {
            List<Employee> employees = collections.get(collectionName);
            employees.removeIf(emp -> emp.id.equals(employeeId));
            System.out.println("Deleted Employee with ID: " + employeeId);
            System.out.println();  // Add space after deletion
        }
    }


    public void getDepFacet(String collectionName) {
        if (collections.containsKey(collectionName)) {
            List<Employee> employees = collections.get(collectionName);
            System.out.println("Department Facet for " + collectionName);
            Map<String, Long> depFacet = employees.stream()
                    .collect(Collectors.groupingBy(emp -> emp.department, Collectors.counting()));

            depFacet.forEach((dep, count) -> System.out.println("Department: " + dep + ", Count: " + count));
            System.out.println();  
        }
    }

    
}

public class EmployeeCollectionManager {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CollectionManager manager = new CollectionManager();


        CollectionManager.initializeStaticData();

  
        System.out.print("Enter your name: ");
        String userName = sc.nextLine();
        System.out.print("Enter your mobile number: ");
        String mobileNumber = sc.nextLine();


        String v_nameCollection = "Hash_" + userName;
        String v_phoneCollection = "Hash_" + mobileNumber.substring(mobileNumber.length() - 4);

        manager.createCollection(v_nameCollection);
        manager.createCollection(v_phoneCollection);


        for (Employee emp : CollectionManager.staticData) {
            if (emp.name.equalsIgnoreCase(userName)) {
                manager.addEmployee(v_nameCollection, emp);
            }
            if (emp.mobileNumber.endsWith(mobileNumber.substring(mobileNumber.length() - 4))) {
                manager.addEmployee(v_phoneCollection, emp);
            }
        }

      


        manager.getEmpCount(v_nameCollection);

        manager.indexData(v_nameCollection, "Department");  
        manager.indexData(v_phoneCollection, "Gender");  

       
        manager.delEmpById(v_nameCollection, "E02003");

       
        manager.getEmpCount(v_nameCollection);

      
        manager.searchByColumn(v_nameCollection, "Name", userName, "Department"); 
        manager.searchByColumn(v_phoneCollection, "Department", "IT", "Gender"); 

        
        manager.getDepFacet(v_nameCollection);
        manager.getDepFacet(v_phoneCollection);

     
    }
}
