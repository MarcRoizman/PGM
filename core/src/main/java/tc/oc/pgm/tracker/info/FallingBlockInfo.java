package tc.oc.pgm.tracker.info;

import javax.annotation.Nullable;
import org.bukkit.Material;
import org.bukkit.entity.FallingBlock;
import tc.oc.pgm.api.player.ParticipantState;
import tc.oc.pgm.api.tracker.info.DamageInfo;
import tc.oc.pgm.util.component.Component;
import tc.oc.pgm.util.component.types.PersonalizedTranslatable;
import tc.oc.pgm.util.translation.MinecraftKeys;

public class FallingBlockInfo extends EntityInfo implements DamageInfo {

  private final Material material;

  public FallingBlockInfo(FallingBlock entity, @Nullable ParticipantState owner) {
    super(entity, owner);
    this.material = entity.getMaterial();
  }

  public FallingBlockInfo(FallingBlock entity) {
    this(entity, null);
  }

  @Override
  public @Nullable ParticipantState getAttacker() {
    return getOwner();
  }

  public Material getMaterial() {
    return material;
  }

  @Override
  public Component getLocalizedName() {
    return new PersonalizedTranslatable(MinecraftKeys.getMaterial(getMaterial()));
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "{world="
        + getMaterial()
        + " name="
        + getCustomName()
        + " owner="
        + getOwner()
        + "}";
  }
}
