import java.util.*;

public class LowPerformanceApp {
    private static final List<String> globalCache = new ArrayList<>();
    private static final Map<Integer, String> staticData = new HashMap<>();
    
    public static void main(String[] args) {
        System.out.println("Starting Low Performance Application...");
        
        LowPerformanceApp app = new LowPerformanceApp();
        
        // Execute multiple performance-heavy operations
        app.inefficientStringOperations();
        app.wastefulObjectCreation();
        app.badCollectionUsage();
        app.memoryLeakSimulation();
        app.boxingUnboxingWaste();
        
        System.out.println("Application completed. Check GC logs for performance issues.");
    }
    
    // Problem 1: Inefficient String concatenation
    private void inefficientStringOperations() {
        System.out.println("Executing inefficient string operations...");
        
        String result = "";
        for (int i = 0; i < 50000; i++) {
            result += "User" + i + "_Data_Processing_" + System.currentTimeMillis() + ";";
        }
        
        // Create many temporary String objects
        String[] parts = result.split(";");
        String finalResult = "";
        for (String part : parts) {
            if (part.contains("User")) {
                finalResult += part.toUpperCase() + "\n";
            }
        }
        
        System.out.println("String operations completed. Result length: " + finalResult.length());
    }
    
    // Problem 2: Massive temporary object creation
    private void wastefulObjectCreation() {
        System.out.println("Creating wasteful objects...");
        
        List<UserData> users = new ArrayList<>();
        
        for (int i = 0; i < 100000; i++) {
            // Create many temporary objects that will be quickly discarded
            UserData user = new UserData(
                "User" + i,
                new Random().nextInt(100),
                createTemporaryAddress(i),
                generateRandomTags(10)
            );
            
            // Only keep users with even IDs, wasting half of the created objects
            if (i % 2 == 0) {
                users.add(user);
            }
        }
        
        System.out.println("Created " + users.size() + " user objects");
    }
    
    // Problem 3: Bad collection usage without initial capacity
    private void badCollectionUsage() {
        System.out.println("Demonstrating bad collection usage...");
        
        // No initial capacity - causes multiple array copies
        List<Integer> numbers = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        Set<String> uniqueItems = new HashSet<>();
        
        // Force many resize operations
        for (int i = 0; i < 100000; i++) {
            numbers.add(i);
            data.put("key" + i, new Object[100]); // Large objects
            uniqueItems.add("item" + (i % 1000)); // Many duplicate attempts
        }
        
        // Linear search instead of using proper data structures
        List<Integer> evenNumbers = new ArrayList<>();
        for (Integer num : numbers) {
            if (num % 2 == 0) {
                evenNumbers.add(num);
            }
        }
        
        System.out.println("Collections processed: " + evenNumbers.size() + " even numbers");
    }
    
    // Problem 4: Memory leak simulation with static collections
    private void memoryLeakSimulation() {
        System.out.println("Simulating memory leaks...");
        
        // Keep adding to static collections - never cleaned up
        for (int i = 0; i < 50000; i++) {
            String data = "LargeDataString_" + i + "_" + 
                         "x".repeat(100) + "_" + System.nanoTime();
            globalCache.add(data);
            staticData.put(i, data);
        }
        
        System.out.println("Added " + globalCache.size() + " items to global cache");
    }
    
    // Problem 5: Excessive boxing/unboxing
    private void boxingUnboxingWaste() {
        System.out.println("Demonstrating boxing/unboxing waste...");
        
        List<Integer> boxedIntegers = new ArrayList<>();
        
        // Lots of autoboxing
        for (int i = 0; i < 100000; i++) {
            boxedIntegers.add(i); // int -> Integer boxing
        }
        
        // Lots of unboxing in calculations
        int sum = 0;
        for (Integer boxed : boxedIntegers) {
            sum += boxed; // Integer -> int unboxing
            sum += boxed * 2; // More unboxing
        }
        
        // Using wrapper classes in maps
        Map<Integer, Double> calculations = new HashMap<>();
        for (int i = 0; i < 50000; i++) {
            calculations.put(i, Math.sqrt(i)); // double -> Double boxing
        }
        
        System.out.println("Boxing operations completed. Sum: " + sum);
    }
    
    // Helper methods that create temporary objects
    private Address createTemporaryAddress(int id) {
        return new Address(
            "Street " + id,
            "City " + (id % 100),
            "State " + (id % 50),
            String.format("%05d", id % 100000)
        );
    }
    
    private List<String> generateRandomTags(int count) {
        List<String> tags = new ArrayList<>();
        Random random = new Random();
        
        for (int i = 0; i < count; i++) {
            tags.add("Tag" + random.nextInt(1000));
        }
        
        return tags;
    }
    
    // Data classes
    static class UserData {
        private String name;
        private int age;
        private Address address;
        private List<String> tags;
        
        public UserData(String name, int age, Address address, List<String> tags) {
            this.name = name;
            this.age = age;
            this.address = address;
            this.tags = tags;
        }
        
        // Getters would be here in real application
    }
    
    static class Address {
        private String street;
        private String city;
        private String state;
        private String zipCode;
        
        public Address(String street, String city, String state, String zipCode) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
        }
    }
}