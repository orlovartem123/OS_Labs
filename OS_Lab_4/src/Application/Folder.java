package Application;

import java.util.ArrayList;

public class Folder implements FileManagerElements {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    private String type = "Folder";

    public String getType() {
        return type;
    }

    private Folder parent;

    public Folder getParent() {
        return parent;
    }

    private int[] folderSize = new int[1];

    public int[] getFolderSize() {
        return folderSize;
    }

    public void setFolderSize(int[] newFolderSize) {
        folderSize = newFolderSize;
    }

    public void insertInFolderSize(int index, int value) {
        folderSize[index] = value;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    private ArrayList<File> childrenFiles;

    public ArrayList<File> getChildrenFiles() {
        return childrenFiles;
    }

    public void setChildrenFiles(ArrayList<File> newChildrenFiles) {
        childrenFiles = newChildrenFiles;
    }

    private ArrayList<Folder> childrenFolders;

    public ArrayList<Folder> getChildrenFolders() {
        return childrenFolders;
    }

    public void setChildrenFolders(ArrayList<Folder> newChildrenFolders) {
        childrenFolders = newChildrenFolders;
    }

    public Folder(Folder parent, String name) {
        this.parent = parent;
        this.name = name;
        childrenFiles = new ArrayList<>();
        childrenFolders = new ArrayList<>();
        for (int i = 0; i < folderSize.length; i++) {
            folderSize[i] = -1;
        }
    }

    public void addFolder(Folder newFolder) {
        childrenFolders.add(newFolder);
    }

    public void addFile(File file) {
        childrenFiles.add(file);
    }

    public void clearMemory() {
        for (int i = 0; i < folderSize.length; i++) {
            if (folderSize[i] >= 0) {
                Disc.memory[folderSize[i]] = -1;
            }
        }
        for (int i = 0; i < childrenFolders.size(); i++) {
            if (childrenFolders.get(i) == null) {
                return;
            }
            childrenFolders.get(i).clearMemory();
        }
        for (int i = 0; i < childrenFiles.size(); i++) {
            if (childrenFiles.get(i) == null) {
                return;
            }
            childrenFiles.get(i).clearMemory();
        }
    }

    public Folder getCopy() {
        Folder copy = new Folder(parent, name);
        ArrayList<File> files = new ArrayList<>();
        int sizeFiles = childrenFiles.size();
        for (int i = 0; i < sizeFiles; i++) {
            files.add(i, childrenFiles.get(i).getCopy());
            files.get(i).setParent(copy);
        }
        ArrayList<Folder> folders = new ArrayList<>();
        int sizeFolders = childrenFolders.size();
        for (int i = 0; i < sizeFolders; i++) {
            int[] folderSize = childrenFolders.get(i).getFolderSize();
            folders.add(i, childrenFolders.get(i).getCopy());
            folders.get(i).setFolderSize(folderSize);
            folders.get(i).setParent(copy);
        }
        copy.setChildrenFiles(files);
        copy.setChildrenFolders(folders);
        return copy;
    }

    public void markSelected() {
        for (int i = 0; i < folderSize.length; i++) {
            if (folderSize[i] >= 0) {
                Disc.memory[folderSize[i]] = 2;
            }
        }
        for (int i = 0; i < childrenFolders.size(); i++) {
            if (childrenFolders.get(i) == null) {
                return;
            }
            childrenFolders.get(i).markSelected();
        }
        for (int i = 0; i < childrenFiles.size(); i++) {
            if (childrenFiles.get(i) == null) {
                return;
            }
            childrenFiles.get(i).markSelected();
        }
    }

}
