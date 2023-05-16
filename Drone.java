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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Drone extends Application {
    Clip clip1;
    Clip clip;
    Clip clip2;

    int position = 0, flag = 0;
    int color = 0;

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {


        Application.launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("Fonts/04B_30__.TTF"), 24);
        File file = new File("audio/intro1.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();

        File file1 = new File("audio/pew.wav");
        AudioInputStream audioStream1 = AudioSystem.getAudioInputStream(file1);
        clip1 = AudioSystem.getClip();
        clip1.open(audioStream1);

        File file2 = new File("audio/intro.wav");
        AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(file2);
        clip2 = AudioSystem.getClip();
        clip2.open(audioStream2);

        Image drone = new Image("images/drone.png");
        ImageView droneView = new ImageView(drone);
        droneView.setScaleX(0.17);
        droneView.setScaleY(0.17);
        droneView.setLayoutX(-60);
        droneView.setLayoutY(500);


        ImageView monster = new ImageView(new Image("images/monster.png"));
        monster.setLayoutX(20);
        monster.setLayoutY(40);
        monster.setScaleX(0.17);
        monster.setScaleY(0.17);


        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(new ImagePattern(new Image("images/back.png")), new CornerRadii(0), new Insets(0))));
        pane.getChildren().addAll(droneView, monster);


        Scene mainScene = new Scene(pane, 500, 700);
        Text win = new Text("You Won");
        win.setLayoutX(mainScene.getWidth() / 4 - 50);
        win.setLayoutY(mainScene.getHeight() / 4);
        win.setFont(Font.loadFont(getClass().getResourceAsStream("Fonts/04B_30__.TTF"), 48));
        win.setFill(Color.WHITE);

        Text heart = new Text("❤");
        heart.setLayoutX(mainScene.getWidth() / 4 + 250);
        heart.setLayoutY(mainScene.getHeight() / 4);
        heart.setFill(Color.RED);
        heart.setFont(new Font(56));
        // win.setFont(new Font(48));
        Text wait = new Text("Loading ..");
        wait.setLayoutY(mainScene.getHeight() / 4);
        wait.setLayoutX(mainScene.getWidth() / 4 - 50);
        wait.setFont(Font.loadFont(getClass().getResourceAsStream("Fonts/04B_30__.TTF"), 48));
        wait.setFill(Color.WHITE);
        if(clip.isRunning()){
            pane.getChildren().add(wait);
        }

        mainScene.setOnKeyPressed(event -> {
            KeyCode k = event.getCode();
            if (k == KeyCode.ENTER && !clip.isRunning()){
                pane.getChildren().remove(wait);
            }
            if (k == KeyCode.LEFT && position >= -260) {
                position = (int) (droneView.getLayoutX() - 5);
                droneView.setLayoutX(position);


            } else if (k == KeyCode.RIGHT && position <= 135) {
                position = (int) (droneView.getLayoutX() + 5);
                droneView.setLayoutX(position);
            } else if (k == KeyCode.SPACE && !clip.isRunning()) {

                Rectangle bullet = new Rectangle(droneView.getLayoutX() + 310, droneView.getLayoutY() + 80, 5, 10);
                bullet.setFill(Color.RED);
                pane.getChildren().add(bullet);
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(30), e -> {
                    // Update the position of the rectangle

                    bullet.setLayoutY(bullet.getLayoutY() - 10);
                    bullet.setLayoutX(bullet.getLayoutX());


                    if (bullet.getLayoutY() == monster.getLayoutY() - 350
                            && bullet.getX() >= monster.getLayoutX() + 235 && bullet.getX() <= monster.getLayoutX() + 330) {
                        monster.setLayoutY(-1000);

                        System.out.println("Killed");


                        pane.getChildren().removeAll(bullet, monster);


                        pane.getChildren().addAll(win, heart);

                        clip2.start();
                        flag = 1;


                    }
                    if (flag == 1) {
                        if (win.getLayoutY() == 370) {
                            flag = 0;
                        }
                        switch (color) {
                            case 0:
                                heart.setFill(Color.RED);
                                color++;
                                break;
                            case 1:
                                heart.setFill(Color.ORANGE);
                                color++;
                                break;
                            case 2:
                                heart.setFill(Color.YELLOW);
                                color++;
                                break;
                            case 3:
                                heart.setFill(Color.GREEN);
                                color++;
                                break;
                            case 4:
                                heart.setFill(Color.BLUE);
                                color++;
                                break;
                            case 5:
                                heart.setFill(Color.BLUEVIOLET);
                                color++;
                                break;
                            case 6:
                                heart.setFill(Color.CHOCOLATE);
                                color++;
                                break;
                            case 7:
                                heart.setFill(Color.RED);
                                color = 0;
                                break;

                            default:
                                heart.setFill(Color.RED);

                        }

                        win.setLayoutY(win.getLayoutY() + 5);
                        heart.setLayoutY(heart.getLayoutY() + 5);
                        win.setRotate(win.getRotate() + 9);

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
