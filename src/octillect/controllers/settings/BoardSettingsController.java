package octillect.controllers.settings;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import octillect.controllers.ApplicationController;
import octillect.controllers.BoardController;
import octillect.controllers.Injectable;
import octillect.controllers.LeftDrawerController;
import octillect.controllers.TitleBarController;
import octillect.controls.ContributorCell;
import octillect.controls.TagCell;
import octillect.controls.OButton;
import octillect.database.repositories.TagRepository;
import octillect.database.repositories.BoardRepository;
import octillect.database.repositories.UserRepository;
import octillect.database.firebase.FirestoreAPI;
import octillect.models.Board;
import octillect.models.Contributor;
import octillect.models.Tag;
import octillect.models.builders.TagBuilder;

public class BoardSettingsController implements Injectable<ApplicationController> {

    //FXML Fields
    @FXML public TitledPane editNameTitledPane;
    @FXML public TitledPane editDescriptionTitledPane;
    @FXML public TitledPane contributorsTitledPane;
    @FXML public TitledPane tagsTitledPane;
    @FXML public TitledPane deleteBoardTitledPane;
    @FXML public BorderPane newContributorBorderPane;
    @FXML public BorderPane newTagBorderPane;
    @FXML public JFXTextField editNameTextField;
    @FXML public JFXTextField newContributorTextField;
    @FXML public JFXTextField newTagTextField;
    @FXML public JFXTextArea editDescriptionTextArea;
    @FXML public JFXListView<Contributor> contributorsListView;
    @FXML public JFXListView<Tag> tagsListView;
    @FXML public JFXComboBox<Board.Role> rolesComboBox;
    @FXML public JFXColorPicker tagColorPicker;
    @FXML public OButton addContributorButton;
    @FXML public OButton addTagButton;

    // Validators
    private RequiredFieldValidator requiredFieldValidator;
    private RegexValidator emailValidator;

    // Injected Controllers
    private ApplicationController applicationController;
    private BoardController boardController;
    private LeftDrawerController leftDrawerController;
    private TitleBarController titleBarController;

    @Override
    public void inject(ApplicationController applicationController) {
        this.applicationController = applicationController;
        boardController = applicationController.boardController;
        leftDrawerController = applicationController.leftDrawerController;
        titleBarController = applicationController.titleBarController;
    }

