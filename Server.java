import java.util.function.Supplier;

abstract class Server {
    protected final int serverID;
    protected final double nextAvailTime; 
    protected final ImList<Customer> q;
    protected final int qmax;
    protected final Supplier<Double> restTimes;
    protected final int qNum;

    Server(int serverID, double nextAvailTime, ImList<Customer> q, 
           int qmax, Supplier<Double> restTimes, int qNum) {
        this.serverID = serverID;
        this.nextAvailTime = nextAvailTime;
        this.q = q;
        this.qmax = qmax;
        this.restTimes = restTimes;
        this.qNum = qNum;
    }

    abstract boolean isHuman();

    public int getqNum() {
        return qNum;
    }
    
    public int getServerID() {
        return this.serverID;
    }

    public double getNextAvailTime() {
        return this.nextAvailTime;
    }

    public ImList<Customer> getQ() {
        return this.q;
    }

    public int getQMax() {
        return this.qmax;
    }
 
    public Supplier<Double> getRestTimes() {
        return this.restTimes;
    }

    public Double getRestTime() {
        return this.restTimes.get();
    }

    public boolean isAvailable(double currentTime) {
        return currentTime >= this.nextAvailTime;
    }

    // checks if input Customer is next to be Served
    public boolean isNext(Customer customer) {
        return this.q.indexOf(customer) == 0;
    }

    public boolean canQ() {
        return this.q.size() < this.qmax;
    }

    abstract Server addCustomer(Customer customer);

    abstract Server moveUp();

    @Override
    public String toString() {
        return String.format("%d", serverID);
    }
}        
