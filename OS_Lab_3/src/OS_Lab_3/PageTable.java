package OS_Lab_3;

import java.util.LinkedList;
import java.util.Random;

public class PageTable {

    private LinkedList<Page> pageList;

    public PageTable(LinkedList<Page> pageList) {
        this.pageList = pageList;
    }

    public Page getPage(int pageId) {
        return pageList.get(pageId);
    }

    //page with id stay without changes
    public void resetPageRequest(int id) {
        Random rnd = new Random();
        for (int i = 0; i < pageList.size(); i++) {
            if (i != id && rnd.nextInt(2) == 1) {
                pageList.get(i).setRequest(false);
            }
        }
    }

}
