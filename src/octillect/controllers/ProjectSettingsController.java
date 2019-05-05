package octillect.controllers;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import javafx.util.StringConverter;

import octillect.controls.ContributorCell;
import octillect.controls.LabelCell;
import octillect.controls.OButton;
import octillect.models.User;
import octillect.models.builders.LabelBuilder;
import octillect.models.builders.UserBuilder;

public class ProjectSettingsController implements Injectable<ApplicationController> {

    //FXML Fields
    @FXML public TitledPane editNameTitledPane;
    @FXML public JFXTextField editNameTextArea;
    @FXML public OButton saveNameButton;
    @FXML public TitledPane editDescriptionTitledPane;
    @FXML public JFXTextArea editDescriptionTextArea;
    @FXML public OButton saveDescriptionButton;
    @FXML public TitledPane contributorsTitledPane;
    @FXML public JFXListView<Pair<User, String>> contributorsListView;
    @FXML public JFXTextField inviteContributorByEmailTextField;
    @FXML public JFXComboBox rolesComboBox;
    @FXML public OButton inviteContributorButton;
    @FXML public TitledPane labelsTitledPane;
    @FXML public JFXListView labelsListView;
    @FXML public JFXTextField newLabelTextField;
    @FXML public JFXColorPicker labelColorPicker;
    @FXML public OButton addLabelButton;
    @FXML public TitledPane deleteProjectTitledPane;

    // Injected Controllers
    private ApplicationController applicationController;

    @Override
    public void inject(ApplicationController applicationController) {
        this.applicationController = applicationController;
    }

    public void initialize() {

        //Initializing rolesComboBox
        rolesComboBox.getItems().add(new Label("Admin"));
        rolesComboBox.getItems().add(new Label("User"));
        rolesComboBox.getItems().add(new Label("FrontEnd Developer"));
        rolesComboBox.getItems().add(new Label("BackEnd Developer"));
        rolesComboBox.setEditable(true);
        rolesComboBox.setConverter(new StringConverter<Label>() {
            @Override
            public String toString(Label object) {
                return object == null ? "" : object.getText();
            }

            @Override
            public Label fromString(String string) {
                return new Label(string);
            }
        });

        //Initializing usersListView
        User user1 = new UserBuilder().with($ -> {
            $.name = "Monica Adel";
            $.email = "monica@gmail.com";
        }).build();
        User user2 = new UserBuilder().with($ -> {
            $.name = "Youssef Raafat";
            $.email = "usf@gmail.com";
        }).build();
        User user3 = new UserBuilder().with($ -> {
            $.name = "Monica Atef";
            $.email = "monica@hotmail.com";
        }).build();

        ObservableList<Pair<User, String>> users = FXCollections.observableArrayList(new Pair(user1, "admin"),
                new Pair(user2, "user"), new Pair(user3, "developer"));
        contributorsListView.setItems(users);
        contributorsListView.setCellFactory(param -> new ContributorCell());

        //Initializing labelsListView
        octillect.models.Label label1 = new LabelBuilder().with($_label1 -> {
            $_label1.name = "back-end";
            $_label1.color = Color.WHITE;
        }).build();
        octillect.models.Label label2 = new LabelBuilder().with($_label2 -> {
            $_label2.name = "bug";
            $_label2.color = Color.LIGHTPINK;
        }).build();
        octillect.models.Label label3 = new LabelBuilder().with($_label3 -> {
            $_label3.name = "improvement";
            $_label3.color = Color.TURQUOISE;
        }).build();

        ObservableList<octillect.models.Label> labels = FXCollections.observableArrayList(label1, label2, label3);
        labelsListView.setItems(labels);
        labelsListView.setCellFactory(param -> new LabelCell() {
        });

    }

    public void handleTitledPaneOnAction(MouseEvent mouseEvent) {
        editNameTitledPane.setExpanded(false);
        editDescriptionTitledPane.setExpanded(false);
        contributorsTitledPane.setExpanded(false);
        labelsTitledPane.setExpanded(false);
        ((TitledPane) mouseEvent.getSource()).setExpanded(true);
    }

    public void handleSaveNameButtonAction(MouseEvent mouseEvent) {
    }

    public void handleSaveDescriptionButtonAction(MouseEvent mouseEvent) {
    }

    public void handleInviteContributorButtonAction(MouseEvent mouseEvent) {
    }

    public void handleAddLabelButtonAction(MouseEvent mouseEvent) {
    }

    public void handleDeleteProjectAction(MouseEvent mouseEvent) {
    }

}
