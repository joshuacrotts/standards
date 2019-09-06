package com.revivedstandards.input;

import com.revivedstandards.commands.Command;



/**
 * Abstract class InputDevice - write a description of the class here
 * 
 * @author (Andrew Matzureff)
 * @version (10/23/2018)
 */
public abstract class InputDevice
{
    private final BitArray keys;
    private final String name;
    private final int id;
    private short alert;
    
    public InputDevice(String name, int size){
        keys = new BitArray(size);
        alert = 0;
        id = Command.register(this);
        this.name = new String(name);
    }
    public boolean alerted(){
        return alert != 0;
    }
    public int alerts(){
        return alert & 0xffff;
    }
    protected void alert(int k, boolean v){
        if(keys.get(k) != v){
            if(v)
                alert++;
            else
                alert--;
            keys.set(k, v);
       }
        return;
    }
    public String toString(){
        return "InputDevice[" + id + "]: " + (name == null? "Unknown": name);
    }
    public boolean get(int key)
    {
        if(key >= 0 && key < keys.size()){
            return keys.get(key);
        }
        return false;
    }
    protected void set(int k, boolean v)
    {
        if(k >= 0 && k < keys.size()){
            alert(k, v);
        }
    }
}