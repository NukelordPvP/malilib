package fi.dy.masa.malilib.gui.widget.list.entry.config;

import java.util.List;
import fi.dy.masa.malilib.config.group.PopupConfigGroup;
import fi.dy.masa.malilib.gui.BaseScreen;
import fi.dy.masa.malilib.gui.config.BaseConfigGroupEditScreen;
import fi.dy.masa.malilib.gui.config.ConfigWidgetContext;
import fi.dy.masa.malilib.gui.util.GuiUtils;
import fi.dy.masa.malilib.gui.widget.button.GenericButton;
import fi.dy.masa.malilib.gui.widget.list.entry.DataListEntryWidgetData;
import fi.dy.masa.malilib.render.text.StyledTextLine;

public class PopupConfigGroupWidget extends BaseConfigWidget<PopupConfigGroup>
{
    protected final PopupConfigGroup config;
    protected final GenericButton groupOpenButton;

    public PopupConfigGroupWidget(PopupConfigGroup config,
                                  DataListEntryWidgetData constructData,
                                  ConfigWidgetContext ctx)
    {
        super(config, constructData, ctx);

        this.config = config;
        this.groupOpenButton = GenericButton.create("malilib.button.config.popup_group.show_configs",
                                                    this::openConfigGroupEditScreen);
        this.groupOpenButton.getHoverInfoFactory().setTextLineProvider("config_list", this::getContainedConfigsHoverInfo);
        this.groupOpenButton.getHoverInfoFactory().setDynamic(false);

        this.getHoverInfoFactory().setTextLineProvider("config_list", this::getContainedConfigsHoverInfo);
        this.getHoverInfoFactory().setDynamic(false);
    }

    @Override
    public void reAddSubWidgets()
    {
        super.reAddSubWidgets();
        this.addWidget(this.groupOpenButton);
    }

    @Override
    public void updateSubWidgetPositions()
    {
        super.updateSubWidgetPositions();
        this.groupOpenButton.setPosition(this.getElementsStartPosition(), this.getY());
    }

    protected void openConfigGroupEditScreen()
    {
        BaseConfigGroupEditScreen screen = new BaseConfigGroupEditScreen(this.config.getModInfo(), null);
        screen.setScreenWidth(Math.max(520, GuiUtils.getScaledWindowWidth() - 80));
        screen.setConfigs(this.config.getConfigs());
        screen.setTitle(this.config.getDisplayName());
        screen.setParent(GuiUtils.getCurrentScreen());
        BaseScreen.openPopupScreen(screen);
    }

    protected List<StyledTextLine> getContainedConfigsHoverInfo()
    {
        return ExpandableConfigGroupWidget.getContainedConfigsHoverInfo(this.config.getConfigs());
    }
}
