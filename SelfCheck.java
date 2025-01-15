import java.util.function.Supplier;

class SelfCheck extends Server {


    // all counters share the same queue
    // do not rest
    // IDs start from k + 1 onwards
    SelfCheck(int serverID, double nextAvailTime, ImList<Customer> q, 
                int qmax, Supplier<Double> restTimes, int qNum) {
        super(serverID, nextAvailTime, q, qmax, restTimes, qNum);
    }

    @Override
    public SelfCheck addCustomer(Customer c) {
        return new SelfCheck(serverID, nextAvailTime, q.add(c), qmax, restTimes, qNum);
    }


    @Override
    public SelfCheck moveUp() {
        if (!q.isEmpty()) {
            return new SelfCheck(serverID, nextAvailTime, q.remove(0), qmax, restTimes, qNum);
        }
        return this;

    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("self-check %s", super.toString());
    }
}