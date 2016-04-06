package sample;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableMapValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.*;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        ObservableList<Menu> currentMenues = FXCollections.observableArrayList();
        currentMenues.add(new Menu(1, "veggie", "vegitarian"));
        currentMenues.add(new Menu(2, "vegan", "vegan"));
        currentMenues.add(new Menu(1, "meatlovers", ""));

        ObservableList<Menu> newMenu = FXCollections.observableArrayList();

        ObservableList<Dish> currentDishes = FXCollections.observableArrayList();
        currentDishes.add(new Dish(1, 29, "spaghetti"));
        currentDishes.add(new Dish(2, 234, "ravioli"));
        currentDishes.add(new Dish(3, 2, "lasagna"));

        ObservableList<Dish> newDish = FXCollections.observableArrayList();

        ObservableList<Ingredient> currentIngredients = FXCollections.observableArrayList(
                new Ingredient(1, "sugar", "kg", 22, 30, 234),
                new Ingredient(2, "flour", "kg", 20, 30, 234),
                new Ingredient(3, "baking soda", "kg", 20, 30, 234)
        );


        ObservableList<Ingredient> newIngredients = FXCollections.observableArrayList();

        //First Combobox
        ComboBox comboBox = new ComboBox(currentMenues);
        comboBox.getSelectionModel().clearSelection();

        Label myLabel = new Label();


        comboBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                newMenu.clear();
                newMenu.add((Menu) newValue);
            }
        });

        comboBox.setCellFactory(new Callback<ListView<Menu>, ListCell<Menu>>() {

            @Override
            public ListCell<Menu> call(ListView<Menu> p) {

                final ListCell<Menu> cell = new ListCell<Menu>() {

                    @Override
                    protected void updateItem(Menu t, boolean bln) {

                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.toString());
                        } else {
                            setText(null);
                        }
                    }
                };

                return cell;
            }
        });


        comboBox.setPromptText("choose menu");
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 5, 5));
        vBox.setSpacing(5);
        vBox.getChildren().addAll(comboBox, myLabel);


        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        Label candidatesLbl = new Label("menues");
        GridPane.setHalignment(candidatesLbl, HPos.CENTER);
        gridpane.add(candidatesLbl, 0, 0);




        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        /*
        first List
         */
        final ListView<Menu> myListView = new ListView<Menu>(newMenu);

        gridpane.add(myListView, 0, 1);


        Button removeMenu = new Button("remove menu");
        removeMenu.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Menu item = myListView.getSelectionModel().getSelectedItem();
                if (item != null) {
                    myListView.getSelectionModel().clearSelection();
                    newMenu.remove(item);
                }
            }
        });
//

        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(removeMenu, myListView);

        gridpane.add(vbox, 1, 1);
        GridPane.setConstraints(vbox, 1, 1, 1, 2, HPos.CENTER, VPos.CENTER);

        //second list
        final ListView<Dish> myListView2 = new ListView<Dish>(newDish);

        gridpane.add(myListView, 0, 1);


//


        SplitPane splitPane2 = new SplitPane();
        splitPane2.setOrientation(Orientation.VERTICAL);


