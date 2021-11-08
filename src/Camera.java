public class Camera {
    static private double x, oldx, y, vx, oldvx, ax, xHero, yHero;

    Camera (double x, double y){
        this.ax=0;
        this.vx=0;
        this.oldx=0;
        this.oldvx=0;
        this.x=x;
        this.y=y;
    }

    public double getX(){
        return this.x;
    }
    public double getOldX(){
        return this.oldx;
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

        x = xHero - (ax - 0.1*vx)*0.00006;
        vx = (x - oldx)/0.016; //vitesse en pixels par secondes
        if(vx<-10000) vx += 800/0.016; //réajustement de la vitesse lorsque le héros revient au debut de la map
        ax = (vx - oldvx)/0.016; //accélération en pixels par secondes par secondes

        //mémorisation en tant qu'anciennes valeurs
        oldx = x;
        oldvx = vx;


    }
}
