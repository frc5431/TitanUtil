package frc.team5431.titan.core.misc

class Toggle {

    var state = false;
    private var prevButton = 0;

    fun isToggled(buttonState:Boolean):Boolean {
        if ((if (buttonState) 1 else 0) > prevButton)
        {
            state = !state;
        }

        this.prevButton = if(buttonState) 1 else 0;

        return state;
    }
}