package com.revivedstandards.commands;

import com.revivedstandards.input.InputDevice;
import java.util.LinkedList;
/**
 * This framework enables the user to define custom game actions encapsulated by Commands and bind arbitrary controls to them.
 * 
 * @author (Andrew Matzureff) 
 * @version (10/3/2016)
 */
public abstract class Command//And so we meet again Larru >:}
{   
	//NOOOOOOO
    public static final InputDevice[] DEVICES = new InputDevice[8];//Registered Input Devices
    public static final LinkedList<Command> COMMANDS = new LinkedList<Command>();//Added Commands
    //ff ff ff ff ff ff ff ff
    public static final long KEY_MASK = 0xffffL;//2 byte key range
    public static final long DEVICE_MASK = 0x7L;//3 bit device range
    public static final long FOCUS_MASK = 0x7L;//3 bit group range
    
    public static final int KEY_0_OFF = 0;//key bind slot 0 shift offset
    public static final int DID_0_OFF = 48;//device id bind slot 0 shift offset
    public static final int ON_0_OFF = 57;//activity state bind slot 0 shift offset
    
    public static final int KEY_1_OFF = 16;
    public static final int DID_1_OFF = 51;
    public static final int ON_1_OFF = 58;
    
    public static final int KEY_2_OFF = 32;
    public static final int DID_2_OFF = 54;
    public static final int ON_2_OFF = 59;

    public static final int FOCUS_OFF = 60;//focus group ids offset
    public static final int PAUSE_OFF = 63;//overall activity state offset
    public static final int DK_OFF = 16;//device mask in key offset
    
    public static final long FOCUS_GROUP_0 = 0;//toggle-able activity groups
    public static final long FOCUS_GROUP_1 = 1;
    public static final long FOCUS_GROUP_2 = 2;
    public static final long FOCUS_GROUP_3 = 3;
    public static final long FOCUS_GROUP_4 = 4;
    public static final long FOCUS_GROUP_5 = 5;
    public static final long FOCUS_GROUP_6 = 6;
    public static final long FOCUS_GROUP_7 = 7;

    public static int FOCUSED = -1;//'Focus' high constant (on)
    
    protected String name;
    protected long interval = 0;//the strike frequency required to trigger a double click/ tap action
    protected long states = 0;//most recent 64 key states
    protected long keys = 0;//least to most significant: key / buttons, devices, activity states, focus group, overall activity state
    protected byte bits = 0;//current sum of all states (bits on)

    public String getName(){return name;}
    public static InputDevice[] getInputDevices(){return DEVICES;}
    public String toString(){
        StringBuffer str = new StringBuffer(((name == null)? "Unknown": name) + ": ");
        boolean o0 = checkFlags(1, ON_0_OFF, 1);
        boolean o1 = checkFlags(1, ON_1_OFF, 1);
        boolean o2 = checkFlags(1, ON_2_OFF, 1);
        long i0 = getFlags(DID_0_OFF, DEVICE_MASK);
        long i1 = getFlags(DID_1_OFF, DEVICE_MASK);
        long i2 = getFlags(DID_2_OFF, DEVICE_MASK);
        String del = ((o0 && o1) || (o0 && o2) || (o1 && o2))? ", ": "";
        String k0 = (o0? DEVICES[(int)i0] + " (" + (char)getFlags(KEY_0_OFF, KEY_MASK) + ")" + del: "");
        String k1 = (o1? DEVICES[(int)i1] + " (" + (char)getFlags(KEY_1_OFF, KEY_MASK) + ")" + del: "");
        String k2 = (o2? DEVICES[(int)i2] + " (" + (char)getFlags(KEY_2_OFF, KEY_MASK) + ") ": "");
        str.append("Binds <" + k0 + k1 + k2 + "> Group " + getFlags(FOCUS_OFF, FOCUS_MASK) + "; " + (keys < 0));
        str.append(" (" + Long.toBinaryString(keys) + ")");
        return str.toString();
    }
    
    public static void suspend(int groups){//expects any combination of the first 8 powers   of 2
        FOCUSED &= ~groups;
    }
    public static void resume(int groups){//expects any combination of the first 8 powers   of 2
        FOCUSED |= groups;
    }
    public static void setFocused(int groups){//expects any combination of the first 8 powers   of 2
        FOCUSED = groups;
    }
    
