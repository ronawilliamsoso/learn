package com.example.demo.utils;


public class ImplimentInterfaceWithLambdaInNewJava{

  interface Function{
    int oper(int a,int b);
  }

  interface Say{

    void print(String msg);
  }

  public static void main(String args[]){
    Function add = (int x,int y) -> x + y;
    System.out.println(add.oper(1, 2));

    Function multi = (int x,int y) -> x * y;
    System.out.println(multi.oper(4,5));

    Say say = msg -> System.out.println("saying  " + msg);
    say.print("nerd");

    String accessable = "Hello! "; //accessable  不能再被修改，否则报错，可直接定义为 final
    Say accessFinalsay = msg -> System.out.println("saying  " + accessable + msg);
    accessFinalsay.print( " nerd");
  }
}
