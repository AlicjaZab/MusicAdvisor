package advisor;

import java.util.ArrayList;

public class View {
    static int elements = 5; //number of element on one page
    ArrayList<ArrayList> pages;
    int actualPage;

    View(){
        actualPage = 0;
    };


    public void createView(ArrayList full_output){
        actualPage = 0;
        pages = new ArrayList();
        if(full_output.size() > elements){
            int pagesNum = full_output.size() / elements;
            pagesNum = full_output.size() % elements == 0 ? pagesNum : pagesNum + 1;
            int k = 0;
            for(int i = 0; i < pagesNum; i++) {
                pages.add(new ArrayList());
                for(int j = 0; j < elements; j++) {
                    if(k < full_output.size()){
                        pages.get(i).add(full_output.get(k));
                        k++;
                    }
                }
            }
        }
    }

    public void print(String option){
        switch (option){
            case "next":
                if (actualPage == pages.size() - 1){
                    System.out.println("No more pages.");
                    return;
                }
                actualPage++;
                break;
            case "prev":
                if (actualPage == 0){
                    System.out.println("No more pages.");
                    return;
                }
                actualPage--;
                break;
        }
        for(int i = 0; i < pages.get(actualPage).size(); i++){
            System.out.println(pages.get(actualPage).get(i));
        }
        System.out.println("---PAGE " + (actualPage + 1) + " OF " + (pages.size()) + "---");
    }
}
