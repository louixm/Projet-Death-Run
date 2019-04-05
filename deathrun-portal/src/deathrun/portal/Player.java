/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deathrun.portal;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author ydejongh
 */
public class Player extends PObject {
    public String name;
    public int avatar;
    private boolean left, right, jump, leftAndRightWithPriorityOnRight; //, hasJumped;
    private Vec2 previousPosition;
    
    Box collision_box;
    
    public static BufferedImage avatars[];
    
    
    public Player(String name, int avatar, int db_id) {
        super(db_id);
        this.name = name; 
        this.avatar = avatar;
        collision_box = new Box(-0.5, 0, 0.5, 1.8);
        
        if (avatars == null) {
            Player.avatars = new BufferedImage[0];
            // charger les images des avatars si pas deja fait
        }
    }
    
    @Override
    public void setPosition(Vec2 pos) {
        super.setPosition(pos);
        collision_box = collision_box.translateToPosition(pos);
    }
    
    //--------------- interface de gestion des collisions -----------------
    public boolean collisionable(PObject other) { 
        return ! (other instanceof Player);
    }
    @Override
    public Box getCollisionBox() 	{ return collision_box; }
    
    //--------------- interface d'affichage -----------------
    @Override
    public void render(Graphics2D g, float scale) {
        super.render(g, scale);
        // TODO
    }
//    
//    public void setLeft(boolean left) { 
//        if (left)   this.velocity.x = -6;
//        else        this.velocity.x = 0;
//    }
//    public void setRight(boolean right) { 
//        if (right)  this.velocity.x = 6;
//        else        this.velocity.x = 0;
//    }
//    public void setJump(boolean jump) { 
//        if (jump && acceleration.y == 0)
//            this.velocity.y = -4;
//    }
    
    public void setLeft(boolean left) { 
        this.left = left;
        if (this.right) this.leftAndRightWithPriorityOnRight = false;
    }
    public void setRight(boolean right) { 
        this.right = right;
        if (this.left) this.leftAndRightWithPriorityOnRight = true;
    }
    public void setJump(boolean jump) { 
        this.jump = jump;  
    }
    
    public void applyMovementChanges(){
        if (this.left && (!this.right || !this.leftAndRightWithPriorityOnRight)) this.velocity.x = -6;
        else if (this.right && (!this.left || this.leftAndRightWithPriorityOnRight)) this.velocity.x = 6;       
        else this.velocity.x = 0;
        
//        if (!this.jump && acceleration.y == 0) this.hasJumped = false;
//        if (!this.hasJumped && this.jump && acceleration.y == 0) {
        if (this.jump) {
            System.out.println("Prev pos: " + previousPosition.x + ", pos: " + position.x + ", == ?: " + (previousPosition.x == position.x));
            if (acceleration.y == 0) {
                this.velocity.y = -5;
//            this.hasJumped = true;
            }
            else if (velocity.y > 0 && (this.right || this.left) && previousPosition.x == position.x){ //au lieu de previouisPosition, ajouter un attribut qui dit de quelle direction vient la collsision, et l'update dans la boucle dans Game lors d'une collision
                this.velocity.y = -4;
                if (this.right) {
//                    this.leftAndRightWithPriorityOnRight = false;
                    this.velocity.x = -10;
                }
                else {
//                    this.leftAndRightWithPriorityOnRight = true;
                    this.velocity.x = 10;
                }
            }
        }
        previousPosition = position;
    }
    
}
