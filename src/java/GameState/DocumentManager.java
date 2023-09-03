package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import src.java.Drawable;
import src.java.GameState.Cows.Cow;
import src.java.Utilities.Input;
import src.java.Utilities.ResolutionManager;

public class DocumentManager implements Drawable{
    Input input = Input.globalInput;
    
    CheerioManager cheerioManager = CheerioManager.getGlobalCheerios();
    LinkedList<Document> applicants = new LinkedList<Document>();
    DocumentTimer documentTimer = new DocumentTimer();
    Trash trashCan = new Trash();
    PlayingField field;
    private int focusedIndex = -1;
    private boolean dragged = false;

    // draw constants
    private final int folderGap = 60; // gap between each folder
    private final int selectGap = 100; // additional gap on selected folder
    private final int selectTab = 40; // additional left potrusion size
    private final int marginLeft = 20;
    private final int marginTop = 140;
    private final int undraggable = marginLeft+selectTab + 80; // undraggable area (from left)

    DocumentManager(){}

    public void init(PlayingField field){
        this.field = field;
        applicants.add(new Document(Cow.WHEAT_CROP));
        applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        applicants.add(new Document(Cow.CEREAL_BOMB));
        applicants.add(new Document(Cow.CRUSHED_CEREAL));
        applicants.add(new Document(Cow.CEREAL_BOX));
    }

    public void addApplicant(Cow applicant){
        applicants.add(new Document(applicant));
    }

    public void update(){
        trashCan.update();
        documentTimer.update();

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
                        if (applicant.getSalary() <= cheerioManager.getCheerios()){
                            field.placeCow(applicant.hire(), input.mouseX(), input.mouseY());
                            removeIndex = i;
                            focusedIndex = -1;
                            cheerioManager.spendCheerios(applicant.getSalary());
                        }
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
                        if (applicant.getSalary() <= cheerioManager.getCheerios()){
                            field.placeCow(applicant.hire(), input.mouseX(), input.mouseY());
                            removeIndex = i;
                            focusedIndex = -1;
                            cheerioManager.spendCheerios(applicant.getSalary());
                        }
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
    

    private class DocumentTimer {
        int stage = 0;

        private final int[] nextStageDelays = {40, 1000, 1500, 2000, 2000, 3000, 3000, 3000, 3000 ,3000};
        private final int[] newDocWeight = {3, 6, 1, 1, 1, 1, 1, 1, 1, 1}; // chance of document appearing
        private int nextStageTimer = nextStageDelays[0];

            
        private final int newDocDelayMin = 200; // generate a number between delay and delay + rng - (stage*decrease) for new delay
        private final int newDocDelayRng = 300;
        private final int newDocDelayStageDecrease = 15; // stage  1: [200 , 500]
        private final int newDocDelayRngDecrease = 18;   // stage 10: [ 50 , 170]

        private int newDocTimer = newDocDelayMin;

        private void update(){

            if (stage < 10) {
                nextStageTimer--;
                if (nextStageTimer <= 0){
                    stage++;
                      
                    if (stage < 10){
                        nextStageTimer = nextStageDelays[stage];
                    }
                }
            }

            if (newDocTimer > 0){
                newDocTimer--;
            }

            if (applicants.size() < 10){
                if (newDocTimer <= 0){
                    // TODO: applicants.add new document( random cow [0, stage] ) --> somehow weighted random??
                    Document applicant = new Document(Cow.WHEAT_CROP);
                    applicant.setY(ResolutionManager.HEIGHT);
                    applicants.add(applicant);
                    newDocTimer = newDocDelayMin + (int)(Math.random() * (newDocDelayRng - newDocDelayRngDecrease*stage)) - (newDocDelayStageDecrease * stage);
                    System.out.println(newDocTimer);
                }
            }
        }
        
    }
}
