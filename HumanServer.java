import java.util.function.Supplier;

class HumanServer extends Server {

    HumanServer(int serverID, double nextAvailTime, ImList<Customer> q, 
                int qmax, Supplier<Double> restTimes, int qNum) {
        super(serverID, nextAvailTime, q, qmax, restTimes, qNum);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public HumanServer addCustomer(Customer customer) {
        return new HumanServer(serverID, nextAvailTime, q.add(customer), qmax, restTimes, qNum);
    }

    @Override
    public HumanServer moveUp() {
        if (!this.q.isEmpty()) {
            ImList<Customer> newQ = this.q.remove(0);
            return new HumanServer(serverID, nextAvailTime, newQ, qmax, restTimes, qNum);
        }
        return this;
    }
}       