package hw4;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * The <code>Simulator</code> class implements an ArrayList of <code>Router</code>
 * objects.
 *
 *
 * @author Shashwat Panigrahi
 *    e-mail: shashwat.panigrahi@stonybrook.edu
 *    Stony Brook ID: 114848893
 **/

public class Simulator {
    static final int MAX_PACKETS = 3;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    Router dispatcher;
    ArrayList<Router> routers;
    int totalServiceTime;
    int totalPacketsArrived;
    int packetsDropped;
    double arrivalProb;
    int numIntRouters;
    int maxBufferSize;
    int minPacketSize;
    int maxPacketSize;
    int bandWidth;
    int duration;


    /** Makes an instance of Simulator
     *
     */
    public Simulator() {
        routers = new ArrayList<Router>();
        dispatcher = new Router();
        dispatcher.setPktCapacity(MAX_PACKETS);
    }

    /** Runs a simulation object using all its fields from user input and
     *  returns a double that represents the average time= the time it takes in total/the num of packages sent
     *
     * @return
     *      a double that represents the average time= the time it takes in total/the num of packages sent
     */
    public double simulate(){
        Double average = 0.0;
        ArrayList<Integer> intRouterIndexQueue = new ArrayList<Integer>();

        for(int simulTime = 1; simulTime <= this.duration; simulTime++){

            System.out.println("Time: " + simulTime);

            //First: Create packet and push it to dispatcher for each time unit
            for(int dispatchCount = 0; dispatchCount<MAX_PACKETS; dispatchCount++){
                if(Math.random() < arrivalProb){
                    Packet packet = new Packet();
                    int size = this.randInt(this.minPacketSize, this.maxPacketSize);
                    int timeToDest = size/100;
                    Packet.packetCount++;
                    packet.setId(Packet.packetCount);
                    packet.setPacketSize(size);
                    packet.setTimeArrive(simulTime);
                    packet.setTimeToDest(timeToDest);
                    dispatcher.enqueue(packet);
                    System.out.println("Packet " + Packet.packetCount + " arrives at dispatcher with size "+ size);
                }
            }
            if(dispatcher.isEmpty()){
                System.out.println("No packets arrived");
            }

            // Second: Decrement the previous intermediate router's first packet's time to destination
            decrementTimeToDest();



            //Third: Send packets from dispatcher to intermediate routers
            while(!dispatcher.isEmpty()){
                Packet packet = dispatcher.dequeue();
                int routerIndex = Router.sendPacketTo(this.routers);
                if(routerIndex == -1){
                    System.out.println("Network is congested. Packet "+Packet.packetCount+" is dropped.");
                    this.packetsDropped++;
                    break;
                } else{
                    routers.get(routerIndex).enqueue(packet);
                    System.out.println("Packet "+packet.getId()+" sent to Router "+(routerIndex+1)+".");
                }
            }

            //Fourth: Send packets from intermediate routers to destination
            /*for(int index =0 ; index< routers.size(); index++){
                Router router = routers.get(index);
                Packet packet = router.peek();
                if(packet != null && packet.getTimeToDest() == 0 && pktSendToDest<bandWidth){
                    pktSendToDest++;
                    router.dequeue();
                    System.out.println("Packet "+packet.getId()+" has successfully reached its destination: +"+(simulTime - packet.getTimeArrive()));
                    totalPacketsArrived++;
                    totalServiceTime = totalServiceTime + (simulTime-packet.getTimeArrive());
                }
            }*/
            int pktSendToDest = 0;
            for(int index = 0; index< routers.size(); index++){
                Packet packet = routers.get(index).peek();
                if(packet != null && packet.getTimeToDest() == 0){
                    intRouterIndexQueue.add(index);
                }
            }
            Iterator<Integer> itr = intRouterIndexQueue.iterator();
            while(itr.hasNext()){
                Integer index = itr.next();
                Router router = routers.get(index);
                Packet packet = router.peek();
                if(pktSendToDest<bandWidth){
                    pktSendToDest++;
                    router.dequeue();
                    System.out.println("Packet "+packet.getId()+" has successfully reached its destination: +"+(simulTime - packet.getTimeArrive()));
                    totalPacketsArrived++;
                    totalServiceTime = totalServiceTime + (simulTime-packet.getTimeArrive());
                    itr.remove();
                }

            }
            System.out.println(this.toString());
            System.out.println();

        }

        average = (double)this.totalServiceTime/(double)this.totalPacketsArrived;
        return average;
    }

