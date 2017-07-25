package anhpha.clientfirst.crm.charting.interfaces.dataprovider;

import anhpha.clientfirst.crm.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}
