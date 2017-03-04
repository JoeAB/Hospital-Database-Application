import com.mongodb.BasicDBObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by joebennett on 3/3/17.
 */
public class Search {
  static void searchMenu(Stage primaryStage){
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

    ListView<String> resultsList = new ListView<String>();
    resultsList.setVisible(false);
    //putting the controls into the organization pane
    addMenu.add(idLabel,0,0);
    addMenu.add(idBox, 1,0 );
    addMenu.add(fNameLabel,0,1);
    addMenu.add(firstNameBox, 1,1 );
    addMenu.add(lNameLabel,0,2);
    addMenu.add(lastNameBox, 1,2);
    addMenu.add(ageLabel,0,3);
    addMenu.add(ageBox, 1,3);
    addMenu.add(resultsList, 1,4);
    addMenu.add(searchButton,1,9);
    addMenu.add(returnMain,1,10);

    //setting the scene for adding
    addMenu.setAlignment(Pos.CENTER);
    Scene scene = new Scene(addMenu, 400, 400);
    primaryStage.setScene(scene);
    returnMain.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
        Main.mainMenu(primaryStage);
      }
    });
    searchButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override public void handle(ActionEvent e) {
        HashMap<String, Object> fields = new HashMap<String, Object>();
        if(ageBox.getText().trim().length() >0){ fields.put("age",Integer.parseInt(ageBox.getText()));}
        if(firstNameBox.getText().trim().length() >0){ fields.put("firstName",firstNameBox.getText());}
        if(lastNameBox.getText().trim().length() >0){ fields.put("lastName",lastNameBox.getText());}

        DatabaseConnection connection = new DatabaseConnection();
        ArrayList<BasicDBObject> results = connection.searchDB(fields,"patient");
        resultsList.getItems().clear();
        if (results.size() > 0){
          for (BasicDBObject a:results) {
            resultsList.getItems().add(a.get("firstName") +" "+a.get("lastName")+" : "+a.get("_id"));
            resultsList.setVisible(true);
          }
        }
        connection.averageAge();
        connection.mostCommon();
      }
    });
    resultsList.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        String id = resultsList.getSelectionModel().getSelectedItem().split(":")[1];
        System.out.println(id);
      }
    });
  }
}
