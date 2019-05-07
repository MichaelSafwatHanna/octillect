package octillect.models;

import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public class User implements IObserver {

    private String id;
    private String name;
    private String email;
    private String password;
    private Image image;
    private ObservableList<Board> boards;


    public User() {}

    public User(String id, String name, String email, String password,
                Image image, ObservableList<Board> boards) {
        this.id       = id;
        this.name     = name;
        this.email    = email;
        this.password = password;
        this.image    = image;
        this.boards = boards;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public ObservableList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ObservableList<Board> boards) {
        this.boards = boards;
    }

    @Override
    public void updateObserver() {
        /* UPDATE USER */
    }

}
