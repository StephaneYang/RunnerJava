public class Camera {
    static private double x, y, vx, vy, ax, ay, dt, sqrtwo, sqrtwo1;
    static private int viewOffset;

    Camera (double x, double y){
        viewOffset = 0;
        ax=0;
        vx=0;
        ay=0;
        vy=0;
        Camera.x =x;
        Camera.y =y;
        dt = 0.016;
        sqrtwo = 5;//paramètre qui influence la rapidité de la caméra à se recentrer sur le héros (+élevé, +rapide)
        sqrtwo1 = 0.1;
    }

    public static void update(long time, Hero hero) {
        double xHero = GameScene.camX;
        double yHero = GameScene.camY + viewOffset;
        if ((xHero -x)<-700) x -= 800;//réajustement de la position lorsque le héros revient au debut de la map
                                     //xHero est réduit de 800, donc il faut que x soit réduit de 800 aussi afin de
                                     //garder l'écart réelle et éviter de fausser les calculs
        ax = sqrtwo*sqrtwo*(xHero -x) - 2*1*sqrtwo*vx; //équation ressort-masse , amortissement = 1 (pas de dépassemnt)
        ay = sqrtwo*sqrtwo*(yHero -y) - 2*0.7*sqrtwo*vy; //amortissement = 0.7 (meilleur temps de réponse)
        if (hero.isJumping == 1) {//monte moins vite que descendre
            ay = sqrtwo1*sqrtwo1*(yHero -y) - 2*1*sqrtwo1*vy;
            viewOffset = 0;
        } else if (hero.isFalling == 1) {
            viewOffset = 80; //décalage vers en bas lorsque le héros tombe afin de voir en avance ce qu'il y a en bas
        } else {
            viewOffset = 0;
        }
        double dvx = ax * dt;
        vx += dvx;//vitesse en pixels par secondes
        double dvy = ay * dt;
        vy += dvy;

        double dx = vx * dt;
        x += dx;//position calculée (décalée ou pas par rapport au héros)
        double dy = vy * dt;
        y += dy;

    }

    public double getX(){
        return x;
    }

    public double getY(){
        if(y<0){
            return 0;
        } else {
            return Math.min(y, 400 - GameScene.windowY);//limitation pour ne pas afficher ce qui est en dehors du décors
        }
    }

    public double getVX(){
        return vx;
    }

    public double getAX(){
        return ax;
    }

    @Override
    public String toString (){
        System.out.println(x+", "+ y);
        return null;
    }
}
