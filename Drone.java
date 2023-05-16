/*
    @ Notes
    This is a simple game I am working on ,but it is not completed yet.
    I will be working on it later when I have time.
    Now the game has some bugs that I will handle later إن شاء الله
    You can download this repository ,play or complete the game code on the way you want
    and if you have any questions or any modifications you can contact me
    at bakrahmed440@gmail.com
                                                            Thank you!
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;

import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Drone extends Application {
    Clip clip1;


    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {


        File file = new File("audio/intro1.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

        Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        File file = new File("audio/pew.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip1 = AudioSystem.getClip();
        clip1.open(audioStream);

        Image drone = new Image("images/drone.png");
        ImageView droneView = new ImageView(drone);
        droneView.setScaleX(0.17);
        droneView.setScaleY(0.17);
        droneView.setLayoutX(-60);
        droneView.setLayoutY(500);

        Random random = new Random();

        ImageView monster = new ImageView(new Image("images/monster.png"));
        monster.setLayoutX(40);
        monster.setLayoutY(40);
        monster.setScaleX(0.17);
        monster.setScaleY(0.17);


        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image("images/back.png")), new CornerRadii(0), new Insets(0))));
        pane.getChildren().addAll(droneView, monster);


        Scene mainScene = new Scene(pane, 500, 700);

        mainScene.setOnKeyPressed(event -> {
            KeyCode k = event.getCode();
            if (k == KeyCode.LEFT) {
                droneView.setLayoutX(droneView.getLayoutX() - 5);

            } else if (k == KeyCode.RIGHT) {
                droneView.setLayoutX(droneView.getLayoutX() + 5);
            } else if (k == KeyCode.UP) {
                droneView.setLayoutY(droneView.getLayoutY() - 5);
            } else if (k == KeyCode.DOWN) {
                droneView.setLayoutY(droneView.getLayoutY() + 5);
            } else if (k == KeyCode.SPACE) {


                double y = droneView.getLayoutY();

                Rectangle bullet = new Rectangle(droneView.getLayoutX() + 310, y + 80, 5, 10);
                bullet.setFill(Color.RED);
                pane.getChildren().add(bullet);
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), e -> {
                    // Update the position of the rectangle

                    bullet.setLayoutY(bullet.getLayoutY() - 10);
                    bullet.setLayoutX(bullet.getLayoutX());


                    if(bullet.getLayoutY() == monster.getLayoutY()-700 ){
                        pane.getChildren().removeAll(bullet,monster);
                        int x= random.nextInt(500);
                        int z = random.nextInt(500);
                        monster.setLayoutX(random.nextInt(100));

//                        pane.getChildren().add(monster);
                    }

//                    System.out.println(bullet.getLayoutY());
                    /*
                        this part is for testing purposes to continue from here
                        when I have spare time.
                     */

                    // End of test

                }));



                timeline.setCycleCount(70);
                timeline.play();
                clip1.setMicrosecondPosition(0);
                clip1.start();


            }


        });


        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("images/drone.png"));
        primaryStage.setTitle("Drone");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
