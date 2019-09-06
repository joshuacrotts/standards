/*
===========================================================================
                   Standards Java Game Library Source Code
           Copyright (C) 2017-2019 Joshua Crotts & Andrew Matzureff 
Standards is free software: you can redistribute it and/or modify it under 
the terms of the GNU General Public License as published by the Free Software 
Foundation, either version 3 of the License, or (at your option) any later 
version.

Standards Source Code is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Standards Source Code. If not, see <http://www.gnu.org/licenses/>.

Standards is the long-overdue update to the everlasting Standards 2.0 library
Andrew Matzureff and I created two years ago. I am including it in this project
to simplify the rendering and logic pipeline, but with a focus on the MVC
paradigm.

We connect to the Apache FastMath API for some of our trigonometric functions,
and we use John Carmack's fast inverse square root function.
===========================================================================
 */
package com.revivedstandards.model;

import com.revivedstandards.handlers.StandardHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class StandardLevel
{

    private String fileLocation;
    private String bgImagePath;

    private BufferedImage levelData;
    private BufferedImage bgImage;

    public StandardHandler stdHandler;

    public StandardLevel ( String fileLocation, String bgImagePath, StandardHandler stdHandler )
    {
        this.fileLocation = fileLocation;
        this.bgImagePath = bgImagePath;
        this.stdHandler = stdHandler;

        try
        {
            this.levelData = ImageIO.read( new File( this.fileLocation ) );
            this.bgImage = ImageIO.read( new File( this.bgImagePath ) );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public abstract void loadLevelData ();

    public abstract void tick ();

    public abstract void render ( Graphics2D paramGraphics2D );

    public String getFileLocation ()
    {
        return this.fileLocation;
    }

    public void setFileLocation ( String fileLocation )
    {
        this.fileLocation = fileLocation;
    }

    public BufferedImage getLevelData ()
    {
        return this.levelData;
    }

    public void setLevelData ( BufferedImage levelData )
    {
        this.levelData = levelData;
    }

    public String getBgImagePath ()
    {
        return this.bgImagePath;
    }

    public void setBgImagePath ( String bgImagePath )
    {
        this.bgImagePath = bgImagePath;
    }

    public BufferedImage getBgImage ()
    {
        return this.bgImage;
    }

    public void setBgImage ( BufferedImage bgImage )
    {
        this.bgImage = bgImage;
    }
}
