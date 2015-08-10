package ptrman.dificultyEnvironment.animation;

import ptrman.dificultyEnvironment.EntityDescriptor;
import ptrman.dificultyEnvironment.JavascriptDescriptor;

/**
 *
 */
public class DelayAnimationEvent extends AnimationEvent {
    private float resetValue;
    private float currentValue;

    public void reset() {
        currentValue = resetValue;
    }

    public void decrement(float timedelta) {
        currentValue -= timedelta;
    }

    @Override
    public boolean isFiring() {
        return currentValue < 0.0;
    }

    @Override
    public void fire(JavascriptDescriptor javascriptDescriptor, EntityDescriptor entityDescriptor) {
        reset();
    }
}
