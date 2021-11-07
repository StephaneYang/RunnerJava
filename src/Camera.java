public class Camera {
    static private double x, oldx, y, vx, ax;

    Camera (double x, double y){
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

    @Override
    public String toString (){
        System.out.println(this.x+", "+this.y);
        return null;
    }

    public static void update(long timer, Hero hero) {
        x = hero.x;
        vx = x - oldx;
        oldx = hero.x;
        y = GameScene.camY;
    }
}
