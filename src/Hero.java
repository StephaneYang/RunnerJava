public class Hero extends AnimatedThing {

    double invincibility;
    Hero(double BaseX, double BaseY) {
       super("C:\\imgRunner\\heros.png", BaseX, BaseY, 30, 10);
    }

    public int isInvincible (){
        if (invincibility > 0){
            invincibleAnimation = 1;
            return 1;
        }
        else {
            invincibleAnimation = 0;
            return 0;
        }
    }
}
