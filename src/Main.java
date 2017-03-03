import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

  @Override
    public void start(Stage primaryStage) throws Exception{
        mainMenu(primaryStage);
    }

    static void mainMenu(Stage primaryStage){
        VBox mainMenu = new VBox();
        //creating all controls to be used within the menu
        Button search = new Button("Search Database");
        Button add = new Button("Add Patient");
        Button addDoctor = new Button("Add Doctor");
        Button viewDashboard = new Button("Statistics Dashboard");
        Button quit = new Button("Quit Application");

        mainMenu.getChildren().addAll(search,add,addDoctor, viewDashboard,quit);
        mainMenu.setAlignment(Pos.CENTER);
        //mainMenu.setStyle(" -fx-background-color: blue;");
        Scene scene = new Scene(mainMenu, 400, 400);
        //setting the stage to display the pane
        primaryStage.setScene(scene);
        primaryStage.show();
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Search.searchMenu(primaryStage);
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                AddPatient.addScrean(primaryStage);
            }
        });
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}