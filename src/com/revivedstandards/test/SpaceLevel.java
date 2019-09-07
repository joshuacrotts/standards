/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.revivedstandards.test;

import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardLevel;
import java.awt.Graphics2D;

/**
 *
 * @author Joshua
 */
public class SpaceLevel extends StandardLevel
{

    private StandardGameObject player;
    
    private int trackX;
    private int trackXX;
    
    public SpaceLevel ( TriangleGameObject player )
    {
        super( null, "src/res/img/bg/space_bg.jpg", null );
        
        this.player = player;
    }

    @Override
    public void loadLevelData ()
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tick ()
    {
        throw new UnsupportedOperationException( "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render ( Graphics2D g2 )
    {
        if ( this.getBgImage() != null )
        {

            trackX -= ( int ) this.player.getVelX() * 0.25;
            trackXX -= ( int ) this.player.getVelX() * 0.50;
            if ( this.trackX <= 0 )
            {
                g2.drawImage( this.getBgImage(), trackX, 0, null );
            }
            else
            {
                g2.drawImage( this.getBgImage(), 0, 0, null );
            }

            if ( this.trackXX <= 0 )
            {
                g2.drawImage( this.getBgImage(), trackXX, 0, null );
            }
            else
            {
                g2.drawImage( this.getBgImage(), 0, 0, null );
            }

        }
    }

}
