package org.example;

import lombok.Data;

import java.util.Scanner;

@Data
public class Game implements Runnable{
    private boolean whiteTurn=true;
    private char [][] board={
            {'R','N','B','Q','K','B','N','R'},
            {'P','P','P','P','P','P','P','P'},
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'},
            {'p','p','p','p','p','p','p','p'},
            {'r','n','b','q','k','b','n','r'}
    };

    @Override
    public void run() {
       printBoard();
        Scanner src =  new Scanner(System.in);
        String c = src.next();
        int num = src.nextInt();
        String cExpect = src.next();
        int numExpect= src.nextInt();
       checkBoard(c.charAt(0),num,cExpect.charAt(0),numExpect);
       printBoard();
    }
    private void checkBoard(char c, int num,char cExpect,int numExpect){
        char aChar = getChar(c);
        int anInt = getInt(num);
        char figure = board[anInt][aChar - '0'];
         board[anInt][aChar - '0']='-';
        char aChar1 = getChar(cExpect);
        int anInt1 = getInt(numExpect);
        board[anInt1][aChar1-'0']=figure;
    }
    private void printBoard(){
        int a =8;
        for (int i = 0; i < board.length; i++) {
            System.out.print(a+"    ");
            a--;
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.print("     ");
        for (char c ='A';c<'I';c++){
            System.out.print(c+" ");
        }
    }
    private int getIndexLetter(char letter){
        switch (letter){
            case 'A':
                letter='0';
                break;
            case 'B':
                letter='1';
                break;
            case 'C':
                letter='2';
                break;
            case 'D':
                letter='3';
                break;
            case 'E':
                letter='4';
                break;
            case 'F':
                letter='5';
                break;
            case 'G':
                letter='6';
                break;
            case 'H':
                letter='7';
                break;
            default:
                System.out.println("bad date");
                break;
        }return letter-'0';
    }
    private int getIndexNumber(int num){
        switch (num){
            case 8:
                num=0;
                break;
            case 7:
                num=1;
                break;
            case 6:
                num=2;
                break;
            case 5:
                num=3;
                break;
            case 4:
                num=4;
                break;
            case 3:
                num=5;
                break;
            case 2:
                num=6;
                break;
            case 1:
                num=7;
                break;
            default:
                System.out.println("bad date");
                break;
        }return num;
    }
}
