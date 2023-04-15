package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.ItemEnum;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Board;

import java.io.PrintStream;
import java.util.Scanner;

public class CliView implements Runnable {
    private Scanner scanner;
    private PrintStream outputStream;
    private boolean done;
    private Controller controllerCli;

//it is temporary; later I will create an interface similar to the observers;

    public CliView(int numPlayers){
        scanner = new Scanner(System.in);
        outputStream = new PrintStream(System.out);
        controllerCli = new Controller(numPlayers);
    }

    public void match(){
        while (true) {
            outputStream.println("How many tiles do you want to pick?");
            int i = scanner.nextInt();
            //control the space in the bookshelf; ...
            outputStream.println("please enter the coordinates of the tiles you want to remove in the order you want to put them in the bookshelf (from bottom to top): ");
            String s = scanner.next();
            try {
                String[] inputs = s.split(",");
//                handleMove(Integer.parseInt(inputs[0]), Integer.parseInt(inputs[1]));
                break;
            }catch(NumberFormatException e){
                outputStream.println("Please provide integer values as coordinates");
            }
        }
    }

/*    private ItemEnum[][] getMatrixBoard(){

    }*/

    @Override
    public void run() {
        while(!done){
            match();
        }
    }
}