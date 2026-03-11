// name surname: Ömer Faruk Karagöz
// student ID: 2023400255

import java.awt.*;
import java.util.Random;

public class Stage {

    //VARIABLES

    private int stageNumber;
    private double gravity;
    private double velocityX;
    private double velocityY;
    private int rightCode;
    private int leftCode;
    private int upCode;
    private String clue;
    private String help;
    private Color color;



    //CONSTRUCTOR
    public Stage(double gravity,double velocityX,double velocityY,
                 int stageNumber,int rightCode,int leftCode,int upCode,
                 String clue,String help){

        this.gravity = gravity;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.stageNumber = stageNumber;
        this.rightCode = rightCode;
        this.leftCode = leftCode;
        this.upCode = upCode;
        this.clue = clue;
        this.help = help;

        // Random background color for each stage
        Random randomColor = new Random();
        this.color = new Color(randomColor.nextInt(256), randomColor.nextInt(256), randomColor.nextInt(256));

    }

    public int getStageNumber(){

        return stageNumber;
    }
    public double getGravity(){

        return gravity;
    }
    public double getVelocityX(){

        return velocityX;
    }
    public double getVelocityY(){

        return velocityY;
    }
    public int[] getKeyCodes(){

        return new int[] {rightCode,leftCode,upCode};
    }
    public String getClue(){

        return clue;
    }
    public String getHelp(){

        return help;
    }
    public Color getColor(){

        return color;
    }


}
