package Application;

import java.util.ArrayList;
import java.util.Random;

public class File implements FileManagerElements {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    private String type = "File";

    public String getType() {
        return type;
    }

    private Folder parent;

    public Folder getParent() {
        return parent;
    }

    private int[] fileSize = new int[1];

    public int[] getFileSize() {
        return fileSize;
    }

    public void setFileSize(int[] newFileSize) {
        fileSize = newFileSize;
    }

    public void insertInFileSize(int index, int value) {
        fileSize[index] = value;
    }


    public void setParent(Folder parent) {
        this.parent = parent;
    }

    private ArrayList<Integer> iNode;

    public ArrayList<Integer> getINode() {
        return iNode;
    }

    public void setINode(ArrayList<Integer> newINode) {
        iNode = newINode;
    }

    public File(Folder parent, String name) {
        this.name = name;
        this.parent = parent;
        Random rnd = new Random();
        iNode = new ArrayList<>();
        int size = rnd.nextInt(16);
        for (int i = 0; i < size; i++) {
            iNode.add(null);
        }
        for (int i = 0; i < fileSize.length; i++) {
            fileSize[i] = -1;
        }
    }

    public File getCopy() {
        File file = new File(parent, name);
        ArrayList<Integer> newINode = new ArrayList<>();
        int[] fileSize = new int[this.fileSize.length];
        for (int i = 0; i < fileSize.length; i++) {
            fileSize[i] = this.fileSize[i];
        }
        file.setFileSize(fileSize);
        int sizeINode = iNode.size();
        for (int i = 0; i < sizeINode; i++) {
            newINode.add(i, iNode.get(i));
        }
        file.setINode(newINode);
        return file;
    }

    public void clearMemory() {
        for (int i = 0; i < fileSize.length; i++) {
            if (fileSize[i] >= 0) {
                Disc.memory[fileSize[i]] = -1;
            }
        }
        for (int i = 0; i < iNode.size(); i++) {
            if (iNode.get(i) == null) {
                return;
            }
            if (iNode.get(i) >= 0) {
                Disc.memory[iNode.get(i)] = -1;
            }
        }
    }

    public void markSelected() {
        for (int i = 0; i < fileSize.length; i++) {
            if (fileSize[i] >= 0) {
                Disc.memory[fileSize[i]] = 2;
            }
        }
        for (int i = 0; i < iNode.size(); i++) {
            if (iNode.get(i) == null) {
                return;
            }
            if (iNode.get(i) >= 0) {
                Disc.memory[iNode.get(i)] = 2;
            }
        }
    }
}
