package fi.dy.masa.malilib.event.dispatch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.WorldClient;
import fi.dy.masa.malilib.config.util.ConfigOverrideUtils;
import fi.dy.masa.malilib.config.util.ConfigUtils;
import fi.dy.masa.malilib.event.ClientWorldChangeHandler;
import fi.dy.masa.malilib.render.overlay.OverlayRendererContainer;

public class ClientWorldChangeEventDispatcherImpl implements ClientWorldChangeEventDispatcher
{
    protected final List<ClientWorldChangeHandler> worldChangeHandlers = new ArrayList<>();

    public ClientWorldChangeEventDispatcherImpl()
    {
    }

    @Override
    public void registerClientWorldChangeHandler(ClientWorldChangeHandler listener)
    {
        if (this.worldChangeHandlers.contains(listener) == false)
        {
            this.worldChangeHandlers.add(listener);
            this.worldChangeHandlers.sort(Comparator.comparing(ClientWorldChangeHandler::getPriority));
        }
    }

    @Override
    public void unregisterClientWorldChangeHandler(ClientWorldChangeHandler listener)
    {
        this.worldChangeHandlers.remove(listener);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onWorldLoadPre(@Nullable WorldClient worldBefore, @Nullable WorldClient worldAfter)
    {
        if (worldBefore != null && worldAfter != null)
        {
            OverlayRendererContainer.INSTANCE.saveToFile(true);
        }

        if (this.worldChangeHandlers.isEmpty() == false)
        {
            for (ClientWorldChangeHandler listener : this.worldChangeHandlers)
            {
                listener.onPreClientWorldChange(worldBefore, worldAfter);
            }
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onWorldLoadPost(@Nullable WorldClient worldBefore, @Nullable WorldClient worldAfter)
    {
        // Save all the configs when exiting a world
        if (worldBefore != null && worldAfter == null)
        {
            this.onExitWorld();
        }
        // (Re-)Load all the configs from file when entering a world
        else if (worldBefore == null && worldAfter != null)
        {
            this.onEnterWorld();
        }
        else if (worldBefore != null && worldAfter != null)
        {
            OverlayRendererContainer.INSTANCE.loadFromFile(true);
        }

        if (this.worldChangeHandlers.isEmpty() == false)
        {
            for (ClientWorldChangeHandler listener : this.worldChangeHandlers)
            {
                listener.onPostClientWorldChange(worldBefore, worldAfter);
            }
        }
    }

    protected void onExitWorld()
    {
        ConfigOverrideUtils.resetConfigOverrides();
        ConfigUtils.saveAllConfigsToFileIfDirty();
    }

    protected void onEnterWorld()
    {
        ConfigUtils.loadAllConfigsFromFile();
        OverlayRendererContainer.INSTANCE.loadFromFile(false);
        OverlayRendererContainer.INSTANCE.resetRenderTimeout();
        ConfigOverrideUtils.applyConfigOverrides();
    }
}
