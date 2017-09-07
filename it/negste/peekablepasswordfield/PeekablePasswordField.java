package it.negste.peekablepasswordfield;

import javafx.scene.control.PasswordField;

/**
 * A password field that supports unmasking ("peeking") the typed password
 * @author negste
 */
public class PeekablePasswordField extends PasswordField {

    public PeekablePasswordField() {
        setSkin(new PeekablePasswordFieldSkin(this));
    }    

}
