package com.revivedstandards.model;

import com.revivedstandards.handlers.StandardHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public abstract class StandardLevel {

    private String fileLocation;
    private String bgImagePath;

    private BufferedImage levelData;
    private BufferedImage bgImage;

    public StandardHandler stdHandler;

    public StandardLevel( String fileLocation, String bgImagePath, StandardHandler stdHandler )
    {
        this.fileLocation = fileLocation;
        this.bgImagePath = bgImagePath;
        this.stdHandler = stdHandler;

        try
        {
            this.levelData = ImageIO.read( new File( this.fileLocation ) );
            this.bgImage = ImageIO.read( new File( this.bgImagePath ) );
        } catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    public abstract void loadLevelData();

    public abstract void tick();

    public abstract void render( Graphics2D paramGraphics2D );

    public String getFileLocation()
    {
        return this.fileLocation;
    }

    public void setFileLocation( String fileLocation )
    {
        this.fileLocation = fileLocation;
    }

    public BufferedImage getLevelData()
    {
        return this.levelData;
    }

    public void setLevelData( BufferedImage levelData )
    {
        this.levelData = levelData;
    }

    public String getBgImagePath()
    {
        return this.bgImagePath;
    }

    public void setBgImagePath( String bgImagePath )
    {
        this.bgImagePath = bgImagePath;
    }

    public BufferedImage getBgImage()
    {
        return this.bgImage;
    }

    public void setBgImage( BufferedImage bgImage )
    {
        this.bgImage = bgImage;
    }
}
