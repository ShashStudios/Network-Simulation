package Homework4;

/**
 * @author Shashwat Panigrahi
 *    e-mail: shashwat.panigrahi@stonybrook.edu
 *    Stony Brook ID: 114848893
 **/
public class Packet {
    static int packetCount = 0;
    int id;
    int packetSize;
    int timeArrive;
    int timeToDest;

    /** Makes an instance of Packet
     *
     */
    public Packet() {
    }

    /** Returns the id of the packet object
     *
     * @return
     *      id of the packet object
     */
    public int getId() {
        return id;
    }

    /** Sets the id of the packet that calls this method to the given parameter
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Returns the size of the packet object
     *
     * @return
     *      size of the packet object
     */
    public int getPacketSize() {
        return packetSize;
    }

    /** Sets the size of the packet that calls this method to the given parameter
     *
     * @param packetSize
     */
    public void setPacketSize(int packetSize) {
        this.packetSize = packetSize;
    }

    /** Returns the arrival time of the packet object
     *
     * @return
     *      arrival time of the packet object
     */
    public int getTimeArrive() {
        return timeArrive;
    }

    /** Sets the arrival time of the packet that calls this method to the given parameter
     *
     * @param timeArrive
     */
    public void setTimeArrive(int timeArrive) {
        this.timeArrive = timeArrive;
    }

    /** Returns the time to destination of the packet object
     *
     * @return
     *      time to destination of the packet object
     */
    public int getTimeToDest() {
        return timeToDest;
    }

    /** Sets the time to destination of the packet that calls this method to the given parameter
     *
     * @param timeToDest
     */
    public void setTimeToDest(int timeToDest) {
        this.timeToDest = timeToDest;
    }

    /** Returns a proper string representation of a packet object
     *
     * @return
     *      string representation of a packet object
     */
    @Override
    public String toString() {
        String packet = "["+id+", "+timeArrive+", "+timeToDest+"]";
        return packet;
    }
}
