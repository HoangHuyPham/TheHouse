package engine.scene;

import lombok.Builder;

@Builder
public class EScene implements IScene{
    private ISceneAction sceneAction;
    @Override
    public void render() {
        sceneAction.perform();
    }
}
