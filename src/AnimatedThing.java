import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract class AnimatedThing {
    protected double BaseX, BaseY;
    public static int temps;
    public int timeFrames = 7;
    public int toggle, invincibleAnimation;//toggle for invincibility animation
    public double dt = 0.016;
    //x et y ne correspondent pas à la position qu'on voit sur la fenêtre
    //(il reste dans la fenêtre et ne quitte pas l'écran) mais on imagine un héros qui évolue dans x et y
    protected double x, y, oldx, oldy, incrementation=3;
    protected double gravity_a = 400, gravity_dv, gravity_v, gravity_dy;
    protected ImageView sprite;
    //attitude : état de notre héros = numéro de la ligne d'une animation (en commencant par 0)
    //index : index d'une image = numéro d'une colonne
    //maxIndex : nb d'image pour une animation complète
    //offset : distance horizontale entre chaque frames dans notre fichier hero.jpg
    protected int attitude, maxIndex, offset=122, isJumping, isFalling, jumpLvl = 0;
    protected double index;
    protected int decalageX, decalageY;//variable servant à rétrécir le rectangle hitbox qui capture notre sprite
                                       //car le rectangle de base (qui sert pour l'animation) est trop grand pour la hitbox

    AnimatedThing (String imgSprite, double BaseX, double BaseY, int decalageX, int decalageY){
        this.x = 0;
        this.y = 0;
        this.oldx = 0;
        this.oldy = 0;
        this.BaseX = BaseX;
        this.BaseY = BaseY;
        this.decalageX = decalageX;
        this.decalageY = decalageY;
        Image image = new Image(imgSprite);
        sprite = new ImageView(image);
        Rectangle2D viewportRect = new Rectangle2D(index*offset, attitude*159, 120, 100);
        sprite.setViewport(viewportRect);
    }

    public void update() {
        //définition des états du héros----DEBUT----
        //Rq : "attitude" correspond également aux états du héros l'utilisation est différente
        //attitude permet de choisir la ligne d'animation tandis que les autres états permettent de choisir l'index (colonne)
        if ((x-oldx)>0) {
            if (GameScene.shoot == 1) attitude = 2;
            else attitude = 0;
        }

        if ((y-oldy)<0) { //hero is jumping
            isJumping = 1;
            isFalling = 0;
            index = 0;
            if (GameScene.shoot == 1) attitude = 3;
            else attitude = 1;
        } else if  (((y-oldy)>=0) && (y!=0)) { //hero is falling ("no variation but still flying" is also considered as falling)
            isFalling = 1;
            isJumping = 0;
            index = 1;
            if (GameScene.shoot == 1) attitude = 3;
            else attitude = 1;
        } else if (((y-oldy)==0) && (y==0)) {
            isJumping = 0;
            isFalling = 0;
        }

        //Mise en mémoire en tant qu'ancienne valeur
        oldx = x;
        oldy = y;

        //définition des états du héros----FIN----

        //Gravité----DEBUT----
        gravity_dv = gravity_a*dt;
        gravity_v += gravity_dv;
        gravity_dy = gravity_v*dt;
        y += gravity_dy;

        if (y>0) { //héros au sol
            gravity_a = 0;
            gravity_v = 0;
            y = 0;
        }

        if (y<-2*105) y = -2*105; //1re limitation du saut atteinte
        if (jumpLvl>0) {
            if (y<-105) y = -105; //2e limitation du saut atteinte
            gravity_a = 550; //application de la gravité seule
        }
        //Gravité----FIN----

        //-------Animation du héros DEBUT-------
        if (temps % timeFrames == 0) {
            if (invincibleAnimation == 1){
                toggle = 1-toggle;//toggle vaudra alternativement 0 ou 1
            } else{
                toggle = 0;
            }

            if (attitude == 0 || attitude == 2) {
                this.maxIndex = 5;
                index++;//pour ces attitudes, on incrémente simplement
            }
            if (attitude == 1 || attitude == 3) {
                this.maxIndex = 1;//pour ces attitudes, on va se servir des if du dessus (définition des états du héros)
            }
            if (index > this.maxIndex){
                index = 0;//rebouclage une fois la dernière image atteinte
            }
            Rectangle2D viewportRect;
            if (toggle == 0){//valeur de base
                viewportRect = new Rectangle2D(index*offset, attitude*160, 120, 100);
            } else {
                viewportRect = new Rectangle2D(3*offset, 3*160, 120, 100);
            }
            sprite.setViewport(viewportRect);
        }
        //-------Animation du héros FIN-------
    }

    public ImageView getImg (){
        return sprite;
    }

    public void jump (){
        jumpLvl ++; // variable du niveau du saut
        if ((jumpLvl<=1) && (isFalling == 0)) {
            gravity_a -= 500*14;//application d'une force de poussée opposée à la gravité
        } else {
            gravity_a = 550;//gravité seule
        }
    }

    public Rectangle2D getHitBox(){
        return new Rectangle2D(sprite.getX()+decalageX, sprite.getY()+decalageY, 120-2*decalageX,100-2*decalageY);
    }

    public int detectCollision(Rectangle2D rect){
        if (rect.getMinX()>sprite.getX()+120-decalageX){
            return 0;
        } else if (rect.getMinY()>sprite.getY()+100-decalageY){
            return 0;
        } else if (rect.getMaxX()<sprite.getX()+decalageX){
            return 0;
        } else if (rect.getMaxY()<sprite.getY()+decalageX){
            return 0;
        } else {
            return 1;
        }
    }
}
