public class Camera {
    static private double x, y, vx, vy, ax, ay, xHero, yHero, dx, dy, dvx, dvy, dt, sqrtwo, sqrtwo1;

    Camera (double x, double y){
        this.ax=0;
        this.vx=0;
        this.ay=0;
        this.vy=0;
        this.x=x;
        this.y=y;
        this.dt = 0.016;
        this.sqrtwo = 5;//paramètre qui influence la rapidité de la caméra à se recentrer sur le hero (+élevé, +rapide)
        this.sqrtwo1 = 0.7;
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getVX(){
        return this.vx;
    }
    public double getAX(){
        return this.ax;
    }

    @Override
    public String toString (){
        System.out.println(this.x+", "+this.y);
        return null;
    }

    public static void update(long time, Hero hero) {
        xHero = GameScene.camX;
        yHero = GameScene.camY;
        if ((xHero-x)<-700) x -= 800;//réajustement de la position lorsque le héros revient au debut de la map
                                     //xHero est réduit de 800, donc il faut que x soit réduit de 800 aussi afin de
                                     //garder l'écart réelle et éviter de fausser les calculs
        ax = sqrtwo*sqrtwo*(xHero-x) - 2*1*sqrtwo*vx; //équation ressort-masse , amortissement = 1 (pas de dépassemnt)
        if(hero.isJumping == 1) {//monte moins vite que descendre
            ay = sqrtwo1*sqrtwo1*(yHero-y) - 2*0.7*sqrtwo1*vy; //amortissement = 0.7 (meilleur temps de réponse)
        } else {
            ay = sqrtwo*sqrtwo*(yHero-y) - 2*1*sqrtwo*vy; //amortissement = 1
        }

        dvx = ax*dt;
        vx += dvx;//vitesse en pixels par secondes
        dvy = ay*dt;
        vy += dvy;

        dx = vx*dt ;
        x += dx;//position calculée (décalée ou pas par rapport au héro)
        dy = vy*dt ;
        y += dy;

    }
}
