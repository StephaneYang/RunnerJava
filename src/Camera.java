public class Camera {
    static private double x, y, vx, ax;

    Camera (double x, double y){
        this.x=x;
        this.y=y;
    }

    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }

    @Override
    public String toString (){
        System.out.println(this.x+", "+this.y);
        return null;
    }

    public static void update(long timer, Hero hero) {
        vx = vx - x;
        ax = 1*(hero.x-x) + 1.2*vx;
    }
}
