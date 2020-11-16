package OS_Lab_3;

public class Page {

    private int ramId = -1;

    public int getRamId() {
        return ramId;
    }

    public void setRamId(int value) {
        ramId = value;
    }

    private boolean isOnHdd = false;

    public boolean getOnHdd() {
        return isOnHdd;
    }

    public void setOnHdd(boolean value) {
        isOnHdd = value;
        //modifyStatus();
    }

    private boolean isRequest = false;

    public void setRequest(boolean value) {
        isRequest = value;
        if(value){
            status=1;
        }else{
            status=0;
        }
        //modifyStatus();
    }

    private int status = 0;

    public int getStatus() {
        return status;
    }

    //private void modifyStatus() {
        //if (!isRequest && !isOnHdd) {
            //status = 1;
        //}
        //if (!isRequest && isOnHdd) {
            //status = 2;
        //}
        //if (isRequest && !isOnHdd) {
            //status = 3;
        //}
        //if (isRequest && isOnHdd) {
            //status = 4;
        //}
    //}
}
