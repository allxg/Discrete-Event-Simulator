class Serve extends Event {

    private final Server server;
    private final double timeOfService;

    Serve(Customer customer, Server server, double timeOfService) {
        super(customer);
        this.server = server;// serving server
        this.timeOfService = timeOfService; // time the server starts serving
    }

    public Event executeEvent(ImList<Server> serverList, int qmax) {
        Server s = serverList.get(server.getServerID() - 1);
        return new Done(this.getCustomer(), s, 
                        s.getNextAvailTime()); 
    }

    @Override
    public ImList<Server> updateServerList(ImList<Server> serverList) {
        Server s = serverList.get(server.getServerID() - 1); // current server
        double newTime = this.getCurrentTime() + this.getCustomer().getServiceTime();
        if (s.isHuman()) {
            Server n = new HumanServer(s.getServerID(), newTime, s.moveUp().getQ(), 
                                s.getQMax(), s.getRestTimes(), s.getqNum());
            serverList = serverList.set(s.getServerID() - 1, n);
            return serverList;
        }
        // move up all queues, update next avail time for current counter
        for (int i = 0; i < serverList.size(); i++) {
            Server d = serverList.get(i);
            if (!d.isHuman()) {
                serverList = serverList.set(i, d.moveUp());
            }
        }
        serverList = serverList.set(s.getServerID() - 1, new SelfCheck(s.getServerID(),
                                                newTime, 
                                                serverList.get(server.getServerID() - 1).getQ(), 
                                                s.getQMax(), 
                                                s.getRestTimes(), 
                                                s.getqNum()));
        return serverList;
    }

    @Override
    public double getCurrentTime() {
        return this.timeOfService;
    }

    @Override
    public boolean updateFirst() {
        return true;
    }

    @Override
    public int addServedCount(int servedCount) {
        return servedCount + 1;
    }

    @Override
    public double addWaitTime(double waitTime) {
        waitTime = waitTime + timeOfService - this.getCustomer().getArrivalTime();
        return waitTime;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d serves by %s\n", this.getCurrentTime(), 
                                                       this.getCustomer().getCustomerID(), 
                                                       server.toString());
    }

}
