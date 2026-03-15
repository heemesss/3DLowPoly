package com.deeep.spaceglad.components;

import com.badlogic.ashley.core.Component;

public class ButtonComponent implements Component {
    public int type;

    public ButtonComponent(int type) {
        this.type = type;
    }
}
