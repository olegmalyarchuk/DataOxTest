package com.company.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Building {
  private int countFloors;
  private Elevator elevator;
  private ArrayList<Integer>[] passengersAtFloor;
  private Random rand = new Random();

  public Building(int countFloors) {
    this.countFloors = countFloors;
    elevator = new Elevator(countFloors);
    passengersAtFloor = new ArrayList[countFloors];
    fillRandomPassengers();
  }

  /** This method shows how the elevator work */
  public void runElevator() {
    int step = 0;
    while (true) {
      step++;
      int removedPassengers = removePassengersFromLift();
      if (elevator.isEmpty()) elevator.setMoveUp(getDirectionByCountPassengers());
      int addedPassengers = addPassengerToElevator();
      if (removedPassengers == 0 && addedPassengers == 0) step--;
      else {
        createRandomPassengers(removedPassengers);
        printInfo(step, removedPassengers, addedPassengers);
      }
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      elevator.move();
    }
  }

  /** This method adds passenger to the elevator */
  private int addPassengerToElevator() {
    elevator.startOppositeDirection();

    ArrayList<Integer> excludedPassengers = new ArrayList<>();
    int currentFloor = elevator.getCurrentFloor();
    for (int i = 0; i < passengersAtFloor[currentFloor - 1].size() && !elevator.isFull(); i++) {
      int currentPassenger = passengersAtFloor[currentFloor - 1].get(i);
      if (elevator.isMoveUp()) {
        if (currentPassenger > currentFloor) {
          excludedPassengers.add(i);
          elevator.addPassenger(currentPassenger);
        }
      } else {
        if (currentPassenger < currentFloor) {
          excludedPassengers.add(i);
          elevator.addPassenger(currentPassenger);
        }
      }
    }
    for (int i = excludedPassengers.size() - 1; i >= 0; i--) {
      passengersAtFloor[currentFloor - 1].remove(i);
    }

    return excludedPassengers.size();
  }

  /** This method removes passenger to the elevator */
  private int removePassengersFromLift() {
    return elevator.removePassengers();
  }

  /** This method fills elevator with random passengers */
  private void fillRandomPassengers() {
    for (int i = 0; i < countFloors; i++) {
      passengersAtFloor[i] = fillFloor(i + 1);
    }
  }

  /** This method fills floor */
  private ArrayList<Integer> fillFloor(int currentFloor) {
    ArrayList<Integer> floor = new ArrayList<Integer>();
    int countOfPassengers = rand.nextInt(11); // 0...10
    for (int j = 1; j < countOfPassengers; j++) {
      floor.add(createRandomPassenger(currentFloor));
    }
    return floor;
  }

  /** This method creates passenger to the elevator */
  private int createRandomPassenger(int currentFloor) {
    int destinationFloor = currentFloor;
    while (destinationFloor == currentFloor) destinationFloor = rand.nextInt(countFloors) + 1;

    return destinationFloor;
  }

  /** This method creates random passengers to the elevator */
  private void createRandomPassengers(int count) {
    for (int j = 0; j < count; j++)
      passengersAtFloor[elevator.getCurrentFloor() - 1].add(
          createRandomPassenger(elevator.getCurrentFloor()));
  }

  /**
   * This method gets direction by count of passengers if peoples who want to go up less then
   * another peoples set direction false(down) true otherwise
   */
  private boolean getDirectionByCountPassengers() {
    int currentFloor = elevator.getCurrentFloor();
    if (currentFloor == 1) return true;
    else if (countFloors == countFloors) return false;
    else {
      int countToUP = 0;
      for (int i = 0; i < passengersAtFloor[currentFloor - 1].size(); i++)
        if (passengersAtFloor[currentFloor - 1].get(i) > elevator.getCurrentFloor()) countToUP++;
      return passengersAtFloor[currentFloor - 1].size() - countToUP < countToUP;
    }
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (int i = countFloors - 1; i >= 0; i--) {
      if (elevator.getCurrentFloor() != i + 1)
        result.append("" + (i + 1) + " floor: " + passengersAtFloor[i].toString() + "\n");
      else
        result.append(
            ""
                + (i + 1)
                + " floor: "
                + passengersAtFloor[i].toString()
                + " Lift:{"
                + elevator
                + "}\n");
    }
    return result.toString();
  }

  /** This method prints info v if elevator moves down ^ otherwise */
  private void printInfo(int step, int removedPassengers, int addedPassengers) {
    if (elevator.isMoveUp()) System.out.println("^^^^^^^^^^^^^" + "Step " + step + "^^^^^^^^^^^^");
    else System.out.println("vvvvvvvvvvvv" + "Step " + step + "vvvvvvvvvvv");
    System.out.println(toString());
    System.out.println(" <Left>  " + " <Entered>");
    System.out.println("    " + removedPassengers + "         " + addedPassengers);
  }
}
