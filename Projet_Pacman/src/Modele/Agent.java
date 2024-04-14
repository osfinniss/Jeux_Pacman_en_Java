package Modele;

public abstract class  Agent  {
    
    private PositionAgent position;
    private Strategie strategie;

    public Agent(PositionAgent position , Strategie s) {

        this.position = position;
        this.strategie = s;
    }

    public PositionAgent getPosition() {
        return position;
    }


    public void setPosition(PositionAgent position) {
        this.position = position;
    }

    public Strategie getStrategie(){
        return this.strategie;
    }
    public void setStrategie(Strategie st){
        this.strategie = st;
    }
   
}