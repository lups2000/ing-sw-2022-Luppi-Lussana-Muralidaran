package it.polimi.ingsw.Utils;

import it.polimi.ingsw.Controller.MainController;
import it.polimi.ingsw.network.server.Server;

import java.io.*;
import java.nio.file.Files;

/**
 * This class allows to store and to restore a single match. (Persistence)
 */
public class StoreGame implements Serializable {

    private static final long serialVersionUID= 2832837267362746721L;
    private MainController mainController;

    public StoreGame(MainController mainController){
        this.mainController=mainController;
    }

    public MainController getMainController() {return mainController;}

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

    public void deleteGame(){
        File fileToDelete=new File("savedGame.bin");
        try{
            Files.deleteIfExists(fileToDelete.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            Server.LOGGER.info("File deleted!");
        }
    }

    private String getJarDirectory(){
        File pathJar= new File(Server.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        return pathJar.getParentFile().getAbsolutePath();
    }
}
