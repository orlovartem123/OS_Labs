package Application;

import java.util.ArrayList;

public class FileManager {

    private int cdSize;

    private int serialNum = 1;

    private int cdDrawSize;

    private ArrayList<Folder> folders;

    public ArrayList<Folder> getFolders() {
        return folders;
    }

    private ArrayList<File> files;

    public ArrayList<File> getFiles() {
        return files;
    }

    public FileManager(int cdSize, int canvasWidth, int canvasHeight) {
        this.cdDrawSize = (int) Math.sqrt(canvasHeight * canvasWidth / cdSize);
        this.cdSize = cdSize;
        Disc.setMemory(cdSize, cdDrawSize);
        System.out.print(Disc.memory.length);
        for (int i = 0; i < Disc.memory.length; i++) {
            Disc.memory[i] = -1;
        }
        files = new ArrayList<>();
        folders = new ArrayList<>();
    }

    private boolean checkFreeFileName(File file, ArrayList<File> names) {
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getName().equals(file.getName())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFreeFoldersName(Folder file, ArrayList<Folder> names) {
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).getName().equals(file.getName())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkFreeSpace(int size) {
        int freeItems = 0;
        for (int i = 0; i < Disc.memory.length; i++) {
            if (Disc.memory[i] == -1) {
                freeItems++;
                if (freeItems >= size) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getFreeItem() {
        for (int i = 0; i < Disc.memory.length; i++) {
            if (Disc.memory[i] == -1) {
                Disc.memory[i] = 1;
                return i;
            }
        }
        return -1;
    }

    public boolean addFolder(Folder currentFolder, Folder newFolder) {
        int size = newFolder.getFolderSize().length;
        if (!checkFreeSpace(size)) {
            return false;
        }
        resetLastUpdates();
        for (int i = 0; i < size; i++) {
            newFolder.insertInFolderSize(i, getFreeItem());
        }
        if (currentFolder == null) {
            if (!checkFreeFoldersName(newFolder, folders)) {
                newFolder.setName(newFolder.getName() + "-Copy(" + serialNum + ')');
                while (!checkFreeFoldersName(newFolder, folders)) {
                    serialNum++;
                    newFolder.setName(newFolder.getName().substring(0, newFolder.getName().indexOf('(')) + '(' + serialNum + ')');
                }
            }
            serialNum = 1;
            newFolder.setParent(null);
            folders.add(newFolder);
        } else {
            if (!checkFreeFoldersName(newFolder, currentFolder.getChildrenFolders())) {
                newFolder.setName(newFolder.getName() + "-Copy(" + serialNum + ')');
                while (!checkFreeFoldersName(newFolder, currentFolder.getChildrenFolders())) {
                    serialNum++;
                    newFolder.setName(newFolder.getName().substring(0, newFolder.getName().indexOf('(')) + '(' + serialNum + ')');
                }
            }
            serialNum = 1;
            newFolder.setParent(currentFolder);
            currentFolder.addFolder(newFolder);
        }
        return true;
    }

    public boolean addFile(Folder currentFolder, File newFile, boolean isCopying) {
        int size = newFile.getINode().size();
        int[] fileSize = newFile.getFileSize();
        if (!checkFreeSpace(size + fileSize.length)) {
            return false;
        }
        resetLastUpdates();
        for (int i = 0; i < fileSize.length; i++) {
            newFile.insertInFileSize(i, getFreeItem());
        }
        if (!isCopying) {
            for (int i = 0; i < size; i++) {
                newFile.getINode().add(i, getFreeItem());
            }
        }
        if (currentFolder == null) {
            if (!checkFreeFileName(newFile, files)) {
                newFile.setName(newFile.getName() + "-Copy(" + serialNum + ')');
                while (!checkFreeFileName(newFile, files)) {
                    serialNum++;
                    newFile.setName(newFile.getName().substring(0, newFile.getName().indexOf('(')) + '(' + serialNum + ')');
                }
            }
            serialNum = 1;
            newFile.setParent(null);
            files.add(newFile);
        } else {
            if (!checkFreeFileName(newFile, currentFolder.getChildrenFiles())) {
                newFile.setName(newFile.getName() + "-Copy(" + serialNum + ')');
                while (!checkFreeFileName(newFile, currentFolder.getChildrenFiles())) {
                    serialNum++;
                    newFile.setName(newFile.getName().substring(0, newFile.getName().indexOf('(')) + '(' + serialNum + ')');
                }
            }
            serialNum = 1;
            newFile.setParent(currentFolder);
            currentFolder.addFile(newFile);
        }
        return true;
    }

    public void deleteFile(Folder currentFolder, int index) {
        ArrayList<File> files;
        ArrayList<Folder> folders;
        if (currentFolder == null) {
            files = this.files;
            folders = this.folders;
        } else {
            files = currentFolder.getChildrenFiles();
            folders = currentFolder.getChildrenFolders();
        }
        if (index >= folders.size()) {
            files.get(index - folders.size()).clearMemory();
            files.remove(index - folders.size());
        } else {
            folders.get(index).clearMemory();
            folders.remove(index);
        }
    }

    public void markSelected(FileManagerElements element) {
        for (int i = 0; i < Disc.memory.length; i++) {
            if (Disc.memory[i] == 2) {
                Disc.memory[i] = 3;
            }
        }
        element.markSelected();
    }

    public void resetLastUpdates() {
        for (int i = 0; i < Disc.memory.length; i++) {
            if (Disc.memory[i] == 1) {
                Disc.memory[i] = 3;
            }
        }
    }

    public FileManagerElements getElement(Folder currentFolder, int index) {
        ArrayList<Folder> folders;
        ArrayList<File> files;
        if (currentFolder == null) {
            folders = this.folders;
            files = this.files;
        } else {
            files = currentFolder.getChildrenFiles();
            folders = currentFolder.getChildrenFolders();
        }
        if (index >= folders.size()) {
            return files.get(index - folders.size());
        }
        return folders.get(index);
    }
}
