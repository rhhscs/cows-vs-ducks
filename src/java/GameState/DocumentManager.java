package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import src.java.Drawable;
import src.java.GameState.Cows.Cow;
import src.java.Utilities.Input;
import src.java.Utilities.ResolutionManager;

public class DocumentManager implements Drawable{
    LinkedList<Document> applicants = new LinkedList<Document>();
    Trash trashCan = new Trash();
    PlayingField field;
    private int focusedIndex = -1;
    private boolean dragged = false;
    Input input = Input.globalInput;

    // draw constants
    private final int folderGap = 60; // gap between each folder
    private final int selectGap = 90; // additional gap on selected folder
    private final int selectTab = 40; // additional left potrusion size
    private final int marginLeft = 20;
    private final int marginTop = 140;
    private final int undraggable = marginLeft+selectTab + 80; // undraggable area (from left)


    DocumentManager(){}

    public void init(PlayingField field){
        this.field = field;
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
    }

    public void update(){
        trashCan.update();

        int removeIndex = -1;
        for (int i = 0; i < applicants.size(); i++){
            Document applicant = applicants.get(i);
            applicant.update();
            if (input.mouseClicked()){
                if (applicant.containsPoint(input.mouseX(), input.mouseY())){
                    focusedIndex = i;
                    dragged = true;
                } else if (i == focusedIndex && !dragged){
                    if (field.isOccupied(input.mouseX(), input.mouseY())) {
                        focusedIndex = -1;
                    } else {
                        field.placeCow(applicant.hire(), input.mouseX(), input.mouseY());
                        removeIndex = i;
                        focusedIndex = -1;
                    }
                }
            }
            

            if (dragged && focusedIndex == i){
                if (trashCan.containsPoint(input.mouseX(), input.mouseY())){
                    trashCan.moveTo(ResolutionManager.HEIGHT-300);
                } else {
                    trashCan.moveTo(ResolutionManager.HEIGHT-150);
                }
                if (input.mouseReleased()){
                    dragged = false;
                    if (!field.isOccupied(input.mouseX(), input.mouseY())) {
                        field.placeCow(applicant.hire(), input.mouseX(), input.mouseY());
                        removeIndex = i;
                        focusedIndex = -1;
                    } else if (trashCan.containsPoint(input.mouseX(), input.mouseY())){
                        removeIndex = i;
                        focusedIndex = -1;
                        trashCan.moveTo(ResolutionManager.HEIGHT-150);
                    }
                }
            }
        }

        for (int i = 0; i < applicants.size(); i++){
            Document applicant = applicants.get(i);
            if (i == focusedIndex){
                if (dragged && input.mouseX() > undraggable){
                    applicant.setX(input.mouseX());
                    applicant.setY(input.mouseY());
                } else {
                    applicant.moveTo(marginLeft+selectTab, i*folderGap + marginTop);
                }
            } else {
                if (i < focusedIndex || focusedIndex == -1){
                    applicant.moveTo(marginLeft, i*folderGap + marginTop);
                } else {
                    applicant.moveTo(marginLeft, i*folderGap + marginTop + selectGap);
                }
            }
        }

        if (removeIndex != -1){
            applicants.remove(removeIndex);
        }
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, undraggable, ResolutionManager.HEIGHT);
        for (Document applicant: applicants){
            applicant.draw(g);
        }

        trashCan.draw(g);
    }
    
}
