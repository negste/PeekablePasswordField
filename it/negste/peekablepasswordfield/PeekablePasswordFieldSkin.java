package it.negste.peekablepasswordfield;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Skin for {@link PeekablePasswordField}; is uses CSS to show 
 * an icon on the right side
 * @author negste
 */
class PeekablePasswordFieldSkin extends TextFieldSkin {

    private boolean _doMask = true;

    public PeekablePasswordFieldSkin(TextField textField) {

        super(textField);

        if (textField instanceof PasswordField) {
            textField.getStylesheets().add(PeekablePasswordFieldSkin.class.getResource("peekablepasswordfield.css").toExternalForm());
            textField.getStyleClass().addAll("withicon", "showing");

            textField.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    double vIconsLeftMargin = textField.getWidth() - 20;
                    final boolean vDoMask = event.getX() <= vIconsLeftMargin;
                    setDoMask(vDoMask);
                    setIcon(vDoMask);
                }
            });

            textField.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!_doMask) {
                        setDoMask(true);
                        setIcon(true);
                    }
                }
            });

            textField.setOnMouseMoved((MouseEvent event) -> {
                double vIconsLeftMargin = textField.getWidth() - 20;
                if (event.getX() > vIconsLeftMargin) {
                    textField.setCursor(Cursor.HAND);
                } else {
                    textField.setCursor(Cursor.TEXT);
                }
            });

            textField.setOnMouseExited((MouseEvent event) -> {
                setDoMask(true);
                setIcon(true);
            });
        }
    }

    /**
     * Allows switching from "masking" to "unmasking" mode
     *
     * @param pValue if true text must be masked
     */
    private void setDoMask(boolean pValue) {

        _doMask = pValue;

        TextField textField = this.getSkinnable();

        String vText = textField.getText();
        textField.setText(vText);

        setIcon(pValue);
    }

    /**
     * Sets the icon to use (either a simple eye or an eye with a slash)
     *
     * @param pShowing if true show the eye icon without slash
     */
    private void setIcon(boolean pShowing) {
        TextField textField = this.getSkinnable();
        textField.getStyleClass().removeAll("showing", "hiding");
        if (pShowing) {
            textField.getStyleClass().add("showing");
        } else {
            textField.getStyleClass().add("hiding");
        }
    }

    @Override
    protected String maskText(String txt) {
        if (_doMask) {
            return super.maskText(txt);
        } else {
            return txt;
        }
    }

}
