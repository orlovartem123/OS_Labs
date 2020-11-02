package OS_Lab_3;

public class Page {

    private int ramId = -1;

    public int getRamId() {
        return ramId;
    }

    public void setRamId(int value) {
        ramId = value;
    }

    private boolean isModified = false;

    public boolean getModified() {
        return isModified;
    }

    public void setModified(boolean value) {
        isModified = value;
        modifyStatus();
    }

    private boolean isRequest = false;

    public void setRequest(boolean value) {
        isRequest = value;
        modifyStatus();
    }

    private int status = 1;

    public int getStatus() {
        return status;
    }

    private void modifyStatus() {
        if (!isRequest && !isModified) {
            status = 1;
        }
        if (!isRequest && isModified) {
            status = 2;
        }
        if (isRequest && !isModified) {
            status = 3;
        }
        if (isRequest && isModified) {
            status = 4;
        }
    }
}
