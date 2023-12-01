import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    private static final int GRID_SIZE = 9;
    private static final double CELL_SIZE = 75;
    //private final MediaPlayer beep3Player;
    private LevelHandler levelHandler;
    private Label levelLabel;
    private Label timerLabel;
    private static int timerSeconds = 20;
    private Timeline timeline;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showMenu(primaryStage);
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

    private void showMenu(Stage primaryStage) {
        Font.loadFont(getClass().getResourceAsStream("SuperFunky-lgmWw.ttf"), 12);
        Image bgImage = new Image(getClass().getResource("./grassbg.jpg").toExternalForm());
        if (bgImage.isError()) {
            primaryStage.setTitle("Old Mcdonald's Farm - Menu");
        }
         primaryStage.setTitle("Old Mcdonald's Farm - Menu");
        StackPane stackPane = new StackPane();
        stackPane.setStyle("-fx-background-color: #63C965;"); 
    
        ImageView imageView = new ImageView(bgImage);
        imageView.setFitWidth(GRID_SIZE*CELL_SIZE); 
        imageView.setFitHeight(GRID_SIZE*CELL_SIZE+50); 
    
        VBox menuVBox = new VBox(20);
        menuVBox.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("Old Mcdonald's Farm");
        titleLabel.setStyle("-fx-font: bold 30px 'Super Funky'; -fx-text-fill: brown;");
    
        Button newGameButton = new Button("New Game");
        Button resumeButton = new Button("Resume");
        Button exitButton = new Button("Exit");
        double buttonWidth = 160;
        newGameButton.setStyle("-fx-text-fill: white; -fx-background-color: #F7458A; -fx-font: bold 18px 'Super Funky'; -fx-padding: 10px;");
        resumeButton.setStyle("-fx-text-fill: white; -fx-background-color: #F7458A; -fx-font: bold 18px 'Super Funky'; -fx-padding: 10px;");
        exitButton.setStyle("-fx-text-fill: white; -fx-background-color: #F7458A; -fx-font: bold 18px 'Super Funky'; -fx-padding: 10px;");
        newGameButton.setPrefWidth(buttonWidth);
        resumeButton.setPrefWidth(buttonWidth);
        exitButton.setPrefWidth(buttonWidth);
    
        newGameButton.setOnAction(event -> startNewGame(primaryStage));
        resumeButton.setOnAction(event -> resumeGame(primaryStage));
        exitButton.setOnAction(event -> primaryStage.close());
    
        menuVBox.getChildren().addAll(titleLabel, newGameButton, resumeButton, exitButton);
    
        stackPane.getChildren().addAll(imageView, menuVBox);
    
        Scene menuScene = new Scene(stackPane, GRID_SIZE*CELL_SIZE, GRID_SIZE *CELL_SIZE +50);
        primaryStage.setScene(menuScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        this.primaryStage = primaryStage;
        primaryStage.setOnCloseRequest(event -> {
            // Handle the close request (go back or perform any other necessary actions)
            // For now, just close the application
            primaryStage.close();
        });
    }
    private void initializeGame(Stage primaryStage) {
        primaryStage.setTitle("Old Mcdonald's Farm");

        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: green;");
        grid.setHgap(0);
        grid.setVgap(0);

        Image farmImage = new Image(getClass().getResource("./field4.jpg").toExternalForm());
        Image treeImage = new Image(getClass().getResource("./tree.png").toExternalForm());
        Image chickenImage = new Image(getClass().getResource("./chickn.gif").toExternalForm());
        Image chickenFeedImage = new Image(getClass().getResource("./chickenfeed.png").toExternalForm());
        Image cowImage = new Image(getClass().getResource("./cowss.gif").toExternalForm());
        Image cowFeedImage = new Image(getClass().getResource("./feedCow.png").toExternalForm());


        levelHandler = new LevelHandler(new Label());
        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(event -> showMenu(primaryStage));
        goBackButton.setStyle("-fx-background-color: pink; -fx-text-fill: white;");
        primaryStage.setOnCloseRequest(event -> {

            stopTimeline();
            // on close button it goes back to menu page
            showMenu(primaryStage);
            event.consume();
        });

        // Add the button to an HBox
        // HBox buttonBox = new HBox(goBackButton);
        // buttonBox.setAlignment(Pos.TOP_RIGHT);
        // buttonBox.setStyle("-fx-padding: 10px; ");

        // here i am setting field image on whole grid
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                // Create a StackPane for each cell
                StackPane cellPane = new StackPane();

                ImageView fieldImageView = new ImageView(farmImage);
                fieldImageView.setFitWidth(CELL_SIZE);
                fieldImageView.setFitHeight(CELL_SIZE);

                cellPane.getChildren().addAll(fieldImageView);

                grid.add(cellPane, col, row);
            }
        }
        ImageView treeImageView = new ImageView(treeImage);
        treeImageView.setFitWidth(CELL_SIZE);
        treeImageView.setFitHeight(CELL_SIZE);
        grid.add(treeImageView, 5, 1);
        ImageView treeImageView2 = new ImageView(treeImage);
        treeImageView2.setFitWidth(CELL_SIZE);
        treeImageView2.setFitHeight(CELL_SIZE);
        grid.add(treeImageView2, 3, 2);
        ImageView treeImageView3 = new ImageView(treeImage);
        treeImageView3.setFitWidth(CELL_SIZE);
        treeImageView3.setFitHeight(CELL_SIZE);
        grid.add(treeImageView3, 1, 4);
        ImageView treeImageView4 = new ImageView(treeImage);
        treeImageView4.setFitWidth(CELL_SIZE);
        treeImageView4.setFitHeight(CELL_SIZE);
        grid.add(treeImageView4, 6, 5);
        ImageView treeImageView5 = new ImageView(treeImage);
        treeImageView5.setFitWidth(CELL_SIZE);
        treeImageView5.setFitHeight(CELL_SIZE);
        grid.add(treeImageView5, 1, 6);
        ImageView treeImageView6 = new ImageView(treeImage);
        treeImageView6.setFitWidth(CELL_SIZE);
        treeImageView6.setFitHeight(CELL_SIZE);
        grid.add(treeImageView6, 8, 8);
        ImageView treeImageView7 = new ImageView(treeImage);
        treeImageView7.setFitWidth(CELL_SIZE);
        treeImageView7.setFitHeight(CELL_SIZE);
        grid.add(treeImageView7, 3, 9);
        ImageView treeImageView8 = new ImageView(treeImage);
        treeImageView8.setFitWidth(CELL_SIZE);
        treeImageView8.setFitHeight(CELL_SIZE);
        grid.add(treeImageView8, 9, 7);

        ImageView chickenImageView = new ImageView(chickenFeedImage);
        chickenImageView.setFitWidth(CELL_SIZE);
        chickenImageView.setFitHeight(CELL_SIZE);
        
        grid.add(chickenImageView, 7, 2);
          ImageView cowImageView = new ImageView(cowImage);
        cowImageView.setFitWidth(CELL_SIZE);
        cowImageView.setFitHeight(CELL_SIZE);

        grid.add(cowImageView, 2, 3);

    
        ImageView cowFeedImageView = new ImageView(cowFeedImage);
        cowFeedImageView.setFitWidth(CELL_SIZE);
        cowFeedImageView.setFitHeight(CELL_SIZE);
        ImageView cowImageView2 = new ImageView(cowImage);
        cowImageView2.setFitWidth(CELL_SIZE);
        cowImageView2.setFitHeight(CELL_SIZE);
        grid.add(cowImageView2, 2, 2);
        ImageView cowImageView3 = new ImageView(cowImage);
        cowImageView3.setFitWidth(CELL_SIZE);
        cowImageView3.setFitHeight(CELL_SIZE);
        grid.add(cowImageView3, 1, 2);
          ImageView cowImageView4 = new ImageView(cowImage);
        cowImageView4.setFitWidth(CELL_SIZE);
        cowImageView4.setFitHeight(CELL_SIZE);
        grid.add(cowImageView4, 1, 3);
        
        
        grid.add(cowFeedImageView, 5, 7);

        ImageView chickenImageView2 = new ImageView(chickenImage);
        chickenImageView2.setFitWidth(CELL_SIZE);
        chickenImageView2.setFitHeight(CELL_SIZE);
   
        grid.add(chickenImageView2, 3, 5);
         ImageView chickenImageView3 = new ImageView(chickenImage);
        chickenImageView3.setFitWidth(CELL_SIZE);
        chickenImageView3.setFitHeight(CELL_SIZE);
   
        grid.add(chickenImageView3, 2, 5);
        ImageView chickenImageView4 = new ImageView(chickenImage);
        chickenImageView4.setFitWidth(CELL_SIZE);
        chickenImageView4.setFitHeight(CELL_SIZE);
        grid.add(chickenImageView4, 3, 6);
        ImageView chickenImageView5 = new ImageView(chickenImage);
        chickenImageView5.setFitWidth(CELL_SIZE);
        chickenImageView5.setFitHeight(CELL_SIZE);
        grid.add(chickenImageView5, 2, 6);
      
        Character character = new Character(new Image(getClass().getResource("farmer2.png").toExternalForm()), 0, 0);

        grid.add(character.getImageView(), character.getCurrentCol(), character.getCurrentRow());
        grid.setFocusTraversable(true);
        grid.requestFocus();
        grid.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    character.moveUp();
                    break;
                case DOWN:
                    character.moveDown();
                    break;
                case LEFT:
                    character.moveLeft();
                    break;
                case RIGHT:
                    character.moveRight();
                    break;
                case ENTER:
                    levelHandler.handleKeyPress(character, event);
                    updateLabels();
                    break;
            }
        });

        // here i am creating labels for level and timer
        levelLabel = new Label("Level: " + levelHandler.getCurrentLevel());
        levelLabel.setStyle("-fx-text-fill: white; -fx-font: bold 30px 'Super Funky';-fx-font-size:20px; -fx-padding:0 0 0 20px;");
        timerLabel = new Label("Time: " + timerSeconds + "s");
        timerLabel.setStyle("-fx-text-fill: white; -fx-font: bold 30px 'Super Funky';-fx-font-size:20px;-fx-padding:0 0 0 20px;");

        HBox labelsBox = new HBox();
        labelsBox.setStyle("-fx-padding: 10px;");
        labelsBox.getChildren().addAll(levelLabel, timerLabel, levelHandler.getFeedLabel());

        grid.setStyle("-fx-background-color: green;");
        VBox root = new VBox();
        root.setStyle("-fx-background-color: green;");
        root.getChildren().addAll(labelsBox, grid);

        Scene scene = new Scene(root, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE + 50);
        primaryStage.setScene(scene);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timerSeconds--;
            timerLabel.setText("Time: " + timerSeconds + "s");

            if (timerSeconds <= 0) {
                System.out.println("Game Over!");

                timerSeconds = 15;

                resetGame();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline
        timeline.play();
        this.primaryStage = primaryStage;
        primaryStage.show();
        grid.requestFocus();

    }

    public static void resetTimer() {

        timerSeconds = 20; // Setting the initial timer value
    }

    private void resetGame() {
        int currentLevel = levelHandler.getCurrentLevel();
        if (currentLevel > 1) {
            playSound("src/lvldown.wav");

            levelHandler.getFeedLabel().setText("Level Decreased!");

            levelHandler = new LevelHandler(levelHandler.getFeedLabel());
            levelHandler.setCurrentLevel(currentLevel - 1);

            
        }   
        else {
                     playSound("src/gover.wav");
            levelHandler.getFeedLabel().setStyle("-fx-text-fill: red; -fx-font: bold 30px 'Super Funky';-fx-font-size:20px; -fx-padding:0 0 0 20px; ");

            levelHandler.getFeedLabel().setText("Game Over");
         //   beep3Player.play();

           // levelHandler.getFeedLabel().setStyle("-fx-text-fill: red; -fx-font-weight: bold;-fx-font-size:20px; -fx-padding:0 0 0 20px;");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            
                closeGame();
            }));
            timeline.setCycleCount(1);
            timeline.play();
        }
    
        timerSeconds = 20;
        updateLabels();
    }
    private void updateLabels() {
        levelLabel.setText("Level: " + levelHandler.getCurrentLevel());
        timerLabel.setText("Time: " + timerSeconds + "s");

    }

    private void resumeGame(Stage primaryStage) {
        initializeGame(primaryStage);
    }

    private void startNewGame(Stage primaryStage) {
        LevelHandler levelHandler = new LevelHandler(new Label());
        levelHandler.setCurrentLevel(1);
        timerSeconds = 20;
        levelHandler.saveLevel();

        initializeGame(primaryStage);
    }

    private void stopTimeline() {
        if (timeline != null) {
            timeline.stop();
        }
    }
    private void closeGame() {
        timeline.stop();    
         showMenu(primaryStage);
    }
}