    /**
     * 
     */
    public static int register(InputDevice id){
        if(id == null)
            return -1;
        int vacant = -1;
        for(int i = 0; i < DEVICES.length; i++){
            if(DEVICES[i] == id){
                return i;
            }
            else if(DEVICES[i] == null){
                vacant = i;
            }
        }
        if(vacant >= 0)
            DEVICES[vacant] = id;
        return vacant;
    }
    
    public static boolean deregister(InputDevice id){
        if(id == null)//null not permitted
            return false;
        for(int i = 0; i < DEVICES.length; i++){//for each device
            if(DEVICES[i] == id){//if specified device is registered (if so, it will be the only instance)
                for(int j = 0; j < COMMANDS.size(); j++){//for each command
                    COMMANDS.get(j).clear(i);//deregister specified device on current command
                    //COMMANDS.get(j).shift(i);//compensate for device removal index shift
                }
                DEVICES[i] = null;
                return true;
            }
        }
        return false;
    }
    
    public void clear()
    {
        states = 0;
        bits = 0;
        setFlags(0, ON_0_OFF, DEVICE_MASK);
        //keys = -1L ^ (1L << ON_0_OFF | 1L << ON_1_OFF | 1L << ON_2_OFF) & ~(1L << PAUSE_OFF);
    }
    
    private void clear(int id)
    {
        if(checkFlags(id, DID_0_OFF, DEVICE_MASK)){//Device id matches that in question?
            setFlags(0, ON_0_OFF, 1);//Move and set this bind's active flag to false
        }
        if(checkFlags(id, DID_1_OFF, DEVICE_MASK)){
            setFlags(0, ON_1_OFF, 1);
        }
        if(checkFlags(id, DID_2_OFF, DEVICE_MASK)){
            setFlags(0, ON_2_OFF, 1);
        }
        else
            return;

        int k1 = (int)getFlags(KEY_1_OFF, KEY_MASK);
        int k2 = (int)getFlags(KEY_2_OFF, KEY_MASK);
        int d1 = (int)getFlags(DID_1_OFF, DEVICE_MASK);
        int d2 = (int)getFlags(DID_2_OFF, DEVICE_MASK);
        
        switch((int)getFlags(ON_0_OFF, 7)){//shift over the device and key values (see rebinding)
            case 0b010://000 001  011    111
                setFlags(k1, KEY_0_OFF, KEY_MASK);
                setFlags(0b001, ON_0_OFF, 7);
                setFlags(d1, DID_0_OFF, DEVICE_MASK);
                break;
            case 0b100:
                setFlags(k2, KEY_0_OFF, KEY_MASK);
                setFlags(0b001, ON_0_OFF, 7);
                setFlags(d2, DID_0_OFF, DEVICE_MASK);
                break;
            case 0b101:
                setFlags(k2, KEY_1_OFF, KEY_MASK);
                setFlags(0b011, ON_0_OFF, 7);
                setFlags(d2, DID_1_OFF, DEVICE_MASK);
                break;
            case 0b110:
                setFlags(k1, KEY_0_OFF, KEY_MASK);
                setFlags(k2, KEY_1_OFF, KEY_MASK);
                setFlags(0b011, ON_0_OFF, 7);
                setFlags(d1, DID_0_OFF, DEVICE_MASK);
                setFlags(d2, DID_1_OFF, DEVICE_MASK);
                break;
            default:
                break;
        }
        return;
    }

    public boolean remove(Command i){
        return COMMANDS.remove(i);
    }
    
    protected boolean checkFlags(long val, int offset, long mask){
        return (((keys >>> offset) & mask) == val);
    }
    
    protected void setFlags(long val, int offset, long mask){
        keys = (keys & ~(mask << offset)) | (val << offset);
    }
    
    protected long getFlags(int offset, long mask){
        return ((keys >>> offset) & mask);
    }
    
