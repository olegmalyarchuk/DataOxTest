package com.company.model;

public class Elevator {
  private static final int CAPACITY = 5;
  private int[] passengers = new int[CAPACITY];

  private int maxFloor;
  private int currentFloor = 1;
  private boolean moveUp = true;

  public Elevator(int maxfloor) {
    this.maxFloor = maxfloor;
  }

  /** This method moves elevator in the following way */
  public void move() {
    startOppositeDirection();
    int nextFloor;
    if (!isFull()) {
      nextFloor = moveUp ? currentFloor + 1 : currentFloor - 1;
    } else nextFloor = getClosestFloorWithFullCapacity();
    currentFloor = nextFloor;
  }

  /** This method adds passenger */
  public void addPassenger(int passengerFloor) {
    for (int i = 0; i < CAPACITY; i++) {
      if (passengers[i] == 0) {
        passengers[i] = passengerFloor;
        return;
      }
    }
  }

  /** This method removes passenger */
  public int removePassengers() {
    int countRemoved = 0;
    for (int i = 0; i < CAPACITY; i++) {
      if (passengers[i] == currentFloor) {
        passengers[i] = 0;
        countRemoved++;
      }
    }
    return countRemoved;
  }

  /** This decides in which way moves */
  public int getClosestFloorWithFullCapacity() {
    int result = 0;
    if (moveUp) {
      int min = maxFloor + 1;
      for (int i = 0; i < CAPACITY; i++) {
        if (passengers[i] != 0 && passengers[i] < min) min = passengers[i];
      }
      if (min != maxFloor + 1) result = min;
      else result = 0;
    } else {
      int max = 0;
      for (int i = 0; i < CAPACITY; i++) {
        if (passengers[i] > max) max = passengers[i];
      }
      result = max;
    }
    if (result == 0) throw new IllegalStateException("Failed to get the closest floor");
    return result;
  }

  /** This method checks whether elevator is full */
  public boolean isFull() {
    boolean isFull = true;
    for (int i = 0; i < CAPACITY; i++) {
      if (passengers[i] == 0) {
        isFull = false;
        break;
      }
    }
    return isFull;
  }

  /** This method checks whether elevator is empty */
  public boolean isEmpty() {
    for (int i = 0; i < CAPACITY; i++) {
      if (passengers[i] != 0) {
        return false;
      }
    }
    return true;
  }

  public int getCurrentFloor() {
    return currentFloor;
  }

  public void setCurrentFloor(int currentFloor) {
    this.currentFloor = currentFloor;
  }

  public boolean isMoveUp() {
    return moveUp;
  }

  public void setMoveUp(boolean moveUp) {
    this.moveUp = moveUp;
  }

  /** This method change direction in opposite if we reached last or first floot */
  public void startOppositeDirection() {
    if (currentFloor == 1) moveUp = true;
    else moveUp = false;
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < CAPACITY; i++) {
      if (i != CAPACITY - 1) {
        if (passengers[i] != 0) result.append(passengers[i] + " ");
      } else result.append(passengers[i]);
    }
    return result.toString();
  }
}
