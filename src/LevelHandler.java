
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

public class LevelHandler {
    private static final String LEVEL_FILE = "level.txt";
    
    
    private int currentLevel;
    private boolean hasChickenFeed = false;
    private boolean hasCowFeed = false;
    private Label feedLabel;
    private Boolean f1 = false;
    private Boolean f2 = false;
    //private final MediaPlayer beep1Player;
    
    //private final MediaPlayer beep2Player;
   // private final MediaPlayer beep3Player;

    public LevelHandler(Label feedLabel) {
        this.currentLevel = loadLevel();
        this.feedLabel = feedLabel;
        feedLabel.setStyle("-fx-text-fill: yellow; -fx-font: bold 30px 'Super Funky';-fx-font-size:20px; -fx-padding:0 0 0 20px; ");
        // Media beep1Media = new Media(getClass().getResource("LVL.mp3").toExternalForm());
        // beep1Player = new MediaPlayer(beep1Media);

        // Media beep2Media = new Media(getClass().getResource("FED.mp3").toExternalForm());
        // beep2Player = new MediaPlayer(beep2Media);
       
    }

    public Label getFeedLabel() {
        return feedLabel;
    }

    public void setCurrentLevel(int level) {
        currentLevel = level;
        saveLevel(); // Save the updated level to the file
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public static void resetTimer() {
        App.resetTimer();
    }
    private void playSound(String filePath) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    public void handleKeyPress(Character character, javafx.scene.input.KeyEvent event) {
       
        if (event.getCode() == KeyCode.ENTER) {
            if (character.getCurrentRow() == 2 && character.getCurrentCol() == 7) {
                hasChickenFeed = true;
                                //    hasCowFeed = false;

                feedLabel.setText("Chicken Feed Picked!");
                
            } else if (character.getCurrentRow() == 7 && character.getCurrentCol() == 5) {
                    feedLabel.setText("Cow Feed Picked!");
                    hasCowFeed = true;
                 //   hasChickenFeed = false;
            }
            else if ( (character.getCurrentRow() == 5 && character.getCurrentCol() == 3) || (character.getCurrentRow() == 3 && character.getCurrentCol() == 2) )
            {
                feedLabel.setText("");
                
            }
            

            

          

            if ((character.getCurrentRow() == 5 && character.getCurrentCol() == 3) || (character.getCurrentRow() == 5 && character.getCurrentCol() == 2) || (character.getCurrentRow() == 6 && character.getCurrentCol() == 3) || (character.getCurrentRow() == 6 && character.getCurrentCol() == 2)) {
               
                

                feedLabel.setText("");
                hasCowFeed = false;
               // System.out.println(f1);
                if (hasChickenFeed==true) {
                     f1 = true;
                    playSound("src/FED.wav"); 
                 //     System.out.println("hello1");
                      //beep2Player.play();
                     
                       hasChickenFeed = false;
                    if(f2){
                    //      System.out.println("hell2");
                    currentLevel++;
                    f2=false; 
                    f1 = false;
                    feedLabel.setText("Level increased!!");
                    playSound("./src/LVL.wav");

                  //  beep1Player.play();

                    resetTimer();
                    saveLevel();
                    }
                }
                 
            }
            if ((character.getCurrentRow() == 3 && character.getCurrentCol() == 2) || (character.getCurrentRow() == 3 && character.getCurrentCol() == 1) || (character.getCurrentRow() == 2 && character.getCurrentCol() == 2) || (character.getCurrentRow() == 2 && character.getCurrentCol() == 1 )){
                
                

                feedLabel.setText("");
                //System.out.println(f1);
                 hasChickenFeed = false;
                if (hasCowFeed==true) {
                    f2 = true;
                     playSound("src/FED.wav"); 
                  //    System.out.println("hello3");
                    hasCowFeed = false;

                   if(f1 ){
                 //   System.out.println("hello4");
                        currentLevel++;
                        f1=false;
                        f2=false;   
                    
                    feedLabel.setText("Level increased!!");
                 //   beep1Player.play();
                    playSound("./src/LVL.wav");

                    resetTimer(); // Reset the timer when the level increases
                    saveLevel(); // Save the updated level to the file
                   }
                    
                }
                
            }
        }
    }

    public void saveLevel() {
        try {
            Files.writeString(Paths.get(LEVEL_FILE), String.valueOf(currentLevel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int loadLevel() {
        Path path = Paths.get(LEVEL_FILE);
        if (Files.exists(path)) {
            try {
                return Integer.parseInt(Files.readString(path));
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 1; // Default level if the file doesn't exist or there is an error reading it
    }

    public static void resetLevel() {
    }
}