package Misc;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Akash-Mac on 2016-02-17.
 */
public class GlobalConstants {
    public static final String APPLICATION_NAME = "Doodle";
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension DEFAULT_CANVAS_SIZE = new Dimension(800, 600);
    public static final Dimension MINIMUM_SCREEN_SIZE = new Dimension(SCREEN_SIZE.width/2, (SCREEN_SIZE.height/2) + 200);
    public static final String FILE_MENU = "File";
    public static final String EDIT_MENU = "Edit";
    public static final String VIEW_MENU = "View";
    public static final String NAME = "Akash Sant";
    public static final String USER_ID = "acsant";
    public static final String STUDENT_NUMBER = "20461809";
    public static final String DESCRIPTION = "";
    public static final String COLOR_PALETTE_NAME = "Colors";
    public static final String TIMELINE_NAME = "Animate";
    public static final String EXIT_TEXT = "Exit";
    public static final String SAVE_TEXT = "Save";
    public static final String LOAD_TEXT = "Load";
    public static final String COPY_TEXT = "Copy";
    public static final String PASTE_TEXT = "Paste";
    public static final String FULL_SIZE_TEXT = "Full-Size";
    public static final String TXT_SAVE_TEXT = "TXT";
    public static final String BIN_SAVE_TEXT = "BIN";
    public static final String FIT_SCREEN_TEXT = "Fit";
    public static final String CREATE_NEW_TEXT = "New";
    public static final int TIMELINE_SPACING = 10;
    public static final int COLOR_SWATCH_DIM = 15;
    public static final int BYTE_LOOKAHEAD = 100;
    public static final Dimension STROKE_BUTTON_DIM = new Dimension(80, 25);
    public static final String STROKE_PALETTE_NAME = "Thickness";
    public static final String RESOURCES_PATH = "/Resources/";
    public static final Dimension CURRENT_COLOR = new Dimension(50, 50);
    public static final int COLOR_VIEWER = 80;
    public static final int PADDING = 10;
    public static final List MICROSOFT_SUGGESTED_COLORS = Arrays.asList(
            "Black", "Gray", "Maroon", "Red", "Green", "Lime", "Olive", "Yellow", "Navy", "Blue", "Purple",
            "Fuchsia", "Teal", "Aqua", "Silver", "White"
    );
    public static final int[] STROKES = {1, 3, 8, 12};
    public static final List COLORS = Arrays.asList(
        Color.BLACK, Color.GRAY, new Color(128, 0, 0), Color.RED, Color.GREEN, new Color(195, 255, 0),
            new Color(128, 128, 0), Color.YELLOW, new Color(0,0,128), Color.BLUE, new Color(128, 0, 128),
            new Color(255, 0, 255), new Color(0, 128, 128), new Color(0, 255, 255), new Color(192, 192, 192),
            Color.WHITE
    );
}
