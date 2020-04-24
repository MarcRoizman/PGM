package tc.oc.pgm.util.chat;

import javax.annotation.Nullable;
import net.kyori.text.TextComponent;
import net.kyori.text.adapter.bukkit.TextAdapter;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import tc.oc.pgm.util.component.Component;
import tc.oc.pgm.util.component.ComponentRenderers;
import tc.oc.pgm.util.translation.ComponentRenderer;

/** An {@link Audience} that represents a virtual {@link CommandSender}. */
@FunctionalInterface
public interface VirtualAudience extends Audience {

  /**
   * Get the {@link CommandSender} of this audience.
   *
   * @return A command sender.
   */
  CommandSender getAudience();

  /**
   * Render a {@link net.kyori.text.Component} to be relevant to this audience.
   *
   * @param message A message.
   * @return A rendered message.
   */
  default net.kyori.text.Component renderMessage(net.kyori.text.Component message) {
    return ComponentRenderer.INSTANCE.render(message, getAudience().getLocale());
  }

  /**
   * Render a {@link net.kyori.text.Component} to be relevant to this audience.
   *
   * @param message A message.
   * @return A rendered message, as "legacy" Minecraft text.
   */
  default String renderMessageLegacy(net.kyori.text.Component message) {
    return LegacyComponentSerializer.legacy().serialize(renderMessage(message));
  }

  @Override
  default void sendMessage(net.kyori.text.Component message) {
    TextAdapter.sendComponent(getAudience(), renderMessage(message));
  }

  @Override
  default void sendWarning(net.kyori.text.Component message) {
    sendMessage(
        TextComponent.of(" \u26a0 ", TextColor.YELLOW).append(message).color(TextColor.RED));
    playSound(new Sound("note.bass", 1f, 0.75f));
  }

  @Override
  default void showHotbar(net.kyori.text.Component hotbar) {}

  @Override
  default void showBossbar(net.kyori.text.Component message, float progress) {}

  ///////////////////////////////////////////////////////////////
  // METHODS BELOW ARE ALL DEPRECATED AND WILL BE REMOVED SOON //
  ///////////////////////////////////////////////////////////////

  @Override
  default void sendMessage(String message) {
    getAudience().sendMessage(message);
  }

  @Override
  default void sendMessage(Component message) {
    ComponentRenderers.send(getAudience(), message);
  }

  @Override
  default void sendHotbarMessage(Component message) {}

  @Override
  default void showTitle(
      @Nullable Component title,
      @Nullable Component subtitle,
      int inTicks,
      int stayTicks,
      int outTicks) {}

  @Override
  default void playSound(Sound sound) {}
}
