package com.revivedstandards.main;

import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class StandardDraw {

    public static Graphics2D Renderer;

    public static void image( BufferedImage image, double x, double y )
    {
        Renderer.drawImage( image, ( int ) x, ( int ) y, null );
    }

    public static void image( BufferedImage image, int x, int y )
    {
        Renderer.drawImage( image, x, y, null );
    }

    public static void text( String text, int x, int y, String font, float size, Color color )
    {
        Font oldFont = Renderer.getFont();
        Color oldColor = Renderer.getColor();

        if ( font != null && !font.equals( "" ) )
        {
            Renderer.setFont( StdOps.initFont( font, size ) );
        } else
        {
            Renderer.setFont( new Font( "Arial", 0, ( int ) size ) );
        }
        Renderer.setColor( color );
        Renderer.drawString( text, x, y );

        Renderer.setColor( oldColor );
        Renderer.setFont( oldFont );
    }

    public static void text( String text, int x, int y, Font font, float size, Color color )
    {
        Font oldFont = Renderer.getFont();
        Color oldColor = Renderer.getColor();

        if ( font != null )
        {
            Renderer.setFont( font.deriveFont( size ) );
        } else
        {
            Renderer.setFont( new Font( "Arial", 0, ( int ) size ) );
        }
        Renderer.setColor( color );
        Renderer.drawString( text, x, y );

        Renderer.setColor( oldColor );
        Renderer.setFont( oldFont );
    }

    public static void rect( double x, double y, double width, double height, Color color, boolean fill )
    {
        if ( color == null )
        {
            color = Color.black;
        }

        if ( fill )
        {
            Color old = Renderer.getColor();
            Renderer.setColor( color );
            Renderer.fillRect( ( int ) x, ( int ) y, ( int ) width, ( int ) height );
            Renderer.setColor( old );
        } else
        {

            Color old = Renderer.getColor();
            Renderer.setColor( color );
            Renderer.drawRect( ( int ) x, ( int ) y, ( int ) width, ( int ) height );
            Renderer.setColor( old );
        }
    }

    public static void circle( double x, double y, double width, double height, Color color, boolean fill )
    {
        if ( color == null )
        {
            color = Color.black;
        }
        if ( fill )
        {
            Color old = Renderer.getColor();
            Renderer.setColor( color );
            Renderer.fillOval( ( int ) x, ( int ) y, ( int ) width, ( int ) height );
            Renderer.setColor( old );
        } else
        {

            Color old = Renderer.getColor();
            Renderer.setColor( color );
            Renderer.drawOval( ( int ) x, ( int ) y, ( int ) width, ( int ) height );
            Renderer.setColor( old );
        }
    }

    public static void Object( StandardGameObject obj )
    {
        obj.render( Renderer );
    }

    public static void Handler( StandardHandler handler )
    {
        handler.render( Renderer );
    }

    public static Color RANDOM = new Color( StdOps.rand( 0, 255 ), StdOps.rand( 0, 255 ), StdOps.rand( 0, 255 ) );

    public static final Color RED = Color.RED;
    public static final Color PINK = new Color( 255, 192, 203 );
    public static final Color SALMON_PINK = new Color( 255, 145, 164 );
    public static final Color CORAL_PINK = new Color( 248, 131, 121 );
    public static final Color SALMON = new Color( 250, 128, 114 );
    public static final Color RED_PANTONE = new Color( 237, 41, 57 );
    public static final Color RED_CRAYOLA = new Color( 238, 32, 77 );
    public static final Color SCARLET = new Color( 255, 36, 0 );
    public static final Color RED_IMPERIAL = new Color( 237, 41, 57 );
    public static final Color INDIAN_RED = new Color( 205, 92, 92 );
    public static final Color SPANISH_RED = new Color( 230, 0, 38 );
    public static final Color DESIRE = new Color( 234, 60, 83 );
    public static final Color LUST = new Color( 230, 32, 32 );
    public static final Color CARMINE = new Color( 150, 0, 24 );
    public static final Color RUBY = new Color( 224, 17, 95 );
    public static final Color CRIMSON = new Color( 220, 20, 60 );
    public static final Color RUSTY_RED = new Color( 218, 44, 67 );
    public static final Color FIRE_ENGINE_RED = new Color( 206, 32, 41 );
    public static final Color CARDINAL_RED = new Color( 196, 30, 58 );
    public static final Color CHILI_RED = new Color( 226, 61, 40 );
    public static final Color CORNELL_RED = new Color( 179, 27, 27 );
    public static final Color FIRE_BRICK = new Color( 178, 34, 34 );
    public static final Color REDWOOD = new Color( 164, 90, 82 );
    public static final Color OU_CRIMSON_RED = new Color( 153, 0, 0 );
    public static final Color DARK_RED = new Color( 139, 0, 0 );
    public static final Color MAROON = new Color( 128, 0, 0 );
    public static final Color BARN_RED = new Color( 124, 10, 2 );
    public static final Color TURKEY_RED = new Color( 169, 17, 1 );

    public static Color BLUE = Color.BLUE;
    public static Color BABY_BLUE = new Color( 137, 207, 240 );
    public static Color LIGHT_BLUE = new Color( 176, 216, 230 );
    public static Color PERIWINKLE = new Color( 204, 204, 255 );
    public static Color POWDER_BLUE = new Color( 176, 224, 230 );
    public static Color MORNING_BLUE = new Color( 141, 163, 153 );
    public static Color BLUE_MUNSELL = new Color( 0, 147, 175 );
    public static Color BLUE_PANTONE = new Color( 0, 24, 168 );
    public static Color BLUE_CRAYOLA = new Color( 31, 117, 254 );
    public static Color BLUE_MEDIUM = new Color( 0, 0, 205 );
    public static Color SPANISH_BLUE = new Color( 0, 112, 184 );
    public static Color LIBERTY = new Color( 84, 90, 167 );
    public static Color EGYPTIAN_BLUE = new Color( 16, 52, 166 );
    public static Color ULTRAMARINE = new Color( 63, 0, 255 );
    public static Color DARK_BLUE = new Color( 0, 0, 139 );
    public static Color RESOLUTION_BLUE = new Color( 0, 35, 185 );
    public static Color NAVY_BLUE = new Color( 0, 0, 128 );
    public static Color MIDNIGHT_BLUE = new Color( 25, 25, 112 );
    public static Color INDEPENDENCE = new Color( 76, 81, 109 );
    public static Color SPACE_CADET = new Color( 29, 41, 81 );
    public static Color CAROLINA_BLUE = new Color( 123, 175, 212 );
    public static Color DUKE_BLUE = new Color( 0, 0, 156 );

    public static Color GREEN = Color.GREEN;
    public static Color ARTICHOKE = new Color( 143, 151, 121 );
    public static Color ARTICHOKE_GREEN_PANTONE = new Color( 75, 111, 68 );
    public static Color ASPARAGUS = new Color( 135, 169, 107 );
    public static Color AVOCADO = new Color( 86, 130, 3 );
    public static Color FERN_GREEN = new Color( 79, 121, 66 );
    public static Color FERN = new Color( 113, 188, 120 );
    public static Color FOREST_GREEN = new Color( 34, 139, 34 );
    public static Color HOOKER_GREEN = new Color( 73, 121, 107 );
    public static Color JUNGLE_GREEN = new Color( 41, 171, 135 );
    public static Color LAUREL_GREEN = new Color( 169, 186, 157 );
    public static Color LIGHT_GREEN = new Color( 144, 238, 144 );
    public static Color MANTIS = new Color( 116, 195, 101 );
    public static Color MOSS_GREEN = new Color( 138, 154, 91 );
    public static Color DARK_MOSS_GREEN = new Color( 74, 93, 35 );
    public static Color MYRTLE_GREEN = new Color( 49, 120, 115 );
    public static Color MINT_GREEN = new Color( 152, 251, 152 );
    public static Color PINE_GREEN = new Color( 1, 121, 111 );
    public static Color SAP_GREEN = new Color( 0, 158, 96 );
    public static Color IRISH_GREEN = new Color( 0, 158, 96 );
    public static Color ST_PATRICK = IRISH_GREEN;
    public static Color TEA_GREEN = new Color( 208, 240, 192 );
    public static Color TEAL = new Color( 0, 128, 128 );
    public static Color DARK_GREEN = new Color( 0, 100, 0 );
    public static Color GREEN_PANTONE = new Color( 0, 173, 131 );
    public static Color GREEN_CRAYOLA = new Color( 28, 172, 120 );
    public static Color ARMY_GREEN = new Color( 75, 83, 32 );
    public static Color BOTTLE_GREEN = new Color( 0, 106, 78 );
    public static Color BRIGHT_GREEN = new Color( 102, 255, 0 );
    public static Color BRIGHT_MINT = new Color( 79, 255, 176 );
    public static Color BRUNSWICK_GREEN = new Color( 27, 77, 62 );
    public static Color CELADON = new Color( 173, 255, 175 );
    public static Color DARK_PASTEL_GREEN = new Color( 3, 192, 160 );
    public static Color DARTMOUTH_GREEN = new Color( 0, 105, 62 );
    public static Color EMERALD = new Color( 80, 220, 100 );
    public static Color GREEN_YELLOW = new Color( 173, 255, 47 );
    public static Color HARLEQUIN = new Color( 63, 255, 0 );
    public static Color HUNTER_GREEN = new Color( 53, 94, 59 );
    public static Color INDIA_GREEN = new Color( 19, 136, 8 );
    public static Color ISLAMIC_GREEN = new Color( 0, 144, 0 );
    public static Color JADE = new Color( 0, 168, 107 );
    public static Color KELLY_GREEN = new Color( 75, 187, 23 );
    public static Color MIDNIGHT_GREEN = new Color( 0, 73, 83 );
    public static Color NEON_GREEN = new Color( 57, 255, 20 );
    public static Color OFFICE_GREEN = new Color( 0, 128, 0 );
    public static Color PERSIAN_GREEN = new Color( 0, 166, 147 );

    public static Color YELLOW = Color.YELLOW;
    public static Color CREAM = new Color( 255, 255, 204 );
    public static Color YELLOW_MUNSELL = new Color( 239, 204, 0 );
    public static Color YELLOW_PANTONE = new Color( 254, 223, 0 );
    public static Color YELLOW_CRAYOLA = new Color( 252, 232, 131 );
    public static Color UNMELLOW_YELLOW = new Color( 255, 255, 102 );
    public static Color LEMON = new Color( 253, 255, 0 );
    public static Color ROYAL_YELLOW = new Color( 250, 219, 94 );
    public static Color GOLD = new Color( 255, 215, 0 );
    public static Color CYBER_YELLOW = new Color( 255, 211, 0 );
    public static Color SAFETY_YELLOW = new Color( 238, 210, 2 );
    public static Color GOLDENROD = new Color( 218, 165, 32 );

    public static Color ORANGE = Color.ORANGE;
    public static Color ORANGE_WHEEL = new Color( 255, 127, 0 );
    public static Color DARK_ORANGE = new Color( 255, 140, 0 );
    public static Color ORANGE_PANTONE = new Color( 255, 88, 0 );
    public static Color ORANGE_CRAYOLA = new Color( 255, 117, 56 );
    public static Color PEACH = new Color( 255, 229, 180 );
    public static Color LIGHT_ORANGE = new Color( 254, 216, 177 );
    public static Color APRICOT = new Color( 251, 206, 177 );
    public static Color MELON = new Color( 253, 188, 180 );
    public static Color TEA_ROSE = new Color( 248, 131, 121 );
    public static Color CARROT_ORANGE = new Color( 237, 145, 33 );
    public static Color ORANGE_PEEL = new Color( 255, 159, 0 );
    public static Color PRINCETON_ORANGE = new Color( 245, 128, 37 );
    public static Color UT_ORANGE = new Color( 255, 130, 0 );
    public static Color SPANISH_ORANGE = new Color( 232, 97, 0 );
    public static Color PUMPKIN = new Color( 255, 117, 24 );
    public static Color VERMILION = new Color( 227, 66, 52 );
    public static Color TOMATO = new Color( 255, 99, 71 );
    public static Color BURNT_ORANGE = new Color( 191, 87, 0 );

    public static Color PURPLE = new Color( 128, 0, 128 );
    public static Color ROYAL_PURPLE = new Color( 120, 81, 169 );
    public static Color RED_VIOLET = new Color( 199, 21, 133 );
    public static Color PURPLE_MEDIUM = new Color( 147, 112, 219 );
    public static Color MUNSELL = new Color( 159, 0, 197 );
    public static Color MAUVE = new Color( 224, 176, 255 );
    public static Color ORCHID = new Color( 218, 112, 214 );
    public static Color HELIOTROPE = new Color( 223, 115, 255 );
    public static Color PHLOX = new Color( 223, 0, 255 );
    public static Color PURPLE_PIZZAZZ = new Color( 254, 78, 218 );
    public static Color MULBERRY = new Color( 197, 75, 140 );
    public static Color PEARLY_PURPLE = new Color( 183, 104, 162 );
    public static Color PURPUREUS = new Color( 154, 78, 174 );
    public static Color MARDI_GRAS = new Color( 136, 0, 137 );
    public static Color PANSY_PURPLE = new Color( 120, 24, 74 );
    public static Color DARK_PURPLE = new Color( 48, 25, 52 );
    public static Color VIOLET = new Color( 127, 0, 255 );
}
