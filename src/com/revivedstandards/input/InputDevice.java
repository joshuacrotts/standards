package com.revivedstandards.input;

public interface InputDevice {
  public static final int COMMAND_MASK = 254;
  
  public static final int STATE_MASK = 1;
  
  public static final int MOUSE_MASK = -2147483648;
  
  public static final int KEYBOARD_MASK = 0;
  
  public static final int STATE = 1;
  
  public static final int COMMAND = 2;
  
  public static final int BOTH = 0;
  
  public static final int UNDEFINED = -1;
  
  public static final int TOGGLE = 1;
  
  public static final int CONTINUOUS = 0;
  
  byte[] getBytes();
  
  int getDeviceMask();
  
  void set(int paramInt1, int paramInt2);
  
  int get(int paramInt1, int paramInt2);
  
  boolean stateOn(int paramInt);
}
