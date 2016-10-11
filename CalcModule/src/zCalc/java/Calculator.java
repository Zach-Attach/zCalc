/**
 * Write a description of class Calculator here.
 *
 * @author Zach-Attach
 * @version Alpha 0.5
 *
 * <p>
 * TODO - Asthetics
 * <p>
 * Make the font more legible
 * Try to round off corners of the Stage
 * Try to square off the buttons
 * make a shadow border with shadowpane
 * make the stage undecorated
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Calculator extends Application {
    private final String title = " + - × ÷ ";
    Stage stage;

    public static void main(String[] args) {
        launch(args);
        System.exit(0);
    }

    private String[][] buttons() {
        String[][] b = new String[4][5];// column# row#

        b[0][0] = "AC";
        b[1][0] = "%";
        b[2][0] = "√"; //√

        b[0][1] = "1";
        b[1][1] = "2";
        b[2][1] = "3";

        b[0][2] = "4";
        b[1][2] = "5";
        b[2][2] = "6";

        b[0][3] = "7";
        b[1][3] = "8";
        b[2][3] = "9";

        b[0][4] = "∓"; //"∓±"
        b[1][4] = "0";
        b[2][4] = ".";

        b[3][4] = "＋"; //"＋"
        b[3][3] = "－"; //"－"
        b[3][2] = "×"; //"·"
        b[3][1] = "÷"; //"⁄"
        b[3][0] = "x²"; //x²

        return b;

    }

    private void colRowSettings(GridPane grid) {
        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(25);

        grid.getColumnConstraints().addAll(column, column, column, column);

        RowConstraints rowTop = new RowConstraints();
        rowTop.setPercentHeight(10);

        RowConstraints row = new RowConstraints();
        row.setPercentHeight(16);

        grid.getRowConstraints().addAll(rowTop, rowTop, row, row, row, row, row);
    }

    private GridPane layout() {
        String[][] btnArray = buttons();

        GridPane grid = new GridPane();

        TextArea txt = new TextArea();
        txt.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        colRowSettings(grid);

        for (int x = 0; x < btnArray.length; x++) {
            for (int y = 0; y < btnArray[x].length; y++) {
                String btnText = btnArray[x][y];
                Button btn = new Button(btnText);

                String z = "button";
                if ((x < 3 && y > 0 && y < 4) || (x == 1 && y == 4)) {
                    z += "Num";
                } else if (x == 3 && y > 0) {
                    z += "Op";
                } else {
                    z += "Func";
                }

                btn.getStyleClass().add(z);
                btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                btn.setOnAction(event -> func.display(btnText, txt, grid));
                grid.add(btn, x, y + 2);
            }
        }

        grid.add(txt, 0, 0, 4, 2);

        Button newLine = new Button("↵"); //⏎
        newLine.getStyleClass().add("buttonTop");
        newLine.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        newLine.setOnAction(event -> func.display("newLine", txt, grid));
        grid.add(newLine, 0, 0, 1, 1);

        return grid;
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        this.stage.getIcons().add(new Image("file:icon.png"));

        GridPane pane = layout();
        double phi = (1 + Math.sqrt(5)) / 2;
        pane.setPrefSize(300, 300 * phi);

        Scene scene = new Scene(pane);
        Application.setUserAgentStylesheet(STYLESHEET_MODENA);

        scene.getStylesheets().add(getClass().getResource("CalcStyle.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle(title);

        primaryStage.initStyle(StageStyle.DECORATED);
        //primaryStage.setOpacity(0.86);

        primaryStage.show();
    }
}
/**
 * ** TODO - Future Plan Notes ***
 * <p>
 * <p>
 * Button paren0 = new Button("new Button("(");
 * paren0.getOnAction(e -> {if paren0});
 * <p>
 * Button paren1 = new Button(")");
 * paren0.getOnAction(e -> {if paren1});
 * <p>
 * Button mC = new Button(")");
 * Button mPlus = new Button(")");
 * Button mMinus = new Button(")");
 * Button mR = new Button("");
 * Button pow2 = new Button("x^2");
 * Button powY = new Button("x^y");
 * <p>
 * <p>
 * RadioButtons or Switch?
 * Button Rad = new Button("");
 * Button Deg = new Button("");
 * <p>
 * History Tab?
 * Increase Size of Text?
 * 2nd function button? or hover over button? or long press button?
 * <p>
 * ≈
 * √
 * ∫
 * ∑
 * ˆ or e
 * π
 * ß
 * ∂ = partial derivative
 * δ
 * ƒ
 * ∆
 * ∇
 * …
 * §
 * ∞
 * Ω
 * log
 * ln
 * euler's number
 * ′ = derivative (dx)
 * ! factorial
 * ∕ - division slash
 * ⁄ - fraction slash
 * ‱ - basis point - a percent of a percent
 * observable (quantum operator)
 * ∞
 * √ 3√
 * ≈ - approximately (to switch between equals and this)
 * ≐ - approaches the limit
 * ∑ - summation
 * ∫ - integral
 * ∮ - contour integral
 * ∯ - double integration
 * ∰ - volume integral
 * ψ
 * φ
 * | | absolute value
 * ‖  ‖ nearest integer of
 * ⌊  ⌋ floor (greatest integer less than / equal to)
 * ⌈  ⌉ ceiling (smallest integer greater than / equal to)
 * ⌊  ⌉ nearest integer to
 * #
 * ⋮ - vertical elipsis (saves space)
 * <p>
 * java.text.AttributedString to do superscript
 * hyperoperations
 * exponentiation - a^b = a*a*a*...(b times)
 * tetration - ^{b}a = a↑↑b = a^a^a^a...(b times)
 * pentation -  a↑↑↑b = a ↑^3 b = a↑↑a↑↑a...(b times)
 * hexation - a↑↑↑↑b = a ↑^4 b = a↑↑↑a↑↑↑a...(b times)
 * <p>
 * <p>
 * make the keyword slots -
 */
