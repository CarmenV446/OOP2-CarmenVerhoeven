import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    private int nrStepsTaken = 0;
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
        
    }

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
            nrStepsTaken++;
            System.out.println(nrStepsTaken);
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead()){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if (onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }

    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;
            System.out.println("moved " + nrStepsTaken );// increment the counter
        }
    }

    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdge(){
        while( ! borderAhead() ){
            move();
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if( onEgg() ){
            return false;
        }else{
            return true;
        }
    }  

    public void turn180(){
        turnRight();
        turnRight();
    }

    public void climbOverFence(){
        if (fenceAhead()){
            turnLeft();
            move();
            turnRight();
            move();
            move();
            turnRight();
            move();
            turnLeft();
        }
    }

    public boolean grainAhead(){
        move();
        if (onGrain()){
            stepOneCellBackwards(); 
            return true;

        }
        else{
            stepOneCellBackwards();
            return false;
        }
    }

    public void stepOneCellBackwards(){
        turn180();
        move();
        turn180();
    }

    public void gotoEgg(){
        while (!onEgg()){
            move();
        }
    }

    public void goBackToStartOfRowAndFaceBack(){
        turn180();
        walkToWorldEdge();
        turn180();
    }

    public void walkToWorldEdgeClimbingOverFences(){
        while (!borderAhead()){
            if (fenceAhead()){
                climbOverFence();
            }
            else if (onNest()){
                layEgg();
                move();
            }
            else {
                move();
            }
        }
    }
    
    public void walkAroundFencedArea(){
        while (!onEgg()){
            if(!fenceAhead()){
                turnRight();
            }
            if (fenceAhead()== true){
                turnLeft();
                move();
            }
            else{
                move();
            }
            
            
        }
    }
    
    public void eggTrailToNest(){
        while (!onNest()){
            if (eggAhead()){
                move();
                pickUpEgg();
            }
            else {
                turnRight();
            }
            if (nestAhead()){
                move();
            }
        }
    }
    
    public void mazeTrailToNest(){
        while (!onNest()){
            if (fenceAhead() || eggAhead()){
                turnRight();
            }
            else{
                layEgg();
                move();
            }
        }
    }

    public void faceEast(){
        setDirection(EAST);
    }
    
    public void moveRandomly(){
        int nrStepsTaken = 0;
        while (nrStepsTaken < Mauritius.MAXSTEPS){
            if (fenceAhead() || borderAhead()){
                turnRight();
        }
            else {
                setDirection(randomDirection());
                move();
                nrStepsTaken++;
        }
    }
    }
    
    public void checkForEgg(){
        int turnNr = 0;
        while (turnNr < 4)
        if (eggAhead()){
            move();
            hatchEgg();
        }
        else {
            turnRight();
            turnNr++;

        }
        
    
    }
    
    public void playGame(){
        while (nrStepsTaken < Mauritius.MAXSTEPS){
            if (onEgg()){
                hatchEgg();
            }
            else{
            checkForEgg();
            moveRandomly();
        }
    }
        
    }
    
        public List<BlueEgg> getListOfBlueEggs() {
        return getWorld().getObjects(BlueEgg.class);
    }
    
        public GoldenEgg findGoldenEgg() {
        List<GoldenEgg> goldenEggs = getWorld().getObjects(GoldenEgg.class);
        return goldenEggs.get(0);
    }
    

    
}

    


