package com.company;

import com.company.model.Building;
import java.util.Random;

public class Main {

  public static void main(String[] args) {
    int countFloors = new Random().nextInt(16) + 5;
    Building building = new Building(countFloors);
    System.out.println("Initial elevator");
    System.out.println(building);
    System.out.println("Launching the elevator");
    building.runElevator();
  }
}