    /**
     * 
     */
    public boolean bind(InputDevice id, int key)//unmasked
    {
        return bind(id, key & (int)KEY_MASK, 0x8000_0000_0000_0000L);//0x1f
    }
    public boolean bind(InputDevice id, int key, long flags)//unmasked device key
    {
        int registered = register(id);
        if(registered >= 0){
            
            switch((int)getFlags(ON_0_OFF, 7)){
            case 0b000:
                setFlags(key, KEY_0_OFF, KEY_MASK);
                setFlags(1, ON_0_OFF, 1);
                setFlags(registered, DID_0_OFF, DEVICE_MASK);
                break;
            case 0b001:
                setFlags(key, KEY_1_OFF, KEY_MASK);
                setFlags(1, ON_1_OFF, 1);
                setFlags(registered, DID_1_OFF, DEVICE_MASK);
                break;
            case 0b011:
                setFlags(key, KEY_2_OFF, KEY_MASK);
                setFlags(1, ON_2_OFF, 1);
                setFlags(registered, DID_2_OFF, DEVICE_MASK);
                break;
            case 0b111:
                int k1 = (int)getFlags(KEY_1_OFF, KEY_MASK);
                int k2 = (int)getFlags(KEY_2_OFF, KEY_MASK);
                setFlags(k1, KEY_0_OFF, KEY_MASK);
                setFlags(k2, KEY_1_OFF, KEY_MASK);
                setFlags(key, KEY_2_OFF, KEY_MASK);

                int d1 = (int)getFlags(DID_1_OFF, DEVICE_MASK);
                int d2 = (int)getFlags(DID_2_OFF, DEVICE_MASK);
                setFlags(d1, DID_0_OFF, DEVICE_MASK);
                setFlags(d2, DID_1_OFF, DEVICE_MASK);
                setFlags(registered, DID_2_OFF, DEVICE_MASK);
                break;
            default:
                return false;
            }
                COMMANDS.add(this);
                keys |= flags;
            return true;
        }
        return false;
    }
    /**
     * 
     */
    protected void pressed(float delta){}
    protected void down(float delta){}
    protected void released(float delta){}
    protected void up(float delta){}
    protected void doubleTapped(float delta){}
    protected void tick(float delta){}
    /**
     * 
     */
    private void setTapInterval(int ticks){
        if((ticks & 63) != ticks)//ticks < 0 || ticks > 63
            return;
        interval = 8L << ticks;
    }
    private void setTapInterval(float timeScale){
        setTapInterval((int)(timeScale * 60));//Remaining 60 bits; ticks, not necessarily 60fps.
    }
    private void processTap(float delta){
        boolean lookingFor = false;
        int gestures = 0;
        long interval = this.interval & ~0xfL;
        for(int i = 4; i != 0; i <<= 1){
            if(i > interval){
                released(delta);
                return;
            }
            boolean press = (states & i) != 0;
            if(lookingFor == press){
                lookingFor = !lookingFor;
                gestures++;
                if(gestures == 3){
                    doubleTapped(delta);
                    return;
                }
            }
        }
    }
    protected void execute(float delta)
    {
        int input0 = (int)getFlags(DID_0_OFF, DEVICE_MASK);
        int input1 = (int)getFlags(DID_1_OFF, DEVICE_MASK);
        int input2 = (int)getFlags(DID_2_OFF, DEVICE_MASK);
        int key = 0;
        int doubleTapEnabled = 0x1;
        if(DEVICES[input0] != null && checkFlags(1, ON_0_OFF, 1))
            key |= DEVICES[input0].get((int)getFlags(KEY_0_OFF, KEY_MASK))? 1: 0;
        if(DEVICES[input1] != null && checkFlags(1, ON_1_OFF, 1))
            key |= DEVICES[input1].get((int)getFlags(KEY_1_OFF, KEY_MASK))? 1: 0;
        if(DEVICES[input2] != null && checkFlags(1, ON_2_OFF, 1))
            key |= DEVICES[input2].get((int)getFlags(KEY_2_OFF, KEY_MASK))? 1: 0;
        bits = (byte)((bits & 0xff) - (int)(states >>> 63) + key);//subtract leftmost bit being shifted off, add most recent bit
        states = (states << 1) | key;//1111 1110; advance the key/ button strike sequence and append the most recent button state onto the bit string
        
        switch((int)states & 0b11)
        {
            case 0b01:
                pressed(delta);
                break;
            case 0b10:
                if((interval & doubleTapEnabled) != 0)
                    processTap(delta);
                else
                    released(delta);
                break;
            case 0b11:
                down(delta);
                break;
            default:
                up(delta);
                break;
        }
        tick(delta);
    }
    /**
     * 
     */
    
    public static void update(float delta){
        java.util.ListIterator<Command> i = COMMANDS.listIterator(0);
        Command c;
        while(i.hasNext())//this loop executes every Command by its corresponding action state
        {
            c = i.next();
            c.execute(delta);
        }
    }
}