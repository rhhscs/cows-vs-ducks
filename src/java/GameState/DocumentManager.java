package src.java.GameState;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;

import src.java.Drawable;
import src.java.GameState.Cows.Cow;
import src.java.GameState.Cows.StackableCow;
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
        applicants.add(new Document(Cow.WHEAT_CROP));
        applicants.add(new Document(Cow.WHEAT_CROP));
        //applicants.add(new Document(Cow.CHEERIO_CATAPULT));
        //applicants.add(new Document(Cow.CEREAL_BOMB));
        //applicants.add(new Document(Cow.CRUSHED_CEREAL));
        //applicants.add(new Document(Cow.CEREAL_BOX));
        //applicants.add(new Document(Cow.COLD_FRIDGE));
        //applicants.add(new Document(Cow.CHEERIO_PITCHER));
        //applicants.add(new Document(Cow.PEA_POD));
        //applicants.add(new Document(Cow.PEA_POD));
        //applicants.add(new Document(Cow.PEA_POD));
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
                            if(!(field.getTileAt(input.mouseX(), input.mouseY()).getCow() instanceof StackableCow &&
                            !(applicant.hire() instanceof StackableCow))){
                                field.placeCow(applicant.hire(), input.mouseX(), input.mouseY());
                                removeIndex = i;
                                focusedIndex = -1;
                                cheerioManager.spendCheerios(applicant.getSalary());
                            }
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
                            if(!(field.getTileAt(input.mouseX(), input.mouseY()).getCow() instanceof StackableCow &&
                            !(applicant.hire() instanceof StackableCow))){
                                field.placeCow(applicant.hire(), input.mouseX(), input.mouseY());
                                removeIndex = i;
                                focusedIndex = -1;
                                cheerioManager.spendCheerios(applicant.getSalary());
                            }
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
        /* 0 | wheat    -  low-mid chance   |    0
         * 1 | cowapult -  high chance      |   40
         * 2 | wall     -  mid chance       |  800
         * 3 | freezer  -  low-mid chance   | 2000
         * 4 | pea pod  -  mid chance       | 2500
         * 5 | spikes   -  mid chance
         * 6 | kaboom   -  low chance
         * 
         * 10, 12, 14, 16, 18
         */
        private final int LOW_CHANCE = 5;
        private final int LOWMED_CHANCE = 7;
        private final int MED_CHANCE = 10;
        private final int MEDHI_CHANCE = 12;
        private final int HI_CHANCE = 15;

        private final int applicantTypes;
        private final Cow[] cows =     { Cow.WHEAT_CROP, Cow.CHEERIO_CATAPULT, Cow.CEREAL_BOX, Cow.COLD_FRIDGE, Cow.PEA_POD, Cow.CRUSHED_CEREAL, Cow.CEREAL_BOMB};
        private int[] newDocWeight =   { LOWMED_CHANCE , HI_CHANCE           , MED_CHANCE    , LOWMED_CHANCE  , MED_CHANCE,  MED_CHANCE,         LOW_CHANCE}; // chance of document appearing
        private final int[] nextStageDelays =  {40, 760, 1200, 500, 500, 1000};
        private int nextStageTimer = nextStageDelays[0];

        
        private final int newDocDelayMin = 150; // generate a number between delay and delay + rng - (stage*decrease) for new delay
        private final int newDocDelayRng = 250;
        private final int newDocDelayStageDecrease = 10; // stage  1: [150 , 400]
        private final int newDocDelayRngDecrease = 13;   // stage 10: [ 50 , 170]

        private int newDocTimer = newDocDelayMin;
        private boolean isFirst = true;

        DocumentTimer(){
            applicantTypes = cows.length;
            for (int i = 1; i < applicantTypes; i ++){
                newDocWeight[i] += newDocWeight[i-1];
            }
        }

        private void update(){

            if (stage < applicantTypes-1) {
                nextStageTimer--;
                if (nextStageTimer <= 0){
                    stage++;
                    
                    if (stage < applicantTypes-1) {
                        nextStageTimer = nextStageDelays[stage];
                    }
                    //System.out.println(stage + " -" + nextStageDelays[stage] + "-> " + (stage+1));
                      
                }
            }

            if (newDocTimer > 0){
                newDocTimer--;
            }

            if (applicants.size() < 10){
                if (newDocTimer <= 0){
                    Document applicant;
                    if (isFirst){
                        applicant = new Document(Cow.CHEERIO_CATAPULT);
                        isFirst = false;
                    } else {
                        applicant = getRandomDocument(stage);
                    }
                    if (applicant == null){
                        System.err.println("Document RNG error");
                    } else {
                        applicant.setY(ResolutionManager.HEIGHT);
                        applicants.add(applicant);
                        newDocTimer = newDocDelayMin + (int)(Math.random() * (newDocDelayRng - newDocDelayRngDecrease*stage)) - (newDocDelayStageDecrease * stage);
                    }

                }
            }
        }

        private Document getRandomDocument(int endIndex){
            int rng = (int)(Math.random()*newDocWeight[endIndex]);
            for (int i = 0; i < endIndex+1; i++){
                if (rng < newDocWeight[i]){
                    return new Document(cows[i]);
                }
            }
            return null;
        }
        
    }
}
