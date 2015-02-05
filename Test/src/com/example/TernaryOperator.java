package com.example;


import java.util.Scanner;


public final class TernaryOperator {
  public static void main(final String[] args) {
    final Scanner scan = new Scanner(System.in);
    
    System.out.printf("Please enter a value : ");
    final int val = scan.nextInt();
    final boolean bool = (val == 0) ? false : true;
    
    System.out.printf("The value entered was : %b\n", bool);
  }
}
