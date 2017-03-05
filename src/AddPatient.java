import com.mongodb.BasicDBObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by joebennett on 3/3/17.
 */
public class AddPatient {
  static void addScrean(Stage primaryStage){
    GridPane addMenu = new GridPane();
    //creating all controls to be used within the menu
    Text fNameLabel = new Text("First Name:");

    TextField firstNameBox = new TextField();
    Text lNameLabel = new Text("Last Name:");
    TextField lastNameBox = new TextField();
    Text ageLabel = new Text("Age:");
    TextField ageBox = new TextField();
    Text conditionLabel = new Text("Condition: ");
    TextField conditionBox = new TextField();
    Text doctorLabel = new Text("Assigned Doctor: ");
    ChoiceBox<String> doctorChoice= new ChoiceBox<String>();
    DatabaseConnection connection = new DatabaseConnection();

    for (BasicDBObject doctor:connection.searchDB(new HashMap(), "doctor")) {
      doctorChoice.getItems().add("Dr. "+doctor.get("lastName")+": "+doctor.get("specialty")+": "+doctor.get("_id"));
    }
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
    addMenu.add(conditionLabel,0,4);
    addMenu.add(conditionBox,1,4);
    addMenu.add(doctorLabel,0,5);
    addMenu.add(doctorChoice,1,5);
    addMenu.add(addButton,1,6);
    addMenu.add(returnMain,1,10);
    //setting the scene for adding
    addMenu.setAlignment(Pos.CENTER);

    Scene scene = new Scene(addMenu, 400, 400);

    primaryStage.setScene(scene);
    addButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Patient newPatient = new Patient(firstNameBox.getText(), lastNameBox.getText(),
          Integer.parseInt(ageBox.getText()), conditionBox.getText(), doctorChoice.getValue().split(":")[2].trim());
        DatabaseConnection connection = new DatabaseConnection();
        connection.add(newPatient, "patient");

      }
    });
    returnMain.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
        Main.mainMenu(primaryStage);
      }
    });
  }
}
