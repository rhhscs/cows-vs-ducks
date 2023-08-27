package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import src.java.Drawable;
import src.java.Utilities.Input;

public class DocumentManager implements Drawable{
    LinkedList<Document> applicants = new LinkedList<Document>();
    PlayingField field;
    private int focusedIndex = -1;
    private boolean dragged = false;
    Input input = Input.globalInput;

    DocumentManager(){}

    public void init(PlayingField field){
        this.field = field;
        applicants.add(new Document(null));
    }

    public void update(){
        int removeIndex = -1;
        for (int i = 0; i < applicants.size(); i++){
            Document applicant = applicants.get(i);
            if (focusedIndex == -1){
                if (input.mouseClicked() && applicant.containsPoint(input.mouseX(), input.mouseY())){
                    focusedIndex = i;
                    dragged = true;
                }
            } else {
                if (input.mouseClicked() && !field.isOccupied(input.mouseX(), input.mouseY())) {
                    field.placeCow(applicant.hire(), input.mouseX(), input.mouseY());
                    removeIndex = i;
                    focusedIndex = -1;
                }
            }
            

            if (dragged){
                if (input.mouseReleased()){
                    dragged = false;
                    focusedIndex = -1;
                    if (!field.isOccupied(input.mouseX(), input.mouseY())) {
                        field.placeCow(applicant.hire(), input.mouseX(), input.mouseY());
                        removeIndex = i;
                    }
                }
            }
        }

        for (int i = 0; i < applicants.size(); i++){
            Document applicant = applicants.get(i);
            if (i == focusedIndex){
                if (dragged){
                    applicant.setX(input.mouseX());
                    applicant.setY(input.mouseY());
                } else {
                    applicant.setX(40);
                    applicant.setY(i*40 + 10);
                }
            } else {
                applicant.setX(10);
                applicant.setY(i*40 + 10);
            }
        }

        if (removeIndex != -1){
            applicants.remove(removeIndex);
        }
    }

    @Override
    public void draw(Graphics g){
        for (Document applicant: applicants){
            applicant.draw(g);
        }
    }
    
}
