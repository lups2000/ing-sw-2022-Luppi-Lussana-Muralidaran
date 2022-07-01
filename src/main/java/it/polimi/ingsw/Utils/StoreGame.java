package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.network.server.Server;

import java.io.*;
import java.nio.file.Files;

/**
 * This class allows to store and to restore a single match. (Persistence advanced functionality)
 */
public class StoreGame implements Serializable {

    @Serial
    private static final long serialVersionUID= 2832837267362746721L;
    private MainController mainController;

    public StoreGame(MainController mainController){
        this.mainController=mainController;
    }

    public MainController getMainController() {return mainController;}


    /**
     * Method to save on a file all the details of the current match, in order to recover it eventually
     * It is invoked after the first round for all the players is completed, so only if some changes were committed 
     * 
     * @param mainController the main controller of the current match that will be saved on file, because it contains
     *                       all the references needed to store (and eventually after) the game
     */
    public void saveMatch(MainController mainController){

        this.mainController=mainController;
        try(FileOutputStream fileOutputStream = new FileOutputStream("savedGame.bin")){

            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(mainController);
            Server.LOGGER.info("Match Saved on disk.");
        }
        catch (IOException e){
            Server.LOGGER.severe(e.getMessage());
        }
    }

    /**
     * If there is already a previous saved match this method recoveries it
     *
     * @return the main controller of the previous saved match
     */
    public MainController getPreviousMatch(){

        try(FileInputStream fileInputStream =new FileInputStream("savedGame.bin")){

            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            mainController=(MainController) objectInputStream.readObject();

            return mainController;
        }
        catch (IOException | ClassNotFoundException e) {
            Server.LOGGER.severe("File not Found.");
        }
        return null;
    }


    /**
     * At the end of the game the match saved on file is deleted with this method
     */
    public void deleteGame(){

        File fileToDelete=new File("savedGame.bin");
        try{
            Files.deleteIfExists(fileToDelete.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            Server.LOGGER.info("File deleted!");
        }
    }

}
