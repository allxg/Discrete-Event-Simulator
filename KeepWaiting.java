class KeepWaiting extends Event {
    
    private final Server server;
    private final double currentTime;

    KeepWaiting(Customer customer, Server server, double currentTime) {
        super(customer);
        this.server = server;
        this.currentTime = currentTime; // server's next avail time
    }

    @Override
    public Event executeEvent(ImList<Server> serverList, int qmax) {
        Server s = serverList.get(server.getServerID() - 1);
        if (s.isHuman()) {
            if (s.isNext(this.getCustomer()) && s.isAvailable(this.currentTime)) {
                return new Serve(this.getCustomer(), s, this.currentTime);
            } 
            return new KeepWaiting(this.getCustomer(), s, s.getNextAvailTime());
        } else {
            double nearest = s.getNextAvailTime();
            for (int i = s.getServerID(); i <= serverList.size(); i++) {
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
        return "";
    }
}
