/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathrun.portal;

import static deathrun.portal.Platform.img;
import static deathrun.portal.Saw.img;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;

/**
 *
 * @author bsoares
 */
public class Bomb extends PObject{
    
    Box collision_box;
    static Image img;
    static final String db_type = "bomb";
    
    public Bomb(Game game, Vec2 position) throws SQLException, IOException {
        super(game, db_type);
        this.collision_box = new Box(0,0,2,2);
        this.setPosition(position);
        
        if (img == null) img = ImageIO.read(new File("./images/bomb.png")); //bombe
    }
    
    @Override
    public void onGameStep(Game g, float dt){
        last_sync++;
        //destruction des objetcs intersectés
        for (HashMap.Entry<Integer,PObject> p : g.objects.entrySet()){
            PObject obj = p.getValue();
            if (!(obj instanceof Player) && obj.getCollisionBox().intersect(this.collision_box)){
                try {
                    PreparedStatement req = g.sync.srv.prepareStatement("UPDATE pobjects SET version=?, type=? WHERE id = ?");
                    // le numero de version
                    req.setLong(1, last_sync);
                    req.setString(2, "null");
                    // id de l'objet a modifier
                    req.setInt(3, obj.db_id);

                    // execution de la requete
                    req.executeUpdate();
                    req.close();
                }
                catch (SQLException err) {
                    System.out.println("bomb onGameStep:\n"+err);
                }
            }
        }
        //self-destruction de la bombe
        try {
            PreparedStatement req = g.sync.srv.prepareStatement("UPDATE pobjects SET version=?, type=? WHERE id = ?");
            // le numero de version
            req.setLong(1, last_sync);
            req.setString(2, "null");
            // id de l'objet a modifier
            req.setInt(3, this.db_id);

            // execution de la requete
            req.executeUpdate();
            req.close();
        }
        catch (SQLException err) {
            System.out.println("bomb onGameStep:\n"+err);
        }
    }
    
    @Override
    public void setPosition(Vec2 pos) {
        super.setPosition(pos);
        collision_box = collision_box.translateToPosition(pos);
    }
    
    //--------------- interface de gestion des collisions -----------------
    @Override
    public int collisionable(PObject other)  { 
        return (other instanceof Player)?2:0;
    }
    @Override
    public Box getCollisionBox()       { return collision_box; }
    
    //--------------- interface d'affichage -----------------
    @Override
    public void render(Graphics2D canvas, float scale) {
        canvas.drawImage(img, 
                (int) (collision_box.p1.x*scale), 
                (int) (collision_box.p1.y*scale), 
                (int) (collision_box.p2.x*scale), 
                (int) (collision_box.p2.y*scale), 
                0, 0,
                img.getWidth(null), img.getHeight(null),
                null);
        super.render(canvas, scale);
    }
}
