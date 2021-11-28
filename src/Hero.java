public class Hero extends AnimatedThing {

    double invincibility, shootCD;
    Hero(double BaseX, double BaseY) {
       super("imgRunner/heros.png", BaseX, BaseY, 35, 10);
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

    public int canShoot (){
        if (shootCD > 0){
            return 0;
        }
        else {
            return 1;
        }
    }
}
