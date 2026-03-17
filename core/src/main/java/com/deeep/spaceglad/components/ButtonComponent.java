package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;

public class ButtonComponent implements Component {
    public int type;
    public boolean isActive;

    public ButtonComponent(int type, boolean isActive) {
        this.type = type;
        this.isActive = isActive;
    }
}
