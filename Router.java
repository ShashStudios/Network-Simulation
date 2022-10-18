package Homework4;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The <code>Router</code> class implements an ArrayList of <code>Packet</code>
 * objects.
 *
 *
 * @author Shashwat Panigrahi
 *    e-mail: shashwat.panigrahi@stonybrook.edu
 *    Stony Brook ID: 114848893
 **/
public class Router {
    ArrayList<Packet> packets;
    private int size;

    private int pktCapacity;

    /** Makes an instance of Router
     *
     */
    public Router() {
        packets  = new ArrayList<Packet>();
    }

    /** Returns the size of arrayList of packets within in the router object
     *
     * @return
     *      size of arrayList of packets within in the router object
     */
    public int getSize() {
        return size;
    }

    /** Sets the size of the arrayList of packets in the router object
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /** Returns the capacity of the ArrayList of packets in the router
     *
     * @return
     *      capacity of the ArrayList of packets in the router
     */
    public int getPktCapacity() {
        return pktCapacity;
    }

    /** Sets the capacity of the ArrayList of
     *
     * @param maxSize
     */
    public void setPktCapacity(int maxSize) {
        this.pktCapacity = maxSize;
    }

    /** Adds the given packet to the Arraylist of packets in a queue format:
     *  first in first out
     *
     * @param p
     */
    public void enqueue(Packet p){
        if(this.size < pktCapacity){
            packets.add(p);
        }
    }

    /** Removes the head of the queue(First in First Out)
     *
     * @return
     *      The packet object in the head of the queue
     */
    public Packet dequeue(){
        Packet p= null;
        if(!(packets.isEmpty())){
            p = packets.remove(0);
            return p;
        }
        return p;
    }

    /** Finds and returns a copy of the head of the ArrayList
     *
     * @return
     *      A copy of the head of the ArrayList
     */
    public Packet peek() {
        Packet packet = null;
        if(!packets.isEmpty()){
            packet = packets.get(0);
        }
        return packet;
    }

    public int size() {
        return packets.size();
    }

    /** Returns a boolean showing if the ArrayLost is empty
     *  true if it is empty, false otherwise
     *
     * @return
     *      boolean showing if the ArrayLost is empty
     *      true if it is empty, false otherwise
     */
    public boolean isEmpty() {
        if(packets.size() == 0){
            return true;
        }
        return false;
    }

    /** Returns a proper string representation of the router object
     *
     * @return
     *      A proper string representation of the router object
     */
    @Override
    public String toString() {
        String str = "{";
        for(Packet packet:packets){
            str = str + packet.toString();
        }
        str= str + "}";
        return str;
    }

    /** Scans all routers and returns an index in the ArrayList of routers that
     *  points to the router with the least number of packages.
     *  if router is full returns -1
     *
     * @param routers
     * @return
     *      an index in the ArrayList of routers that
     *      points to the router with the least number of packages.
     *      if router is full returns -1
     */
    public static int sendPacketTo(ArrayList<Router> routers){
        int entryRouterIndex= -1;
        int min = routers.get(0).pktCapacity;
        for(int i = 0; i < routers.size(); i++){
            Router eachRouter = (Router) routers.get(i);
            if(eachRouter.packets.size() < min){
                entryRouterIndex = i;
                min = eachRouter.packets.size();
            }
        }
        return entryRouterIndex;
    }

}