    /** Decrements the "time to destination" variable of the packets in the head of the intermediate routers
     *
     */
    private void decrementTimeToDest() {
        for(int eachRouter = 0; eachRouter<this.routers.size(); eachRouter++){
            Router router = routers.get(eachRouter);
            Packet packet = router.peek();
            if(packet != null && packet.getTimeToDest()>0){
                packet.setTimeToDest(packet.getTimeToDest()-1);
            }
        }
    }

    /** Takes two inputs and returns a random integer in the range between the input values, inclusive of the minimum
     *  and exclusive of the maximum
     *
     * @param minVal
     * @param maxVal
     * @return
     *      A random integer in the range between the input values, inclusive of the minimum
     *      and exclusive of the maximum
     */
    private int randInt(int minVal, int maxVal){
        if (maxVal <= minVal){
            return -1;
        }
        int rand =  (int) (minVal + (maxVal-minVal)*Math.random());
        return rand;
    }

    /** Returns a proper string representation of the simulation object
     *
     * @return
     *      A proper string representation of the simulation object
     */
    @Override
    public String toString() {
        String simulatorStr="";
        for(int i = 0; i<routers.size(); i++){
            simulatorStr = simulatorStr + "R"+(i+1)+": "+routers.get(i).toString()+"\n";
        }
        return simulatorStr;
    }

    /**
     * Gets user input and invokes all other methods
     * @param args
     */
    public static void main(String[] args){
        boolean isDone = false;
        while (!isDone) {
            try {
                Simulator simulator = new Simulator();
                Scanner sc = new Scanner(System.in);
                System.out.println("Starting simulator...");
                System.out.print("Enter the number of Intermediate routers:");
                simulator.numIntRouters = sc.nextInt();
                System.out.print("Enter the arrival probability of a packet:");
                Double prob = sc.nextDouble();
                if(prob < 0 || prob > 1){
                    throw new Exception();
                }
                simulator.arrivalProb = prob;
                System.out.print("Enter the maximum buffer size of a router:");
                simulator.maxBufferSize = sc.nextInt();
                System.out.print("Enter the minimum size of a packet:");
                simulator.minPacketSize = sc.nextInt();
                System.out.print("Enter the maximum size of a packet:");
                simulator.maxPacketSize = sc.nextInt();

                System.out.print("Enter the bandwidth size:");
                simulator.bandWidth = sc.nextInt();
                System.out.print("Enter the simulation duration:");
                simulator.duration = sc.nextInt();
                for(int i = 0; i < simulator.numIntRouters; i++){
                    Router intermediateRouter = new Router();
                    intermediateRouter.setPktCapacity(simulator.maxBufferSize);
                    simulator.routers.add(intermediateRouter);
                }

                Double average = simulator.simulate();


                System.out.println("Simulation ending...\n" +
                        "Total service time: "+ simulator.totalServiceTime+"\n" +
                        "Total packets served: "+simulator.totalPacketsArrived+"\n" +
                        "Average service time per packet: "+ df.format(average)+"\n" +
                        "Total packets dropped: "+simulator.packetsDropped);
                System.out.println();
                System.out.print("Do you want to run another simulation:(y/n):");
                String option = sc.next().trim();
                isDone =(option.equalsIgnoreCase("y")? false:true);
                Packet.packetCount = 0;
            } catch (Exception e) {
                System.out.println("Error Occurred. Try Again.");
            }
        }
    }




}
