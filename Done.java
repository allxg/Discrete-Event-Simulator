class Done extends Event {

    private final Server server;
    private final double currentTime;

    Done(Customer customer, Server server, double currentTime) {
        super(customer);
        this.server = server;
        this.currentTime = currentTime; // accessed thru server's next avail time
    }

    @Override
    public Event executeEvent(ImList<Server> serverList, int qmax) {
        return this;
    }

    // update server's next available time with added rest time
    // int serverID, double nextAvailTime, ImList<Customer> q, int qmax
    @Override
    public ImList<Server> updateServerList(ImList<Server> serverList) {
        Server s  = serverList.get(server.getServerID() - 1);
        if (s.isHuman()) {
            return serverList.set(s.getServerID() - 1, 
                                new HumanServer(s.getServerID(), 
                                                s.getNextAvailTime() + s.getRestTime(),
                                                s.getQ(), s.getQMax(), 
                                                s.getRestTimes(), s.getqNum()));
        }
        return serverList;
    }

    @Override
    public double getCurrentTime() {
        return this.currentTime;
    }

    @Override
    public boolean hasNextEvent() {
        return false;
    }

    public int addLeftCount(int leftCount) {
        return leftCount;
    }

    public int addServedCount(int servedCount) {
        return servedCount;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d done serving by %s\n", this.getCurrentTime(), 
                                                    this.getCustomer().getCustomerID(), 
                                                    server.toString());
    }
}
