package fi.dy.masa.malilib.gui.widget.list.entry.config;

import fi.dy.masa.malilib.config.option.DoubleConfig;
import fi.dy.masa.malilib.gui.BaseScreen;
import fi.dy.masa.malilib.gui.config.ConfigWidgetContext;
import fi.dy.masa.malilib.gui.widget.DoubleTextFieldWidget;
import fi.dy.masa.malilib.gui.widget.list.entry.DataListEntryWidgetData;

public class DoubleConfigWidget extends NumericConfigWidget<Double, DoubleConfig>
{
    public DoubleConfigWidget(DoubleConfig config,
                              DataListEntryWidgetData constructData,
                              ConfigWidgetContext ctx)
    {
        super(config, constructData, ctx,
              DoubleConfig::setValueFromString, DoubleConfig::getStringValue);

        this.textField.setTextValidator(new DoubleTextFieldWidget.DoubleValidator(config.getMinDoubleValue(),
                                                                                  config.getMaxDoubleValue()));
        this.textField.translateAndAddHoverString("malilib.hover.config.numeric.range_and_default",
                                                  config.getMinDoubleValue(),
                                                  config.getMaxDoubleValue(),
                                                  config.getDefaultDoubleValue());
        this.sliderWidget.translateAndAddHoverString("malilib.hover.config.numeric.range_and_default",
                                                     config.getMinDoubleValue(),
                                                     config.getMaxDoubleValue(),
                                                     config.getDefaultDoubleValue());
    }

    @Override
    protected boolean onValueAdjustButtonClick(int mouseButton)
    {
        double amount = mouseButton == 1 ? -0.1 : 0.1;
        if (BaseScreen.isShiftDown()) { amount *= 20.0; }
        if (BaseScreen.isAltDown()) { amount *= 40.0; }

        this.config.setDoubleValue(this.config.getDoubleValue() + amount);
        this.updateWidgetState();

        return true;
    }
}

