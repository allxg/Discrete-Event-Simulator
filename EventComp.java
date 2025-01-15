import java.util.Comparator;

class EventComp implements Comparator<Event> {
    public int compare(Event event1, Event event2) {
        if (event1.getPriority() != event2.getPriority()) {
            return event1.getPriority() ? -1 : 1; 
        } else if (event1.getCurrentTime() < 
            event2.getCurrentTime()) {
            return -1;
        } else if (event1.getCurrentTime() > 
                   event2.getCurrentTime()) {
            return 1;
        } else {
            return event1.getCustomer().getCustomerID() - 
            event2.getCustomer().getCustomerID();
        }
    }
}
