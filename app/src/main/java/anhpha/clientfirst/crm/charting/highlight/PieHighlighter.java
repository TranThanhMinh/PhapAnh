package anhpha.clientfirst.crm.charting.highlight;

import anhpha.clientfirst.crm.charting.charts.PieChart;
import anhpha.clientfirst.crm.charting.data.Entry;
import anhpha.clientfirst.crm.charting.interfaces.datasets.IPieDataSet;

/**
 * Created by philipp on 12/06/16.
 */
public class PieHighlighter extends PieRadarHighlighter<PieChart> {

    public PieHighlighter(PieChart chart) {
        super(chart);
    }

    @Override
    protected anhpha.clientfirst.crm.charting.highlight.Highlight getClosestHighlight(int index, float x, float y) {

        IPieDataSet set = mChart.getData().getDataSet();

        final Entry entry = set.getEntryForIndex(index);

        return new anhpha.clientfirst.crm.charting.highlight.Highlight(index, entry.getY(), x, y, 0, set.getAxisDependency());
    }
}
