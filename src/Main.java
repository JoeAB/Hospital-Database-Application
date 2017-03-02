import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;


public class Main extends Application {

  @Override
    public void start(Stage primaryStage) throws Exception{
        mainMenu(primaryStage);
    }
    private static void searchMenu(Stage primaryStage){
        GridPane addMenu = new GridPane();
        //creating all controls to be used within the menu
        Text idLabel = new Text("ID:");
        TextField idBox = new TextField();
        Text fNameLabel = new Text("First Name:");
        TextField firstNameBox = new TextField();
        Text lNameLabel = new Text("Last Name:");
        TextField lastNameBox = new TextField();
        Text ageLabel = new Text("Age:");
        TextField ageBox = new TextField();
        Button searchButton = new Button();
        searchButton.setText("Search");
        Button returnMain = new Button();
        returnMain.setText("Return to Main Menu");

        //putting the controls into the organization pane
        addMenu.add(idLabel,0,0);
        addMenu.add(idBox, 1,0 );
        addMenu.add(fNameLabel,0,1);
        addMenu.add(firstNameBox, 1,1 );
        addMenu.add(lNameLabel,0,2);
        addMenu.add(lastNameBox, 1,2);
        addMenu.add(ageLabel,0,3);
        addMenu.add(ageBox, 1,3);
        addMenu.add(searchButton,1,4);
        addMenu.add(returnMain,1,10);

        //setting the scene for adding
        addMenu.setAlignment(Pos.CENTER);
        Scene scene = new Scene(addMenu, 400, 400);
        primaryStage.setScene(scene);
        returnMain.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                mainMenu(primaryStage);
            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                HashMap<String, Object> fields = new HashMap<String, Object>();
                if(ageBox.getText().trim().length() >0){ fields.put("age",Integer.parseInt(ageBox.getText()));}
                if(firstNameBox.getText().trim().length() >0){ fields.put("firstName",firstNameBox.getText());}
                if(lastNameBox.getText().trim().length() >0){ fields.put("lastName",lastNameBox.getText());}

                DatabaseConnection connection = new DatabaseConnection();
                connection.getPatients(fields);
                connection.averageAge();
                connection.mostCommon();
            }
        });
    }

    private static void addScrean(Stage primaryStage){
        GridPane addMenu = new GridPane();
        //creating all controls to be used within the menu
        Text fNameLabel = new Text("First Name:");

        TextField firstNameBox = new TextField();
        Text lNameLabel = new Text("Last Name:");
        TextField lastNameBox = new TextField();
        Text ageLabel = new Text("Age:");
        TextField ageBox = new TextField();
        Button addButton = new Button();
        addButton.setText("Add");
        Button returnMain = new Button();
        returnMain.setText("Return to Main Menu");

        //putting the controls into the organization pane
        addMenu.add(fNameLabel,0,1);
        addMenu.add(firstNameBox, 1,1 );
        addMenu.add(lNameLabel,0,2);
        addMenu.add(lastNameBox, 1,2);
        addMenu.add(ageLabel,0,3);
        addMenu.add(ageBox, 1,3);
        addMenu.add(addButton,1,4);

        addMenu.add(returnMain,1,10);
        //setting the scene for adding
        addMenu.setAlignment(Pos.CENTER);

        Scene scene = new Scene(addMenu, 400, 400);

        primaryStage.setScene(scene);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Patient newPatient = new Patient(firstNameBox.getText(), lastNameBox.getText(),
                  Integer.parseInt(ageBox.getText()));
              DatabaseConnection connection = new DatabaseConnection();
              connection.addPatient(newPatient);

            }
        });
        returnMain.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                mainMenu(primaryStage);
            }
        });
    }
    private static void mainMenu(Stage primaryStage){
        VBox mainMenu = new VBox();
        //creating all controls to be used within the menu
        Button search = new Button("Search Database");
        Button add = new Button("Add Patient");
        Button viewDashboard = new Button("Statistics Dashboard");
        Button quit = new Button("Quit Application");

        mainMenu.getChildren().addAll(search,add,viewDashboard,quit);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setStyle(" -fx-background-color: blue;");
        Scene scene = new Scene(mainMenu, 400, 400);
        //setting the stage to display the pane
        primaryStage.setScene(scene);
        primaryStage.show();
        search.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                searchMenu(primaryStage);
            }
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                addScrean(primaryStage);
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