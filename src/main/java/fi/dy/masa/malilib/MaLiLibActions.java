package fi.dy.masa.malilib;

import fi.dy.masa.malilib.action.Action;
import fi.dy.masa.malilib.action.ActionUtils;
import fi.dy.masa.malilib.action.NamedAction;
import fi.dy.masa.malilib.action.ParameterizedAction;
import fi.dy.masa.malilib.action.UtilityActions;
import fi.dy.masa.malilib.config.util.ConfigUtils;
import fi.dy.masa.malilib.gui.action.ActionPromptScreen;
import fi.dy.masa.malilib.gui.action.ActionWidgetScreen;
import fi.dy.masa.malilib.gui.edit.CustomIconListScreen;
import fi.dy.masa.malilib.gui.edit.MessageRedirectListScreen;
import fi.dy.masa.malilib.input.callback.AdjustableValueHotkeyCallback;
import fi.dy.masa.malilib.listener.EventListener;
import fi.dy.masa.malilib.overlay.message.MessageUtils;

public class MaLiLibActions
{
    public static final NamedAction OPEN_ACTION_PROMPT_SCREEN       = register("openActionPromptScreen", ActionPromptScreen::openActionPromptScreen);
    public static final NamedAction OPEN_CONFIG_SCREEN              = register("openConfigScreen", MaLiLibConfigScreen::open);
    public static final NamedAction SCROLL_VALUE_ADJUST_DECREASE    = register("scrollValueAdjustDecrease", AdjustableValueHotkeyCallback::scrollAdjustDecrease);
    public static final NamedAction SCROLL_VALUE_ADJUST_INCREASE    = register("scrollValueAdjustIncrease", AdjustableValueHotkeyCallback::scrollAdjustIncrease);

    public static void init()
    {
        register("addMessage", MessageUtils::addMessageAction);
        register("addToast", MessageUtils::addToastAction);
        register("createActionWidgetScreen", ActionWidgetScreen::openCreateActionWidgetScreen);
        register("loadAllConfigsFromFile", ConfigUtils::loadAllConfigsFromFileAction);
        register("openActionWidgetScreen", ActionWidgetScreen::openActionWidgetScreen);
        register("openCustomIconsListScreen", CustomIconListScreen::openCustomIconListScreenAction);
        register("openMessageRedirectsListScreen", MessageRedirectListScreen::openMessageRedirectListScreenAction);
        register("openPreviousActionWidgetScreen", ActionWidgetScreen::openPreviousActionWidgetScreen);
        register("switchConfigProfile", ConfigUtils::switchConfigProfile);

        register("cycleGameMode", UtilityActions::cycleGameMode);
        register("dropHeldStack", UtilityActions::dropHeldStack);
        register("runCommand", UtilityActions::runVanillaCommand);
        register("setPlayerPitch", UtilityActions::setPlayerPitch);
        register("setPlayerYaw", UtilityActions::setPlayerYaw);
        register("setSelectedHotbarSlot", UtilityActions::setSelectedHotbarSlot);
        register("takeScreenshot", UtilityActions::takeScreenshot);
        register("toggleChunkBorders", UtilityActions::toggleChunkBorders);
        register("toggleF3Screen", UtilityActions::toggleF3Screen);
        register("toggleF3ScreenLagometer", UtilityActions::toggleF3ScreenLagometer);
        register("toggleF3ScreenProfilerPieChart", UtilityActions::toggleF3ScreenProfilerPieChart);

        ActionUtils.registerBooleanConfigActions(MaLiLibConfigs.Generic.OPTIONS);
        ActionUtils.registerBooleanConfigActions(MaLiLibConfigs.Debug.OPTIONS);
    }

    private static NamedAction register(String name, EventListener action)
    {
        return ActionUtils.register(MaLiLibReference.MOD_INFO, name, action);
    }

    private static NamedAction register(String name, Action action)
    {
        return ActionUtils.register(MaLiLibReference.MOD_INFO, name, action);
    }

    private static NamedAction register(String name, ParameterizedAction action)
    {
        return ActionUtils.register(MaLiLibReference.MOD_INFO, name, action);
    }
}
