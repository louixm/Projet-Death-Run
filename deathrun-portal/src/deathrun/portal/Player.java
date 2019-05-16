/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathrun.portal;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import static java.lang.Math.abs;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ydejongh
 */
public class Player extends PObject {
    public String name;
    public int avatar;
    public boolean controled = false;
    public boolean dead = false, hasReachedExitDoor = false, disconnected = false;
    
    boolean left, right, jump, leftAndRightWithPriorityOnRight; //, hasJumped;
    ArrayList<String> collisionDirection;
//    private BufferedImage robot, robotBase, robotDr, robotDrEx, robotSaut, robotBasegauche, robotgauche, robotgaucheex, robotsautgauche;     // rajouté par louis animation
    Box collision_box;
    Game game;
    
    int compteurdanimation = 1;    //compteur pour les animations de personnage
    long time_next_image;
    long image_duration = 300000000;
    int lignedevue = 1;
    public static BufferedImage avatars[][];
    BufferedImage current_image;
    
    public static int availableId(Game game) {
        int id = -1;
        boolean id_found = true;
        while (id_found) {
            id_found = false;
            for (int i=0; i<game.players.size(); i++) {
                if (game.players.get(i).db_id == id)    {
                    id_found = true;
                    --id;
                    break;
                }
            }
        }
        System.out.println("id = " + id);
        return id;
    }
    
    
    public Player(Game game, String name, int avatar) throws SQLException {
        super(game, availableId(game));  // creer en ajoutant a la fin
        this.game = game;
        this.name = name; 
        this.avatar = avatar;
        
        collision_box = new Box(-0.5, 0, 0.5, 1.8);
        if (avatars == null) {
            this.avatars = new BufferedImage[4][];
            try {
                this.avatars[0] = new BufferedImage[] { 
                    ImageIO.read(new File("./images/robotbase.png")),
                    ImageIO.read(new File("./images/robotdroite.png")),
                    ImageIO.read(new File("./images/robotdroitee.png")),
                    ImageIO.read(new File("./images/robotsaut.png")),
                    ImageIO.read(new File("./images/robotbasegauche.png")),
                    ImageIO.read(new File("./images/robotgauche.png")),
                    ImageIO.read(new File("./images/robotdgaucheex.png")),
                    ImageIO.read(new File("./images/robotsautgauche.png")),
                    ImageIO.read(new File("./images/robotDead.png")),
                    
                };
                this.avatars[1] = new BufferedImage[]{
                    ImageIO.read(new File("./images/tourellebase.png")),
                    ImageIO.read(new File("./images/tourelledroite2.png")),
                    ImageIO.read(new File("./images/tourelledroite3.png")),
                    ImageIO.read(new File("./images/tourellesautdroite.png")),
                    ImageIO.read(new File("./images/tourellebasegauche.png")),
                    ImageIO.read(new File("./images/tourellegauche2.png")),
                    ImageIO.read(new File("./images/tourellegauche3.png")),
                    ImageIO.read(new File("./images/tourellesautgauche.png")),
                    ImageIO.read(new File("./images/robotDead.png")),
            
                 };
                this.avatars[2] = new BufferedImage[]{
                    ImageIO.read(new File("./images/tourellebase.png")),
                    ImageIO.read(new File("./images/tourelledroite2.png")),
                    ImageIO.read(new File("./images/tourelledroite3.png")),
                    ImageIO.read(new File("./images/tourellesautdroite.png")),
                    ImageIO.read(new File("./images/tourellebasegauche.png")),
                    ImageIO.read(new File("./images/tourellegauche2.png")),
                    ImageIO.read(new File("./images/tourellegauche3.png")),
                    ImageIO.read(new File("./images/tourellesautgauche.png")),
                    ImageIO.read(new File("./images/robotDead.png")),
                 };
                this.avatars[3] = new BufferedImage[]{
                    ImageIO.read(new File("./images/robotDead.png")),
                    ImageIO.read(new File("./images/robotDead.png")),
                    ImageIO.read(new File("./images/robotDead.png")),
                };
                //this.avatars[3] = new BufferedImageImageIO.read(new File("./images/robotDead.png"));
                
                //this.avatars[2] = ImageIO.read(new File("./images/robotOrange.png"));
                //this.robotBase = ImageIO.read(new File("robotbase.png"));    // rajouté par louis animation
                //this.robotDr = ImageIO.read(new File("robotdroite.png"));    // rajouté par louis animation
                //this.robotDrEx = ImageIO.read(new File("robotdroitee.png"));    // rajouté par louis animation
                //this.robotSaut = ImageIO.read(new File("robotsaut.png"));    // rajouté par louis animation
                //this.robotBasegauche = ImageIO.read(new File("robotbasegauche.png"));    // rajouté par louis animation
                //this.robotgauche = ImageIO.read(new File("robotgauche.png"));    // rajouté par louis animation
                //this.robotgaucheex = ImageIO.read(new File("robotdgaucheex.png"));    // rajouté par louis animation
                //this.robotsautgauche = ImageIO.read(new File("robotsautgauche.png"));    // rajouté par louis animation
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // horloge pour l'affichage des animations
        time_next_image = System.nanoTime() + image_duration;
        System.out.println("avatar = "+avatar);
        System.out.println("avatars[avatar] = "+avatars[avatar]);
        current_image = this.avatars[avatar][0];
        
        //double j = game.map.enter.position.y + game.map.enter.size - this.avatars.getHight();
        this.setPosition(game.map.enter.position.add(new Vec2(-0.25,0)));
        
        
        if (game.sync != null) {
            PreparedStatement req = game.sync.srv.prepareStatement("SELECT EXISTS(SELECT id FROM players WHERE id = ?)");
            req.setInt(1, db_id);
            ResultSet r = req.executeQuery();
            r.next();
            if (!r.getBoolean(1)) {
                req = game.sync.srv.prepareStatement("INSERT INTO players VALUES (?, ?, 0, 0, ?, 0, 0, 0)"); //TODO
                req.setInt(1, db_id);
                req.setString(2, name);
                req.setInt(3, avatar);
                req.executeUpdate();
                req.close();
                System.out.println("ok");
            }
        }
        
        // tout est pret, declaration du nouveau joueur
        game.players.add(this);
    }
    
    public void disconnect() {
        try {
            PreparedStatement req;
            // effacement de la table des objets
            req = game.sync.srv.prepareStatement("DELETE FROM pobjects WHERE id = ?");
            req.setInt(1, db_id);
            req.executeUpdate();
            // effacement de la table de joueurs
            req = game.sync.srv.prepareStatement("DELETE FROM players WHERE id = ?");
            req.setInt(1, db_id);
            req.executeUpdate();
            
            System.out.println("Deleted player with id " + db_id);
            req.close();
        }
        catch (SQLException err) {
            System.out.println("Player.disconnect(): "+err);
        }
    }
    
    @Override
    public void setPosition(Vec2 pos) {
        super.setPosition(pos);
        collision_box = collision_box.translateToPosition(pos);
    }
    
    //--------------- interface de gestion des collisions -----------------
    public int collisionable(PObject other) { 
        if (other instanceof Player)    return 0;
        else                            return 1;
    }
    @Override
    public Box getCollisionBox() 	{ return collision_box; }
    
    //--------------- interface d'affichage -----------------
    @Override
    public void render(Graphics2D g, float scale) {
        
        long ac_time = System.nanoTime();
        if (ac_time > time_next_image)  {
            if (avatar!=3){
                if (left) { 
                    lignedevue =2;
                
                    if (compteurdanimation == 1){
                        current_image = avatars[avatar][4];
                        compteurdanimation = compteurdanimation + 1;
                    }
                    else if (compteurdanimation == 2){
                        current_image = avatars[avatar][5];
                        compteurdanimation = compteurdanimation + 1;
                    }
                    else if (compteurdanimation == 3){
                        current_image = avatars[avatar][6];
                        compteurdanimation = 1;
                    }
            
                }
                if (right) { 
                    lignedevue =1;
                    if (compteurdanimation == 1){
                        current_image = avatars[avatar][0];
                        compteurdanimation++;
                    }
                    else if (compteurdanimation == 2){
                        current_image = avatars[avatar][1];
                        compteurdanimation++;
                    }
                    else if (compteurdanimation == 3){
                        current_image = avatars[avatar][2];
                        compteurdanimation = 1;
                    }
                
            
                }
                if (jump){
                    if (lignedevue == 1){
                        current_image = avatars[avatar][3];
                    }
                    if (lignedevue == 2) {
                        current_image = avatars[avatar][7];
                    }
                }
            }
            if (avatar==3){
                    if (compteurdanimation == 1){
                        current_image = avatars[3][0];
                        compteurdanimation++;
                    }
                    else if (compteurdanimation == 2){
                        current_image = avatars[3][1];
                        compteurdanimation++;
                    }
                    else if (compteurdanimation == 3){
                        current_image = avatars[3][2];
                        compteurdanimation = 1;
                }
            
            }
            time_next_image = ac_time + image_duration;
        }
    
        
        g.drawImage(current_image, 
            (int) (collision_box.p1.x*scale), 
            (int) (collision_box.p1.y*scale), 
            (int) (collision_box.p2.x*scale), 
            (int) (collision_box.p2.y*scale), 
            0, 0,
            current_image.getWidth(null), current_image.getHeight(null),
            null);
        
        g.setColor(getPlayerColor());
        g.drawString(name, (int) ((collision_box.p1.x)*scale), (int) ((collision_box.p1.y - 0.1)*scale));
        super.render(g, scale);
    }

    public Color getPlayerColor(){
        switch(this.db_id){
            case(-1): return Color.BLUE;
            case(-2): return Color.ORANGE;
            case(-3): return Color.GREEN;
            case(-4): return Color.PINK;
            case(-5): return Color.CYAN;
            case(-6): return Color.RED;
            case(-7): return Color.YELLOW;
            case(-8): return Color.MAGENTA; 
            default: return Color.WHITE;
        }
    }
    
    public void setLeft(boolean left) {
        if (dead)   return;
        this.left = left;
        if (this.right) this.leftAndRightWithPriorityOnRight = false;
    }
    public void setRight(boolean right) { 
        if (dead)   return;
        this.right = right;
        if (this.left) this.leftAndRightWithPriorityOnRight = true;
    }
    public void setJump(boolean jump) { 
        if (dead)   return;
        this.jump = jump;  
    }
    
    public void setDead(boolean dead) {
        if (!this.dead) {
            this.dead = dead;
            if (dead) {
                avatar = 3;
                System.out.println("player "+name+" is dead");
                try {
                    PreparedStatement req = game.sync.srv.prepareStatement("UPDATE players SET state=? WHERE id = ?");
                    req.setInt(1, 1); //state = 0 (en vie), 1 (dead), 2 (exit door)
                    // id de l'objet a modifier
                    req.setInt(2, db_id);

                    // execution de la requete
                    req.executeUpdate();
                    req.close();
                }
                catch (SQLException err) {
                    System.out.println("sql exception:\n"+err);
                }
                game.tryEndRound();
            }
        }
    }
    
    @Override
    public void onGameStep(Game game, float dt) {
        applyMovementChanges(dt);
    }
    
    public void applyMovementChanges(float dt){
        if (dead || hasReachedExitDoor)   return;
        
        if (this.left && (!this.right || !this.leftAndRightWithPriorityOnRight)){
            if (this.velocity.x > 0)        this.acceleration.x = -40;
            else if (this.velocity.x > -7)  this.acceleration.x = -20;
            else                            this.acceleration.x = 0;
            syncMovement(1);
        }
        else if (this.right && (!this.left || this.leftAndRightWithPriorityOnRight)){
            if (this.velocity.x < 0)        this.acceleration.x = 40;
            else if (this.velocity.x < 7)   this.acceleration.x = 20;
            else                            this.acceleration.x = 0;
            syncMovement(2);
        }       
        else{
            if (abs(this.velocity.x) > 1)        this.acceleration.x = -40 * abs(this.velocity.x)/this.velocity.x;
            else                                {this.acceleration.x = 0;      this.velocity.x = 0;}
            if ((this.velocity.x + this.acceleration.x*dt) * this.velocity.x < 0) 
                                                {this.acceleration.x = 0;      this.velocity.x = 0;};
            syncMovement(0);
        }
        
        if (this.jump) {
            if (acceleration.y == 0)    this.velocity.y = -6;
            else if (velocity.y > 0 && (
                    (this.right && this.collisionDirection.contains("right")) 
                 || (this.left && this.collisionDirection.contains("left"))))
            {
                this.velocity.y = -5;
                if (this.right) this.velocity.x = -12;               
                else            this.velocity.x = 12;
            }
            syncMovement(3);
        }
    }
    
    public void setCollisionDirection(ArrayList<String> array){ this.collisionDirection = array; }
    public void addCollisionDirection(String direction){ this.collisionDirection.add(direction); }
    public ArrayList<String> getCollisionDirection(){ return this.collisionDirection; }
    
    public void setControled(boolean controled){ this.controled = controled; }
    public boolean isControled(){ return this.controled; }
    
    public void syncMovement(int move){
        if (game.sync != null){
            try {
                PreparedStatement req = game.sync.srv.prepareStatement("UPDATE players SET movement=? WHERE id = ?");
                req.setInt(1, move); 
                // id de l'objet a modifier
                req.setInt(2, db_id);

                // execution de la requete
                req.executeUpdate();
                req.close();
            }
            catch (SQLException err) {
                System.out.println("sql exception:\n"+err);
            }
        }
    }
    
}
