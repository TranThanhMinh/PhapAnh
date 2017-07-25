package anhpha.clientfirst.crm.charting.interfaces.dataprovider;

import anhpha.clientfirst.crm.charting.components.YAxis.AxisDependency;
import anhpha.clientfirst.crm.charting.data.BarLineScatterCandleBubbleData;
import anhpha.clientfirst.crm.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends anhpha.clientfirst.crm.charting.interfaces.dataprovider.ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
