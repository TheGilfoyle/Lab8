package org.example.managers;

import org.example.Main;
import org.example.exceptions.NoSuchCommandException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleManager {
    private String[] tokens;
    public Scanner sc = Main.sc;

//    public void parse(){
//        String income;
//        try {
////            AllManagers.getManagers().getCommandManager().callCommand("help");
//            while (true) {
//                income = sc.nextLine().trim().toLowerCase();
//                tokens = income.split(" ");
//                try {
//                    if (cmd.getCommands().get(tokens[0]).isComposite().equalsIgnoreCase("NO")) {
//                        if (!tokens[tokens.length-1].isEmpty() && tokens.length>1){
//                            throw new IllegalArgumentException();
//                        }
//                    }
//                    cmd.callCommand(tokens[0]);
//                } catch (NoSuchCommandException ex) {
//                    ex.getMessage();
//                }catch (NullPointerException ex){
//                    System.out.println("Команда с таким названием не найдена");
//                }catch (IllegalArgumentException e){
//                    System.out.println("Эта команда не подразумевает наличие параметра");
//                }
//            }
//        } catch (NoSuchElementException e) {
//            System.out.println("А команда exit для тебя шутка?");
//        } catch (NoSuchCommandException ex){
//            ex.getMessage();
//        }
//    }


    public String[] getTokens() {
        return tokens;
    }

    public String getToken(int i) {
        return tokens[i];
    }

    public void setTokens(String[] tokens) {
        this.tokens = tokens;
    }
}
