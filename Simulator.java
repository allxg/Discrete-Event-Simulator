import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;
    private static final double startTime = 0.000;

    Simulator(int numOfServers, int numOfSelfChecks, int qmax, 
              ImList<Double> arrivalTimes, 
              Supplier<Double> serviceTimes, 
              Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.restTimes = restTimes;
    }
    
    // method to generate server list
    private ImList<Server> generateServerList(int numOfServers,
                                            int numOfSelfChecks) {
        ImList<Server> serverList = new ImList<Server>();
        for (int k = 1; k <= numOfServers; k++) {
            serverList = serverList.add(new HumanServer(k, 
                                                   startTime, 
                                                   new ImList<Customer>(), 
                                                   this.qmax, this.restTimes, k));
        }

        for (int i = numOfServers + 1; i <= numOfServers + numOfSelfChecks; i++) {
            serverList = serverList.add(new SelfCheck(i,
                                                    startTime,
                                                    new ImList<Customer>(),
                                                    this.qmax, this.restTimes,
                                                    numOfServers + 1));
        }
        return serverList;
    }    

    public String simulate() {
        ImList<Server> serverList = generateServerList(numOfServers, 
                                                    numOfSelfChecks); 
        String output = "";
        int totalServed = 0;
        int totalLeft = 0;
        double totalWaitTime = startTime;

        PQ<Event> events = new PQ<Event>(new EventComp());
        for (int i = 0; i < arrivalTimes.size(); i++) {
            Customer c = new Customer(i + 1, arrivalTimes.get(i), this.serviceTimes);
            events = events.add(new Arrive(c));
        }

        while (!events.isEmpty()) {
            Pair<Event, PQ<Event>> pair = events.poll();
            Event currentEvent = pair.first();
            events = pair.second();

            // update serverlist first for serve so that it can return a done event
            // with the server's next avail time
            if (currentEvent.updateFirst()) {
                serverList = currentEvent.updateServerList(serverList);
            }
            Event nextEvent = currentEvent.executeEvent(serverList, this.qmax);
            if (currentEvent.hasNextEvent()) {
                events = events.add(nextEvent);
            }
            if (!currentEvent.updateFirst()) {
                serverList = currentEvent.updateServerList(serverList);
            }

            output += currentEvent.toString();
            totalServed = currentEvent.addServedCount(totalServed);
            totalLeft = currentEvent.addLeftCount(totalLeft);
            totalWaitTime = currentEvent.addWaitTime(totalWaitTime);
        }

        double avgWaitTime = totalWaitTime;
        if (avgWaitTime != startTime) {
            avgWaitTime = avgWaitTime / totalServed;
        }

        output += String.format("[%.3f %d %d]", 
                                 avgWaitTime, totalServed, totalLeft);
        return output;
    }
}
