class Arrive extends Event {

    Arrive(Customer customer) {
        super(customer);
    }

    // returns Serve if there is an available server & empty queue
    // else Wait in the first server w q space else Leave
    public Event executeEvent(ImList<Server> serverList, int qmax) {
        for (int i = 0; i < serverList.size(); i++) {
            Server s = serverList.get(i);
            if (s.isAvailable(this.getCurrentTime()) && s.getQ().isEmpty()) {
                return new Serve(this.getCustomer(), serverList.get(i), this.getCurrentTime());
            }
        }
        
        // either queue at human server or at (k+1)th counter
        for (int j = 0; j < serverList.size(); j++) {
            Server s = serverList.get(j);
            if (s.canQ()) {
                return new Wait(this.getCustomer(), 
                                serverList.get(s.getqNum() - 1), 
                                this.getCurrentTime()); 
            }
        }
        return new Leave(this.getCustomer());
    }
   
    // if server is available, return the same serverlist
    // if there is q space, add customer to q, update server q
    // if s is not a human, update all self check queues.
    // if not customer leaves, no changes to serverlist
    @Override
    public ImList<Server> updateServerList(ImList<Server> serverList) {
        for (int i = 0; i < serverList.size(); i++) {
            Server s = serverList.get(i);
            if (s.isAvailable(this.getCurrentTime()) && s.getQ().isEmpty()) {
                return serverList;
            }
        }
        for (int j = 0; j < serverList.size(); j++) {
            Server s = serverList.get(j);
            if (s.canQ() && s.isHuman()) {
                serverList = serverList.set(j, s.addCustomer(this.getCustomer()));
                return serverList;
            } else if (s.canQ() && !s.isHuman()) {
                for (int h = j; h < serverList.size(); h++) {
                    serverList = serverList.set(h, serverList.get(h)
                                                .addCustomer(this.getCustomer()));
                }
                return serverList;
            }
        }
        return serverList;
    }

    public int addLeftCount(int leftCount) {
        return leftCount;
    }

    public int addServedCount(int servedCount) {
        return servedCount;
    }

    @Override
    public String toString() {
        return String.format("%.3f %d arrives\n",  
                              this.getCurrentTime(), 
                              this.getCustomer().getCustomerID());
    }
}  