//        // TODO: 02.04.2016
        Dish newDish1 = new Dish(1, 2, "Dish no1");
        Dish newDish2 = new Dish(1, 2, "Dish no2");
        Dish newDish3 = new Dish(1, 2, "Dish no3");

        ObservableList<Dish> myDishes = FXCollections.observableArrayList(newDish1, newDish2, newDish3);


        ListView<Dish> listView = new ListView<>();
        /*for (int i = 1; i <= 20; i++) {
            String item = "Item " + i;
            listView.getItems().add(item);
        }*/
        listView.getItems().addAll(myDishes);
        listView.setCellFactory(CheckBoxListCell.forListView(new Callback<Dish, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Dish item) {
                BooleanProperty observable = new SimpleBooleanProperty();
                observable.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            newDish.add(item);
                            System.out.print("asdf");
                        } else {
                            newDish.remove(item);
                        }
                    }
                });
                return observable;
            }
        }));


        TableView<Dish> table = new TableView<Dish>();
        ObservableList<Dish> data = newDish;


        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("dishName");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("dishName"));
        TableColumn lastNameCol = new TableColumn("price");
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Dish, DoubleProperty>("price")
        );

        Button removeDish = new Button("remove Dish");
        removeDish.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Dish item = table.getSelectionModel().getSelectedItem();
                if (item != null) {
                    table.getSelectionModel().clearSelection();
                    newDish.remove(item);
                }
            }
        });





        // TODO: 05.04.2016 TRYING TO TIE <INTEGER>QUANTITY AND MENU IN AN OBSERVABLELIST

        Map <String, String> myMap = new HashMap<>();

        myMap.put("key1", "value1");
        myMap.put("key2", "value2");
        myMap.put("key3", "value3");

        ObservableMap<String, String> myObservableHashMap = FXCollections.observableMap(myMap);








// sample data
        Map<Dish, Integer> map = new HashMap<>();
        map.put(newDish1, 1);
        map.put(newDish2, 2);
        map.put(newDish3, 3);


        // use fully detailed type for Map.Entry<String, String>
        TableColumn<Map.Entry<Dish, Integer>, String> column1 = new TableColumn<>("Key");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String> p) {
                // this callback returns property for just one cell, you can't use a loop here
                // for first column we use key
                return new SimpleStringProperty(p.getValue().getKey().toString());
            }
        });

        TableColumn<Map.Entry<Dish, Integer>, String> column2 = new TableColumn<>("Value");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Dish, Integer>, String> p) {
                // for second column we use value
                return new SimpleStringProperty(p.getValue().getValue().toString());
            }
        });

        ObservableList<Map.Entry<Dish, Integer>> items = FXCollections.observableArrayList(map.entrySet());
        final TableView<Map.Entry<Dish, Integer>> table2 = new TableView<>(items);

        table2.getColumns().setAll(column1, column2);











        /*// sample data
        Map<String, String> map = new HashMap<>();
        map.put("one", "One");
        map.put("two", "Two");
        map.put("three", "Three");


        // use fully detailed type for Map.Entry<String, String>
        TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>("Key");
        column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                // this callback returns property for just one cell, you can't use a loop here
                // for first column we use key
                return new SimpleStringProperty(p.getValue().getKey());
            }
        });

        TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>("Value");
        column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                // for second column we use value
                return new SimpleStringProperty(p.getValue().getValue());
            }
        });

        ObservableList<Map.Entry<String, String>> items = FXCollections.observableArrayList(map.entrySet());
        final TableView<Map.Entry<String,String>> table2 = new TableView<>(items);

        table2.getColumns().setAll(column1, column2);

*/














        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol);

        final VBox vbox3 = new VBox();
        vbox3.setSpacing(5);
        vbox3.setPadding(new Insets(10, 0, 0, 10));
        vbox3.getChildren().addAll(table);

        VBox vbox2 = new VBox(5);
        vbox2.getChildren().addAll(removeDish, table2);

        gridpane.add(vbox2, 1, 1);
        GridPane.setConstraints(vbox, 1, 1, 1, 2, HPos.CENTER, VPos.CENTER);























        HBox upper = new HBox(comboBox, listView);

        HBox lower = new HBox(gridpane, removeMenu, vbox2, vbox3);

        splitPane2.getItems().add(upper);
        splitPane2.getItems().add(lower);

        StackPane root = new StackPane(splitPane2);
        root.getChildren().addAll();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 350, 250, Color.WHITE);
        splitPane2.prefWidthProperty().bind(scene.widthProperty());
        splitPane2.prefHeightProperty().bind(scene.heightProperty());
        primaryStage.setScene(scene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
