/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathrun.portal;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;

/**
 *
 * @author ydejongh
 */
public class DeathrunPortal {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, IOException {
        Game game = new Game(true);
        Player controled, otherone;
        //try {
            game.init(4);// choix de la map 1, 2, 3, 4	// connecte au serveur et construit tous les objets tels que dans la base de donnnées
            //TODO: check dans la db players et ajouter au jeu tous ceux deja existants
            controled = new Player(game, "Jean Naymar", 1);
            controled.setControled(true);

//            game.map = Map.MapInitialization(game, 4);  // choix de la map 1, 2, 3, 4
            controled.setPosition(game.map.enter.position.add(new Vec2((game.map.enter.box.getWidth() - controled.collision_box.getWidth())/2, game.map.enter.box.getHeight() ))); //- controled.collision_box.getHeight()

//            game.map.objects.add(new EnterDoor(game, new Vec2(2, 2)));
//            game.map.objects.add(new ExitDoor(game, new Vec2(20, 15)));
            game.map.objects.add(new Saw(game, game.map.size.center().sub(new Vec2(1, 1))));
            game.map.objects.add(new Laser(game,new Vec2(4, 2),0));
        
            controled.acceleration.y = -1; // valeur différente de 0 pour forcer l'update de physicstep initiale
            //TODO: faire plus prore que ca
        
        /*
        }
        catch (Exception err) {
            System.out.println("error initializing the game: "+err);
            return;
        }
        */
        
        Gui gui = new Gui(game, controled);
        gui.setVisible(true);
        
    }
    
}
