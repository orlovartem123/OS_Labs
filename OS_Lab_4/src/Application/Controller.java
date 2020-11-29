package Application;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Optional;


public class Controller {

    @FXML
    private Canvas cdView;

    @FXML
    private ListView<HBox> listBoxFiles;

    @FXML
    private TextField textFieldName;

    private FileManager fm;

    private Folder currentFolder;

    private FileManagerElements buffer;

    private void Draw() {
        GraphicsContext gr = cdView.getGraphicsContext2D();
        gr.clearRect(0, 0, cdView.getWidth(), cdView.getHeight());
        for (int i = 0; i < Disc.memory.length; i++) {
            if (Disc.memory[i] == -1) {
                gr.setFill(Color.GRAY);
            }
            if (Disc.memory[i] == 1) {
                gr.setFill(Color.BLUE);
            }
            if (Disc.memory[i] == 2) {
                gr.setFill(Color.RED);
            }
            if (Disc.memory[i] == 3) {
                gr.setFill(Color.GREEN);
            }
            int i1 = i / ((int) cdView.getWidth() / Disc.drawSize);
            int j1 = i % ((int) cdView.getWidth() / Disc.drawSize);
            gr.fillRect(j1 * Disc.drawSize, i1 * Disc.drawSize, Disc.drawSize, Disc.drawSize);
        }
    }


    private void ReloadLevels() {
        int index = listBoxFiles.getSelectionModel().getSelectedIndex();
        listBoxFiles.getItems().clear();
        ArrayList<Folder> folders;
        ArrayList<File> files;
        if (currentFolder == null) {
            folders = fm.getFolders();
            files = fm.getFiles();
        } else {
            folders = currentFolder.getChildrenFolders();
            files = currentFolder.getChildrenFiles();
        }
        for (int i = 0; i < folders.size(); i++) {
            HBox box = new HBox();
            box.setSpacing(5);
            Label textLabel = new Label(folders.get(i).getName());
            Label iconLabel = new Label();
            iconLabel.setGraphic(new ImageView(new Image("Application//images//folder.png")));
            box.getChildren().addAll(iconLabel, textLabel);
            listBoxFiles.getItems().add(box);
        }
        for (int i = 0; i < files.size(); i++) {
            HBox box = new HBox();
            box.setSpacing(5);
            Label textLabel = new Label(files.get(i).getName());
            Label iconLabel = new Label();
            iconLabel.setGraphic(new ImageView(new Image("Application//images//file.png")));
            box.getChildren().addAll(iconLabel, textLabel);
            listBoxFiles.getItems().add(box);
        }
        if (listBoxFiles.getItems().size() > 0 && (index == -1 || index >= listBoxFiles.getItems().size())) {
            listBoxFiles.getSelectionModel().selectFirst();
        } else if (listBoxFiles.getItems().size() > 0 && index > -1 && index < listBoxFiles.getItems().size()) {
            listBoxFiles.getSelectionModel().select(index);
        }

    }

    @FXML
    void buttonCreateFolderClick() {
        if (textFieldName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Enter folder or file name");
            alert.showAndWait();
            return;
        }
        Folder newFolder = new Folder(currentFolder, textFieldName.getText());
        if (fm.addFolder(currentFolder, newFolder)) {
            ReloadLevels();
            Draw();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Memory is full");
            alert.showAndWait();
        }
    }

    @FXML
    void buttonCreateFileClick() {
        if (textFieldName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Enter folder or file name");
            alert.showAndWait();
            return;
        }
        File newFile = new File(currentFolder, textFieldName.getText());
        if (fm.addFile(currentFolder, newFile, false)) {
            ReloadLevels();
            Draw();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Memory is full");
            alert.showAndWait();
        }
    }

    @FXML
    void buttonGoInClick() {
        int index = listBoxFiles.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        if (currentFolder == null) {
            if (index >= fm.getFolders().size()) {
                return;
            }
            currentFolder = fm.getFolders().get(index);
        } else {
            if (index >= currentFolder.getChildrenFolders().size()) {
                return;
            }
            currentFolder = currentFolder.getChildrenFolders().get(index);
        }
        ReloadLevels();
    }

    @FXML
    void buttonBackClick() {
        if (currentFolder == null) {
            return;
        }
        currentFolder = currentFolder.getParent();
        ReloadLevels();
    }

    @FXML
    void buttonDeleteClick() {
        int index = listBoxFiles.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        Label label = (Label) listBoxFiles.getItems().get(index).getChildren().get(1);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText("Delete " + label.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK && index >= 0) {
            fm.deleteFile(currentFolder, index);
            ReloadLevels();
        }
    }

    @FXML
    void buttonCopyClick() {
        int index = listBoxFiles.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        buffer = fm.getElement(currentFolder, index);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setHeaderText(null);
        alert.setContentText("Copied!");
        alert.showAndWait();
    }

    @FXML
    void buttonCutClick() {
        int index = listBoxFiles.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        buffer = fm.getElement(currentFolder, index);
        fm.deleteFile(currentFolder, index);
        ReloadLevels();
    }

    @FXML
    void buttonPasteClick() {
        if (buffer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Buffer is empty");
            alert.showAndWait();
            return;
        }
        if (buffer.getType().equals("Folder")) {
            Folder folder = (Folder) buffer;
            if (fm.addFolder(currentFolder, folder.getCopy())) {
                ReloadLevels();
            }
        } else {
            File file = (File) buffer;
            if (fm.addFile(currentFolder, file.getCopy(), true)) {
                ReloadLevels();
            }
        }
        buffer = null;
    }

    @FXML
    void initialize() {
        currentFolder = null;
        fm = new FileManager(256, (int) cdView.getWidth(), (int) cdView.getHeight());
        Draw();
        listBoxFiles.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals(-1)) {
                int index = listBoxFiles.getSelectionModel().getSelectedIndex();
                if (index < 0) {
                    return;
                }
                fm.markSelected(fm.getElement(currentFolder, index));
                Draw();
            }
        });
    }
}