    @Override
    public void init() {

        // Validators

        requiredFieldValidator = new RequiredFieldValidator("Required field.");
        emailValidator = new RegexValidator();
        emailValidator.setRegexPattern("^((?!.*" + newContributorTextField.getText() + ".*).)*$");

        newTagTextField.getValidators().add(requiredFieldValidator);
        rolesComboBox.getValidators().add(requiredFieldValidator);
        newContributorTextField.getValidators().add(requiredFieldValidator);
        newContributorTextField.getValidators().add(emailValidator);


        // TextFields' Listeners

        editNameTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && boardController.currentBoard != null) {
                BoardRepository.getInstance().updateName(boardController.currentBoard.getId(), editNameTextField.getText());
                boardController.currentBoard.setName(editNameTextField.getText());
                titleBarController.boardNameLabel.setText(editNameTextField.getText());
                int index = leftDrawerController.userBoardsListView.getItems().indexOf(boardController.currentBoard);
                leftDrawerController.userBoardsListView.getItems().set(index, boardController.currentBoard);
            }
        });

        editDescriptionTextArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && boardController.currentBoard != null) {
                BoardRepository.getInstance().updateDescription(boardController.currentBoard.getId(), editDescriptionTextArea.getText());
                boardController.currentBoard.setDescription(editDescriptionTextArea.getText());
            }
        });

        rolesComboBox.setItems(FXCollections.observableArrayList(Board.Role.values()));
        contributorsListView.setCellFactory(param -> {
            ContributorCell contributorCell = new ContributorCell();
            contributorCell.inject(applicationController);
            return contributorCell;
        });

        tagsListView.setCellFactory(param -> {
            TagCell tagCell = new TagCell();
            tagCell.inject(applicationController);
            return tagCell;
        });

    }

    @FXML
    public void handleTitledPaneOnAction(MouseEvent mouseEvent) {
        editNameTitledPane.setExpanded(false);
        editDescriptionTitledPane.setExpanded(false);
        contributorsTitledPane.setExpanded(false);
        tagsTitledPane.setExpanded(false);
        ((TitledPane) mouseEvent.getSource()).setExpanded(true);
    }

    @FXML
    public void handleAddContributorButtonAction(MouseEvent mouseEvent) {

        resetRequiredFieldValidators();
        newContributorTextField.validate();
        rolesComboBox.validate();
        boolean isContributor = false;

        if (!requiredFieldValidator.getHasErrors()) {

            for (Contributor contributor : boardController.currentBoard.getContributors()) {
                if (contributor.getEmail().equals(newContributorTextField.getText())) {
                    isContributor = true;
                }
            }

            if (isContributor) {
                emailValidator.setMessage("Already a Contributor.");
                newContributorTextField.getValidators().add(emailValidator);
                newContributorTextField.validate();
                newContributorTextField.getValidators().remove(emailValidator);
            } else {
                Contributor contributor = UserRepository.getInstance().getContributor(FirestoreAPI.getInstance().encrypt(newContributorTextField.getText()));

                if (contributor == null) {
                    emailValidator.setMessage("That Octillect account doesn't exist.");
                    newContributorTextField.getValidators().add(emailValidator);
                    newContributorTextField.validate();
                    newContributorTextField.getValidators().remove(emailValidator);
                } else {
                    contributor.setRole(rolesComboBox.getValue());

                    BoardRepository.getInstance().addContributor(boardController.currentBoard.getId(), contributor.getEmail(), contributor.getRole());
                    UserRepository.getInstance().addBoardId(FirestoreAPI.getInstance().encrypt(newContributorTextField.getText()), boardController.currentBoard.getId());

                    int index = applicationController.user.getBoards().indexOf(boardController.currentBoard);
                    applicationController.user.getBoards().get(index).getContributors().add(contributor);
                    boardController.currentBoard.getContributors().add(contributor);
                    contributorsListView.getItems().add(contributor);

                    newContributorTextField.setText(null);
                    rolesComboBox.getSelectionModel().clearSelection();
                    resetRequiredFieldValidators();
                }
            }
        }
    }

    @FXML
    public void handleAddTagButtonAction(MouseEvent mouseEvent) {

        resetRequiredFieldValidators();
        newTagTextField.validate();

        if (!requiredFieldValidator.getHasErrors()) {

            Tag tag = new TagBuilder().with($ -> {
                $.id = FirestoreAPI.getInstance().encryptWithDateTime(newTagTextField.getText() + applicationController.user.getId());
                $.name = newTagTextField.getText();
                $.color = tagColorPicker.getValue();
            }).build();

            TagRepository.getInstance().add(tag);
            BoardRepository.getInstance().addTagId(boardController.currentBoard.getId(), tag.getId());
            tagsListView.getItems().add(tag);
            resetRequiredFieldValidators();

            newTagTextField.setText(null);
            tagColorPicker.setValue(Color.WHITE);
        }
    }

    @FXML
    public void handleDeleteBoardAction(MouseEvent mouseEvent) {
        BoardRepository.getInstance().delete(boardController.currentBoard);
        for (Contributor contributor : boardController.currentBoard.getContributors()) {
            UserRepository.getInstance().deleteBoardId(contributor.getId(), boardController.currentBoard.getId());
        }

        applicationController.user.getBoards().remove(boardController.currentBoard);
        boardController.init();
        leftDrawerController.init();
    }

    private void resetRequiredFieldValidators() {
        newContributorTextField.resetValidation();
        newTagTextField.resetValidation();
        rolesComboBox.resetValidation();
    }

    public void loadBoardSettings() {
        editNameTextField.setText(boardController.currentBoard.getName());
        editDescriptionTextArea.setText(boardController.currentBoard.getDescription());
        controlRoleAccess(boardController.currentBoard.getUserRole(applicationController.user.getId()));
        loadContributors();
        loadTags();
    }

    private void loadContributors() {
        ObservableList<Contributor> users = FXCollections.observableArrayList();
        boardController.currentBoard.getContributors().forEach(users::add);
        contributorsListView.setItems(users);
    }

    private void loadTags() {
        if (boardController.currentBoard.getTags() != null) {
            ObservableList<Tag> tags = FXCollections.observableArrayList();
            boardController.currentBoard.getTags().forEach(tags::add);
            tagsListView.setItems(tags);
        }
    }

    public void resetBoardSettings() {
        editNameTextField.setText(null);
        editDescriptionTextArea.setText(null);
        contributorsListView.getItems().clear();
        newContributorTextField.setText(null);
        rolesComboBox.getSelectionModel().clearSelection();
        tagsListView.getItems().clear();
        newTagTextField.setText(null);
        tagColorPicker.setValue(Color.WHITE);
    }

    private void controlRoleAccess(Board.Role role) {

        if (role.equals(Board.Role.owner)) {
            deleteBoardTitledPane.setDisable(false);
            deleteBoardTitledPane.setOpacity(1);
        } else {
            deleteBoardTitledPane.setDisable(true);
            deleteBoardTitledPane.setOpacity(0);
        }

        if (role.equals(Board.Role.viewer)) {

            newContributorTextField.setDisable(true);
            rolesComboBox.setDisable(true);
            addContributorButton.setDisable(true);
            newContributorBorderPane.setManaged(false);
            newContributorBorderPane.setOpacity(0);

            newTagTextField.setDisable(true);
            tagColorPicker.setDisable(true);
            addTagButton.setDisable(true);
            newTagBorderPane.setDisable(true);
            newTagBorderPane.setManaged(false);
            newTagBorderPane.setOpacity(0);

        } else {

            newContributorTextField.setDisable(false);
            rolesComboBox.setDisable(false);
            addContributorButton.setDisable(false);
            newContributorBorderPane.setManaged(true);
            newContributorBorderPane.setOpacity(1);

            newTagTextField.setDisable(false);
            tagColorPicker.setDisable(false);
            addTagButton.setDisable(false);
            newTagBorderPane.setDisable(false);
            newTagBorderPane.setManaged(true);
            newTagBorderPane.setOpacity(1);

        }

    }

}
