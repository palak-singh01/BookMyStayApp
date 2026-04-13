import java.util.HashMap;

abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }

    public String getRoomType() {
        return roomType;
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

class RoomInventory {
    private HashMap<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void addRoom(String roomType, int count) {
        availability.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        availability.put(roomType, count);
    }

    public void displayInventory() {
        for (String roomType : availability.keySet()) {
            System.out.println(roomType + " Available: " + availability.get(roomType));
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        inventory.addRoom(single.getRoomType(), 5);
        inventory.addRoom(doubleRoom.getRoomType(), 3);
        inventory.addRoom(suite.getRoomType(), 2);

        single.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(single.getRoomType()));
        System.out.println();

        doubleRoom.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(doubleRoom.getRoomType()));
        System.out.println();

        suite.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(suite.getRoomType()));
        System.out.println();

        inventory.updateAvailability("Single Room", 4);

        System.out.println("Updated Inventory:");
        inventory.displayInventory();
    }
}