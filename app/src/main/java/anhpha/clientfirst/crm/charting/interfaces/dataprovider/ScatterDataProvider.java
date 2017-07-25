package anhpha.clientfirst.crm.charting.interfaces.dataprovider;

import anhpha.clientfirst.crm.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
