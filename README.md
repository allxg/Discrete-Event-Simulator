# Discrete Event Simulator

The programme simulates customer queuing and service processes at servers and self-service counters using object-oriented programming principles. The system dynamically manages customer arrivals, service times, server availability, and queue limits.

## Key Components
### Classes
- **`Customer`**: Represents a customer with unique attributes like arrival time and service time.
- **`Server`** and Subclasses:
  - **`HumanServer`**: Handles customer queues and can take rest periods.
  - **`SelfCheck`**: Represents self-service counters without rest functionality.
- **Events**:
  - **`Event`**: Abstract base class for all event types.
  - **`Serve`, `Done`, `Wait`, `Leave`, `KeepWaiting`**: Represent specific customer-server interactions.
- **`Simulator`**: Orchestrates the event execution and simulation process.
- **`EventComp`**: Comparator for prioritizing events based on time and customer ID.

### Input Parameters
- Number of servers and self-service counters.
- Maximum queue size (`qmax`).
- Probability of server rest (`probRest`).
- Customer arrival times and service times.

### Simulation Flow
1. Customers arrive at specific times and are assigned to queues.
2. Events are processed in chronological order, with priority for customers already in the queue.
3. Human servers can take rest periods after completing service.
4. The simulation outputs a timeline of customer actions and server statuses.
