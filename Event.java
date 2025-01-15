abstract class Event {
   
    protected static final double Zero = 0.000;
    protected final Customer customer;

    Event(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    abstract Event executeEvent(ImList<Server> serverList, int qmax);
    
    public ImList<Server> updateServerList(ImList<Server> serverList) {
        return serverList;
    }   

    public double getCurrentTime() {
        return this.customer.getArrivalTime();
    }

    public boolean updateFirst() {
        return false;
    }

    public boolean hasNextEvent() {
        return true;
    }
 
    public boolean getPriority() {
        return false;
    }
 
    public int addServedCount(int servedCount) {
        return servedCount;
    }
    
    public int addLeftCount(int leftCount) {
        return leftCount;
    }

    public double addWaitTime(double waitTime) {
        return waitTime;
    }

} 

