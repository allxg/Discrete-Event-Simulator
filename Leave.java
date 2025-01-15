class Leave extends Event {
    
    Leave(Customer customer) {
        super(customer);
    }

    
    @Override
    public Event executeEvent(ImList<Server> serverList, int qmax) {
        return this;
    }

    @Override
    public boolean hasNextEvent() {
        return false;
    }

    @Override
    public boolean getPriority() {
        return true;
    }

    @Override
    public int addLeftCount(int leftCount) {
        return leftCount + 1;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d leaves\n", this.getCustomer().getArrivalTime(),
                                                 this.getCustomer().getCustomerID());
    }
}

