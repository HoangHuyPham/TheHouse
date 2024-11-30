package engine.lighting;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Vector3f;

@SuperBuilder
@Setter
@Getter
public class PointLight extends Light{
    public static final PointLight RANGE_3250 = PointLight.builder().linear(0.0014f).quadratic(0.000007f).build();
    public static final PointLight RANGE_600 = PointLight.builder().linear(0.007f).quadratic(0.0002f).build();
    public static final PointLight RANGE_325 = PointLight.builder().linear(0.014f).quadratic(0.0007f).build();
    public static final PointLight RANGE_200 = PointLight.builder().linear(0.022f).quadratic(0.0019f).build();
    public static final PointLight RANGE_160 = PointLight.builder().linear(0.027f).quadratic(0.0028f).build();
    public static final PointLight RANGE_100 = PointLight.builder().linear(0.045f).quadratic(0.0075f).build();
    public static final PointLight RANGE_65 = PointLight.builder().linear(0.07f).quadratic(0.017f).build();
    public static final PointLight RANGE_20 = PointLight.builder().linear(0.22f).quadratic(0.2f).build();
    public static final PointLight RANGE_7 = PointLight.builder().linear(0.7f).quadratic(1.8f).build();
    private boolean isActive;
    @Builder.Default
    private Vector3f ambient = new Vector3f(1f);
    @Builder.Default float constant = 1f, linear = 0.7f, quadratic = 1.8f;

    @Override
    public void onTick() {}

    public void apply(PointLight pointLight){
        this.linear = pointLight.getLinear();
        this.constant = pointLight.getConstant();
        this.quadratic = pointLight.getQuadratic();
    }
}
