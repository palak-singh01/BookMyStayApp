import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public void addRoom(String roomType, int count) {
        availability.put(roomType, count);
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        availability.put(roomType, availability.get(roomType) - 1);
    }
}

class BookingService {
    private Set<String> allocatedRoomIds = new HashSet<>();
    private Map<String, Set<String>> allocationMap = new HashMap<>();
    private int roomCounter = 1;

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            String roomType = r.getRoomType();

            if (inventory.getAvailability(roomType) > 0) {

                String roomId = roomType.replace(" ", "") + roomCounter++;
                
                if (!allocatedRoomIds.contains(roomId)) {
                    allocatedRoomIds.add(roomId);

                    allocationMap.putIfAbsent(roomType, new HashSet<>());
                    allocationMap.get(roomType).add(roomId);

                    inventory.decrement(roomType);

                    System.out.println(r.getGuestName() + " booked " + roomType + " | Room ID: " + roomId);
                }

            } else {
                System.out.println("No rooms available for " + r.getGuestName() + " (" + roomType + ")");
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Suite Room"));

        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single Room", 1);
        inventory.addRoom("Suite Room", 1);

        BookingService service = new BookingService();
        service.processBookings(queue, inventory);
    }
}