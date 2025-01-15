class Wait extends Event {

    private final Server server;
    private final double currentTime;

    Wait(Customer customer, Server server, double currentTime) {
        super(customer);
        this.server = server; // the ID customer queues at
        this.currentTime = currentTime; // customer's arrival time
    }

    // for if customer is next in queue, and server is free, serve the customer
    // else, keep waiting
    @Override
    public Event executeEvent(ImList<Server> serverList, int qmax) {
        Server s = serverList.get(server.getServerID() - 1);

        if (s.isHuman()) {
            if (s.isNext(this.getCustomer()) && s.isAvailable(this.currentTime)) {
                return new Serve(this.getCustomer(), server, this.currentTime);
            }
            return new KeepWaiting(this.getCustomer(), s, s.getNextAvailTime());
        } else {
            double nearest = s.getNextAvailTime();
            for (int i = server.getServerID(); i <= serverList.size(); i++) {
                double t = serverList.get(i - 1).getNextAvailTime();
                nearest = t < nearest ? t : nearest;
                if (serverList.get(i - 1).isNext(this.getCustomer())
                    && serverList.get(i - 1).isAvailable(this.currentTime)) {
                    return new Serve(this.getCustomer(), serverList.get(i - 1), this.currentTime);
                }
            }
            return new KeepWaiting(this.getCustomer(), s, nearest);
        }

    }

    @Override
    public double getCurrentTime() {
        return this.currentTime;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d waits at %s\n", this.getCurrentTime(),
                                                      this.getCustomer().getCustomerID(),
                                                      server.toString());
    }
}
