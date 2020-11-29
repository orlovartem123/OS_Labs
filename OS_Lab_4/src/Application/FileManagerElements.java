package Application;

public interface FileManagerElements {

    void setName(String newName);

    String getName();

    void setParent(Folder parent);

    Folder getParent();

    String getType();

    void markSelected();
}
